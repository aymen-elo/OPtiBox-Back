package com.example.tdspring.services;

import com.example.tdspring.exceptions.DBException;
import com.example.tdspring.exceptions.NotFoundException;
import com.example.tdspring.models.Check;
import com.example.tdspring.repositories.CheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckService {

    private final CheckRepository checkRepository;

    public List<Check> getAllChecks() {
        return this.checkRepository.findAll();
    }

    public Check updateCheck(Check check) throws DBException, NotFoundException {
        Check existing;

        if (check.getId() != null) {
            existing = this.checkRepository.findById(check.getId()).orElse(null);
            if (existing == null) throw new NotFoundException("Could not find check with id : " + check.getId());
        } else {
            existing = new Check();
        }

        existing.setStock(check.getStock());
        existing.setDate(check.getDate());
        existing.setComment(check.getComment());
        existing.setStatus(check.getStatus());
        existing.setUser(check.getUser());

        try {
            Check checkCreated = this.checkRepository.save(existing);
            return checkCreated;
        } catch (Exception e) {
            throw new DBException("Could not create check");
        }
    }

    public Check deleteCheck(Long id) throws NotFoundException, DBException {
        Check existing = this.checkRepository.findById(id).orElse(null);
        if (existing == null) throw new NotFoundException("Could not find check with id : " + id);

        try {
            this.checkRepository.delete(existing);
            return existing;
        } catch (Exception e) {
            throw new DBException("Could not delete check");
        }
    }
}
