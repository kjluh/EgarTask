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

    private SalaryService salaryService;

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
    public ResponseEntity<List<EmpToSalary>> getAllEmplSalary() {
        return ResponseEntity.ok(salaryService.getAllEmplSalary());
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
        if (null == salaryService.getEmplSalary(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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
    @PatchMapping("/{id}/{sal}")
    public ResponseEntity<Employee> setSalary(@PathVariable Long id,
                                              @PathVariable Integer sal){
        if (null == salaryService.setSalary(id, sal)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(salaryService.setSalary(id, sal));
    }
}
