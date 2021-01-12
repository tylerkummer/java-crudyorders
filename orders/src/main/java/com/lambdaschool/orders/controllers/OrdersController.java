package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrdersController
{
    @Autowired
    OrdersService ordersService;

    @PostMapping(value = "/order",
        consumes = {"application/json"},
        produces = {"application/json"})
    public ResponseEntity<?> addNewOrder(
        @Valid
        @RequestBody
            Order newOrder)
    {
        newOrder.setOrdnum(0);
        newOrder = ordersService.save(newOrder);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{ordernum}")
            .buildAndExpand(newOrder.getOrdnum())
            .toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(null,
            responseHeaders,
            HttpStatus.CREATED);
    }

    @PutMapping(value = "/order/{id}",
        consumes = {"application/json"},
        produces = {"application/json"})
    public ResponseEntity<?> updateOrder(
        @RequestBody
            Order updateOrder,
        @PathVariable
            long id)
    {
        updateOrder.setOrdnum(id);
        ordersService.save(updateOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/order/{id}")
    public ResponseEntity<?> deleteCustomerById(
        @PathVariable
            long id)
    {
        ordersService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
