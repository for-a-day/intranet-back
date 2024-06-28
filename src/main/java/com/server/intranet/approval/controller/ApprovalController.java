package com.server.intranet.approval.controller;

import com.server.intranet.approval.dto.ApprovalFormResponseDTO;
import com.server.intranet.approval.service.impl.ApprovalServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/approval")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ApprovalController {

    private final ApprovalServiceImpl approvalService;

    @GetMapping("/form")
    public ResponseEntity<Map<String, Object>> selectFormList() throws Exception{
        List<ApprovalFormResponseDTO> formList = approvalService.selectFormList();
        List<ApprovalFormResponseDTO> storageList = approvalService.selectStorageList();

        Map<String, Object> data = new HashMap<>();
        data.put("formList", formList);
        data.put("storageList", storageList);

        Map<String, Object> map = new HashMap<>();
        map.put("data",data);
        map.put("code", "SUCCESS");
        map.put("msg", "조회가 완료되었습니다.");

        return ResponseEntity.ok().body(map);
    }
}
