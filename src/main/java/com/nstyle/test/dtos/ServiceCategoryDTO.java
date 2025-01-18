package com.nstyle.test.dtos;

import com.nstyle.test.utils.constant.ServiceCatgryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ServiceCategoryDTO {
    private long id;
    private String name;
    private String image;
    private ServiceCatgryStatus status;
    private List<ServiceSubCategoryDTO> serviceSubCategoryDTO;
}
