package com.nstyle.test.services.service;

import com.nstyle.test.dtos.CustomerDTO;
import com.nstyle.test.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ICustomerService {

    void createCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(long id) throws ResourceNotFoundException;

    void updateCustomer(CustomerDTO customerDTO) throws ResourceNotFoundException;

    void deleteCustomer(long id) throws ResourceNotFoundException;
}