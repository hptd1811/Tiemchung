package com.project.dto;
/**
 * Class DTO để gửi dữ liệu để đăng ký biểu mẫu
 */
public interface PeriodicVaccinationDto {
    Integer getPatientId();
    String getName();
    String getDateOfBirth();
    String getGender();
    String getGuardian();
    String getPhone();
    String getAddress();
    String getStatus();

}
