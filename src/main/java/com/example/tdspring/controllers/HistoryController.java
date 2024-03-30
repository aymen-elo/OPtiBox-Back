package com.example.tdspring.controllers;

import com.example.tdspring.exceptions.DBException;
import com.example.tdspring.exceptions.NotFoundException;
import com.example.tdspring.models.History;
import com.example.tdspring.services.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
@Slf4j
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public ResponseEntity<List<History>> getHistorys() {
        return new ResponseEntity<>(this.historyService.getAllHistory(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<History> postHistory(@RequestBody History historySent) {
        try {
            log.info("Creating history ...");
            return historySent.getId() == null ?
                    new ResponseEntity<>(this.historyService.updateHistory(historySent), HttpStatus.CREATED) :
                    new ResponseEntity<>(this.historyService.updateHistory(historySent), HttpStatus.ACCEPTED);
        } catch (DBException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<History> deleteHistory(@PathVariable Long id) {
        try {
            log.info("Deleting history ...");
            return new ResponseEntity<>(this.historyService.deleteHistory(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DBException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
