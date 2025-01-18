package com.nstyle.test.controllers;

import com.nstyle.test.dtos.ServiceCategoryDTO;
import com.nstyle.test.dtos.ServiceSubCategoryDTO;
import com.nstyle.test.exceptions.ResourceNotFoundException;
import com.nstyle.test.services.service.IServiceCategoryService;
import com.nstyle.test.services.service.IServiceSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceController {

    IServiceCategoryService serviceCategoryService;
    IServiceSubCategoryService serviceSubCategoryService;

    @Autowired
    public ServiceController(IServiceCategoryService serviceCategoryService, IServiceSubCategoryService serviceSubCategoryService) {
        this.serviceCategoryService = serviceCategoryService;
        this.serviceSubCategoryService = serviceSubCategoryService;
    }

    @PostMapping("/categories")
    public void createCategory(@RequestBody ServiceCategoryDTO serviceCatergoryDTO){
        serviceCategoryService.createServiceCategory(serviceCatergoryDTO);
    }

    @GetMapping("/categories")
    public List<ServiceCategoryDTO> getCategory(){
        return serviceCategoryService.getAllServiceCategories();
    }

    @GetMapping("/categories/{id}")
    public ServiceCategoryDTO getCategory(@PathVariable long id) throws ResourceNotFoundException {
        return serviceCategoryService.getServiceCategoriesById(id);
    }

    @PutMapping("/categories")
    public void updateCategory(@RequestBody ServiceCategoryDTO serviceCatergoryDTO) throws ResourceNotFoundException {
        serviceCategoryService.updateServiceCategory(serviceCatergoryDTO);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable long id) throws ResourceNotFoundException {
        serviceCategoryService.deleteServiceCategory(id);
    }

    @PostMapping("/sub-categories")
    public void createSubCategory(@RequestBody ServiceSubCategoryDTO serviceSubCategoryDTO) throws ResourceNotFoundException {
        serviceSubCategoryService.createSubServiceCategory(serviceSubCategoryDTO);
    }

    @GetMapping("/sub-categories")
    public List<ServiceSubCategoryDTO> getSubCategory(){
        return serviceSubCategoryService.getAllServiceSubCategories();
    }

    @GetMapping("/sub-categories/{id}")
    public ServiceSubCategoryDTO getSubCategory(@PathVariable long id) throws ResourceNotFoundException {
        return serviceSubCategoryService.getServiceSubCategoriesById(id);
    }

    @PutMapping("/sub-categories")
    public void updateSubCategory(@RequestBody ServiceSubCategoryDTO serviceSubCategoryDTO) throws ResourceNotFoundException {
        serviceSubCategoryService.updateServiceSubCategory(serviceSubCategoryDTO);
    }

    @DeleteMapping("/sub-categories/{id}")
    public void deleteSubCategory(@PathVariable long id) throws ResourceNotFoundException {
        serviceSubCategoryService.deleteServiceSubCategory(id);
    }


}
