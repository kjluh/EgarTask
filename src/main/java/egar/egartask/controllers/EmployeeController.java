package egar.egartask.controllers;

import egar.egartask.dto.EmpDto;
import egar.egartask.entites.Employee;
import egar.egartask.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("employee")
public class EmployeeController {

   private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmpDto empDto){
        return ResponseEntity.ok(employeeService.save(empDto));
    }
    @PatchMapping
    public ResponseEntity<EmpDto> updateEmployee(@RequestBody Employee employee){
        if (null!=employeeService.update(employee)){
        return ResponseEntity.ok(employeeService.update(employee));}
        return ResponseEntity.status(400).build();
    }

    @GetMapping()
    public ResponseEntity<List<Employee>> getEmployee(@RequestParam("name") String name, @RequestParam("family") String family){
        return ResponseEntity.ok(employeeService.get(name,family));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.find(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        if (employeeService.delete(id)) {
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        return ResponseEntity.ok(employeeService.getAll());
    }

}
