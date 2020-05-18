package com.blaze.demo.service;

import com.blaze.demo.domain.Customer;
import com.blaze.demo.enums.ApplicationProperty;
import com.blaze.demo.repository.CustomerRepository;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final static String INVALID_CUSTOMER_ID = "Invalid Customer id";
    private final static String CUSTOMER_NOT_FOUND = "Customer not found";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Environment env;

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        customer.setCreated(DateTime.now().getMillis());
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(String id,Customer customer) {
        if (ObjectId.isValid(id)){
            Customer dbCustomer = getCustomerById(id);
            if (dbCustomer!=null){
                dbCustomer.setFirstName(customer.getFirstName());
                dbCustomer.setLastName(customer.getLastName());
                dbCustomer.setEmail(customer.getEmail());
                dbCustomer.setPhoneNumber(customer.getPhoneNumber());
                dbCustomer.setModified(DateTime.now().getMillis());
                return customerRepository.save(customer);
            }
        }
        return null;
    }

    @Override
    public Customer deleteCustomer(String id) {
        if (ObjectId.isValid(id)){
            Customer dbCustomer = getCustomerById(id);
            if (dbCustomer!=null){
                dbCustomer.setActive(false);
                dbCustomer.setModified(DateTime.now().getMillis());
                return customerRepository.save(dbCustomer);
            }
        }
        return null;
    }

    @Override
    public List<Customer> generateData(Integer count) {
        List<Customer> customers = new ArrayList<>();
        Long total = customerRepository.count();
        if (count>0){
            for (int i=0;i<count;i++){
                Long number = total + i;
                String firstName = env.getProperty(ApplicationProperty.CUSTOMER_FIRSTNAME)+number;
                String lastName = env.getProperty(ApplicationProperty.CUSTOMER_LASTNAME)+number;
                String email = String.format("test%s@%s",number,env.getProperty(ApplicationProperty.CUSTOMER_EMAIL_DOMAIN));
                Customer customer = new Customer();
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setEmail(email);

                customer.setActive(true);
                customers.add(customer);
            }
            return customerRepository.saveAll(customers);
        }
        return null;
    }

    @Override
    public Page<Customer> getCustomers(Integer page, Integer size) {
        Sort sort = Sort.by("name").ascending();
        return customerRepository.findAll(PageRequest.of(page,size,sort));
    }
}
