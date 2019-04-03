package com.listener;

import java.util.HashSet;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.model.Book;
import com.model.Execution;
import com.model.FinantialInstrument;
import com.model.Order;
import com.model.enums.OrderEnum;
import com.model.enums.OrderStatusEnum;
import com.service.BookService;
import com.service.FinantialInstrumentService;
import com.service.OrderService;

/**
 * @author Pontalti X
 *
 */
@Component
public class BrookerListener {

	private final static Logger logger = LoggerFactory.getLogger(BrookerListener.class);

	@Autowired
	@Qualifier("OrderService")
	private OrderService<Order> orderService;

	@Autowired
	@Qualifier("BookService")
	private BookService<Book> bookService;
	
	@Autowired
	@Qualifier("FinantialInstrumentService")
	private FinantialInstrumentService<FinantialInstrument> fService;

	public BrookerListener() {
		super();
	}

	@EventListener(FinantialInstrument.class)
	public void onInstrumentChangeEvent(FinantialInstrument instrument) {
		logger.info("instrument name = " + instrument.getName());
	}

	@EventListener(Order.class)
	public void onOrderCreateEvent(Order order) {
		Order o = this.orderService.findById(order.getId());
		Hibernate.initialize(o.getBook().getOrders());
		if (OrderEnum.BUY.equals(o.getType())) {
			if (o.getOrderPrice() > o.getInstrumentPrice()) {
				cancelOrder(o);
				logger.info("CANCELED BUY - Order price greater than instrument price -> {}", o.getId());
			}else if( o.getQuantity() > o.getBook().getInstrument().getQuantity() ) {
				cancelOrder(o);
				logger.info("CANCELED BUY - Instrument quantity greater than order quantity -> {}", o.getId());
			}else {
				o.getBook().getInstrument().setQuantity(o.getBook().getInstrument().getQuantity() - o.getQuantity());
				o.setStatus(OrderStatusEnum.EXECUTED);
				this.fService.saveOrUpdate(o.getBook().getInstrument());
				logger.info("EXECUTED BUY -> {}", o.getId());
			}
		} else if (OrderEnum.SELL.equals(o.getType())) {
			Integer total = o.getBook()
								.getOrders()
								.stream()
								.filter(
										b-> b.getType().equals(OrderEnum.BUY) && 
											b.getStatus().equals(OrderStatusEnum.EXECUTED))
								.mapToInt(b -> b.getQuantity())
								.sum();
			if (o.getOrderPrice() < o.getInstrumentPrice()) {
				cancelOrder(o);
				logger.info("CANCELED SELL - Order price lower than instrument price {}", order.getId());
			}else if( o.getQuantity() > total ) {
				cancelOrder(o);
				logger.info("CANCELED SELL - Order quantity greater than total quantity -> {}", o.getId());
			}else {
				Integer t = o.getBook().getInstrument().getQuantity() + o.getQuantity();
				o.setStatus(OrderStatusEnum.EXECUTED);
				o.getBook().getInstrument().setQuantity(t);
				this.fService.saveOrUpdate(o.getBook().getInstrument());
				logger.info("EXECUTED SELL -> {}", o.getId());
			}
		} else {
			o.setStatus(OrderStatusEnum.CANCELED);
			cancelOrder(o);
			logger.info("Invalid order identified -> {}", order.getId());
		}
		buildExecution(o);
		this.orderService.saveOrUpdate(o);
	}

/**
	@EventListener(Order.class)
	public void onOrderCreateEvent(Order order) {
		Order o = this.orderService.findById(order.getId());
		if (OrderEnum.BUY == o.getType()) {
			if ((o.getOrderPrice() > o.getInstrumentPrice())
					|| (o.getBook().getInstrument().getQuantity() < o.getQuantity())) {
				cancelOrder(o);
				logger.info("Order price greater than instrument price -> {}", o.getId());
			}else {
				o.getBook().getInstrument().setQuantity(o.getBook().getInstrument().getQuantity() - o.getQuantity());
				o.setStatus(OrderStatusEnum.EXECUTED);
				this.fService.saveOrUpdate(o.getBook().getInstrument());
			}
		} else if (OrderEnum.SELL == o.getType()) {
			Integer total = o.getBook()
								.getOrders()
								.stream()
								.filter(
										b-> b.getType().equals(OrderEnum.BUY) && 
											b.getStatus().equals(OrderStatusEnum.EXECUTED))
								.mapToInt(b -> b.getQuantity())
								.sum();
			if ((o.getOrderPrice() < o.getInstrumentPrice()) || 
					(o.getBook().getInstrument().getQuantity() > o.getQuantity()) ||
						o.getQuantity() > total ) {
				cancelOrder(o);
				logger.info("Order price lower than instrument price {}", order.getId());
			}else {
				Integer t = o.getBook().getInstrument().getQuantity() + o.getQuantity();
				o.setStatus(OrderStatusEnum.EXECUTED);
				o.getBook().getInstrument().setQuantity(t);
				this.fService.saveOrUpdate(o.getBook().getInstrument());
			}
		} else {
			o.setStatus(OrderStatusEnum.CANCELED);
			cancelOrder(o);
			this.orderService.saveOrUpdate(o);
			logger.info("Invalid order identified -> {}", order.getId());
		}
		buildExecution(o);
		this.orderService.saveOrUpdate(o);
	}
*/
	private void cancelOrder(final Order o) {
		o.setOrderPrice(0.0);
		o.setStatus(OrderStatusEnum.CANCELED);
	}

	private void buildExecution(final Order o) {
		Execution execution = new Execution();
		execution.setOrder(o);
		execution.setQuantity(o.getQuantity());
		execution.setStatus(o.getStatus());
		Hibernate.initialize(o.getExecutions());
		if( null == o.getExecutions() ) {
			o.setExecutions(new HashSet<Execution>());
		}
		o.getExecutions().add(execution);
	}

}
