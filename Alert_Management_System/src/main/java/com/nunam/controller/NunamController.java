package com.nunam.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nunam.entity.Customer;
import com.nunam.service.CustomerService;

@RestController
@RequestMapping("/api")
public class NunamController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        Customer registeredCustomer = customerService.registerCustomer(customer);
        return new ResponseEntity<>(registeredCustomer,HttpStatus.CREATED);
    }

    @GetMapping("/auth")
    public ResponseEntity<Customer> loginCustomerHandler( Authentication auth) {
    	System.out.println(auth);
        Customer cust  = customerService.findByEmail(auth.getName());
        return new ResponseEntity<>(cust,HttpStatus.OK);
    }
   


    
}


