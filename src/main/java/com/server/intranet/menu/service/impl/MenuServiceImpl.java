package com.server.intranet.menu.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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
						menu.getMenu_end()
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
