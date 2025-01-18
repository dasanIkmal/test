package com.nstyle.test.services.service;
import com.nstyle.test.dtos.ServiceSubCategoryDTO;
import com.nstyle.test.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IServiceSubCategoryService {
    void createSubServiceCategory(ServiceSubCategoryDTO serviceCatergoryDTO) throws ResourceNotFoundException;
    List<ServiceSubCategoryDTO> getAllServiceSubCategories();
    ServiceSubCategoryDTO getServiceSubCategoriesById(long id) throws ResourceNotFoundException;
    void updateServiceSubCategory(ServiceSubCategoryDTO serviceCatergoryDTO) throws ResourceNotFoundException;
    void deleteServiceSubCategory(long id) throws ResourceNotFoundException;

}
