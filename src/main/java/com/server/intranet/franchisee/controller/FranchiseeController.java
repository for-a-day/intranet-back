package com.server.intranet.franchisee.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.intranet.franchisee.dto.FranchiseeResponsetDto;
import com.server.intranet.franchisee.service.FranchiseeService;

@RestController
@RequestMapping("/app/store")
@CrossOrigin(origins = "http://localhost:3000")
public class FranchiseeController {
	private final FranchiseeService franchiseeService;
	
	public FranchiseeController(FranchiseeService franchiseeService) {
		this.franchiseeService = franchiseeService;
	}
	
	@GetMapping
	public List<FranchiseeResponsetDto> franList(){		
		List<FranchiseeResponsetDto> franList = franchiseeService.franList();
		return franList;		
	}
}
