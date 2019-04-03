package com.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.builder.StatisticBuilder;
import com.dto.BookDto;
import com.dto.OrderDto;
import com.dto.StatisticDto;
import com.model.Book;
import com.model.Execution;
import com.model.FinantialInstrument;
import com.model.Order;
import com.model.enums.BookEnum;
import com.model.enums.OrderEnum;
import com.model.enums.OrderStatusEnum;
import com.service.BookService;
import com.service.ExecutionService;
import com.service.FinantialInstrumentService;
import com.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController()
@Api(value = "Broker")
public class BrokerController {

	private final ApplicationEventPublisher eventPublisher;
	
	@Autowired
	@Qualifier("FinantialInstrumentService")
	private FinantialInstrumentService<FinantialInstrument> fService;
	
	@Autowired
	@Qualifier("BookService")
	private BookService<Book> bookService;
	
	@Autowired
	@Qualifier("OrderService")
	private OrderService<Order> orderService;
	
	@Autowired
	@Qualifier("ExecutionService")
	private ExecutionService<Execution> execution;
	
	public BrokerController(ApplicationEventPublisher eventPublisher) {
		super();
		this.eventPublisher = eventPublisher;
	}
	
	@ApiOperation(value = "Retrieve all Finantial Instruments availables.")
	@GetMapping(path = "/listAllFinantialInstrument", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<List<FinantialInstrument>> getListAllFinantialInstrument() {
		ResponseEntity<List<FinantialInstrument>> response = new ResponseEntity<List<FinantialInstrument>>(this.fService.findAll(), HttpStatus.OK);
		return response;
	}
	
	@ApiOperation(value = "Retrieve all books by status.")
	@GetMapping(path = "/listAllBooksByStatus/{status}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<List<Book>> getListAllBooksByStatus(@PathVariable("status") BookEnum status) {
		ResponseEntity<List<Book>> response = new ResponseEntity<List<Book>>(this.bookService.retrieveByStatus(status), HttpStatus.OK);
		return response;
	}
	
	@ApiOperation(value = "Retrieve all books availables.")
	@GetMapping(path = "/listAllBooks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<List<Book>> getListAllBooks() {
		ResponseEntity<List<Book>> response = new ResponseEntity<List<Book>>(this.bookService.findAll(), HttpStatus.OK);
		return response;
	}
	
	@ApiOperation(value = "Retrieve Order statistics.")
	@GetMapping(path = "/statistics", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<List<StatisticDto>> getStatistics() {
		List<Object[]> ret = this.fService.retrieveStatistics();
		List<StatisticDto> s = new ArrayList<StatisticDto>();
		StatisticBuilder builder = new StatisticBuilder();
		ret.forEach(o->{
			Object[] objectArray = (Object[]) o;
			builder
					.setId((String)objectArray[0])
					.setStatus(((OrderStatusEnum)objectArray[1]).name())
					.setType(((OrderEnum)objectArray[2]).name())
					.setTotal(((Long)objectArray[3]).intValue())
					.setMinQuantity((Integer)objectArray[4])
					.setMaxQuantity((Integer)objectArray[5])
					.setAvgQuantity((Double)objectArray[6])
					.setMinInstrumentPrice((Double)objectArray[7])
					.setMaxInstrumentPrice((Double)objectArray[8])
					.setAvgInstrumentPrice((Double)objectArray[9])
					.setMinLimitPrice((Double)objectArray[10])
					.setMaxLimitPrice((Double)objectArray[11])
					.setAvgLimitPrice((Double)objectArray[12])
					.setMinOrderPrice((Double)objectArray[13])
					.setMaxOrderPrice((Double)objectArray[14])
					.setAvgOrderPrice((Double)objectArray[15]);
			s.add(builder.build());
		});
		
		ResponseEntity<List<StatisticDto>> response = new ResponseEntity<List<StatisticDto>>(s, HttpStatus.OK);
		return response;
	}
	
	@ApiOperation(value = "Retrieve all Orders from book by book id.")
	@GetMapping(path = "/listOrders/book/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Set<Order>> getListOrdersByBookId(@PathVariable("id") Long id) {
		ResponseEntity<Set<Order>> response = null;
		try {
			Book book = this.bookService.findById(id);
			if( null != book ) {
				Hibernate.initialize(book.getOrders());
				response = new ResponseEntity<Set<Order>>(book.getOrders(), HttpStatus.OK);
			}else {
				response = new ResponseEntity<Set<Order>>(HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<Set<Order>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@ApiOperation(value = "Retrieve an Order by id.")
	@GetMapping(path = "/order/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
		ResponseEntity<Order> response = null;
		try {
			Order order = this.orderService.findById(id);
			if( null != order ) {
				response = new ResponseEntity<Order>(order, HttpStatus.OK);
			}else {
				response = new ResponseEntity<Order>(HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<Order>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@ApiOperation(value = "Create and open book.")
	@PostMapping(path = "/create/book/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Book> createBook(@RequestBody BookDto book) {
		ResponseEntity<Book> response = null;
		try {
			if( null == book.getInstrumentId() ) {
				response = new ResponseEntity<Book>(HttpStatus.BAD_REQUEST);
				return response;
			}
			Book b = new Book();
			FinantialInstrument fi =  this.fService.findById(book.getInstrumentId());
			if( null == fi ) {
				response = new ResponseEntity<Book>(HttpStatus.BAD_REQUEST);
				return response;
			}
			b.setInstrument(fi);
			b.setStatus(BookEnum.OPEN);
			b = this.bookService.saveOrUpdate(b);
			fi.setBook(b);
			this.fService.saveOrUpdate(fi);
			response = new ResponseEntity<Book>(b, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@ApiOperation(value = "Close book.")
	@PutMapping(path = "/update/book/{id}/status/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Book> updateBookStatus(@PathVariable("id") Long id) {
		ResponseEntity<Book> response = null;
		try {
			if( null == id ) {
				response = new ResponseEntity<Book>(HttpStatus.BAD_REQUEST);
				return response;
			}
			Book book = this.bookService.findById(id);
			if( null == book  ) {
				response = new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
				return response;
			}
			book.setStatus(BookEnum.CLOSE);
			Book b = this.bookService.saveOrUpdate(book);
			response = new ResponseEntity<Book>(b, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@ApiOperation(value = "Create order.")
	@PostMapping(path = "/create/order/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Order> createOrder(@RequestBody OrderDto order) {
		ResponseEntity<Order> response = null;
		try {
			Book book = this.bookService.findById(order.getBookId());
			
			Order o = new Order();
			o.setBook(book);
			o.setEntryDate(LocalDate.now());
			o.setStatus(OrderStatusEnum.IN_PROGRESS);
			o.setInstrumentPrice(order.getInstrumentPrice());
			o.setLimitPrice(order.getLimitPrice());
			o.setOrderPrice(order.getOrderPrice());
			o.setQuantity(order.getQuantity());
			
			if(null != order.getType()) {
				if( order.getType().equals(OrderEnum.BUY) ) {
					o.setType(OrderEnum.BUY);
				}else if( order.getType().equals(OrderEnum.SELL) ) {
					o.setType(OrderEnum.SELL);
				}
			}else {
				response = new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
				return response;
			}
			o = this.orderService.saveOrUpdate(o);
			Hibernate.initialize(o.getBook().getInstrument().getOrders());
			if( null == o.getBook().getInstrument().getOrders()  ) {
				o.getBook().getInstrument().setOrders(new HashSet<Order>());
			}
			o.getBook().getInstrument().getOrders().add(o);
			this.fService.saveOrUpdate(o.getBook().getInstrument());
			this.eventPublisher.publishEvent(o);
			response = new ResponseEntity<Order>(o, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<Order>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@ApiOperation(value = "Update order status.")
	@PutMapping(path = "/update/order/{id}/status/{status}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Order> updateOrderStatus(@PathVariable("id") Long id, @PathVariable("status") OrderStatusEnum status) {
		ResponseEntity<Order> response = null;
		try {
			if( null == id ) {
				response = new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
				return response;
			}else if( null == status ) {
				response = new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
				return response;
			}
			Order order = this.orderService.findById(id);
			if( null == order  ) {
				response = new ResponseEntity<Order>(HttpStatus.INTERNAL_SERVER_ERROR);
				return response;
			}
			order.setStatus(status);
			Order o = this.orderService.saveOrUpdate(order);
			response = new ResponseEntity<Order>(o, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<Order>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
}
