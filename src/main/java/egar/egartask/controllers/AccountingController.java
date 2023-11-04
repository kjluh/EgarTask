package egar.egartask.controllers;

import egar.egartask.dto.EmpDto;
import egar.egartask.dto.NotWorkingDaysDto;
import egar.egartask.dto.WorkTimeDto;
import egar.egartask.dto.WorkingTimeDto;
import egar.egartask.entites.NotWorkingDays;
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
    private final AccountingService accountingService;

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
                            )),
                    @ApiResponse(
                            responseCode = "404",
                            description = "сотруднику не разрешен доступ",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)

                    )
            }
    )
    @PatchMapping("come/{id}")
    public ResponseEntity<EmpDto> eCome(@PathVariable Long id) {
        EmpDto empDto = accountingService.comeEmp(id);
        if (null == empDto) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(empDto);
    }

    @Operation(
            summary = "Отметка об уходе сотрудника с работы",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сотрудник отметился и получил информацию",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ))
                    , @ApiResponse(
                    responseCode = "404",
                    description = "сотрудник еще выходил",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
            }
    )
    @PatchMapping("out/{id}")
    public ResponseEntity<EmpDto> eOut(@PathVariable Long id) {
        EmpDto empDto = accountingService.outEmp(id);
        if (empDto == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(empDto);
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
    public ResponseEntity<Map<EmpDto, List<WorkTimeDto>>> searchAll(
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
                            description = "сотрудник не найден"
                    )
            }
    )
    @GetMapping("/workingTimeEmpl/{id}")
    public ResponseEntity<WorkingTimeDto> workingTimeEmpl(@RequestParam Long id) {
        WorkingTimeDto workingTimeDto = accountingService.workingTime(id);
        if (null == workingTimeDto) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(workingTimeDto);
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
                            responseCode = "404",
                            description = "сотрудник не найден"
                    )
            }
    )
    @GetMapping("/workingTimeEmplInDate/{id}")
    public ResponseEntity<WorkingTimeDto> workingTimeEmplInDate(@PathVariable Long id,
                                                                @RequestParam LocalDate start,
                                                                @RequestParam LocalDate end) {
        WorkingTimeDto workingTimeDto = accountingService.workingTime(id, start, end);
        if (null == workingTimeDto) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(workingTimeDto);
    }

    @Operation(
            summary = "Установка нерабочих дней ( отпуск и тд)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "нерабочие дни записаны",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Сотрудник с таким id не найден"
                    )
            }
    )
    @PostMapping("/NWD/{id}")
    public ResponseEntity<NotWorkingDaysDto> setNotWorkingDays(@PathVariable Long id,
                                                               @RequestParam LocalDate start,
                                                               @RequestParam LocalDate end,
                                                               @RequestParam String info) {
        NotWorkingDaysDto notWorkingDays = accountingService.setNotWorkingDays(id, start, end, info);
        if (null == notWorkingDays) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(notWorkingDays);
    }

    @Operation(
            summary = "Получение отчета о нерабочих днях по id сотрудника",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "отчет найден",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @GetMapping("/NWD")
    public ResponseEntity<List<NotWorkingDaysDto>> getNotWorkingDays(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String comment
    ) {
        return ResponseEntity.ok(accountingService.getNotWorkingDays(id,comment));
    }
}



