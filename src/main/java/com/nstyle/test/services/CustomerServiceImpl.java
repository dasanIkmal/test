package com.nstyle.test.services;

import com.nstyle.test.dtos.CustomerDTO;
import com.nstyle.test.entities.CustomerEntity;
import com.nstyle.test.exceptions.ResourceNotFoundException;
import com.nstyle.test.repositories.CustomerRepository;
import com.nstyle.test.services.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void createCustomer(CustomerDTO customerDTO) {
        customerRepository.save(CustomerEntity.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .build());
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(customer -> CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .build()).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(long id) throws ResourceNotFoundException {
        Optional<CustomerEntity> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.map(customer -> CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .build()).orElseThrow(() -> new ResourceNotFoundException("Customer not found for ID: " + id));
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) throws ResourceNotFoundException {
        if (!customerRepository.existsById(customerDTO.getId())) {
            throw new ResourceNotFoundException("Customer not found for ID: " + customerDTO.getId());
        }
        customerRepository.save(CustomerEntity.builder()
                .id(customerDTO.getId())
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .build());
    }

    @Override
    public void deleteCustomer(long id) throws ResourceNotFoundException {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found for ID: " + id);
        }
        customerRepository.deleteById(id);
    }
}