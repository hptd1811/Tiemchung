package com.project.service;

import com.project.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAllRole();

    void setDefaultRole(int accountId, Integer roleId);

    /**
     * @return
     */
    List<Role> getAllRoles();


}
