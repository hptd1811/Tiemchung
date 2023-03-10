package com.project.controller;

import com.project.dto.*;
import com.project.entity.VaccinationHistory;
import com.project.payload.reponse.MessageResponse;
import com.project.payload.request.VerifyRequest;
import com.project.service.AccountService;
import com.project.service.VaccinationHistoryService;
import com.project.service.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/public/vaccination")
public class VaccinationController {
    @Autowired
    private VaccinationService vaccinationService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private VaccinationHistoryService vaccinationHistoryService;

    /*KhoaTA
     *display list of registrable periodical vaccinations
     * Test Native Query
     */
//    @GetMapping("/register-list")
//    public ResponseEntity<List<RegistrablePeriodicalVaccinationDTO>> findAllRegistrablePeriodicalVaccination() {
//        List<RegistrablePeriodicalVaccinationDTO> registrableVaccinationList = this.vaccinationService.findAllRegistrableVaccination();
//
//        if (registrableVaccinationList.size() == 0) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        return new ResponseEntity<>(registrableVaccinationList, HttpStatus.OK);
//    }
    /*
     *display list of registrable periodical vaccinations
     */
    @GetMapping("/register-list/{id}")
    public ResponseEntity<RegistrablePeriodicalVaccinationDTO> findAllRegistrablePeriodicalVaccination(@PathVariable Integer id) {
        RegistrablePeriodicalVaccinationDTO registrableVaccination = this.vaccinationService.findRegistrableVaccinationById(id);
        if (registrableVaccination == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(registrableVaccination, HttpStatus.OK);
    }
    /**
     * Ph????ng ph??p l??u ng?????i b???nh v?? ????ng k?? ti??m ch???ng ?????nh k???
     */
    @PostMapping("/register-patient")
    public ResponseEntity<Boolean> savePeriodicalVaccinationRegister(@RequestBody PeriodicalVaccinationTempRegisterDTO register) throws UnsupportedEncodingException, MessagingException {
        VaccinationHistory vaccinationHistory = this.vaccinationHistoryService.createNewRegister(register);
//        this.vaccinationHistoryService.createNewRegister(register);
                this.accountService.sendInfoEmail(register,vaccinationHistory);
                return new ResponseEntity<>(true, HttpStatus.CREATED);
    }
    /**
     * l???y danh s??ch t???t c??? v???c xin
     */
    @GetMapping("/age-list")
    public ResponseEntity<List<String>> findAllVaccineAge() {
        List<String> ageList = this.vaccinationService.findAllVaccineAge();
        return new ResponseEntity<>(ageList, HttpStatus.OK);
    }

    /**
     * l???y danh s??ch t???t c??? th???i gian ti??m ch???ng
     */
    @GetMapping("/time-list")
    public ResponseEntity<List<TimeDTO>> findAllVaccinationTime() {
        List<TimeDTO> timeList = this.vaccinationService.findAllVaccinationTime();
        return new ResponseEntity<>(timeList, HttpStatus.OK);
    }
    /**
     * l???y t???ng s??? trang t??m ki???m
     */
    @PostMapping("/get-total-page")
    public ResponseEntity<Integer> findTotalPage(@RequestBody PeriodicalSearchDataDTO searchData) {
        return new ResponseEntity<>((int) this.vaccinationService.getTotalPage(searchData), HttpStatus.OK);
    }

    /**
     *nh???n k???t qu??? ti??m ch???ng ?????nh k??? t??m ki???m
     */
    @PostMapping("/get-custom-list")
    public ResponseEntity<List<RegistrablePeriodicalVaccinationDTO>> findCustomList(@RequestBody PeriodicalSearchDataDTO searchData) {
        List<RegistrablePeriodicalVaccinationDTO> registrableVaccinationList = this.vaccinationService.findCustomVaccination(searchData);
        if (registrableVaccinationList.size() == 0) {
            return new ResponseEntity<>(registrableVaccinationList,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(registrableVaccinationList, HttpStatus.OK);
    }

    @PostMapping("/cancel")
    public void VerifyCancel(@RequestBody VerifyRequest code) {
        System.out.println(code.getCode());
        int vaccinationId = Integer.parseInt(code.getCode().substring(0,code.getCode().indexOf("|")));
        System.out.println(vaccinationId);
        int patientId = Integer.parseInt(code.getCode().substring(code.getCode().indexOf("|")+1));
        System.out.println(patientId);
        this.vaccinationHistoryService.cancelRegister(vaccinationId, patientId);
    }

    /**
     * Ph????ng ph??p l??u ng?????i b???nh v?? ????ng k?? ti??m ch???ng ?????nh k???
     */
    @PostMapping("/check-register")
    public ResponseEntity<PeriodicalVaccinationTempRegisterDTO> checkPeriodicalVaccinationRegister(@RequestBody PeriodicalVaccinationTempRegisterDTO register) {
        System.out.println(register.getPatientId());
        System.out.println(register.getVaccinationId());
        return new ResponseEntity<>(this.vaccinationService.checkRegister(register), HttpStatus.OK);
    }
}
