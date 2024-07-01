package com.server.intranet.franchisee.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.intranet.franchisee.dto.FranchiseeRequestDto;
import com.server.intranet.franchisee.dto.FranchiseeResponsetDto;
import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.franchisee.service.FranchiseeService;

@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "http://localhost:3000")
public class FranchiseeController {
	private final FranchiseeService franchiseeService;
	
	public FranchiseeController(FranchiseeService franchiseeService) {
		this.franchiseeService = franchiseeService;
	}
	
	// 목록
	@GetMapping("/store")
	public ResponseEntity<Map<String, Object>> franList() {		
	    List<FranchiseeResponsetDto> franList = franchiseeService.franList();
	    Map<String, FranchiseeResponsetDto> franMap = franList.stream()
	            .collect(Collectors.toMap(FranchiseeResponsetDto::getFranchiseeId, Function.identity()));
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("data", franMap);
	    response.put("message", "가맹점 목록 생성 완료");
	    response.put("status", "success");
	    
	    return ResponseEntity.ok(response);
	}
	
	// 등록
	@PostMapping("/store")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody FranchiseeRequestDto franchiseeRequestDto){
		try {
			FranchiseeEntity insert = franchiseeService.insert(franchiseeRequestDto);
			return new ResponseEntity<>(Collections.singletonMap("message", "등록 : 성공"), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(Collections.singletonMap("message", "Insert failed!"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 수정
	@PutMapping("/store/{franchiseeId}")
	public ResponseEntity<Map<String, Object>> editList(@PathVariable String franchiseeId,
													    @RequestBody FranchiseeRequestDto franchiseeRequestDto){
		if(!franchiseeId.equals(franchiseeRequestDto.getFranchiseeId())) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("error", "경로 내 가맹점 아이디가 적합하지 않다잉");
		    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }															
			try {
				FranchiseeEntity updateFran = franchiseeService.edit(franchiseeRequestDto);
				return new ResponseEntity<>(Collections.singletonMap("message", "수정 : 성공"), HttpStatus.OK);
			} catch (Exception e) {
				 return new ResponseEntity<>(Collections.singletonMap("message", "Update failed!"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	//삭제
	@DeleteMapping("/store/{franchiseeId}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable String franchiseeId){
		franchiseeService.delete(franchiseeId);
		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
        response.put("message", "Franchisee deleted successfully.");
        return ResponseEntity.ok(response);
	}
	
}
