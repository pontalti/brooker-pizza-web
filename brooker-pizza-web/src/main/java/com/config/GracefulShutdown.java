package com.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @author Pontalti X
 *
 */
public class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

	private final static Logger logger = LoggerFactory.getLogger(GracefulShutdown.class);

	private static final int TIMEOUT = 30;

	private volatile Connector connector;

	public GracefulShutdown() {
		super();
	}

	@Override
	public void customize(Connector connector) {
		this.connector = connector;
	}

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		this.connector.pause();
		Executor executor = this.connector.getProtocolHandler().getExecutor();
		if (executor instanceof ThreadPoolExecutor) {
			try {
				ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
				threadPoolExecutor.shutdown();
				if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
					logger.warn("Tomcat thread pool did not shut down gracefully within " + TIMEOUT
							+ " seconds. Proceeding with forceful shutdown");

					threadPoolExecutor.shutdownNow();

					if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
						logger.error("Tomcat thread pool did not terminate");
					}
				}
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

}
