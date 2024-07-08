package com.server.intranet.order.service;

import java.sql.Date;
import java.util.List;

import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.menu.entity.MenuEntity;
import com.server.intranet.order.dto.OrderResponseDto;
import com.server.intranet.order.entity.OrderEntity;

public interface OrderService {
	OrderEntity saveOrderData(OrderEntity order, String storeCode, String menuCode); // api 정보 저장
	boolean existsByFranchiseeIdAndMenuIdAndOrderDate(FranchiseeEntity storeCode, MenuEntity menuId, Date orderDate); // api 중복 체크
	List<OrderResponseDto> orderList(); // 목록
}
