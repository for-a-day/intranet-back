package com.server.intranet.resource.controller;

import com.server.intranet.resource.dto.ResourceResponseDTO;
import com.server.intranet.resource.service.impl.ResourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class ResourceController {

    private final ResourceServiceImpl resourceServiceImpl;

    @Autowired
    public ResourceController(ResourceServiceImpl resourceServiceImpl) {
        this.resourceServiceImpl = resourceServiceImpl;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ResourceResponseDTO>> getAllEmployees() {
        List<ResourceResponseDTO> employees = resourceServiceImpl.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/register")
    public String showEmployeeRegistrationForm() {
        return "employees/register"; // register.html과 같은 사원 등록 폼의 뷰 이름
    }
}
