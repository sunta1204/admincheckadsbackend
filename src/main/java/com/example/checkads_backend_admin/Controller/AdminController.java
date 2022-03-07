package com.example.checkads_backend_admin.Controller;

import com.example.checkads_backend_admin.Company.Company;
import com.example.checkads_backend_admin.Company.CompanyRepo;
import com.example.checkads_backend_admin.User.Admin;
import com.example.checkads_backend_admin.User.AdminRepo;
import com.example.checkads_backend_admin.User.Role;

import com.example.checkads_backend_admin.jwt.JwtTokenProvider;
import com.example.checkads_backend_admin.service.AdminService;
import com.nimbusds.oauth2.sdk.Response;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "https://user.check-ads.com", "http://admin.check-ads.com" })
@RestController
public class AdminController {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private CompanyRepo companyRepo;

	@GetMapping("/api/admin/adminlogin")
	public ResponseEntity<?> adminlogin(Principal principal) {
		if (principal == null) {
			// This should be ok http status because this will be used for logout path.
			return ResponseEntity.ok(principal);
		}
		UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
		Admin admin = adminService.findByEmail(authenticationToken.getName());
		admin.setToken(jwtTokenProvider.generateToken(authenticationToken));

		if (admin.getRole() == Role.ADMIN) {
			HashMap<String, Object> adminMap = new HashMap<String, Object>();
			adminMap.put("id", admin.getId());
			adminMap.put("email", admin.getEmail());
			adminMap.put("role", admin.getRole());
			adminMap.put("token", admin.getToken());

			System.out.print("admin id " + admin.getId());
			return new ResponseEntity<>(adminMap, HttpStatus.OK); // ที่รีเทิร์นไปเก็บใน local storage
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * @GetMapping("/api/admin/all") public ResponseEntity<?> findAllUsers() {
	 * return ResponseEntity.ok(userService.findAllUsers()); // User ทุกคน }
	 */

	@PostMapping("/api/admin/getAdminProfile")
	public ResponseEntity<?> getAdminProfile(@RequestBody Map<String, Object> payload) {
		System.out.println("ดึง User Email " + payload.get("id"));
		long objToLong = ((Number) payload.get("id")).longValue();
		Admin getAdmin = adminService.findById(objToLong);
		System.out.println("ดึง User Email " + getAdmin.getEmail());
		System.out.println("ดึง User Id " + getAdmin.getId());
		return new ResponseEntity<>(getAdmin, HttpStatus.OK);
	}

	@PostMapping("/api/admin/editAdmin")
	public ResponseEntity<?> editAdmin(@RequestBody Map<String, Object> payload) { // แบบ post

		long objToLong = ((Number) payload.get("id")).longValue();
		Admin adminEdit = adminService.findById(objToLong);

		if (adminService.findByEmail(payload.get("email").toString()) != null) { // มี email
			Admin checkEmail = adminService.findByEmail(payload.get("email").toString());

			if (adminEdit.getEmail() == checkEmail.getEmail()) { // ถ้า Email เดียวกันกับID สำเร็จ

				/*
				 * System.out.println(user.getFirstname());
				 * System.out.println(user.getLastname()); System.out.println(user.getTel());
				 * System.out.println(user.getCareer());
				 */
				adminEdit.setEmail(payload.get("email").toString());
				adminEdit.setFirstname(payload.get("firstname").toString());
				adminEdit.setLastname(payload.get("lastname").toString());
				adminEdit.setTel(payload.get("tel").toString());
				adminEdit.setCareer(payload.get("career").toString());

				System.out.println("แก้ไขข้อมูลสำเร็จ แบบ Emailเดิม");
				return new ResponseEntity<>(adminService.save(adminEdit), HttpStatus.OK);

			} else { // มี email แต่เป็นของคนอื่น

				System.out.println("แก้ไขข้อมูลไม่สำเร็จ Email ซ้ำคนอื่น");
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}

		} else { // ไม่มี email ในระบบ สำเร็จ

			/*
			 * System.out.println(user.getFirstname());
			 * System.out.println(user.getLastname()); System.out.println(user.getTel());
			 * System.out.println(user.getCareer());
			 */
			adminEdit.setEmail(payload.get("email").toString());
			adminEdit.setFirstname(payload.get("firstname").toString());
			adminEdit.setLastname(payload.get("lastname").toString());
			adminEdit.setTel(payload.get("tel").toString());
			adminEdit.setCareer(payload.get("career").toString());

			System.out.println("แก้ไขข้อมูลสำเร็จ แบบ Email ใหม่");
			return new ResponseEntity<>(adminService.save(adminEdit), HttpStatus.OK);
		}

	}

	@PostMapping("/api/admin/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody Map<String, Object> payload) { // แบบ post
		// ดึง id มา ดึง password มาเเปลงมาเทียบ ถ้าตรง เปลี่ยนพาสใหม่
		long objToLong = ((Number) payload.get("id")).longValue();
		Admin userEdit = adminService.findById(objToLong);
		String newPass = payload.get("newpassword").toString();
		String curPass = payload.get("password").toString();
		boolean result = passwordEncoder.matches(curPass, userEdit.getPassword()); // เทียบรหัสผ่าน
		if (result == true) {
			System.out.println("รหัสผ่านเหมือนกัน "); // เอารหัสผ่านใหม่มาแปลง
			userEdit.setPassword(passwordEncoder.encode(newPass));
			return new ResponseEntity<>(adminService.save(userEdit), HttpStatus.OK);
		} else {
			System.out.println("รหัสผ่านต่างกัน "); // รีเทิร์นแอเร่อ
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	@PostMapping("/api/admin/checkPassword") // เช็คพาสเวิดแอดมินว่าตรงมั้ย
	public ResponseEntity<?> checkPassword(@RequestBody Map<String, Object> payload) { // แบบ post
		// ดึง id มา ดึง password มาเเปลงมาเทียบ ถ้าตรง เปลี่ยนพาสใหม่
		Admin getUser = adminService.findByEmail(payload.get("email").toString());
		String inputPass = payload.get("password").toString();
		boolean result = passwordEncoder.matches(inputPass, getUser.getPassword()); // เทียบรหัสผ่าน
		if (result == true) {
			System.out.println("รหัสผ่านเหมือนกัน ");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			System.out.println("รหัสผ่านต่างกัน "); // รีเทิร์นแอเร่อ
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	/* Super Admin Function */

	@GetMapping("/api/admin/superadminLogin")
	public ResponseEntity<?> superadminLogin(Principal principal) {
		if (principal == null) {
			// This should be ok http status because this will be used for logout path.
			return ResponseEntity.ok(principal);
		}
		UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
		Admin admin = adminService.findByEmail(authenticationToken.getName());

		if (admin.getRole() == Role.SUPERADMIN) {
			admin.setToken(jwtTokenProvider.generateToken(authenticationToken));

			HashMap<String, Object> adminMap = new HashMap<String, Object>();
			adminMap.put("id", admin.getId());
			adminMap.put("email", admin.getEmail());
			adminMap.put("role", admin.getRole());
			adminMap.put("token", admin.getToken());

			System.out.print("admin id " + admin.getId());
			return new ResponseEntity<>(adminMap, HttpStatus.OK); // ที่รีเทิร์นไปเก็บใน local storage
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT); // ที่รีเทิร์นไปเก็บใน local storage
		}
	}

	@PostMapping("/api/admin/createAdmin") // Register ขึ้นมาเทส ของจริงจะเป็น Super Admin สร้าง Admin อีกที
	public ResponseEntity<?> createAdmin(@RequestBody Map<String, Object> payload) {

		if (adminService.findByEmail(payload.get("email").toString()) != null) { // มี email
			System.out.println("Email ซ้ำกับคนอื่น");
			return new ResponseEntity<>(HttpStatus.CONFLICT);

		} else { // ไม่มี Email สร้าง Admin ได้
			Admin admin = new Admin();

			admin.setEmail(payload.get("email").toString());
			admin.setPassword(passwordEncoder.encode(payload.get("password").toString()));
			admin.setPolicy("U");
			admin.setRole(Role.ADMIN);
			Date date2 = new Date();
			SimpleDateFormat formatterr = new SimpleDateFormat("yyyy/MM/dd ", Locale.ENGLISH);
			formatterr.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
			String strDate2 = formatterr.format(date2);
			admin.setRegisDate(strDate2);

			adminRepo.save(admin);

			Company company = new Company(null, null, null, admin.getId());
			companyRepo.save(company);

			return new ResponseEntity<>("success", HttpStatus.OK);

		}

	}

	@PostMapping("/api/admin/getAddress")
	public ResponseEntity<?> getAddress(@RequestBody Map<String, Object> payload) {

		Long userId = Long.valueOf((Integer) payload.get("userId"));

		Company company = companyRepo.findByUserId(userId);
		if (company != null) {
			return new ResponseEntity<>(company, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("error", HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/api/admin/updateAddressCompany")
	public ResponseEntity<?> createAddressCompany(@RequestBody Map<String, Object> payload) {

		Long userId = Long.valueOf((Integer) payload.get("userId"));

		Company company = companyRepo.findByUserId(userId);
		if (company != null) {
			company.setAddress((String) payload.get("address"));
			company.setAddresPhone((String) payload.get("addressPhone"));
			company.setPhone((String) payload.get("phone"));
			if (companyRepo.save(company) != null) {
				return new ResponseEntity<>("success", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("error", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>("error", HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/api/admin/testgetdata")
	public ResponseEntity<?> testgetdata() {
		
		return new ResponseEntity<>("success", HttpStatus.OK); 
		
	}


	/* ---------------Register------------------ */
	/*
	 * @PostMapping("/api/user/registration") public ResponseEntity<?>
	 * register(@RequestBody User user){ if(userService.findByEmail(user.getEmail())
	 * != null){ return new ResponseEntity<>(HttpStatus.CONFLICT); }
	 * user.setRole(Role.USER); Date date2 = new Date(); SimpleDateFormat formatterr
	 * = new SimpleDateFormat("yyyy/MM/dd ", Locale.ENGLISH);
	 * formatterr.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok")); String strDate2
	 * = formatterr.format(date2); user.setRegisDate(strDate2);
	 * 
	 * return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
	 * }
	 */

	// Admin Regis

}
