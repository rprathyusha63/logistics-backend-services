package org.miraclesoft.repository;
import org.miraclesoft.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("select o from Order o order by o.receivedOn desc")
    List<Order> findAllOrdersSortByDate();

    @Query("select o from Order o where o.receivedOn >= :today")
    List<Order> findAllOrdersToday(@Param("today") Timestamp today);

    @Query("select o from Order o where o.warehouseId = :warehouseId")
    List<Order> findOrdersByWarehouse(@Param("warehouseId") String warehouseId);

}

