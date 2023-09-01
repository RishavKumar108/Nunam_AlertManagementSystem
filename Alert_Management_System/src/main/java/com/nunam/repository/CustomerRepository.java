package com.nunam.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nunam.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Optional<Customer> findByEmail(String email);

	Optional<Customer> findByPhone(String phone);

}
