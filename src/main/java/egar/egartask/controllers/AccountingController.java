package egar.egartask.controllers;

import egar.egartask.dto.WorkTimeDto;
import egar.egartask.entites.Employee;
import egar.egartask.entites.WorkTime;
import egar.egartask.service.AccountingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController("time/")
public class AccountingController {
    private AccountingService accountingService;

    public AccountingController(AccountingService accountingControllerService) {
        this.accountingService = accountingControllerService;
    }

    @PutMapping("come/{id}")
    public ResponseEntity<?> ECome(@PathVariable Long id) {
        return ResponseEntity.ok(accountingService.comeEmp(id));
    }

    @PutMapping("out/{id}")
    public ResponseEntity<?> EOut(@PathVariable Long id) {
        return ResponseEntity.ok(accountingService.outEmp(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Map<Employee, List<WorkTimeDto>>> searchAll(@RequestParam("date") LocalDate date, @RequestParam("family") String family){
        return ResponseEntity.ok(accountingService.search(date,family));
    }
}
