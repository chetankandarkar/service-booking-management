package com.cognizant.products.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelTest {

	AppProduct product = new AppProduct();
	Message message = new Message();
	
	@Test
	void testAppProduct() {
		product.setId(1);
		product.setMake("Test");
		product.setProductImageUrl("testUrl");
		product.setModel("Test Model");
		product.setCreatedBy("Zaid Khan");
		product.setCost(3000);
		
		
		assertEquals(product.getCost(), 3000);
		assertEquals(product.getMake(), "Test");
		assertEquals(product.getProductImageUrl(),"testUrl");
		assertEquals(product.getModel(), "Test Model");
		assertEquals(product.getCreatedBy(), "Zaid Khan");
	}
	
	@Test
	void testMessage() {
		message.setMessage("This is a test");
		message.setPayload("Test");
		message.setStatus(200);
		
		assertEquals(message.getMessage(), "This is a test");
		assertEquals(message.getPayload(), "Test");
		assertEquals(message.getStatus(), 200);
	}
	
}
