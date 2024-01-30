package org.miraclesoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.miraclesoft.domain.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, String> {
}
