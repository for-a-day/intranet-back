package com.server.intranet.login.service.impl;

import com.server.intranet.global.config.JwtUtil;
import com.server.intranet.login.repository.LoginRepository;
import com.server.intranet.resource.entity.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoginServiceImpl {

    private final LoginRepository loginRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginServiceImpl(LoginRepository loginRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
        this.loginRepository = loginRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, Object> login(Long employeeId, String employeePassword) {
        Optional<EmployeeEntity> employeeEntityOptional = loginRepository.findById(employeeId);

        if (employeeEntityOptional.isPresent()) {
            EmployeeEntity employee = employeeEntityOptional.get();

            if (bCryptPasswordEncoder.matches(employeePassword, employee.getEmployeePassword())) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("roles", employee.getAuthority().getAuthorityName());

                String token = jwtUtil.generateToken(String.valueOf(employeeId), claims);

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("employee", employee);

                return response;
            }
        }

        return null;
    }
}
