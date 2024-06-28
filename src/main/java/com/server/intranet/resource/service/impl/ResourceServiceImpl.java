package com.server.intranet.resource.service.impl;

import com.server.intranet.resource.dto.*;
import com.server.intranet.resource.entity.*;
import com.server.intranet.resource.repository.*;
import com.server.intranet.resource.service.ResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final DepartmentRepository departmentRepository;
    private final AuthorityRepository authorityRepository;
    private final LevelRepository levelRepository;

    private final ExitEmployeeRepository exitEmployeeRepository;

    @Autowired
    public ResourceServiceImpl(ResourceRepository resourceRepository, DepartmentRepository departmentRepository, AuthorityRepository authorityRepository, LevelRepository levelRepository, ExitEmployeeRepository exitEmployeeRepository) {
        this.resourceRepository = resourceRepository;
        this.departmentRepository = departmentRepository;
        this.authorityRepository = authorityRepository;
        this.levelRepository = levelRepository;
        this.exitEmployeeRepository = exitEmployeeRepository;
    }

    public List<ResourceResponseDTO> getAllEmployees() {
        List<EmployeeEntity> employees = resourceRepository.findAll();
        return employees.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<DepartmentResponseDTO> getAllDepartments() {
        List<DepartmentEntity> department = departmentRepository.findAll();
        return department.stream().map(this::convertToDepartmentDTO).collect(Collectors.toList());
    }

    public List<AuthorityResponseDTO> getAllAuthoritys() {
        List<AuthorityEntity> authority = authorityRepository.findAll();
        return authority.stream().map(this::convertToAuthorityDTO).collect(Collectors.toList());
    }

    public List<LevelResponseDTO> getAllLevels() {
        List<LevelEntity> level = levelRepository.findAll();
        return level.stream().map(this::convertToLevelDTO).collect(Collectors.toList());
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

    private DepartmentResponseDTO convertToDepartmentDTO(DepartmentEntity department){
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setDepartmentCode(department.getDepartmentCode());
        dto.setDepartmentName(department.getDepartmentName());
        return dto;
    }

    private AuthorityResponseDTO convertToAuthorityDTO(AuthorityEntity authority){
        AuthorityResponseDTO dto = new AuthorityResponseDTO();
        dto.setAuthorityCode(authority.getAuthorityCode());
        dto.setAuthorityName(authority.getAuthorityName());
        return dto;
    }

    private LevelResponseDTO convertToLevelDTO(LevelEntity level){
        LevelResponseDTO dto = new LevelResponseDTO();
        dto.setLevelCode(level.getLevelCode());
        dto.setLevelName(level.getLevelName());
        return dto;
    }

    public boolean registerEmployee(ResourceRequestDTO employeeDTO) {
        // DTO에서 Entity로 변환

        EmployeeEntity employeeEntity = new EmployeeEntity();

        employeeEntity.setEmployeeId(employeeDTO.getEmployeeId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String formattedBirth = dateFormat.format(employeeDTO.getBirth());
        employeeEntity.setEmployeePassword(formattedBirth);

        employeeEntity.setName(employeeDTO.getName());
        employeeEntity.setGender(employeeDTO.getGender());
        employeeEntity.setBirth(employeeDTO.getBirth());
        employeeEntity.setDateEmployment(employeeDTO.getDateEmployment());
        employeeEntity.setContact(employeeDTO.getContact());
        employeeEntity.setAddress(employeeDTO.getAddress());
        employeeEntity.setEmailAddress(employeeDTO.getEmailAddress());
        employeeEntity.setEmploymentStatus(employeeDTO.getEmploymentStatus());

        LevelEntity levelEntity = levelRepository.findById(employeeDTO.getLevelCode()).orElse(null);
        employeeEntity.setLevel(levelEntity);

        DepartmentEntity departmentEntity = departmentRepository.findById(employeeDTO.getDepartmentCode()).orElse(null);
        employeeEntity.setDepartment(departmentEntity);

        AuthorityEntity authorityEntity = authorityRepository.findById(employeeDTO.getAuthorityCode()).orElse(null);
        employeeEntity.setAuthority(authorityEntity);

        resourceRepository.save(employeeEntity);
        return true; // 성공 여부에 따라 적절히 처리
    }

    public boolean updateEmployee(Long employeeId, ResourceRequestDTO employeeDTO) {
        try {
            EmployeeEntity employeeEntity = resourceRepository.findById(employeeId).orElse(null);
            if (employeeEntity != null) {
                BeanUtils.copyProperties(employeeDTO, employeeEntity);
                resourceRepository.save(employeeEntity);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean registerExitEmployee(ExitEmployeeRequestDTO exitEmployeeRequestDTO) {
        // DTO에서 Entity로 변환

        ExitEmployeeEntity exitEmployeeEntity = new ExitEmployeeEntity();

        exitEmployeeEntity.setEmployeeId(exitEmployeeRequestDTO.getEmployeeId());
        exitEmployeeEntity.setName(exitEmployeeRequestDTO.getName());
        exitEmployeeEntity.setGender(exitEmployeeRequestDTO.getGender());
        exitEmployeeEntity.setBirth(exitEmployeeRequestDTO.getBirth());
        exitEmployeeEntity.setDateEmployment(exitEmployeeRequestDTO.getDateEmployment());
        exitEmployeeEntity.setContact(exitEmployeeRequestDTO.getContact());
        exitEmployeeEntity.setEmailAddress(exitEmployeeRequestDTO.getEmailAddress());
        exitEmployeeEntity.setLevelName(exitEmployeeRequestDTO.getLevelName());
        exitEmployeeEntity.setDepartmentName(exitEmployeeRequestDTO.getDepartmentName());
        exitEmployeeEntity.setDateRetirement(exitEmployeeRequestDTO.getDateRetirement());
        exitEmployeeEntity.setReasonRetirement(exitEmployeeRequestDTO.getReasonRetirement());

        exitEmployeeRepository.save(exitEmployeeEntity);
        return true; // 성공 여부에 따라 적절히 처리
    }

    public boolean deleteEmployee(Long employeeId){

        try {
            resourceRepository.deleteById(employeeId);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
