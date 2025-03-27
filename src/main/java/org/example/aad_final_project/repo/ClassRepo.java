package org.example.aad_final_project.repo;

import org.example.aad_final_project.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ClassRepo extends JpaRepository<ClassEntity,Integer> {

    @Query("SELECT c from ClassEntity c where c.class_name=:className")
    Optional<ClassEntity> findByClassName(@Param("className") String className);


    @Modifying
    @Query("DELETE from ClassEntity c where c.class_name=:class_name")
    @Transactional
    void deleteByClassName(@Param("class_name") String class_name);


    @Query("SELECT c.class_id from ClassEntity c where c.class_name=:class_name")
    String findByClass_name(@Param("class_name") String class_name);
}
