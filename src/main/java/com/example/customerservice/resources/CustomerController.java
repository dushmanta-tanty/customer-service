package com.example.customerservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.customerservice.entity.Customer;
import com.example.customerservice.exceptions.CustomerNotFoundException;
import com.example.customerservice.exceptions.InvalidPasswordException;
import com.example.customerservice.exceptions.RecordExistException;
import com.example.customerservice.services.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin(value = "*")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@GetMapping(value ="/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) 
                                                    throws CustomerNotFoundException {
		try {
			Customer entity = customerService.getCustomerInfo(id);			 
	        return new ResponseEntity<Customer>(entity, new HttpHeaders(), HttpStatus.OK);
		} catch (CustomerNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
    }
 
    @PostMapping("/")
    public ResponseEntity<?> insertCustomerData(@RequestBody Customer customer) {
    	try {
    		Customer entity = customerService.insertCustomer(customer);		 
	        return new ResponseEntity<Customer>(entity, new HttpHeaders(), HttpStatus.CREATED);
		} catch (InvalidPasswordException | RecordExistException e) {
			return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
    }
}
