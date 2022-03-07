package com.example.checkads_backend_admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.checkads_backend_admin.User.Admin;
import com.example.checkads_backend_admin.User.AdminRepo;
import com.example.checkads_backend_admin.User.Role;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	
	@Autowired
    private AdminRepo adminRepo;
	
   

    @Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public Admin saveUser(Admin admin) {
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
	        return adminRepo.save(admin);
	}

	@Override
	public Admin save(Admin admin) {
		  return adminRepo.save(admin);
	}

	@Override
	public Admin findByEmail(String email) {
		 return adminRepo.findByEmail(email);
	}

	@Override
	public Admin findById(Long id) {
		return adminRepo.findById(id);
	}

	

  
	
}
