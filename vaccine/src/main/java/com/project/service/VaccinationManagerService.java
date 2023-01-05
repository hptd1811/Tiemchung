package com.project.service;

import com.project.entity.Vaccination;
import com.project.dto.VaccinationManagerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VaccinationManagerService {
    /**
     * lấy danh sách + phân trang + tìm kiếm
     */
    Page<Vaccination> searchAllVaccinationManager(String startDate, String endDate, String name, String status, int pageable, int type);

    /**
     *lấy danh sách + phân trang
     */
    Page<Vaccination> findAllVaccination(Pageable pageable, int type);

    /**
     *tìm id
     */
    Vaccination findByIdVaccinationManager(Integer id);

    /**
     * thêm mới
     */
    void saveVaccinationManager(VaccinationManagerDto vaccinationManagerDto);

    /**
     * Cập nhật lịch tiêm chủng định kỳ
     */
    void updateVaccinationManager(VaccinationManagerDto vaccinationManagerDto);

    /**
     * Cập nhật trạng thái đã tiêm hay chưa
     */
    void updateStatusVaccinationManager(Integer id);

    /**
     * Xóa lịch tiêm chủng định kỳ
     */
    void removeVaccinationManager(Integer id);
}
