package com.amch.controller;

import java.util.List;

import com.amch.dto.BookDTO;
import com.amch.service.BookService;

import org.apache.tomcat.jni.Address;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.var;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book", description = "the Book API")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Create book ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created | OK"),
            @ApiResponse(responseCode = "401", description = "Not Authorized!"),
            @ApiResponse(responseCode = "500", description = "Internal server error!") })
    @PostMapping
    public ResponseEntity<BookDTO> create(
            @Parameter(description = "Book to create.", required = true, schema = @Schema(implementation = BookDTO.class)) @RequestBody BookDTO book) {
        try {
            var saved = bookService.create(book);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Operation(summary = "Get all books ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success|OK"),
            @ApiResponse(responseCode = "401", description = "Not Authorized!"),
            @ApiResponse(responseCode = "403", description = "Forbidden!"),
            @ApiResponse(responseCode = "404", description = "Not Found!") })
    @GetMapping
    public ResponseEntity<List<BookDTO>> all() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(bookService.all());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get book by id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success|OK"),
            @ApiResponse(responseCode = "401", description = "Not Authorized!"),
            @ApiResponse(responseCode = "403", description = "Forbidden!"),
            @ApiResponse(responseCode = "404", description = "Not Found!") })
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> book(@PathVariable("id") long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(bookService.bookById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book ")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO updateBook(
            @Parameter(description = "Id of the book to be update. Cannot be empty.", required = true) @PathVariable("id") final String id,
            @Parameter(description = "Book to update.", required = true, schema = @Schema(implementation = BookDTO.class)) @RequestBody final BookDTO book) {
        return book;
    }

}
