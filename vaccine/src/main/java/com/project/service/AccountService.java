package com.project.service;

import com.project.dto.PeriodicalVaccinationRegisterDTO;
import com.project.dto.PeriodicalVaccinationTempRegisterDTO;
import com.project.dto.RegistrablePeriodicalVaccinationDTO;
import com.project.entity.Account;
import com.project.entity.VaccinationHistory;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;


public interface AccountService {

    Account findAccountByUserName(String username);

    Integer     findIdUserByUserName(String username);

    String existsByUserName(String username);

    Boolean existsById(Integer bookId);

    void addNew(String username, String password, String email) throws MessagingException, UnsupportedEncodingException;

    Boolean findAccountByVerificationCode(String code);


    Boolean findAccountByVerificationCodeToResetPassword(String code);

    void addVerificationCode(String username) throws MessagingException, UnsupportedEncodingException;


    List<Account> getAllAccount();


    void addNew(String username, String password);


    void saveNewPassword(String password,String code);

    void sendInfoEmail(PeriodicalVaccinationTempRegisterDTO register, VaccinationHistory vaccinationHistory) throws MessagingException, UnsupportedEncodingException;
}
