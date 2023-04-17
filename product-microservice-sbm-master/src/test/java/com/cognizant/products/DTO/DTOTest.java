package com.cognizant.products.DTO;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DTOTest {

	AppProductDTO appProductDTO = new AppProductDTO();
	ValidatingDTO validatingDTO = new ValidatingDTO();
	
	@Test
	void testAppProductDTO() {
		appProductDTO.setCost(3000);
		appProductDTO.setMake("Test");
		appProductDTO.setModel("Test Model");
		appProductDTO.setName("Testing");
		appProductDTO.setProductImageUrl("testUrl");
		
		assertEquals(appProductDTO.getCost(),3000);
		assertEquals(appProductDTO.getMake(),"Test");
		assertEquals(appProductDTO.getModel(),"Test Model");
		assertEquals(appProductDTO.getName(),"Testing");
		assertEquals(appProductDTO.getProductImageUrl(), "testUrl");
	}
	
	@Test
	void testValidatingDTO() {
		validatingDTO.setEmail("test@test.com");
		validatingDTO.setUserRole("ROLE_USER");
		validatingDTO.setValidStatus(true);
		
		assertTrue(validatingDTO.isValidStatus());
		assertEquals(validatingDTO.getEmail(), "test@test.com");
		assertEquals(validatingDTO.getUserRole(), "ROLE_USER");
	}
	
}
