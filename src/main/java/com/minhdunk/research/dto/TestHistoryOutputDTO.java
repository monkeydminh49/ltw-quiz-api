package com.minhdunk.research.dto;

import com.minhdunk.research.entity.QuestionHistory;
import com.minhdunk.research.entity.Test;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.utils.TestType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class TestHistoryOutputDTO {
    private Long id;
    private String title;
    private List<QuestionHistoryOutputDTO> questions;
    private Long submitterId;
    private Long authorId;
    private TestType type;
    private Long testId;
    private Double totalScore;
    private LocalDateTime submitAt;
    private Double durationInMinutes;
}
