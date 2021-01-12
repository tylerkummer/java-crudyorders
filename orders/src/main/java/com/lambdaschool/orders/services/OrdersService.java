package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;

public interface OrdersService
{
    Order save(Order order);

    void delete(long id);
}
