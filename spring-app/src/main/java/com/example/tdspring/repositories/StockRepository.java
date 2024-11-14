package com.example.tdspring.repositories;

import com.example.tdspring.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByAvailable(boolean b);
    // findByProductId(Long id); -> to get all stocks for a product id given
    List<Stock> findByProductId (Long id);
}

