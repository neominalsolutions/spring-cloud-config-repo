package com.mertalptekin.orderservicedemo.repository;

import com.mertalptekin.orderservicedemo.model.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {
    // Gerekirse işlenmemiş eventleri çekmek için metotlar yazılabilir
}
