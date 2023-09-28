package egar.egartask.controllers;

import egar.egartask.dto.EmpDto;
import egar.egartask.entites.Employee;
import egar.egartask.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RequestMapping("/employee")
public class EmployeeController {

   private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @Operation(
            summary = "Добавление сотрудника в базу",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавление сотрудника прошло успешно",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PostMapping()
    public String createEmployee(@ModelAttribute("empDto") EmpDto empDto){
        employeeService.save(empDto);
        return "redirect:/employee/all";
    }

    @GetMapping("/new")
    public String create(Model model){
        model.addAttribute("empDto", new EmpDto());
        return "newEmployee";
    }

    @Operation(
            summary = "Обновление сотрудника в базу",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновление сотрудника прошло успешно",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Данные сотрудника не обновлены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PatchMapping
    public String updateEmployee(@ModelAttribute("employee") Employee employee){
        employeeService.update(employee);
        return "redirect:/employee/all";
    }
    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id){
        model.addAttribute("employee", employeeService.find(id));
        return "updateEmployee";
    }

    @Operation(
            summary = "Получение сотрудников из базы",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список сотрудников...",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<List<Employee>> getEmployee(@RequestParam("name") String name, @RequestParam("family") String family){
        return ResponseEntity.ok(employeeService.get(name,family));
    }
    @Operation(
            summary = "Поиск сотрудника в базе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сотрудник найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Сотрудник не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public String getEmployee(@PathVariable Long id, Model model){
        model.addAttribute("employee",employeeService.find(id));
        return "employee";
    }
    @Operation(
            summary = "Удаление сотрудника из базы",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сотрудник удален",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Сотрудник не удален",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        if (employeeService.delete(id)) {
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(400).build();
    }
    @Operation(
            summary = "Получение всех сотрудников из базы",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сотрудники получены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @GetMapping("/all")
    public String getAllEmployee(Model model){
        model.addAttribute("getAllEmpl",employeeService.getAll());
        return "allEmployee";
    }
}
