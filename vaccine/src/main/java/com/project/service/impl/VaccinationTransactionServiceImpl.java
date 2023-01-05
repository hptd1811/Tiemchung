package com.project.service.impl;

import com.project.entity.VaccinationHistory;
import com.project.entity.VaccinationTransaction;
import com.project.repository.VaccinationHistoryRepository;
import com.project.repository.VaccinationTransactionRepository;
import com.project.service.VaccinationTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccinationTransactionServiceImpl implements VaccinationTransactionService {
    @Autowired
    VaccinationTransactionRepository vaccinationTransactionRepository;

    @Autowired
    VaccinationHistoryRepository vaccinationHistoryRepository;

    /**
     * lấy danh sách giao dịch
     */
    @Override
    public Page<VaccinationTransaction> findAll(Pageable pageable) {
        return vaccinationTransactionRepository.findAll(pageable);
    }

    /**
     * tìm kiếm các trường trong list
     */
    @Override
    public Page<VaccinationTransaction> search(String patientName, String vaccineType, Pageable pageable) {
        return vaccinationTransactionRepository.findAllByVaccinationHistory_PatientNameContainingAndVaccinationHistory_Vaccination_Vaccine_VaccineTypeNameContaining(patientName, vaccineType, pageable);
    }

    /**
     * chỉnh sửa giao dịch
     */
    @Override
    public void edit(Integer id, double price, Long quantity) {
        VaccinationTransaction vaccinationTransaction;
        vaccinationTransaction = vaccinationTransactionRepository.findById(id).orElse(null);
        vaccinationTransaction.setPrice(price);
        vaccinationTransaction.setQuantity(quantity);
        this.vaccinationTransactionRepository.save(vaccinationTransaction);
    }

    /**
     * lấy đối tượng giao dịch
     */
    @Override
    public VaccinationTransaction findById(Integer id) {
        return this.vaccinationTransactionRepository.findById(id).orElse(null);
    }

    /**
     * lưu giao dịch
     */
    @Override
    public void save(Integer idVaccineHistory, double price, Long quantity) {
        this.vaccinationTransactionRepository.createVaccineTransaction(price,quantity,idVaccineHistory);
    }

    /**
     * xóa giao dịch
     */
    @Override
    public void delete(Integer id) {
        this.vaccinationTransactionRepository.deleteById(id);
    }

}
