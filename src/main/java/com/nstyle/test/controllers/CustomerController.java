package com.nstyle.test.controllers;

import com.nstyle.test.dtos.CustomerDTO;
import com.nstyle.test.exceptions.ResourceNotFoundException;
import com.nstyle.test.services.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public void createCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.createCustomer(customerDTO);
    }


    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }


    @GetMapping("/{customerId}")
    public CustomerDTO getCustomerById(@PathVariable long customerId) throws ResourceNotFoundException {
        return customerService.getCustomerById(customerId);
    }


    @PutMapping("/{customerId}")
    public void updateCustomer(@PathVariable long customerId, @RequestBody CustomerDTO customerDTO) throws ResourceNotFoundException {
        customerService.updateCustomer(customerDTO);
    }


    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable long customerId) throws ResourceNotFoundException {
        customerService.deleteCustomer(customerId);
    }
}
