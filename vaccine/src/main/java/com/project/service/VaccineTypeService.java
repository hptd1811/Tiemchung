package com.project.service;

import com.project.entity.VaccineType;

public interface VaccineTypeService {
    /**
     * @return
     */
    void createVaccineType(String name);

    /**
     * @return
     */
    VaccineType findVaccineType(String name);
}
