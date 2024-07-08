package com.server.intranet.approval.service;

import com.server.intranet.approval.dto.ApprovalFormResponseDTO;
import com.server.intranet.approval.dto.ApprovalRequestDTO;
import com.server.intranet.approval.dto.ApprovalResponseDTO;

import java.util.List;
import java.util.Map;

public interface ApprovalService {
    public Map<String,Object> selectApprovalMain() throws Exception;
    public List<ApprovalFormResponseDTO> selectFormList() throws Exception;
    public List<ApprovalFormResponseDTO> selectStorageList() throws Exception;
    public ApprovalResponseDTO createApproval(ApprovalRequestDTO requestDTO) throws Exception;
    public List<ApprovalResponseDTO> selectApprovalList(String category) throws Exception;
    public ApprovalResponseDTO selectApprovalDetail(Long approvalId, String type) throws Exception;
    public ApprovalResponseDTO updateApproval(ApprovalRequestDTO requestDTO) throws Exception;
    public Long updateApprovalCancel(ApprovalRequestDTO requestDTO) throws Exception;
    public Long updateApprovalRejection(ApprovalRequestDTO requestDTO) throws Exception;
}
