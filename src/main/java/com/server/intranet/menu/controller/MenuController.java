package com.server.intranet.menu.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.server.intranet.menu.dto.MenuRequestDto;
import com.server.intranet.menu.dto.MenuResponseDto;
import com.server.intranet.menu.entity.MenuEntity;
import com.server.intranet.menu.service.MenuService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "http://localhost:3000")
public class MenuController {
	private final MenuService menuService;
	
	public MenuController(MenuService menuService) {
		this.menuService = menuService;
	}
	
	//목록
	@GetMapping("/menu")
	public ResponseEntity<Map<String,Object>> menuList(){
		List<MenuResponseDto> menuList = menuService.menuList();
		Map<String, MenuResponseDto> menuMap = menuList.stream()
				.collect(Collectors.toMap(MenuResponseDto::getMenu_id, Function.identity()));
		
		Map<String, Object> response = new HashMap<>();
		response.put("data", menuMap);
		response.put("message", "메뉴 목록 생성 완료");
		response.put("status", "success");
		
		return ResponseEntity.ok(response);
	}

	// 등록
	@PostMapping("/menu")
	public ResponseEntity<Map<String, Object>> insert(
			@RequestParam("menu_id") String menuId,
            @RequestParam("menu_name") String menuName,
            @RequestParam("menu_price") int menuPrice,
            @RequestParam("menu_recipe") String menuRecipe,
            @RequestParam("menu_origin_price") int menuOriginPrice,
            @RequestParam("menu_end") int menuEnd,
            @RequestParam(value = "menu_image", required = false) MultipartFile menuImage) {
					
		try {
			// MenuEntity를 생성하고 저장
	        MenuRequestDto menuRequestDto = new MenuRequestDto(menuId, menuName, menuPrice, menuRecipe, menuOriginPrice, menuEnd, menuImage);
	        MenuEntity menu = menuService.insert(menuRequestDto);
			return new ResponseEntity<>(Collections.singletonMap("message", "메뉴 등록 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Collections.singletonMap("message", "메뉴 등록 실패"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 수정
	@PutMapping("/menu/{menu_id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable String menu_id, @RequestBody MenuRequestDto menuRequestDto) {
		
		if(!menu_id.equals(menuRequestDto.getMenu_id())) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", "메뉴 아이디가 틀림");
			ResponseEntity.badRequest().body(error);
		} 
			try {
				MenuEntity menu = menuService.edit(menuRequestDto, menu_id);
				return new ResponseEntity<>(Collections.singletonMap("message", "메뉴 수정 성공"), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(Collections.singletonMap("message", "메뉴 수정 실패"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	// 삭제
	@PutMapping("/menu/delete/{menu_id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable String menu_id, @RequestBody MenuRequestDto menuRequestDto) {
		
		if(!menu_id.equals(menuRequestDto.getMenu_id())) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", "메뉴 아이디가 틀림");
			ResponseEntity.badRequest().body(error);
		} 
			try {
				MenuEntity menu = menuService.delete(menuRequestDto, menu_id);
				return new ResponseEntity<>(Collections.singletonMap("message", "메뉴 미판매 성공"), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(Collections.singletonMap("message", "메뉴 미판매 실패"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}

}
