package com.server.intranet.approval.repository;

import com.server.intranet.approval.entity.Notification;
import com.server.intranet.resource.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByEmployeeId_EmployeeId(Long id);

    List<Notification> findByEmployeeId(EmployeeEntity employeeId);
}