package com.example.tdspring.services;

import com.example.tdspring.exceptions.DBException;
import com.example.tdspring.exceptions.NotFoundException;
import com.example.tdspring.models.History;
import com.example.tdspring.repositories.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    public List<History> getAllHistory() {
        return this.historyRepository.findAll();
    }

    public List<History> getWithdraw() {
        return this.historyRepository.findByType("withdraw");
    }

    public List<History> getDeposit() {
        return this.historyRepository.findByType("deposit");
    }

    public List<History> getChecks() {
        return this.historyRepository.findByType("check");
    }

    public History updateHistory(History history) throws DBException, NotFoundException {
        History existing;

        if (history.getId() != null) {
            existing = this.historyRepository.findById(history.getId()).orElse(null);
            if (existing == null) throw new NotFoundException("Could not find history with id : " + history.getId());
        } else {
            existing = new History();
        }

        existing.setStock(history.getStock());
        existing.setDate(history.getDate());
        existing.setType(history.getType());
        existing.setUser(history.getUser());

        try {
            History historyCreated = this.historyRepository.save(existing);
            return historyCreated;
        } catch (Exception e) {
            throw new DBException("Could not create history");
        }
    }

    public History deleteHistory(Long id) throws NotFoundException, DBException {
        History existing = this.historyRepository.findById(id).orElse(null);
        if (existing == null) throw new NotFoundException("Could not find history with id : " + id);

        try {
            this.historyRepository.delete(existing);
            return existing;
        } catch (Exception e) {
            throw new DBException("Could not delete history");
        }
    }
}
