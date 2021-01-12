package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;

import java.util.List;

public interface CustomersService
{
    List<Customer> findAllCustomers();

    List<Customer> findByCustomerName(String custname);

    Customer findCustomersById(long id);
}
