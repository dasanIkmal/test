package com.nstyle.test.dtos;

import com.nstyle.test.utils.constant.ServiceCatgryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ServiceSubCategoryDTO {
    private long id;
    private String name;
    private String image;
    private ServiceCatgryStatus status;
    private long srvcCatId;
}
