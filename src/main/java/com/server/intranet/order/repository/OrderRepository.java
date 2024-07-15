package com.server.intranet.order.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.menu.entity.MenuEntity;
import com.server.intranet.order.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	boolean existsByFranchiseeIdAndMenuIdAndOrderDate(FranchiseeEntity storeCode, MenuEntity menuId, Date orderDate); // api 중복체크
}
