package com.server.intranet.approval.controller;

import com.server.intranet.approval.dto.ApprovalFormResponseDTO;
import com.server.intranet.approval.dto.ApprovalRequestDTO;
import com.server.intranet.approval.dto.ApprovalResponseDTO;
import com.server.intranet.approval.service.impl.ApprovalServiceImpl;
import com.server.intranet.global.util.SecurityUtil;
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
@CrossOrigin(origins = "*")
public class ApprovalController {

    private final ApprovalServiceImpl approvalService;

    @GetMapping("")
    public ResponseEntity<Map<String,Object>> selectApprovalMain() throws Exception{
        System.out.println("전자 " + SecurityUtil.getCurrentUserId());
        Map<String,Object> data = approvalService.selectApprovalMain();
        Map<String,Object> map = new HashMap<>();
        map.put("data",data);
        map.put("code", "SUCCESS");
        map.put("msg", "조회가 완료되었습니다.");

        return ResponseEntity.ok().body(map);
    }


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
     * @param request dto
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
     * methodName : selectApprovalList
     * author : YunJae Lee
     * description : 전자결재 목록 조회
     *
     * @param category
     * @return response entity
     * @throws Exception the exception
     */
    @GetMapping("/draft/{category}")
    public ResponseEntity<Map<String,Object>> selectApprovalList(@PathVariable("category") String category) throws Exception{
        List<ApprovalResponseDTO> data = approvalService.selectApprovalList(category);

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
     * @param approval id
     * @param type
     * @return response entity
     * @throws Exception the exception
     */
    @GetMapping("/draft/doc/{approvalId}")
    public ResponseEntity<Map<String,Object>> selectApprovalDetail(@PathVariable("approvalId") Long approvalId, @RequestParam(value = "type", required = false) String type) throws Exception{
        ApprovalResponseDTO data = approvalService.selectApprovalDetail(approvalId, type);

        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("code", "SUCCESS");
        map.put("msg", "저장이 완료되었습니다.");

        return ResponseEntity.ok().body(map);
    }

    /**
     * methodName : updateApproval
     * author : YunJae Lee
     * description : 임시 저장소에서 결재 요청
     *
     * @param request dto
     * @return response entity
     * @throws Exception the exception
     */
    @PostMapping("/draft/doc")
    public ResponseEntity<Map<String,Object>> updateApproval(@RequestBody ApprovalRequestDTO requestDTO) throws Exception{
        ApprovalResponseDTO data = approvalService.updateApproval(requestDTO);

        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("code", "SUCCESS");
        map.put("msg", "저장이 완료되었습니다.");

        return ResponseEntity.ok().body(map);
    }

    /**
     * methodName : updateApprovalCancel
     * author : YunJae Lee
     * description : 상신 취소
     *
     * @param request dto
     * @return response entity
     * @throws Exception the exception
     */
    @PutMapping("/draft/doc")
    public ResponseEntity<Map<String,Object>> updateApprovalCancel(@RequestBody ApprovalRequestDTO requestDTO) throws Exception{
        Long data = approvalService.updateApprovalCancel(requestDTO);

        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("code", "SUCCESS");
        map.put("msg", "저장이 완료되었습니다.");

        return ResponseEntity.ok().body(map);
    }

    /**
     * methodName : updateApprovalRejection
     * author : YunJae Lee
     * description : 결재 및 반려
     *
     * @param request dto
     * @return response entity
     * @throws Exception the exception
     */
    @PutMapping("/draft")
    public ResponseEntity<Map<String,Object>> updateApprovalRejection(@RequestBody ApprovalRequestDTO requestDTO) throws Exception{
        System.out.println(requestDTO);
        Long data = approvalService.updateApprovalRejection(requestDTO);

        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("code", "SUCCESS");
        map.put("msg", "저장이 완료되었습니다.");

        return ResponseEntity.ok().body(map);
    }
}
