package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.CustomersRepository;
import com.lambdaschool.orders.repositories.OrdersRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Implements the OrdersService Interface.
 */
@Transactional
@Service(value = "ordersService")
public class OrdersServiceImpl
    implements OrdersService
{
    @Autowired
    private OrdersRepository ordersrepos;

    @Autowired
    private CustomersRepository custrepos;

    @Autowired
    private PaymentRepository payrepos;

    @Override
    public Order save(Order order)
    {
        Order newOrder = new Order();

        if (order.getOrdnum() != 0)
        {
            ordersrepos.findById(order.getOrdnum())
                .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " Not Found!"));

            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());
        newOrder.setCustomer(custrepos.findById(order.getCustomer()
            .getCustcode())
            .orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer()
                .getCustcode() + " Not Found!")));

        newOrder.getPayments().clear();
        // payments must already exist
        for (Payment p : order.getPayments())
        {
            Payment newPayment = payrepos.findById(p.getPaymentid())
                .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found!"));
            newOrder.getPayments().add(newPayment);
        }

        return ordersrepos.save(newOrder);
    }

    @Override
    public void delete(long id)
    {
        if (ordersrepos.findById(id)
            .isPresent())
        {
            ordersrepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException("Order " + id + " Not Found");
        }
    }

}
