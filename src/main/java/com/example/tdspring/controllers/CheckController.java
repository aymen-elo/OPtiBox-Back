package com.example.tdspring.controllers;

import com.example.tdspring.exceptions.DBException;
import com.example.tdspring.exceptions.NotFoundException;
import com.example.tdspring.models.Check;
import com.example.tdspring.models.Stock;
import com.example.tdspring.services.CheckService;
import com.example.tdspring.services.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checks")
@RequiredArgsConstructor
@Slf4j
public class CheckController {

    private final CheckService checkService;
    private final StockService stockService;

    @GetMapping
    public ResponseEntity<List<Check>> getChecks() {
        return new ResponseEntity<>(this.checkService.getAllChecks(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Check> postCheck(@RequestBody Check checkSent) {
        try {
            log.info("Creating check ...");

            if (checkSent.getStock().getId() == null) {
                throw new NotFoundException("Stock not found");
            }
            // Set status of stock element
            Stock stock = checkSent.getStock();
            stock.setStatus(checkSent.getStatus());
            stockService.updateStock(stock);

            return checkSent.getId() == null ?
                    new ResponseEntity<>(this.checkService.updateCheck(checkSent), HttpStatus.CREATED) :
                    new ResponseEntity<>(this.checkService.updateCheck(checkSent), HttpStatus.ACCEPTED);
        } catch (DBException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Check> deleteCheck(@PathVariable Long id) {
        try {
            log.info("Deleting check ...");
            return new ResponseEntity<>(this.checkService.deleteCheck(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DBException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
