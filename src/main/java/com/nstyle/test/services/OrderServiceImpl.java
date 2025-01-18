package com.nstyle.test.services;

import com.nstyle.test.dtos.OrderDTO;
import com.nstyle.test.entities.OrderEntity;
import com.nstyle.test.entities.ServiceCategoryEntity;
import com.nstyle.test.exceptions.ResourceNotFoundException;
import com.nstyle.test.repositories.CustomerRepository;
import com.nstyle.test.repositories.OrderRepository;
import com.nstyle.test.repositories.ServiceCategoryRepository;
import com.nstyle.test.repositories.UserRepository;
import com.nstyle.test.services.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {


    private OrderRepository orderRepository;

    private CustomerRepository customerRepository;

    private ServiceCategoryRepository serviceCategoryRepository;

    private UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, ServiceCategoryRepository serviceCategoryRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createOrder(OrderDTO orderDTO) throws ResourceNotFoundException {
        orderRepository.save(OrderEntity.builder()
                .customer(customerRepository.findById(orderDTO.getCustomerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Customer not found")))
                .services(serviceCategoryRepository.findAllById(orderDTO.getServiceIds()))
                .user(userRepository.findById(orderDTO.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found")))
                .totalBill(orderDTO.getTotalBill())
                .message(orderDTO.getMessage())
                .build());
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> OrderDTO.builder()
                        .id(order.getId())
                        .customerId(order.getCustomer().getId())
                        .serviceIds(order.getServices().stream().map(ServiceCategoryEntity::getId).collect(Collectors.toList()))
                        .userId(order.getUser().getUserId())
                        .totalBill(order.getTotalBill())
                        .message(order.getMessage())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(long id) throws ResourceNotFoundException {
        return orderRepository.findById(id)
                .map(order -> OrderDTO.builder()
                        .id(order.getId())
                        .customerId(order.getCustomer().getId())
                        .serviceIds(order.getServices().stream().map(ServiceCategoryEntity::getId).collect(Collectors.toList()))
                        .userId(order.getUser().getUserId())
                        .totalBill(order.getTotalBill())
                        .message(order.getMessage())
                        .build())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for ID: " + id));
    }

    @Override
    public void updateOrder(long id, OrderDTO orderDTO) throws ResourceNotFoundException {
        if (!orderRepository.existsById(orderDTO.getId())) {
            throw new ResourceNotFoundException("Customer not found for ID: " + orderDTO.getId());
        }
        orderRepository.save(OrderEntity.builder()
                .customer(customerRepository.findById(orderDTO.getCustomerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Customer not found")))
                .services(serviceCategoryRepository.findAllById(orderDTO.getServiceIds()))
                .user(userRepository.findById(orderDTO.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found")))
                .totalBill(orderDTO.getTotalBill())
                .message(orderDTO.getMessage())
                .build());
    }

    @Override
    public void deleteOrder(long id) throws ResourceNotFoundException {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found for ID: " + id);
        }
        orderRepository.deleteById(id);
    }
}