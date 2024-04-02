package com.minhdunk.research.dto;

import com.minhdunk.research.utils.TestType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class TestInputDTO {
    private String title;
    private List<QuestionInputDTO> questions;
    @Schema(description = "Type of the test", example = "WRITING | MULTIPLE_CHOICE")
    private TestType type;
    private Double durationInMinutes;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
