package com.minhdunk.research.dto;

import com.minhdunk.research.entity.ChoiceHistory;
import com.minhdunk.research.entity.TestHistory;
import com.minhdunk.research.utils.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class QuestionHistoryOutputDTO {
    private Long id;
    private String question;
    private QuestionType type;
    private Long testId;
    private List<ChoiceHistoryOutputDTO> choices;
}
