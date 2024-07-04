package com.server.intranet.order.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.intranet.franchisee.entity.FranchiseeEntity;
import com.server.intranet.franchisee.repository.FranchiseeRepository;
import com.server.intranet.franchisee.service.FranchiseeService;
import com.server.intranet.menu.entity.MenuEntity;
import com.server.intranet.menu.repository.MenuRepository;
import com.server.intranet.menu.service.MenuService;
import com.server.intranet.order.dto.OrderResponseDto;
import com.server.intranet.order.entity.OrderEntity;
import com.server.intranet.order.repository.OrderRepository;
import com.server.intranet.order.service.OrderService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService{
	private final OrderRepository orderRepository;
	private FranchiseeService franchiseeService;
	private FranchiseeRepository franchiseeRepository;
	private MenuService menuService;
	private MenuRepository menuRepository;
	private EntityManager entityManager;
	
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository, FranchiseeService franchiseeService, 
							FranchiseeRepository franchiseeRepository, EntityManager entityManager
							,MenuService menuService, MenuRepository menuRepository) {
		this.orderRepository = orderRepository;
		this.entityManager = entityManager;
		this.franchiseeRepository = franchiseeRepository;
		this.franchiseeService = franchiseeService;
		this.menuRepository = menuRepository;
		this.menuService = menuService;
	}
	
	// 주문 api 저장하는 메서드
	@Override
	@Transactional
	public OrderEntity saveOrderData(OrderEntity order, String storeCode, String menuCode) {
		System.err.println("saveOrderData 서비스 단입니다.");
		// 가맹점 정보 저장 조회
		FranchiseeEntity franchiseeEntity = franchiseeRepository.findByFranchiseeId(storeCode);
		order.setFranchisee_id(franchiseeEntity); // OrderEntity에 저장된 가맹점 정보 설정
		
		System.out.println("가맹점 아이디 조회");
		
		// 메뉴 아이디 조회
		Optional<MenuEntity> menuEntity = menuRepository.findById(menuCode);
		//order.setMenu_id(menuEntity);
		
		if (menuEntity.isPresent()) {
	        MenuEntity menu = menuEntity.get();
	        order.setMenu_id(menu);
	    } else {
	        throw new EntityNotFoundException("Menu not found for code: " + menuCode);
	    }
		
		System.out.println("메뉴 아이디 조회");
		
		// 주문 데이터 저장
		if(entityManager.contains(order)) {
			entityManager.merge(order);
		}else {
			orderRepository.save(order);
		}
		
		System.err.println("성공 => 반환 시작");
		
		return order;
	}

	// 목록
	@Override
	public List<OrderResponseDto> orderList(){
		return orderRepository.findAll().stream()
				.map(order -> new OrderResponseDto(
					order.getOrder_id(),
					order.getOrder_quantity(),
					order.getOrder_price(),
					order.getOrder_date(),
					order.getFranchisee_id().getFranchiseeId(),
					order.getMenu_id().getMenu_id()
					)).collect(Collectors.toList());
	}
}
