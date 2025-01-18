package com.nstyle.test.controllers;

import com.nstyle.test.dtos.OrderDTO;
import com.nstyle.test.exceptions.ResourceNotFoundException;
import com.nstyle.test.services.service.IOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {


    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void createOrder(@RequestBody OrderDTO orderDTO) throws ResourceNotFoundException {
        orderService.createOrder(orderDTO);
    }

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDTO getOrderById(@PathVariable long orderId) throws ResourceNotFoundException {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}")
    public void updateOrder(@PathVariable long orderId, @RequestBody OrderDTO orderDTO) throws ResourceNotFoundException {
        orderService.updateOrder(orderId, orderDTO);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable long orderId) throws ResourceNotFoundException {
        orderService.deleteOrder(orderId);
    }
}