package com.project.service;
import com.project.dto.EmployeeDto;
import com.project.dto.EmployeeEditDTO;
import com.project.dto.EmployeeFindIdDTO;
import com.project.dto.EmployeeListDTO;
import com.project.entity.Account;
import com.project.entity.Employee;
import com.project.entity.Position;

import javax.mail.MessagingException;
import java.util.List;

public interface EmployeeService {
    /**
    * Hien thi danh sach nhan vien
     */
    List<EmployeeListDTO> getAllEmployee();
    /**
     * tim kiem nhan vien theo id
     */
    EmployeeFindIdDTO findById(int id);
    /**
     * chinh sua thong tin nhan vien
     */
    void editEmployee(EmployeeEditDTO employeeEditDTO, int roleId, int accountId);
    /**
     * xoa nhan vien
     */
    void deleteEmployee(int id);

    /**
     * tim kiem nhan vien theo id, ten, chuc vu
     */
    List<EmployeeListDTO> findEmployeeByIdAndNameAndPosition(String nameSearch, String idEmpSearch, String positionSearch);

    /**
     * @param employeeDto
     * @throws MessagingException
     */
    void createNewEmployee(EmployeeDto employeeDto) throws MessagingException;

    /**
     * @param phone
     * @return
     */
    Integer findByPhone(String phone);
  
     /**
     * @param
     * @paramphone
     * @return
     */
    Integer findByIdCard(String idCard);

}
