package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController
{
    @Autowired
    private CustomersService customersService;

    // http://localhost:2019/customers/orders
    @GetMapping(value = "/orders",
        produces = {"application/json"})
    public ResponseEntity<?> listAllCustomers()
    {
        List<Customer> myCustomers = customersService.findAllCustomers();
        return new ResponseEntity<>(myCustomers,
            HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer/17
    @GetMapping(value = "/customer/{custid}",
        produces = {"application/json"})
    public ResponseEntity<?> getCustomerById(
        @PathVariable
            long custid)
    {
        Customer c = customersService.findCustomersById(custid);
        return new ResponseEntity<>(c,
            HttpStatus.OK);
    }

    // http://localhost:2019/customers/namelike/sun
    @GetMapping(value = "/namelike/{custname}",
        produces = {"application/json"})
    public ResponseEntity<?> findCustomerByName(
        @PathVariable
            String custname)
    {
        List<Customer> myCustomerList = customersService.findByCustomerName(custname);
        return new ResponseEntity<>(myCustomerList,
            HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer
    @PostMapping(value = "/customer",
        consumes = {"application/json"},
        produces = {"application/json"})
    public ResponseEntity<?> addNewCustomer(
        @Valid
        @RequestBody
            Customer newCustomer)
    {
        newCustomer.setCustcode(0);
        newCustomer = customersService.save(newCustomer);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{custid}")
            .buildAndExpand(newCustomer.getCustcode())
            .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(null,
            responseHeaders,
            HttpStatus.CREATED);
    }

    // http://localhost:2019/customers/customer/5
    @PutMapping(value = "/customer/{custid}",
        consumes = {"application/json"},
        produces = {"application/json"})
    public ResponseEntity<?> replaceCustomer(
        @RequestBody
            Customer updateCustomer,
        @PathVariable
            long custid)
    {
        updateCustomer.setCustcode(custid);
        customersService.save(updateCustomer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer/5
    @PatchMapping(value = "/customer/{custid}",
        consumes = {"application/json"},
        produces = {"application/json"})
    public ResponseEntity<?> updateCustomer(
        @RequestBody
            Customer updateCustomer,
        @PathVariable
            long custid)
    {
        customersService.update(updateCustomer,
            custid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer/5
    @DeleteMapping(value = "/customer/{custid}")
    public ResponseEntity<?> deleteCustomerById(
        @PathVariable
            long custid)
    {
        customersService.delete(custid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
