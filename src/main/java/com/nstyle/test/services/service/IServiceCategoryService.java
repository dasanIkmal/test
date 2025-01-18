package com.nstyle.test.services.service;

import com.nstyle.test.dtos.ServiceCategoryDTO;
import com.nstyle.test.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IServiceCategoryService {
    void createServiceCategory(ServiceCategoryDTO serviceCatergoryDTO);
    List<ServiceCategoryDTO> getAllServiceCategories();
    ServiceCategoryDTO getServiceCategoriesById(long id) throws ResourceNotFoundException;
    void updateServiceCategory(ServiceCategoryDTO serviceCatergoryDTO) throws ResourceNotFoundException;
    void deleteServiceCategory(long id) throws ResourceNotFoundException;

}
