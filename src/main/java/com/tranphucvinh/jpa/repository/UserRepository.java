package com.tranphucvinh.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tranphucvinh.jpa.entity.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailIgnoreCase(String email);

    List<User> findByIdIn(List<Long> userIds);

//	Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update tb_users set password =?1 where user_id =?2", nativeQuery = true)
    void updateUserPassword(String password, Long userId);

    Optional<User> findByUsername(String username);
}