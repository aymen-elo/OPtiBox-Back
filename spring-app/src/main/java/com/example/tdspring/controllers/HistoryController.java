package com.example.tdspring.controllers;

import com.example.tdspring.exceptions.DBException;
import com.example.tdspring.exceptions.NotFoundException;
import com.example.tdspring.models.History;
import com.example.tdspring.models.Stock;
import com.example.tdspring.services.HistoryService;
import com.example.tdspring.services.StockService;
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
    private final StockService stockService;

    @GetMapping
    public ResponseEntity<List<History>> getHistory() {
        return new ResponseEntity<>(this.historyService.getAllHistory(), HttpStatus.OK);

    }

    @GetMapping("/withdraw")
    public ResponseEntity<List<History>> getWithdrawAndDeposit() {
        return new ResponseEntity<>(this.historyService.getWithdraw(), HttpStatus.OK);
    }

    @GetMapping("/deposit")
    public ResponseEntity<List<History>> getDeposit() {
        return new ResponseEntity<>(this.historyService.getDeposit(), HttpStatus.OK);
    }

    @GetMapping("/checks")
    public ResponseEntity<List<History>> getChecks() {
        return new ResponseEntity<>(this.historyService.getChecks(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<History> postHistory(@RequestBody History historySent) {
        try {
            log.info("Creating history ...");
            // call stockService.updateStock and set available to false
            Stock stock = historySent.getStock();

            if(historySent.getStock().getId() == null) {
                throw new NotFoundException("Stock not found");
            }
            if (historySent.getType().equals("withdraw")) {
                stock.setAvailable(false);
                stockService.updateStock(historySent.getStock());
            }else if (historySent.getType().equals("deposit")) {
                stock.setAvailable(true);
                stockService.updateStock(historySent.getStock());
            }

            // create history
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
