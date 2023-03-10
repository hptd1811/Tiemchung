package com.project.service.impl;

import com.project.dto.PeriodicalVaccinationRegisterDTO;
import com.project.dto.PeriodicalVaccinationTempRegisterDTO;
import com.project.dto.RegistrablePeriodicalVaccinationDTO;
import com.project.entity.Account;
import com.project.entity.Patient;
import com.project.entity.Vaccination;
import com.project.entity.VaccinationHistory;
import com.project.repository.AccountRepository;
import com.project.repository.PatientRepository;
import com.project.repository.VaccinationHistoryRepository;
import com.project.repository.VaccinationRepository;
import com.project.service.AccountService;
import com.project.service.VaccinationHistoryService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private VaccinationRepository vaccinationRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private VaccinationHistoryRepository vaccinationHistoryRepository;


    @Override
    public Account findAccountByUserName(String username) {
        return accountRepository.findAccountByUserName(username);
    }


    @Override
    public Integer findIdUserByUserName(String username) {
        return accountRepository.findIdUserByUserName(username);
    }

    @Override
    public String existsByUserName(String username) {
        return accountRepository.existsByUserName(username);
    }


    @Override
    public Boolean existsById(Integer bookId) {
        return accountRepository.existsById(bookId);
    }


    @Override
    public void addNew(String username, String password) {
        accountRepository.addNewAccount(username, password);
    }

    @Override
    public void saveNewPassword(String password,String code) {
        accountRepository.saveNewPassword(password,code);
    }


    @Override
    public void addNew(String username, String password, String email) throws MessagingException, UnsupportedEncodingException {
        String randomCode = RandomString.make(64);
        accountRepository.addNew(username, password, false, randomCode, email);
        sendVerificationEmail(username, randomCode, email);
    }

    @Override
    public Boolean findAccountByVerificationCode(String code) {
        Account account = accountRepository.findAccountByVerificationCode(code);
        if (account == null || account.getEnabled()) {
            return false;
        } else {
            account.setEnabled(true);
            account.setVerificationCode(null);
            accountRepository.save(account);
            return true;
        }
    }

    @Override
    public Boolean findAccountByVerificationCodeToResetPassword(String code) {
        Account account = accountRepository.findAccountByVerificationCode(code);
        return account != null;
    }


    @Override
    public void addVerificationCode(String username) throws MessagingException, UnsupportedEncodingException {
        String code = RandomString.make(64);
        accountRepository.addVerificationCode(code, username);
        Account account = accountRepository.findAccountByVerificationCode(code);
        this.sendVerificationEmailForResetPassWord(account.getUserName(), code, account.getEmail());
    }

    public void sendVerificationEmail(String userName, String randomCode, String email) throws MessagingException, UnsupportedEncodingException {
        String subject = "H??y x??c th???c email c???a b???n";
        String mailContent = "";
        String confirmUrl = "http://localhost:4200/verification?code=" + randomCode;


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(email);
        helper.setFrom("tuandungdn123@gmail.com","TR???M Y T??? PH?????NG TH???CH THANG");
        helper.setSubject(subject);
        mailContent = "<p sytle='color:red;'>Xin ch??o " + userName + " ,<p>" + "<p> Nh???n v??o link sau ????? x??c th???c email c???a b???n:</p>" +
                "<h3><a href='" + confirmUrl + "'>Link X??c th???c( nh???n v??o ????y)!</a></h3>" +
                "<p>TR???M Y T??? X?? PH?????NG TH???CH THANG</p>";
        helper.setText(mailContent, true);
        javaMailSender.send(message);
    }

    public void sendVerificationEmailForResetPassWord(String userName, String randomCode, String email) throws MessagingException, UnsupportedEncodingException {
        String subject = "H??y x??c th???c email c???a b???n";
        String mailContent = "";
        String confirmUrl = "http://localhost:4200/verify-reset-password?code=" + randomCode;


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setFrom("tuandungdn123@gmail.com","TR???M Y T??? PH?????NG TH???CH THANG");
        helper.setTo(email);
        helper.setSubject(subject);
        mailContent = "<p sytle='color:red;'>Xin ch??o " + userName + " ,<p>" + "<p> Nh???n v??o link sau ????? x??c th???c email c???a b???n:</p>" +
                "<h3><a href='" + confirmUrl + "'>Link X??c th???c( nh???n v??o ????y)!</a></h3>" +
                "<p>TR???M Y T??? PH?????NG TH???CH THANG</p>";
        helper.setText(mailContent, true);
        javaMailSender.send(message);
    }


    /**
     * Hien thi list
     */
    @Override
    public List<Account> getAllAccount() {
        return accountRepository.getAllAccount();
    }

    /**
     * G???i email th??ng tin cho b???nh nh??n sau khi ????ng k?? ti??m ch???ng
     */
    @Override
    public void sendInfoEmail(PeriodicalVaccinationTempRegisterDTO register, VaccinationHistory vaccinationHistory) throws MessagingException, UnsupportedEncodingException {
        Patient patient = this.patientRepository.getOne(register.getPatientId());
        Vaccination vaccination = this.vaccinationRepository.getOne(register.getVaccinationId());
        StringBuilder randomCode = new StringBuilder();
        randomCode.append(register.getVaccinationId()).append("|").append(register.getPatientId());
        String subject = "Th??ng tin ????ng k?? ti??m ch???ng c???a b???n";
        String mailContent = "";
        String cancelRegisterUrl = "http://localhost:4200/cancel-register?code=" + randomCode;

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(patient.getEmail());
        helper.setFrom("maytao159@gmail.com","TR???M Y T??? PH?????NG TH???CH THANG");
        helper.setSubject(subject);
        mailContent = "<span style=\"font-weight: bold\">Xin ch??o<span> "+patient.getGuardian()+",</span></span>\n" +
                "<br><br>\n" +
                "<span style=\"font-weight: bold\"> "+patient.getName()+"</span> <span>v???a ???????c ????ng k?? ti??m ch???ng ?????nh k??? v???i th??ng tin sau:</span>\n" +
                "<br><br>\n" +
                "<span style=\"font-weight: bold\"> Ng??y ti??m ch???ng: </span><span>"+vaccination.getDate()+"</span>\n" +
                "<br><br>\n" +
                "<span style=\"font-weight: bold\"> Gi??? ti??m ch???ng: </span><span>"+vaccinationHistory.getStartTime()+"  - "+vaccinationHistory.getEndTime()+"</span>\n" +
                "<br><br>\n" +
                "<span style=\"font-weight: bold\"> ?????a ??i???m: </span><span> TR???M Y T??? PH?????NG TH???CH THANG - H???i Ch??u, ???? N???ng </span>\n" +
                "<br><br>\n" +
                "<span style=\"font-weight: bold\"> T??n V???c xin: </span><span>"+vaccination.getVaccine().getName()+" </span>\n" +
                "<br><br>\n" +
                "<span style=\"font-weight: bold\"> Xu???t x???: </span><span>"+vaccination.getVaccine().getOrigin()+" </span>\n" +
                "<br><br>\n" +
                "<p style=\"font-style: italic; color: red\">Trong tr?????ng h???p b???n kh??ng th??? tham gia v?? l?? do n??o ????, b???n c?? th??? h???y ????ng k?? b???ng link b??n d?????i:</p>\n" +
                "<h3><a href='" + cancelRegisterUrl + "'>Link h???y ????ng k??!</a></h3>" +
                "<p>TR???M Y T??? PH?????NG TH???CH THANG K??NH B??O</p>";
        helper.setText(mailContent, true);
        javaMailSender.send(message);
    }
}
