package com.minhdunk.research.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class TestStatisticOutputDTO {
    private Integer numberOfAttempt;
    private Double averageScore;
}