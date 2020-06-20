package com.example.customerservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customerservice.entity.Customer;
import com.example.customerservice.exceptions.CustomerNotFoundException;
import com.example.customerservice.exceptions.InvalidPasswordException;
import com.example.customerservice.exceptions.RecordExistException;
import com.example.customerservice.repos.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepos;
	
	
	public Customer insertCustomer(Customer customer) throws RecordExistException, InvalidPasswordException {
		Optional<Customer> receord = customerRepos.findByEmail(customer.getEmail());
		if (receord.isPresent()) {
			throw new RecordExistException("Customer already exists");
		} else {
			if (validateCustomerReq(customer)) {
				return customerRepos.save(customer);
			}
		}
		return receord.get();
	}

	public Customer getCustomerInfo(Long id) throws CustomerNotFoundException {
		try {
			Optional<Customer> customer = customerRepos.findById(id);
			if (customer.isPresent()) {
				return customer.get();
			} else {
				throw new CustomerNotFoundException("No customer found with :: " + id);
			}
		} catch (Exception e) {
			throw new CustomerNotFoundException("No customer found with :: " + id);
		}
	}
	
	private boolean validateCustomerReq(Customer customer) throws InvalidPasswordException {
		if (customer.getPassword().length() >= 8) {
			return true;
		} else {
			throw new InvalidPasswordException("Minimum length should be 8");
		}
	}
}
