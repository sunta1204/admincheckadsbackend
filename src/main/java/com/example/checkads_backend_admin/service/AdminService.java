package com.example.checkads_backend_admin.service;

import java.util.List;

import com.example.checkads_backend_admin.User.Admin;
import com.example.checkads_backend_admin.User.Role;

public interface AdminService {
    Admin saveUser(Admin admin);
    
    Admin save(Admin admin); // save ลง db 
    
    //User findByUsername(String username);

    Admin findByEmail(String email);
    Admin findById(Long id);

    //List<User> findAllUsers(); // เรียกในฟังก์ชันของ user
    
    
  
}
