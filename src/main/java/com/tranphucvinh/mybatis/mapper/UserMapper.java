package com.tranphucvinh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    void updateProfile(Map<String, Object> params);

    void updatePasswordByUsername(Map<String, Object> params);

    void updateUser(Map<String, Object> params);

    void insertUser(Map<String, Object> params);

    void insertUserRoles(Map<String, Object> params);

    <T> List<T> selectUserList();

    void deleteUserRoles(Map<String, Object> params);

    void deleteUsers(Map<String, Object> params);

	Map<String, Object> selectSupperAdminInfo();
}
