package egar.egartask.controllers;

import egar.egartask.dto.EmpDto;
import egar.egartask.entites.Employee;
import egar.egartask.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeeJson")
public class EmployeeControllerJson {
    private final EmployeeService employeeService;

    public EmployeeControllerJson(EmployeeService employeeService) {
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
    public ResponseEntity<Employee> createEmployee(@RequestBody EmpDto empDto) {
        return ResponseEntity.ok(employeeService.save(empDto));
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
                            responseCode = "BAD_REQUEST",
                            description = "Данные сотрудника не обновлены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PatchMapping
    public ResponseEntity<EmpDto> updateEmployee(@RequestBody Employee employee) {
        EmpDto empDto = employeeService.update(employee);
        if (null == empDto) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(empDto);
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
    public ResponseEntity<List<Employee>> getEmployee(
            @RequestParam("name") String name,
            @RequestParam("family") String family) {
        return ResponseEntity.ok(employeeService.get(name, family));
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
                            responseCode = "BAD_REQUEST",
                            description = "Сотрудник не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        Employee employee = employeeService.find(id);
        if (null == employee) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employee);
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
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
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
    public ResponseEntity<List<EmpDto>> getAllEmployee(Model model) {
        return ResponseEntity.ok(employeeService.getAll());
    }
}
