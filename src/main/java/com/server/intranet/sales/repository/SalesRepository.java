package com.server.intranet.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.intranet.sales.entity.SalesEntity;

public interface SalesRepository extends JpaRepository<SalesEntity, Long> {

}
