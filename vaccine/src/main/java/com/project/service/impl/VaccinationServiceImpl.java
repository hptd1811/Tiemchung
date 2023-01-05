package com.project.service.impl;

import com.project.dto.*;
import com.project.entity.Vaccination;
import com.project.repository.*;
import com.project.service.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VaccinationServiceImpl implements VaccinationService {
    @Autowired
    private VaccinationRepository vaccinationRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private VaccinationHistoryRepository vaccinationHistoryRepository;
    @Autowired
    private VaccineRepository vaccineRepository;
    @Autowired
    private StorageRepository storageRepository;



//    @Override
//    public List<RegistrablePeriodicalVaccinationDTO> findAllRegistrableVaccination() {
//        return this.vaccinationRepository.findAllRegistrableVaccination();
//    }

    @Override
    public RegistrablePeriodicalVaccinationDTO findRegistrableVaccinationById(Integer id) {
        return this.vaccinationRepository.findRegistrableVaccinationById(id);
    }

    @Override
    public int saveRegister(PeriodicalVaccinationRegisterDTO register) {
        this.patientRepository.savePatient(register.getName(), register.getDateOfBirth(), register.getGender(), register.getGuardian(), register.getPhone(), register.getAddress(), register.getEmail());
        int patientId = this.patientRepository.findLatestPatientId();
        this.vaccinationHistoryRepository.savePeriodicalVaccinationRegister(register.getVaccinationId(), patientId);
        return patientId;
    }


    /**
     * Tạo mới lịch tiêm theo yêu cầu
     **/
    @Override
    public Vaccination registerVaccination(Vaccination vaccinationTemp) {
        return vaccinationRepository.save(vaccinationTemp);
    }


    @Override
    public List<String> findAllVaccineAge() {
        return this.vaccinationRepository.findAllAge();
    }


    @Override
    public List<TimeDTO> findAllVaccinationTime() {
        return this.vaccinationRepository.findAllTimeStamp();
    }


    @Override
    public double getTotalPage(PeriodicalSearchDataDTO searchData) {
        if (searchData.getDate().equals("")) {
            return Math.ceil((double) this.vaccinationRepository.findTotalPage('%'+searchData.getAge()+'%', '%'+searchData.getStartTime()+'%', '%'+searchData.getEndTime()+'%',
                    '%'+searchData.getVaccineName()+'%', '%'+ searchData.getDescription()+ '%')/5);
        }
        return Math.ceil( (double) this.vaccinationRepository.findTotalPage('%'+searchData.getAge()+'%', '%'+ searchData.getDate() +'%', '%'+searchData.getStartTime()+'%', '%'+searchData.getEndTime()+'%',
                '%'+searchData.getVaccineName()+'%', '%'+ searchData.getDescription()+ '%')/5);
    }

    @Override
    public List<RegistrablePeriodicalVaccinationDTO> findCustomVaccination(PeriodicalSearchDataDTO searchData) {
        if (searchData.getDate().equals("")) {
            return this.vaccinationRepository.findCustomListWithPageWithoutDate('%'+searchData.getAge()+'%', '%'+searchData.getStartTime()+'%', '%'+searchData.getEndTime()+'%',
                    '%'+searchData.getVaccineName()+'%','%'+ searchData.getDescription()+ '%', (searchData.getCurrentPage()-1)*5);
        } else {
            return this.vaccinationRepository.findCustomListWithPageWithDate('%'+searchData.getAge()+'%', '%'+searchData.getDate()+'%', '%'+searchData.getStartTime()+'%', '%'+searchData.getEndTime()+'%',
                    '%'+searchData.getVaccineName()+'%','%'+ searchData.getDescription()+ '%', (searchData.getCurrentPage()-1)*5);
        }
    }

    @Override
    public PeriodicalVaccinationTempRegisterDTO checkRegister(PeriodicalVaccinationTempRegisterDTO register) {
        Integer vaccineId = this.vaccinationRepository.getOne(register.getVaccinationId()).getVaccine().getVaccineId();
        Integer registerQuantity = this.vaccinationHistoryRepository.findAllByVaccination_VaccinationIdIs(register.getVaccinationId()).size();
        Long maximumRegister = this.storageRepository.findAllByVaccine_VaccineIdIs(vaccineId).getQuantity();
        System.out.println("maximum register : " + maximumRegister);
        register.setQuantityIsValid(registerQuantity+1 <= maximumRegister);
        register.setTimeIsValid(this.vaccinationHistoryRepository.findAllByVaccination_VaccinationIdIsAndStartTimeContainsAndEndTimeContains(register.getVaccinationId(), register.getStartTime(), register.getEndTime()).size()+1 < 3);
        String vaccineName = this.vaccineRepository.getOne(vaccineId).getName();
        register.setAlreadyRegister(this.vaccinationHistoryRepository.findAllByPatient_PatientIdAndVaccination_Vaccine_NameIsAndDeleteFlagIs(register.getPatientId(), vaccineName, false).size() > 0);
        return register;
    }
}
