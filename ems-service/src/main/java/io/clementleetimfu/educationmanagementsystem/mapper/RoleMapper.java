package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.entity.Role;

public interface RoleMapper {
    Role selectRoleByName(String value);
}
