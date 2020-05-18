package com.blaze.demo.controller;

import com.blaze.demo.domain.Customer;
import com.blaze.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List getAllCustomers(){
        return customerService.getCustomers();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page getCustomers(@RequestParam(required = false) Optional<Integer> page, @RequestParam(required = false) Optional<Integer> size){
        return customerService.getCustomers(page.orElse(0),size.orElse(Integer.MAX_VALUE));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("id") String id){
        return customerService.getCustomerById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Customer addCustomer(@Valid @RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Customer updateCustomer(@PathVariable("id") String id,@Valid @RequestBody Customer customer){
        return customerService.updateCustomer(id,customer);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public Customer deleteCustomer(@PathVariable("id") String id){
        return customerService.deleteCustomer(id);
    }

    @RequestMapping(value = "/generateData", method = RequestMethod.POST)
    public List generateData(Integer count){
        return customerService.generateData(count);
    }

}
