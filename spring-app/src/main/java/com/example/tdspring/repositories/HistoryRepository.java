package com.example.tdspring.repositories;

import com.example.tdspring.models.History;
import com.example.tdspring.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByType(String type);
    List<History> findByUser(User user);
}
