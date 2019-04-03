package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Teste Graceful Shutdown Spring Boot Applications")
@RestController
public class LongProcessController {

	@ApiOperation(value = "Create a long process to test Graceful Shutdown Spring Boot Applications .")
	@RequestMapping("/long-process")
	public String pause() throws InterruptedException {
		Thread.sleep(10000);
		return "Process finished";
	}
}
