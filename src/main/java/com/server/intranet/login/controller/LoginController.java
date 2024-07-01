package com.server.intranet.login.controller;

import com.server.intranet.login.service.impl.LoginServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {

    private final LoginServiceImpl loginServiceImpl;

    public LoginController(LoginServiceImpl loginServiceImpl) {
        this.loginServiceImpl = loginServiceImpl;
    }



    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        Long employeeId = Long.valueOf(loginData.get("employeeId"));
        String employeePassword = loginData.get("employeePassword");

        Map<String, Object> result = loginServiceImpl.login(employeeId, employeePassword);

        if (result == null) {
            return ResponseEntity.badRequest().body(null); // 로그인 실패 시 400 에러 반환
        }

        return ResponseEntity.ok(result); // 로그인 성공 시 JWT 토큰 및 사용자 정보 반환
    }
}
