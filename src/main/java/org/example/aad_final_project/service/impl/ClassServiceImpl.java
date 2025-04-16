package org.example.aad_final_project.service.impl;

import org.example.aad_final_project.dto.ClassDTO;
import org.example.aad_final_project.dto.StudentMyClassDTO;
import org.example.aad_final_project.entity.User;
import org.example.aad_final_project.entity.ClassEntity;
import org.example.aad_final_project.repo.AdminRepo;
import org.example.aad_final_project.repo.ClassRepo;
import org.example.aad_final_project.service.ClassService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {


    @Autowired
    private ClassRepo classRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdminRepo adminRepo;

    @Override
    public boolean saveClass(ClassDTO classDTO) {
        ClassEntity classEntity = modelMapper.map(classDTO, ClassEntity.class);

        // Fetch Admin entity from database
        Optional<User> optionalAdmin = adminRepo.findById(Integer.valueOf(classDTO.getAdmin_id()));

        if (optionalAdmin.isPresent()) {
            classEntity.setAdmin(optionalAdmin.get()); // Set the Admin entity
            classRepo.save(classEntity);
            return true;
        } else {
            System.out.println("Admin not found with ID: " + classDTO.getAdmin_id());
            return false;
        }
    }

    @Override
    public List<ClassDTO> getAll() {
        return modelMapper.map(classRepo.findAll(),
                new TypeToken<List<ClassDTO>>() {}.getType());
    }

    @Override
    public boolean updateClass(ClassDTO classDTO) {

        Optional<ClassEntity> optionalClass = classRepo.findByClassName(classDTO.getClass_name());

        if (optionalClass.isPresent()) {

            ClassEntity classEntity = optionalClass.get();
            classEntity.setClass_name(classDTO.getClass_name());
            classEntity.setCapacity(classDTO.getCapacity());
            classEntity.setHall_number(classDTO.getHall_number());
            classEntity.setAdmin(adminRepo.findById(Integer.valueOf(classDTO.getAdmin_id())).orElse(null));
            classEntity.setAdmin_name(classDTO.getAdmin_name());
            classEntity.setClass_fee(classDTO.getClass_fee());

            classRepo.save(classEntity);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteClass(String class_name) {
         Optional<ClassEntity> classEntity =classRepo.findByClassName(class_name);
         if (classEntity.isPresent()) {
             classRepo.deleteByClassName(class_name);
             return true;
         }
         return false;
    }

    @Override
    public List<String> getClassNames() {
        return classRepo.findAll().stream()
                .map(ClassEntity::getClass_name) // Extract only customer IDs
                .collect(Collectors.toList());
    }

    @Override
    public String getClassId(String className) {
        return classRepo.findByClass_name(className);
    }

    @Override
    public List<StudentMyClassDTO> getClassDetails(String classId,LocalDate scheduleDate) {
       List<StudentMyClassDTO> details = classRepo.findByClassAndSubject(classId,scheduleDate);

        System.out.println(details.toString());

       return details;
    }

    @Override
    public String getClassFee(String className) {
        return classRepo.findClassFee(className);
    }

    @Override
    public long getClassesCount() {
        return classRepo.count();
    }

    @Override
    public String getUniqueClass(String studentName) {
        String class_name = classRepo.findByStudent_name(studentName);
        System.out.println(class_name);
        if (class_name != null) {
            return class_name;
        }
        return null;
    }


}
