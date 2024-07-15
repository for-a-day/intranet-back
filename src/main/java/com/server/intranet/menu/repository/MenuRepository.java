package com.server.intranet.menu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.intranet.menu.entity.MenuEntity;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, String> {
	List<MenuEntity> findAll();
	Optional<MenuEntity> findById(String menu_id);
	//MenuEntity findByMenu_id(String menu_id);
}
