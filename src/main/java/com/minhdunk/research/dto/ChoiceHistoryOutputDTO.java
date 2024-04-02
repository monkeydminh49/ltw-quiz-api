package com.minhdunk.research.dto;

import com.minhdunk.research.entity.QuestionHistory;
import com.minhdunk.research.entity.TestHistory;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ChoiceHistoryOutputDTO {
    private Long id;
    private String content;
    private Boolean isAnswer;
    private Boolean isPicked;
    private Long questionId;
    private Long testId;
}
