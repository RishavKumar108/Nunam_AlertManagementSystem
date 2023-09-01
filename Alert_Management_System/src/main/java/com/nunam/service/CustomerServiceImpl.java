package com.nunam.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nunam.entity.Customer;
import com.nunam.exception.CustomerException;
import com.nunam.exception.CustomerNotFoundException;
import com.nunam.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Customer registerCustomer(Customer customer)  {
		// TODO Auto-generated method stub
		Optional<Customer> cust = customerRepository.findByEmail(customer.getEmail());
		if(cust.isEmpty()) {
			customer.setPassword(passwordEncoder.encode(customer.getPassword()));
			return customerRepository.save(customer);
		}else {
			throw new CustomerException("Customer is already register with this email : "+customer.getEmail());
		}
	}

	@Override
	public Customer findByEmail(String email) {
		// TODO Auto-generated method stub
		return   customerRepository.findByEmail(email).orElseThrow(()-> new CustomerNotFoundException("Customer Not Found with this email "+ email));
	     
	}

	@Override
	public Customer findByPhone(String phone) {
		
		return   customerRepository.findByPhone(phone).orElseThrow(()-> new CustomerNotFoundException("Customer Not Found with this phone "+ phone));
	}

	@Override
	public Customer findbyId(Long id) {
		// TODO Auto-generated method stub
		return   customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("Customer Not Found with this id "+ id));
	}

	
	

}
