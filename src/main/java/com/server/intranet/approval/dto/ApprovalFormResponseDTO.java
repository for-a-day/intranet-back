package com.server.intranet.approval.dto;

import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.server.intranet.approval.dto
 * fileName       : ApprovalFormResponseDTO
 * author         : gladious
 * date           : 2024-06-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-27        gladious       최초 생성
 */
@Data
@Builder
public class ApprovalFormResponseDTO {
    
    //양식 아이디
    private Long formId;

    //양식 제목
    private String subject;

    //양식 내용
    private String content;

    //사용 여부
    private String status;
    
    //전결 옵션
    private String option;

    //권한
    private String auth;

    //선결재 사용여부
    private String preApproval;

    //저장소 카테고리 아이디
    private Long storageId;

    //저장소 카테고리 이름
    private String storageName;

    
}
