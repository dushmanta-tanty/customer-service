package com.example.customerservice.services;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.customerservice.entity.Customer;
import com.example.customerservice.exceptions.CustomerNotFoundException;
import com.example.customerservice.exceptions.InvalidPasswordException;
import com.example.customerservice.exceptions.RecordExistException;
import com.example.customerservice.repos.CustomerRepository;

public class CustomerServiceTests {

	@Mock
	CustomerRepository customerRepository;
	
	@InjectMocks
	CustomerService customerService;
	
	@MockBean
	Customer customer;
	
	Optional<Customer> options;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customer = new Customer(1l, "Sree", "Moha", "sree420@gmail.com", new Date("1990-01-10"), "sree420");
        options = Optional.of(customer);
	}
	
	@Test
	public void itShouldInsertCustomer() throws RecordExistException, InvalidPasswordException {
		when(customerRepository.save(any())).thenReturn(customer);
		Customer actualCustomer = customerService.insertCustomer(customer);
		assertEquals(customer, actualCustomer);
	}
	
	@Test
	public void itShouldThrowRecordExistException() throws RecordExistException, InvalidPasswordException {
		when(customerRepository.save(any())).thenThrow(new RecordExistException("Customer Exist"));
		try {
			customerService.insertCustomer(customer);
		} catch (Exception exp) {
			assertEquals("Customer Exist", exp.getMessage());
		}
	}
	
	@Test
	public void itShouldThrowInvalidPasswordException() throws RecordExistException, InvalidPasswordException {
		when(customerRepository.save(any())).thenThrow(new InvalidPasswordException("Invalid password"));
		try {
			customerService.insertCustomer(customer);
		} catch (Exception exp) {
			assertEquals("Invalid password", exp.getMessage());
		}
	}
	
	@Test
	public void itShouldFetchCustomer() throws CustomerNotFoundException {
		when(customerRepository.findById(any())).thenReturn(options);
		Customer actualCustomer = customerService.getCustomerInfo(customer.getId().longValue());
		assertEquals(customer, actualCustomer);
	}
}
