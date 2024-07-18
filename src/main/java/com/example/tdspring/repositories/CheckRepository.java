package com.example.tdspring.repositories;

import com.example.tdspring.models.Check;
import com.example.tdspring.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckRepository extends JpaRepository<Check, Long> {
    List<Check> findByUser(User user);
}
