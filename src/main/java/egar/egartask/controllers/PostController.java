package egar.egartask.controllers;

import egar.egartask.entites.PostEmployee;
import egar.egartask.service.DepartmentService;
import egar.egartask.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
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
    @PostMapping()
    public ResponseEntity<PostEmployee> createPostEmployee(@RequestBody PostEmployee postEmployee){
        PostEmployee employee =postService.createPostEmployee(postEmployee);
        if (null== employee) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(employee);
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

    @PatchMapping()
    public ResponseEntity<PostEmployee> updatePostEmployee(@RequestBody PostEmployee postEmployee){
        PostEmployee employee = postService.updatePostEmployee(postEmployee);
        if (null== employee) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employee);
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
    @GetMapping("/{id}")
    public ResponseEntity<PostEmployee> getPostEmployee(@PathVariable Long id){
        PostEmployee employee =postService.getPostEmployee(id);
        if (null== employee) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employee);
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
    @DeleteMapping("/{id}")
    public ResponseEntity<PostEmployee> deletePostEmployee(@PathVariable Long id){
        PostEmployee employee =postService.deletePostEmployee(id);
        if (null== employee) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employee);
    }
}