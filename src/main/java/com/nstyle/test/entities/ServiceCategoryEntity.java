package com.nstyle.test.entities;

import com.nstyle.test.utils.constant.ServiceCatgryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "service_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String image;
    private ServiceCatgryStatus status;

    @OneToMany(mappedBy = "serviceCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceSubCategoryEntity> serviceSubCategoryEntities;
}
