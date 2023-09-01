package com.nunam.service;

import com.nunam.entity.Customer;

public interface CustomerService {

	public Customer registerCustomer(Customer customer);
	
	public Customer findByEmail(String email);
	
	public Customer findByPhone(String phone);
	
	public Customer findbyId(Long id);
}
