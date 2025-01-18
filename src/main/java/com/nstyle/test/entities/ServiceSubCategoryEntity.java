package com.nstyle.test.entities;

import com.nstyle.test.utils.constant.ServiceCatgryStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service_sub_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceSubCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String image;
    private ServiceCatgryStatus status;

    @ManyToOne
    @JoinColumn(name = "service_category_id")
    private ServiceCategoryEntity serviceCategory;
}
