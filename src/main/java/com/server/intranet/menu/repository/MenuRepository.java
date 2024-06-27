package com.server.intranet.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.intranet.menu.entity.MenuEntity;

public interface MenuRepository extends JpaRepository<MenuEntity, String> {

}
