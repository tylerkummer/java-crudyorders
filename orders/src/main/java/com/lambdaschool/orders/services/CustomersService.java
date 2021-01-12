package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;

import java.util.List;

public interface CustomersService
{
    List<Customer> findAllCustomers();

    List<Customer> findByCustomerName(String custname);

    Customer findCustomersById(long id);

    Customer save(Customer customer);

    Customer update(
        Customer customer,
        long id);

    void delete(long id);
}
