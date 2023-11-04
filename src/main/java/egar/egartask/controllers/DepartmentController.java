package egar.egartask.controllers;

import egar.egartask.dto.DepartmentDto;
import egar.egartask.entites.Department;
import egar.egartask.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(
            summary = "добавление нового отдела",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "отдел добавлен",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @PostMapping()
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody Department department) {
        return ResponseEntity.ok(departmentService.saveDepartment(department));
    }

    @Operation(
            summary = "получение существующего отдела",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "отдел получен",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "отдел не найден",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable Long id) {
        DepartmentDto department = departmentService.getDepartment(id);
        if (null == department) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(department);
    }

    @Operation(
            summary = "обновление существующего отдела",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "отдел обновлен",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "отдел для обновления не найден",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @PatchMapping()
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody Department department) {
        DepartmentDto departmentDto = departmentService.patchDepartment(department);
        if (null == departmentDto) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(departmentDto);
    }

    @Operation(
            summary = "удаление существующего отдела",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "отдел удален",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "отдел для удаления не найден",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentDto> deleteDepartment(@PathVariable Long id) {
       DepartmentDto department = departmentService.deleteDepartment(id);
        if (null == department) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(department);
    }

}
