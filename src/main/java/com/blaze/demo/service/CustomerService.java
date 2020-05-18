package com.blaze.demo.service;

import com.blaze.demo.domain.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();
    Customer getCustomerById(String id);
    Customer addCustomer(Customer customer);
    Customer updateCustomer(String id,Customer customer);
    Customer deleteCustomer(String id);
    List<Customer> generateData(Integer count);
    Page<Customer> getCustomers(Integer page, Integer size);
}
