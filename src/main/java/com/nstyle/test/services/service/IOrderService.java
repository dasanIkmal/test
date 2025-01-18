package com.nstyle.test.services.service;

import com.nstyle.test.dtos.OrderDTO;
import com.nstyle.test.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IOrderService {
    void createOrder(OrderDTO orderDTO) throws ResourceNotFoundException;

    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(long id) throws ResourceNotFoundException;

    void updateOrder(long id, OrderDTO orderDTO) throws ResourceNotFoundException;

    void deleteOrder(long id) throws ResourceNotFoundException;
}
