package org.example.aad_final_project.controller;


import org.example.aad_final_project.dto.PasswordChangeRequestDTO;
import org.example.aad_final_project.dto.ResponseDTO;
import org.example.aad_final_project.dto.UserDTO;
import org.example.aad_final_project.entity.Role;
import org.example.aad_final_project.entity.User;
import org.example.aad_final_project.service.impl.AdminServiceImpl;
import org.example.aad_final_project.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;


    @GetMapping("getAdId")
    public List<Integer> getAdminIds(){
         List<Integer> adminIds =  adminService.getAllIds();
        System.out.println(adminIds);
         return adminIds;
    }

    @GetMapping("getAdName")
    public List<String> getAdminName(){
        return adminService.getAllNames();
    }

    @GetMapping("getAdmin/{role}/{adminName}")
    public Integer getAdmin(@PathVariable Role role, @PathVariable String adminName) {

        System.out.println("admin name , role " + adminName + " "+ role);

        Integer adminId = adminService.getAdmin(role,adminName);
        System.out.println("adminId " + adminId);
        return adminId;
    }

    @GetMapping("getAdminName/{adminId}")
    public String getAdminName(@PathVariable Integer adminId){
        String adminName = adminService.getAdminName(adminId);
        return adminName;
    }

    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<UserDTO> getAdminByEmail(@PathVariable String email) {
        UserDTO adminDTO = adminService.getAdminByEmail(email);
        return ResponseEntity.ok(adminDTO);
    }


    @PutMapping("updateProfile")
    public ResponseEntity<ResponseDTO> updateProfile(@RequestBody UserDTO userDTO) {

        System.out.println("getAdmin " + userDTO.toString());

        UserDTO adminDTO = adminService.updateAdmin(userDTO);

        if (adminDTO != null) {
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Admin updated successfully!", adminDTO));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "Admin not updated!", null));
        }

    }

    @PostMapping("/change-password")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody PasswordChangeRequestDTO request) {
        boolean isChanged = adminService.changePassword(request);

        if (isChanged){
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Password changed successfully!", null));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "Password not updated!", null));
        }
    }


    @GetMapping("test1")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String check(){
        return "passed~!1";
    }

    @GetMapping("test2")
    @PreAuthorize("hasAuthority('USER')")
    public String checks(){
        return "passed~!2";
    }
    @GetMapping("/test3")
    @PreAuthorize("hasAuthority('USER')")
    public String checkss(){
        return "passed~!2";
    }

}
