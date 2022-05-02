package com.amch.dto;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BookDTO {

    @Schema(required = false)
    private Long id;

    @Schema(required = true)
    private String title;

    private String authorName;

    @NotNull
    private Double price;

}
