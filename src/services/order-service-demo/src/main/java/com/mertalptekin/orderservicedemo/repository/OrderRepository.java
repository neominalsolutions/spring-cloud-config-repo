package com.mertalptekin.orderservicedemo.repository;

import com.mertalptekin.orderservicedemo.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
