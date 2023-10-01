package egar.egartask.controllers;

import egar.egartask.dto.EmpToSalary;
import egar.egartask.entites.Department;
import egar.egartask.service.AccountingService;
import egar.egartask.service.SalaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    private SalaryService salaryService;
    private Long id;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping
    public ResponseEntity<List<EmpToSalary>> getAllEmplSalary() {
        return ResponseEntity.ok(salaryService.getAllEmplSalary());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpToSalary> getEmplSalary(@PathVariable Long id) {
        if (null == salaryService.getEmplSalary(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(salaryService.getEmplSalary(id));
    }

    @PostMapping("/department")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return ResponseEntity.ok(salaryService.saveDepartment(department));
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
        if (null == salaryService.getDepartment(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(salaryService.getDepartment(id));
    }

    @PatchMapping("/department")
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department) {
        if (null == salaryService.patchDepartment(department)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(salaryService.patchDepartment(department));
    }
    @DeleteMapping("/department/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable Long id) {
        if (null == salaryService.deleteDepartment(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(salaryService.deleteDepartment(id));
    }
}
