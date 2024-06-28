package com.server.intranet.resource.service.impl;

import com.server.intranet.resource.dto.ResourceResponseDTO;
import com.server.intranet.resource.entity.EmployeeEntity;
import com.server.intranet.resource.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl {

    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public List<ResourceResponseDTO> getAllEmployees() {
        List<EmployeeEntity> employees = resourceRepository.findAll();
        return employees.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ResourceResponseDTO convertToDTO(EmployeeEntity employee) {
        ResourceResponseDTO dto = new ResourceResponseDTO();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setName(employee.getName());
        dto.setGender(employee.getGender());
        dto.setBirth(employee.getBirth());
        dto.setDateEmployment(employee.getDateEmployment());
        dto.setContact(employee.getContact());
        dto.setAddress(employee.getAddress());
        dto.setEmailAddress(employee.getEmailAddress());
        dto.setEmploymentStatus(employee.getEmploymentStatus());
        dto.setLevel(employee.getLevel().getLevelName());
        dto.setDepartment(employee.getDepartment().getDepartmentName());
        dto.setAuthority(employee.getAuthority().getAuthorityName());
        return dto;
    }
}
