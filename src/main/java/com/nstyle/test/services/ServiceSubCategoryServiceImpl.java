package com.nstyle.test.services;

import com.nstyle.test.dtos.ServiceSubCategoryDTO;
import com.nstyle.test.entities.ServiceCategoryEntity;
import com.nstyle.test.entities.ServiceSubCategoryEntity;
import com.nstyle.test.exceptions.ResourceNotFoundException;
import com.nstyle.test.repositories.ServiceCategoryRepository;
import com.nstyle.test.repositories.ServiceSubCategoryRepository;
import com.nstyle.test.services.service.IServiceSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceSubCategoryServiceImpl implements IServiceSubCategoryService {

    @Autowired
    private ServiceSubCategoryRepository serviceSubCategoryRepository;

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    @Override
    public void createSubServiceCategory(ServiceSubCategoryDTO serviceSubCategoryDTO) throws ResourceNotFoundException {

        ServiceCategoryEntity serviceCatergory = serviceCategoryRepository.findById(serviceSubCategoryDTO.getSrvcCatId()).orElseThrow(() -> new ResourceNotFoundException("Service Catergory Id not found"));

        serviceSubCategoryRepository.save(ServiceSubCategoryEntity.builder()
                .name(serviceSubCategoryDTO.getName())
                .image(serviceSubCategoryDTO.getImage())
                .status(serviceSubCategoryDTO.getStatus())
                .serviceCategory(serviceCatergory)
                .build());
    }

    @Override
    public List<ServiceSubCategoryDTO> getAllServiceSubCategories() {
        return serviceSubCategoryRepository.findAll().stream()
                .map(subCategory -> ServiceSubCategoryDTO.builder()
                        .id(subCategory.getId())
                        .name(subCategory.getName())
                        .image(subCategory.getImage())
                        .status(subCategory.getStatus())
                        .srvcCatId(subCategory.getServiceCategory().getId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ServiceSubCategoryDTO getServiceSubCategoriesById(long id) throws ResourceNotFoundException {
        Optional<ServiceSubCategoryEntity> optionalSubCategory = serviceSubCategoryRepository.findById(id);

        return optionalSubCategory.map(subCategory -> ServiceSubCategoryDTO.builder()
                        .id(subCategory.getId())
                        .name(subCategory.getName())
                        .image(subCategory.getImage())
                        .status(subCategory.getStatus())
                .srvcCatId(subCategory.getServiceCategory().getId())
                        .build())
                .orElseThrow(() -> new ResourceNotFoundException("ServiceSubCategoryEntity not found for Id: " + id));
    }

    @Override
    public void updateServiceSubCategory(ServiceSubCategoryDTO serviceSubCategoryDTO) throws ResourceNotFoundException {
        if (!serviceSubCategoryRepository.existsById(serviceSubCategoryDTO.getId())) {
            throw new ResourceNotFoundException("Service Sub-Category not found for ID: " + serviceSubCategoryDTO.getId());
        }
        ServiceCategoryEntity serviceCatergory = serviceCategoryRepository.findById(serviceSubCategoryDTO.getSrvcCatId()).orElseThrow(() -> new ResourceNotFoundException("Service Catergory Id not found"));

        serviceSubCategoryRepository.save(ServiceSubCategoryEntity.builder()
                .id(serviceSubCategoryDTO.getId())
                .name(serviceSubCategoryDTO.getName())
                .image(serviceSubCategoryDTO.getImage())
                .status(serviceSubCategoryDTO.getStatus())
                .serviceCategory(serviceCatergory)
                .build());
    }

    @Override
    public void deleteServiceSubCategory(long id) throws ResourceNotFoundException {
        if (!serviceSubCategoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Service Sub-Category not found for ID: " + id);
        }
        serviceSubCategoryRepository.deleteById(id);
    }
}