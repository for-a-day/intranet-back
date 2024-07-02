package com.server.intranet.warning.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.intranet.closing.controller.ClosingController;
import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.warning.dto.WarningRequestDto;
import com.server.intranet.warning.dto.WarningResponseDto;
import com.server.intranet.warning.entity.WarningEntity;
import com.server.intranet.warning.service.WarningService;

@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "http://localhost:3000")
public class WarningController {
	private final WarningService warningService;
	
	private static final Logger logger = LoggerFactory.getLogger(WarningController.class);
	
	@Autowired
	public WarningController(WarningService warningService){
		this.warningService = warningService;
	}
	// 목록
	@GetMapping("/warn")
	public ResponseEntity<Map<String, Object>> warnList(){
		List<WarningResponseDto> warnList = warningService.warnList();
		List<Map<String, Object>> warnings = warnList.stream()
	            .map(warn -> {
	                Map<String, Object> warningMap = new HashMap<>();
	                warningMap.put("warningId", warn.getWarningId());
	                warningMap.put("warningReason", warn.getWarningReason());
	                
	                if (warn.getFranchisee_id() != null) {
	                    Map<String, Object> franchiseeMap = new HashMap<>();
	                    franchiseeMap.put("franchiseeId", warn.getFranchisee_id().getFranchiseeId());
	                    franchiseeMap.put("franchiseeName", warn.getFranchisee_id().getFranchiseeName());
	                    
	                    warningMap.put("franchisee", franchiseeMap);
	                }
	                
	                if (warn.getClosing_id() != null) {
	                    Map<String, Object> closingMap = new HashMap<>();
	                    closingMap.put("closingId", warn.getClosing_id().getClosingId());
	                    closingMap.put("closingName", warn.getClosing_id().getClosingName());
	                    
	                    warningMap.put("closing", closingMap);
	                }
	                
	                return warningMap;
	            })
	            .collect(Collectors.toList());
		
		Map<String, Object> response = new HashMap<>();
		response.put("data", warnings);
		response.put("message", "경고 목록 생성 완료");
		response.put("status", "success");
		
		return ResponseEntity.ok(response);
	}
	
	// 등록
	@PostMapping("/warn")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody WarningRequestDto warningRequestDto){
		try {
			WarningEntity insertWarn = warningService.insert(warningRequestDto);
			return new ResponseEntity<>(Collections.singletonMap("message", "경고등록 : 성공"), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(Collections.singletonMap("message", "경고등록 : 실패"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 수정 - 가맹점 삭제 시, update
	@PutMapping("/warn/{franchisee_id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable FranchiseeEntity franchisee_id, @RequestBody WarningRequestDto warningRequestDto) {
        try {
            WarningEntity updateWarning = warningService.update(franchisee_id, warningRequestDto);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "경고 수정 성공"));
        } catch (Exception e) {
        	 logger.error("폐점 등록 중 오류 발생: franchiseeId=" + franchisee_id + ", warningRequestDto=" + warningRequestDto, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "경고 수정 실패"));
        }
    }

	// 수정 - 가맹점 id 여부 확인하는 get
	@GetMapping("/warn/exist/{franchisee_id}")
	public ResponseEntity<?> warnExist(@PathVariable FranchiseeEntity franchisee_id){
		try {
			boolean exists = warningService.existId(franchisee_id);
			return ResponseEntity.ok(exists);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("경고 가맹ID 확인 중 오류 발생");
		}
	}
	
}
