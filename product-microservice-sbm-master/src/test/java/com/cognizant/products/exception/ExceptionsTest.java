package com.cognizant.products.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExceptionsTest {

	InvalidUserAccessException userAccessException;
	ProductNotFoundException productNotFoundException;
	
	@Test
	void testExceptionClassLoad() {
		assertThat(userAccessException).isNull();
		assertThat(productNotFoundException).isNull();
	}
	
	
}
