package com.server.intranet.approval.service;

import com.server.intranet.approval.dto.ApprovalFormResponseDTO;

import java.util.List;

public interface ApprovalService {
    public List<ApprovalFormResponseDTO> selectFormList() throws Exception;
    public List<ApprovalFormResponseDTO> selectStorageList() throws Exception;
}
