package com.project.service.impl;


import com.project.entity.Role;
import com.project.repository.RoleRepository;
import com.project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public void setDefaultRole(int accountId, Integer roleId) {
        roleRepository.setDefaultRole(accountId,roleId);
    }



    @Override
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }


    @Override
    public List<Role> getAllRoles() {
        return roleRepository.finAllRole();

    }
}

