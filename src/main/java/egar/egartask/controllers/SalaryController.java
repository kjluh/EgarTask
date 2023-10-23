package egar.egartask.controllers;

import egar.egartask.dto.EmpToSalary;
import egar.egartask.entites.Employee;
import egar.egartask.service.SalaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @Operation(
            summary = "получение отчета со всеми работающими сотрудниками и их зп",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "отчет сформирован",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<EmpToSalary>> getAllEmplSalary(
            @RequestParam Integer number,
            @RequestParam Integer size) {
        return ResponseEntity.ok(salaryService.getAllEmplSalary(number, size));
    }

    @Operation(
            summary = "получение сотрудника по id с его зп",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "сотрудник получен",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "сотрудника с таким id нет в базе",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EmpToSalary> getEmplSalary(@PathVariable Long id) {
        return ResponseEntity.ok(salaryService.getEmplSalary(id));
    }

    @Operation(
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "зп сохранена",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Данный сотрудник не найден",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @PatchMapping("/{id}/")
    public ResponseEntity<Employee> setSalary(@PathVariable Long id,
                                              @RequestParam Integer sal) {
        Employee employee = salaryService.setSalary(id, sal);
        if (null == employee) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employee);
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", description = "изменения сохранены"),
                    @ApiResponse(responseCode = "400", description = "что-то не та")
            }
    )
    @PatchMapping("/updateSalary")
    public ResponseEntity<?> updateSalarys(@RequestParam Long employeeId,
                                           @RequestParam Integer employeeSalary,
                                           @RequestParam Long postId,
                                           @RequestParam Float postSalary) {
        salaryService.updateSalarys(employeeId, employeeSalary, postId, postSalary);
        return ResponseEntity.ok().build();
    }
}
