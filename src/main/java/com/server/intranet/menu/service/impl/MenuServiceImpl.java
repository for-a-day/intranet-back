package com.server.intranet.menu.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.server.intranet.menu.dto.MenuRequestDto;
import com.server.intranet.menu.dto.MenuResponseDto;
import com.server.intranet.menu.entity.MenuEntity;
import com.server.intranet.menu.repository.MenuRepository;
import com.server.intranet.menu.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{
	
	private final MenuRepository menuRepository;
	
	public MenuServiceImpl(MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}
	
    @Value("${upload.directory}")
    private String uploadDirectory;
	
	// 목록
	@Override
	public List<MenuResponseDto> menuList() {
		return menuRepository.findAll().stream()
				.map(menu -> new MenuResponseDto(
						menu.getMenu_id(),
						menu.getMenu_name(),
						menu.getMenu_price(),
						menu.getMenu_recipe(),
						menu.getMenu_origin_price(),
						menu.getMenu_end(),
						menu.getMenu_image()
						)).collect(Collectors.toList());
	}

	// 등록
	@Override
	public MenuEntity insert(MenuRequestDto dto) throws Exception {
		MenuEntity menu = new MenuEntity();
		menu.setMenu_id(dto.getMenu_id());
		menu.setMenu_name(dto.getMenu_name());
		menu.setMenu_price(dto.getMenu_price());
		menu.setMenu_recipe(dto.getMenu_recipe());
		menu.setMenu_origin_price(dto.getMenu_origin_price());
		menu.setMenu_end(dto.getMenu_end());
		
		MultipartFile file = dto.getMenu_image();
		
		// 파일이 없는 경우를 대비
		if(file != null && !file.isEmpty()) {
			
			try {			
			// 파일 원본이름 가져오기
			String fileName = file.getOriginalFilename();
			// 파일 경로 설정
			Path uploadPath = Paths.get(uploadDirectory);
			System.out.println("1. 파일 경로 설정");
			
				byte[] bytes = file.getBytes();
				// 파일 저장
				Path filePath = uploadPath.resolve(fileName);
				Files.write(filePath, bytes);
				System.out.println("2. 파일 저장");
				// 파일 경로 설정
				String fileUrl = fileName;
				// 파일 경로를 엔티티에 설정
				menu.setMenu_image(fileUrl);
				System.out.println("3. 파일 경로 설정");
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("File upload failed: " + e.getMessage());
			}
		}
		System.out.println("4. 레포지터리 저장");
		return menuRepository.save(menu);
	}
	
	// 수정
	@Override
	public MenuEntity edit(MenuRequestDto dto, String menu_id) throws Exception {
		try {
			MenuEntity menu = menuRepository.findById(menu_id)
					.orElseThrow(() -> new RuntimeException("관련 메뉴 ID가 없어요"));
			menu.setMenu_id(dto.getMenu_id());
			menu.setMenu_name(dto.getMenu_name());
			menu.setMenu_price(dto.getMenu_price());
			menu.setMenu_recipe(dto.getMenu_recipe());
			menu.setMenu_origin_price(dto.getMenu_origin_price());
			menu.setMenu_end(dto.getMenu_end());
			
			return menuRepository.save(menu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("메뉴 수정 중 에러 발생");
		}
	}

	// 삭제
	@Override
	public MenuEntity delete(MenuRequestDto dto, String menu_id) throws Exception {
		try {
			MenuEntity menu = menuRepository.findById(menu_id)
					.orElseThrow(() -> new RuntimeException("관련 메뉴 ID가 없어요"));
			menu.setMenu_id(dto.getMenu_id());
			menu.setMenu_name(dto.getMenu_name());
			menu.setMenu_price(dto.getMenu_price());
			menu.setMenu_recipe(dto.getMenu_recipe());
			menu.setMenu_origin_price(dto.getMenu_origin_price());
			menu.setMenu_end(dto.getMenu_end());
			
			return menuRepository.save(menu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("메뉴 수정 중 에러 발생");
		}
	}
}
