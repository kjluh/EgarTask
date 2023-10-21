package egar.egartask.controllers;

import egar.egartask.dto.EmpDto;
import egar.egartask.entites.Employee;
import egar.egartask.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/all")
    public String getAllEmployee(Model model) {
        model.addAttribute("getAllEmpl", employeeService.getAllEmployee());
        return "allEmployee";
    }

    @GetMapping("/{id}")
    public String getEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.find(id));
        return "employee";
    }


    @GetMapping("/new")
    public String create(@ModelAttribute("empDto") EmpDto empDto) {
        return "newEmployee";
    }

    @PostMapping()
    public String createEmployee(@ModelAttribute("empDto") EmpDto empDto) {
        employeeService.save(empDto);
        return "redirect:/employee/all";
    }

    @GetMapping("/{id}/update")
    public String update(Model model, @PathVariable Long id) {
        model.addAttribute("employee", employeeService.find(id));
        return "updateEmployee";
    }

    @PostMapping("/{id}/update")
    public String updateEmployee(
            @ModelAttribute("employee") Employee employee,
            @PathVariable Long id) {
        employee.setId(id);
        employeeService.update(employee);
        return "redirect:/employee/all";
    }



    @PostMapping("/{id}/del")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/employee/all";
    }


}