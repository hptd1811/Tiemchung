package com.project.dto;

/**
 *KhoaTA
 * DTO Class để lấy dữ liệu cần thiết và gửi đến FE để hiển thị danh sách
 */
public interface RegistrablePeriodicalVaccinationDTO {
    Integer getVaccinationId();
    String getDate();
    String getStartTime();
    String getEndTime();
    String getVaccineName();
    String getVaccineTypeName();
    String getAge();
    String getDescription();
    String getLocation();
    String getCountry();
    String getImage();
    Integer getDuration();
    Integer getTimes();
}
