package com.server.intranet.menu.service;

import java.util.List;

import com.server.intranet.menu.dto.MenuRequestDto;
import com.server.intranet.menu.dto.MenuResponseDto;
import com.server.intranet.menu.entity.MenuEntity;

public interface MenuService {
	List<MenuResponseDto> menuList(); // 등록
	MenuEntity insert(MenuRequestDto dto) throws Exception;
	MenuEntity edit(MenuRequestDto dto, String menu_id) throws Exception;
	MenuEntity delete(MenuRequestDto dto, String menu_id) throws Exception;
}
