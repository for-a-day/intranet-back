package com.server.intranet.approval.controller;

import com.server.intranet.approval.dto.ApprovalFormResponseDTO;
import com.server.intranet.approval.dto.ApprovalRequestDTO;
import com.server.intranet.approval.dto.ApprovalResponseDTO;
import com.server.intranet.approval.service.impl.ApprovalServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Approval controller.
 */
@RestController
@RequestMapping("/app/approval")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ApprovalController {

    private final ApprovalServiceImpl approvalService;


    /**
     * methodName : selectFormList
     * author : YunJae Lee
     * description : 결재 양식 폼 목록 조회
     *
     * @return response entity
     * @throws Exception the exception
     */
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


    /**
     * methodName : createApproval
     * author : YunJae Lee
     * description : 전자결재 기안문 등록
     *
     * @param requestDTO
     * @return response entity
     * @throws Exception the exception
     */
    @PostMapping("/draft")
    public ResponseEntity<Map<String,Object>> createApproval(@RequestBody ApprovalRequestDTO requestDTO) throws Exception{
        ApprovalResponseDTO data = approvalService.createApproval(requestDTO);
        
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("code", "SUCCESS");
        map.put("msg", "저장이 완료되었습니다.");

        return ResponseEntity.ok().body(map);
    }

    /**
     * methodName : selectApprovalDetail
     * author : YunJae Lee
     * description : 전자결재 기안문 상세조회
     *
     * @param approvalId
     * @return response entity
     * @throws Exception the exception
     */
    @GetMapping("/draft/{approvalId}")
    public ResponseEntity<Map<String,Object>> selectApprovalDetail(@PathVariable("approvalId") Long approvalId) throws Exception{
        ApprovalResponseDTO data = approvalService.selectApprovalDetail(approvalId);

        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("code", "SUCCESS");
        map.put("msg", "저장이 완료되었습니다.");

        return ResponseEntity.ok().body(map);
    }
}
