package com.example.customerservice.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;


public class CustomerTests {

	@MockBean
	Customer classUnderTest;
	
	@BeforeEach
	public void setUpBasics() throws Exception {

		MockitoAnnotations.initMocks(this);
		classUnderTest = new Customer();
		classUnderTest.setId(1l);
	}

	@Test
	public void itShouldCreateCustomerObject() {
		assertEquals(1l, classUnderTest.getId().longValue());
	}
}
