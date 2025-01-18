package com.nstyle.test.services;

import com.nstyle.test.dtos.ServiceCategoryDTO;
import com.nstyle.test.dtos.ServiceSubCategoryDTO;
import com.nstyle.test.entities.ServiceCategoryEntity;
import com.nstyle.test.entities.ServiceSubCategoryEntity;
import com.nstyle.test.exceptions.ResourceNotFoundException;
import com.nstyle.test.repositories.ServiceCategoryRepository;
import com.nstyle.test.repositories.ServiceSubCategoryRepository;
import com.nstyle.test.services.service.IServiceCategoryService;
import com.nstyle.test.utils.constant.ServiceCatgryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceCategoryServiceImpl implements IServiceCategoryService {


    private final ServiceCategoryRepository serviceCategoryRepository;

    private final ServiceSubCategoryRepository serviceSubCategoryRepository;

    public ServiceCategoryServiceImpl(ServiceCategoryRepository serviceCategoryRepository, ServiceSubCategoryRepository serviceSubCategoryRepository) {
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.serviceSubCategoryRepository = serviceSubCategoryRepository;
    }

    @Override
    public void createServiceCategory(ServiceCategoryDTO serviceCatergoryDTO) {
        ServiceCategoryEntity service =serviceCategoryRepository.save(ServiceCategoryEntity.builder()
                .name(serviceCatergoryDTO.getName())
                .image(serviceCatergoryDTO.getImage())
                .status(serviceCatergoryDTO.getStatus())
                .build());

        List<ServiceSubCategoryEntity> subcat = serviceCatergoryDTO.getServiceSubCategoryDTO().stream()
                .map(dto -> ServiceSubCategoryEntity.builder()
                        .name(dto.getName())
                        .status(ServiceCatgryStatus.ACTIVE)
                        .serviceCategory(service) // Link the saved serviceCategory
                        .build())
                .collect(Collectors.toList());

        serviceSubCategoryRepository.saveAll(subcat);

    }

    @Override
    public List<ServiceCategoryDTO> getAllServiceCategories() {
        return serviceCategoryRepository.findAll().stream().map(sc -> ServiceCategoryDTO.builder()
                .id(sc.getId())
                .image(sc.getImage())
                .name(sc.getName())
                .status(sc.getStatus())
                .serviceSubCategoryDTO(sc.getServiceSubCategoryEntities().stream().map(subCategory -> ServiceSubCategoryDTO.builder()
                                .id(subCategory.getId())
                                .name(subCategory.getName())
                                .image(subCategory.getImage())
                                .status(subCategory.getStatus())
                                .srvcCatId(subCategory.getServiceCategory().getId())
                                .build()).collect(Collectors.toList()))
                .build()).collect(Collectors.toList());
    }

    @Override
    public ServiceCategoryDTO getServiceCategoriesById(long id) throws ResourceNotFoundException {
        Optional<ServiceCategoryEntity> optionalServiceCategoryEntity = serviceCategoryRepository.findById(id);
        return optionalServiceCategoryEntity.map(sc -> ServiceCategoryDTO.builder()
                .id(sc.getId())
                .image(sc.getImage())
                .name(sc.getName())
                .status(sc.getStatus())
                .build()).orElseThrow(()->new ResourceNotFoundException("ServiceCategoryEntity not found for Id: "+id));
    }

    @Override
    public void updateServiceCategory(ServiceCategoryDTO serviceCatergoryDTO) throws ResourceNotFoundException {
        if (!serviceCategoryRepository.existsById(serviceCatergoryDTO.getId())) {
            throw new ResourceNotFoundException("Service Category not found for ID: " + serviceCatergoryDTO.getId());
        }
        serviceCategoryRepository.save(ServiceCategoryEntity.builder()
                        .id(serviceCatergoryDTO.getId())
                .name(serviceCatergoryDTO.getName())
                .image(serviceCatergoryDTO.getImage())
                .status(serviceCatergoryDTO.getStatus())
                .build());
    }

    @Override
    public void deleteServiceCategory(long id) throws ResourceNotFoundException {
        if (!serviceCategoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Service Category not found for ID: " + id);
        }
        serviceCategoryRepository.deleteById(id);
    }
}
