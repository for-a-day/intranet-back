package com.server.intranet.global.controller;

import com.server.intranet.global.config.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, String>> validateToken(@RequestHeader("Authorization") String token) {
        try {
            String actualToken = token.replace("Bearer ", "");
            Claims claims = jwtUtil.parseToken(actualToken);
            Map<String, String> response = new HashMap<>();
            response.put("status", "valid");
            return ResponseEntity.ok(response);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(401).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
    }
}
