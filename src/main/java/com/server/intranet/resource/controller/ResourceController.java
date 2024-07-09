package com.server.intranet.resource.controller;

import com.server.intranet.global.util.SecurityUtil;
import com.server.intranet.resource.dto.*;
import com.server.intranet.resource.service.impl.ResourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("app/employees")
public class ResourceController {

    private final ResourceServiceImpl resourceServiceImpl;

    @Autowired
    public ResourceController(ResourceServiceImpl resourceServiceImpl) {
        this.resourceServiceImpl = resourceServiceImpl;
    }

    private boolean isHRDepartment() {
        String department = SecurityUtil.getCurrentUserDepartmentName();
        return "인사부".equals(department);
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllEmployeesWithDetails() {
        if (!isHRDepartment()) {
            return ResponseEntity.status(403).body(null); // 접근 금지 상태 반환
        }

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
        if (!isHRDepartment()) {
            return ResponseEntity.status(403).body(null); // 접근 금지 상태 반환
        }

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
        if (!isHRDepartment()) {
            return ResponseEntity.status(403).body(null); // 접근 금지 상태 반환
        }

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
        if (!isHRDepartment()) {
            return ResponseEntity.status(403).body(null); // 접근 금지 상태 반환
        }

        boolean isUpdated = resourceServiceImpl.updateEmployee(Long.valueOf(employeeId), employeeDTO);

        Map<String, Object> response = new HashMap<>();

        System.out.println("뭔데~~~~~~~~₩" + employeeDTO.toString());
        if (isUpdated) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(500).body(response);
        }
    }

    //퇴사테이블에 추가
    @PostMapping("/exit")
    public ResponseEntity<Map<String, Object>> registerExitEmployee(@RequestBody ExitEmployeeRequestDTO exitEmployeeRequestDTO){
        if (!isHRDepartment()) {
            return ResponseEntity.status(403).body(null); // 접근 금지 상태 반환
        }

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
        if (!isHRDepartment()) {
            return ResponseEntity.status(403).body(null); // 접근 금지 상태 반환
        }

        boolean isDeleted = resourceServiceImpl.deleteEmployee(Long.valueOf(employeeId));

        Map<String, Object> response = new HashMap<>();

        if (isDeleted) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping("/update-password")
    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        boolean isUpdated = resourceServiceImpl.updatePassword(updatePasswordDTO);
        Map<String, Object> response = new HashMap<>();
        if (isUpdated) {
            response.put("message", "Password updated successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Current password is incorrect.");
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping("/current")
    public ResponseEntity<Map<String, String>> getCurrentUserInfo() {
        Map<String, String> currentUserInfo = new HashMap<>();
        currentUserInfo.put("department", SecurityUtil.getCurrentUserDepartmentName());
        currentUserInfo.put("name", SecurityUtil.getCurrentUserName());
        currentUserInfo.put("level", SecurityUtil.getCurrentUserLevelName());
        currentUserInfo.put("id", SecurityUtil.getCurrentUserId());
        currentUserInfo.put("gender", SecurityUtil.getCurrentUserGender());
        currentUserInfo.put("birth", SecurityUtil.getCurrentUserBirth());
        currentUserInfo.put("dateEmployment", SecurityUtil.getCurrentUserDateEmployment());
        currentUserInfo.put("contact", SecurityUtil.getCurrentUserContact());
        currentUserInfo.put("address", SecurityUtil.getCurrentUserAddress());
        currentUserInfo.put("emailAddress", SecurityUtil.getCurrentUserEmailAddress());
        currentUserInfo.put("employmentStatus", SecurityUtil.getCurrentUserEmploymentStatus());

        return ResponseEntity.ok(currentUserInfo);
    }

}
