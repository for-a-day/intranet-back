package com.server.intranet.approval.service.impl;

import com.server.intranet.approval.dto.ApprovalFormResponseDTO;
import com.server.intranet.approval.entity.ApprovalForm;
import com.server.intranet.approval.entity.Storage;
import com.server.intranet.approval.repository.ApprovalFormRepository;
import com.server.intranet.approval.repository.StorageRepository;
import com.server.intranet.approval.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Approval service.
 */
@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
    //양식 폼 레포지토리
    private final ApprovalFormRepository formRepository;
    private final StorageRepository storageRepository;

    /**
     * methodName : selectFormList
     * author : YunJae Lee
     * description : 양식 폼 목록 불러오기
     *
     *
     * @return list
     * @throws Exception the exception
     */
    @Override
    public List<ApprovalFormResponseDTO> selectFormList() throws Exception {
        List<ApprovalForm> list = formRepository.findAll();
        List<ApprovalFormResponseDTO> formList = new ArrayList<>();
        for(ApprovalForm form : list){
            if(!list.isEmpty()){
                ApprovalFormResponseDTO responseDTO = ApprovalFormResponseDTO.builder()
                        .formId(form.getFormId())
                        .subject(form.getSubject())
                        .content(form.getContent())
                        .auth(form.getAuth())
                        .preApproval(form.getPreApproval())
                        .option(form.getOption())
                        .status(form.getStatus())
                        .storageId(form.getStorageId().getStorageId())
                        .storageName(form.getStorageId().getName())
                        .build();
                formList.add(responseDTO);
            }
        }

        return formList;
    }


    /**
     * methodName : selectStorageList
     * author : YunJae Lee
     * description : 저장소 목록 조회
     *
     *
     * @return list
     * @throws Exception the exception
     */
    @Override
    public List<ApprovalFormResponseDTO> selectStorageList() throws Exception {
        List<Storage> list = storageRepository.findAll();
        List<ApprovalFormResponseDTO> storageList = new ArrayList<>();
        for(Storage storage : list){
            if(!list.isEmpty()){
                ApprovalFormResponseDTO responseDTO = ApprovalFormResponseDTO.builder()
                        .storageId(storage.getStorageId())
                        .storageName(storage.getName())
                        .build();
                storageList.add(responseDTO);
            }
        }

        return storageList;
    }
}
