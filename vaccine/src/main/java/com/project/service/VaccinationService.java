package com.project.service;

import com.project.dto.*;
import com.project.entity.Vaccination;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VaccinationService {


    RegistrablePeriodicalVaccinationDTO findRegistrableVaccinationById(Integer id);

    /**
     * @return
     */
    int saveRegister(PeriodicalVaccinationRegisterDTO register);


    /**
     *Tạo mới lịch tiêm theo yêu cầu
     **/
    Vaccination registerVaccination(Vaccination vaccinationTemp);


    List<String> findAllVaccineAge();


    List<TimeDTO> findAllVaccinationTime();

    double getTotalPage(PeriodicalSearchDataDTO searchData);

    List<RegistrablePeriodicalVaccinationDTO> findCustomVaccination(PeriodicalSearchDataDTO searchData);

    PeriodicalVaccinationTempRegisterDTO checkRegister(PeriodicalVaccinationTempRegisterDTO register);
}
