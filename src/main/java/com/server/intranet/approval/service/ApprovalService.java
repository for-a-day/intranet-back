package com.server.intranet.approval.service;

import com.server.intranet.approval.dto.ApprovalFormResponseDTO;
import com.server.intranet.approval.dto.ApprovalRequestDTO;
import com.server.intranet.approval.dto.ApprovalResponseDTO;

import java.util.List;

public interface ApprovalService {
    public List<ApprovalFormResponseDTO> selectFormList() throws Exception;
    public List<ApprovalFormResponseDTO> selectStorageList() throws Exception;
    public ApprovalResponseDTO createApproval(ApprovalRequestDTO requestDTO) throws Exception;
    public ApprovalResponseDTO selectApprovalDetail(Long approvalId) throws Exception;
}
