package spring.demo.dao;

import spring.demo.entity.Role;

public interface RoleDao {

    public Role findRoleByName(String theRoleName);

}