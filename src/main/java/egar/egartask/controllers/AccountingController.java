package egar.egartask.controllers;

import egar.egartask.dto.WorkTimeDto;
import egar.egartask.entites.Employee;
import egar.egartask.entites.WorkTime;
import egar.egartask.service.AccountingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/time")
public class AccountingController {
    private AccountingService accountingService;

    public AccountingController(AccountingService accountingControllerService) {
        this.accountingService = accountingControllerService;
    }

    @Operation(
            summary = "Отметка о приходе на работу сотрудника",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сотрудник отметился и получил информацию",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PutMapping("come/{id}")
    public ResponseEntity<String> ECome(@PathVariable Long id) {
        return ResponseEntity.ok(accountingService.comeEmp(id));
    }

    @Operation(
            summary = "Отметка об уходе сотрудника с работы",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сотрудник отметился и получил информацию",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PutMapping("out/{id}")
    public ResponseEntity<String> EOut(@PathVariable Long id) {
        return ResponseEntity.ok(accountingService.outEmp(id));
    }

    @Operation(
            summary = "Получение информации о времени прихода и ухода сотрудника в определенный день",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "время прихода и ухода",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @GetMapping("/search")
    public ResponseEntity<Map<Employee, List<WorkTimeDto>>> searchAll(
            @RequestParam("date") LocalDate date,
            @RequestParam("family") String family) {
        return ResponseEntity.ok(accountingService.search(date, family));
    }

    @Operation(
            summary = "Получение часов отработанных сотрудником",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "сотрудник и количество часов",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )),
                    @ApiResponse(
                            responseCode = "400",
                            description = "сотрудник не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )

                    )
            }
    )
    @GetMapping("/workingTimeEmpl/{id}")
    public ResponseEntity<?> workingTimeEmpl(@RequestParam Long id) {
        if (null == accountingService.workingTime(id)) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(accountingService.workingTime(id));
    }

}



