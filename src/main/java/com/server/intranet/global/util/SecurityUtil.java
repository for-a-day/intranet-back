package com.server.intranet.global.util;

import com.server.intranet.global.config.CustomUserDetails;
import com.server.intranet.resource.entity.EmployeeEntity;
import com.server.intranet.resource.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private static ResourceRepository employeeRepository;

    @Autowired
    public SecurityUtil(ResourceRepository employeeRepository) {
        SecurityUtil.employeeRepository = employeeRepository;
    }

    public static CustomUserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    public static EmployeeEntity getCurrentEmployee() {
        CustomUserDetails userDetails = getCurrentUserDetails();
        if (userDetails != null) {
            Long employeeId = userDetails.getEmployeeId();
            return employeeRepository.findById(employeeId).orElse(null);
        }
        return null;
    }

    public static String getCurrentUserDepartmentName() {
        EmployeeEntity employee = getCurrentEmployee();
        return (employee != null) ? employee.getDepartment().getDepartmentName() : null;
    }
    
    public static Long getCurrentUserDepartmentCode() { // 부서코드 반환 메소드 추가
        EmployeeEntity employee = getCurrentEmployee();
        return (employee != null) ? employee.getDepartment().getDepartmentCode() : null;
    }

    public static String getCurrentUserName() {
        EmployeeEntity employee = getCurrentEmployee();
        return (employee != null) ? employee.getName() : null;
    }

    public static String getCurrentUserLevelName() {
        EmployeeEntity employee = getCurrentEmployee();
        return (employee != null) ? employee.getLevel().getLevelName() : null;
    }

    public static String getCurrentUserId() {
        CustomUserDetails userDetails = getCurrentUserDetails();
        return (userDetails != null) ? userDetails.getUsername() : null;
    }

    public static String getCurrentUserGender() {
        EmployeeEntity employee = getCurrentEmployee();
        return (employee != null) ? employee.getGender() : null;
    }

    public static String getCurrentUserBirth() {
        EmployeeEntity employee = getCurrentEmployee();
        return (employee != null) ? employee.getBirth().toString() : null;
    }

    public static String getCurrentUserDateEmployment() {
        EmployeeEntity employee = getCurrentEmployee();
        return (employee != null) ? employee.getDateEmployment().toString() : null;
    }

    public static String getCurrentUserContact() {
        EmployeeEntity employee = getCurrentEmployee();
        return (employee != null) ? employee.getContact() : null;
    }

    public static String getCurrentUserAddress() {
        EmployeeEntity employee = getCurrentEmployee();
        return (employee != null) ? employee.getAddress() : null;
    }

    public static String getCurrentUserEmailAddress() {
        EmployeeEntity employee = getCurrentEmployee();
        return (employee != null) ? employee.getEmailAddress() : null;
    }

    public static String getCurrentUserEmploymentStatus() {
        EmployeeEntity employee = getCurrentEmployee();
        return (employee != null) ? employee.getEmploymentStatus() : null;
    }
}
