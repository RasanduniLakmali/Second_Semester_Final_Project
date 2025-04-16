package org.example.aad_final_project.repo;

import org.example.aad_final_project.entity.Role;
import org.example.aad_final_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<User,Integer> {

//    @Query("SELECT a.admin_id from User a where a.admin_name=:adminName")
//    Integer findByAdminName(@Param("adminName") String adminName);

    @Query("SELECT a from User a where a.email=:adminEmail AND a.password=:adminPassword")
    Optional<User> findByAdminEmailAndPassword(@Param("adminEmail") String adminEmail, @Param("adminPassword") String adminPassword);

    @Query("SELECT a from User  a where a.email=:adminEmail")
    Optional<User> findByAdminEmail(@Param("adminEmail") String adminEmail);


    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);

    @Query("SELECT u.id from User u where u.role=:role AND u.name=:name")
    Integer findByRoleAndName(@Param("role") Role role, @Param("name") String name);

}
