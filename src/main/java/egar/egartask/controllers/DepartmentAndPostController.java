package egar.egartask.controllers;

import egar.egartask.entites.Department;
import egar.egartask.entites.PostEmployee;
import egar.egartask.service.DepartmentAndPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class DepartmentAndPostController {

    private DepartmentAndPostService departmentAndPostService;

    public DepartmentAndPostController(DepartmentAndPostService departmentAndPostService) {
        this.departmentAndPostService = departmentAndPostService;
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
    @PostMapping("/department")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return ResponseEntity.ok(departmentAndPostService.saveDepartment(department));
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
    @GetMapping("/department/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
        if (null == departmentAndPostService.getDepartment(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(departmentAndPostService.getDepartment(id));
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
    @PatchMapping("/department")
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department) {
        if (null == departmentAndPostService.patchDepartment(department)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(departmentAndPostService.patchDepartment(department));
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
    @DeleteMapping("/department/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable Long id) {
        if (null == departmentAndPostService.deleteDepartment(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(departmentAndPostService.deleteDepartment(id));
    }
    @Operation(
            summary = "создание существующей должности",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "должность создана",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "должность с таким названием уже существует",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @PostMapping("/post")
    public ResponseEntity<PostEmployee> createPostEmployee(@RequestBody PostEmployee postEmployee){
        if (null== departmentAndPostService.createPostEmployee(postEmployee)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(departmentAndPostService.createPostEmployee(postEmployee));
    }
    @Operation(
            summary = "обновление существующей должности",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "должность обновлена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "должность для обновления не найдена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )

    @PatchMapping("/post")
    public ResponseEntity<PostEmployee> updatePostEmployee(@RequestBody PostEmployee postEmployee){
        if (null== departmentAndPostService.updatePostEmployee(postEmployee)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(departmentAndPostService.updatePostEmployee(postEmployee));
    }
    @Operation(
            summary = "получение существующей должности",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "должность найдена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "должность не найдена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @GetMapping("/post/{id}")
    public ResponseEntity<PostEmployee> getPostEmployee(@PathVariable Long id){
        if (null== departmentAndPostService.getPostEmployee(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(departmentAndPostService.getPostEmployee(id));
    }
    @Operation(
            summary = "удаление существующей должности",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "должность удалена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "должность для удаления не найдена",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            }
    )
    @DeleteMapping("/post/{id}")
    public ResponseEntity<PostEmployee> deletePostEmployee(@PathVariable Long id){
        if (null== departmentAndPostService.getPostEmployee(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(departmentAndPostService.getPostEmployee(id));
    }
}