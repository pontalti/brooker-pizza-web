package com;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.dto.BookDto;
import com.dto.OrderDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.enums.OrderEnum;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = MainApp.class)
@AutoConfigureMockMvc 
@AutoConfigureTestDatabase
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpringContextIntegrationTest {

	@Autowired
    private MockMvc mvc;
	
	public SpringContextIntegrationTest() {
		super();
	}
	
    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
    
    @ParameterizedTest(name = "Retrieve all Finantial Instruments availables.")
	@DisplayName("Retrieve all Finantial Instruments availables.")
	@Tag("Important")
    @CsvSource({"1"})
	public void test_1() {
    	try {
			mvc.perform(get("/listAllFinantialInstrument"))
				.andDo(print())
				.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	@ParameterizedTest(name = "Book creation for params''{0}'', ''{1}''")
	@DisplayName("Testing book creation")
	@CsvSource({"1,2"})
	@Tag("Important")
	public void test_2(Long instrumentId_1, Long instrumentId_2) {
		
		try {
			BookDto bookDto = new BookDto();
			bookDto.setInstrumentId(instrumentId_1);
			mvc.perform(post("/create/book/").content(toJson(bookDto)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

			bookDto.setInstrumentId(instrumentId_2);
			mvc.perform(post("/create/book/").content(toJson(bookDto)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@ParameterizedTest(name = "Order creation for params ''{0}'', ''{1}'', ''{2}'', ''{4}'', ''{5}''")
	@DisplayName("Testing Order creation")
	@CsvSource({"2,10,10,10,8,BUY", "2,10,10,10,2,SELL", "2,10,10,10,3,SELL", "2,10,10,10,1000,BUY", "2,10,10,10,5222,SELL",
				"1,10,10,10,8,BUY", "1,10,10,10,2,SELL", "1,10,10,10,3,SELL", "1,10,10,10,1000,BUY", "1,10,10,10,5222,SELL"})
	@Tag("Important")
	public void test_3(	Long bookId, Double instrumentPrice, Double limitPrice, Double orderPrice, Integer quantity, String type) {
		OrderDto order = new OrderDto();
		order.setBookId(bookId);
		order.setInstrumentPrice(instrumentPrice);
		order.setLimitPrice(limitPrice);
		order.setOrderPrice(orderPrice);
		order.setQuantity(quantity);
		if( null != type ) {
			if( type.equals(OrderEnum.BUY.name()) ) {
				order.setType(OrderEnum.BUY);
			}else if( type.equals(OrderEnum.SELL.name()) ) {
				order.setType(OrderEnum.SELL);
			}
		}
		try {
			mvc.perform(post("/create/order/").content(toJson(order)).contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    @ParameterizedTest(name = "Retrieve all Orders from book by book id ''{0}''.")
	@DisplayName("Retrieve all Orders from book by book id.")
	@Tag("Important")
    @CsvSource({"1","2"})
	public void test_4(Long bookId) {
    	try {
			mvc.perform(get("/listOrders/book/"+bookId))
				.andDo(print())
				.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    @ParameterizedTest(name = "Retrieve Order statistics.")
	@DisplayName("Retrieve Order statistics.")
	@Tag("Important")
    @CsvSource({"1"})
	public void test_5() {
    	try {
			mvc.perform(get("/statistics"))
				.andDo(print())
				.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    @ParameterizedTest(name = "Close book. ''{0}''.")
	@DisplayName("Close book.")
	@Tag("Important")
    @CsvSource({"1","2"})
	public void test_6(Long bookId) {
    	try {
			mvc.perform(put("/update/book/"+bookId+"/status/"))
				.andDo(print())
				.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    @ParameterizedTest(name = "Update order status. ''{0}''.")
	@DisplayName("Update order status")
	@Tag("Important")
    @CsvSource({"1,CANCELED","2,CANCELED"})
	public void test_6(Long orderId, String status) {
    	try {
			mvc.perform(put("/update/order/"+orderId+"/status/"+status))
				.andDo(print())
				.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected static byte[] toJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
    
}
