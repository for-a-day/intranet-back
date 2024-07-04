package com.server.intranet.order.service;

import java.util.List;

import com.server.intranet.order.dto.OrderResponseDto;
import com.server.intranet.order.entity.OrderEntity;

public interface OrderService {
	OrderEntity saveOrderData(OrderEntity order, String storeCode, String menuCode);
	List<OrderResponseDto> orderList();
}
