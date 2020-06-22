package com.example.customerservice.resources;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.customerservice.entity.Customer;
import com.example.customerservice.exceptions.CustomerNotFoundException;
import com.example.customerservice.exceptions.InvalidPasswordException;
import com.example.customerservice.exceptions.RecordExistException;
import com.example.customerservice.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CustomerControllerTests {

	@Autowired
    private MockMvc mockMvc;
	
	
	@MockBean
	CustomerService customerService;
	
	@InjectMocks
	CustomerController customerController;
	
	Customer customer;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customer = new Customer(1l, "Sree", "Moha", "sree420@gmail.com", new Date("1990-01-10"), "sree420");
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}
	
	@Test
    public void itShouldReturnCustomer() throws Exception {
		when(customerService.getCustomerInfo(customer.getId())).thenReturn(customer);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
    public void itShouldHandleException() throws Exception {
        when(customerService.getCustomerInfo(customer.getId())).thenThrow(CustomerNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
	
	@Test
    public void itShoudlInsertCustomerRecord() throws Exception {
		when(customerService.insertCustomer(any())).thenReturn(customer);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(customer)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
	}
	
	@Test
    public void itShouldThrowInvalidPasswordException() throws Exception {
		when(customerService.insertCustomer(any())).thenThrow(InvalidPasswordException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(customer)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andDo(MockMvcResultHandlers.print());
	}
    
	@Test
    public void itShouldThrowRecordExistException() throws Exception {
		when(customerService.insertCustomer(any())).thenThrow(RecordExistException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(customer)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andDo(MockMvcResultHandlers.print());
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @description: helps to convert an object into a JSON 
	 */
	private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
