package egar.egartask.controllers;

import egar.egartask.dto.WorkTimeDto;
import egar.egartask.dto.WorkingTimeDto;
import egar.egartask.entites.Employee;
import egar.egartask.entites.NotWorkingDays;
import egar.egartask.entites.WorkTime;
import egar.egartask.service.AccountingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
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
            summary = "Получение часов отработанных сотрудником за прошлый месяц",
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
    public ResponseEntity<WorkingTimeDto> workingTimeEmpl(@RequestParam Long id) {
        if (null == accountingService.workingTime(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(accountingService.workingTime(id));
    }

    @Operation(
            summary = "Получение часов отработанных сотрудником \n" +
                    "в определенный промежуток времени",
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
    @GetMapping("/workingTimeEmplInDate/{id}")
    public ResponseEntity<WorkingTimeDto> workingTimeEmplInDate(@PathVariable Long id,
                                                                @RequestParam LocalDate start,
                                                                @RequestParam LocalDate end) {
        if (null == accountingService.workingTime(id, start, end)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(accountingService.workingTime(id));
    }

    @PostMapping("/NWD/{id}")
    public NotWorkingDays setNotWorkingDays(@PathVariable Long id,
                                            @RequestParam LocalDate start,
                                            @RequestParam LocalDate end,
                                            @RequestParam String info) {
        return accountingService.setNotWorkingDays(id,start,end, info);
    }

    @GetMapping("/NWD/{id}")
    public NotWorkingDays getNotWorkingDays(@PathVariable Long id){
        return accountingService.getNotWorkingDays(id);
    }
}



