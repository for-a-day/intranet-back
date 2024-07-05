package com.server.intranet.resource.controller;

import com.server.intranet.global.config.JwtUtil;
import com.server.intranet.resource.dto.*;
import com.server.intranet.resource.entity.EmployeeEntity;
import com.server.intranet.resource.service.impl.ResourceServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("app/employees")
public class ResourceController {

    private final ResourceServiceImpl resourceServiceImpl;
    private final JwtUtil jwtUtil;

    @Autowired
    public ResourceController(ResourceServiceImpl resourceServiceImpl, JwtUtil jwtUtil) {
        this.resourceServiceImpl = resourceServiceImpl;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllEmployeesWithDetails() {
        List<ResourceResponseDTO> employees = resourceServiceImpl.getAllEmployees();
        List<DepartmentResponseDTO> departments = resourceServiceImpl.getAllDepartments();
        List<AuthorityResponseDTO> authorities = resourceServiceImpl.getAllAuthoritys();
        List<LevelResponseDTO> levels = resourceServiceImpl.getAllLevels();

        Map<String, Object> response = new HashMap<>();
        response.put("employees", employees);
        response.put("departments", departments);
        response.put("authorities", authorities);
        response.put("levels", levels);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/register")
    public ResponseEntity<Map<String, Object>> showEmployeeRegistrationForm() {

        List<DepartmentResponseDTO> departments = resourceServiceImpl.getAllDepartments();
        List<AuthorityResponseDTO> authorities = resourceServiceImpl.getAllAuthoritys();
        List<LevelResponseDTO> levels = resourceServiceImpl.getAllLevels();

        Map<String, Object> response = new HashMap<>();
        response.put("departments", departments);
        response.put("authorities", authorities);
        response.put("levels", levels);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>>registerEmployee(@RequestBody ResourceRequestDTO employeeDTO) {

        boolean isRegistered = resourceServiceImpl.registerEmployee(employeeDTO);

        Map<String, Object> response = new HashMap<>();

        if (isRegistered) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Map<String, Object>> updateEmployee(@PathVariable String employeeId, @RequestBody ResourceRequestDTO employeeDTO) {

        boolean isUpdated = resourceServiceImpl.updateEmployee(Long.valueOf(employeeId), employeeDTO);

        Map<String, Object> response = new HashMap<>();

        if (isUpdated) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(500).body(response);
        }
    }

    //퇴사테이블에 추가
    @PostMapping("/exit")
    public ResponseEntity<Map<String, Object>> registerExitEmployee(@RequestBody ExitEmployeeRequestDTO exitEmployeeRequestDTO){

        System.out.println(exitEmployeeRequestDTO.toString());

        boolean isRegistered = resourceServiceImpl.registerExitEmployee(exitEmployeeRequestDTO);

        Map<String, Object> response = new HashMap<>();

        if (isRegistered) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable String employeeId){

        boolean isDeleted = resourceServiceImpl.deleteEmployee(Long.valueOf(employeeId));

        Map<String, Object> response = new HashMap<>();

        if (isDeleted) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(500).body(response);
        }
        
    }
    
    @GetMapping("/token")
    public ResponseEntity<Map<String, Object>> loginToken(HttpServletRequest request) {
    	String token = request.getHeader("Authorization").substring(7);
    	Long employeeId = jwtUtil.extractEmployeeId(token);
    	
    	EmployeeEntity employee = resourceServiceImpl.loginToken(employeeId);
    	if(employee == null) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    	}
    	
    	Map<String, Object> response = new HashMap<>();
    	response.put("employee", employee);
    	return ResponseEntity.ok(response);
    }





}
