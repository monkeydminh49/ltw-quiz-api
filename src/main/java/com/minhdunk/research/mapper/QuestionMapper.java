package com.minhdunk.research.mapper;

import com.minhdunk.research.entity.Question;
import com.minhdunk.research.entity.QuestionHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionHistory getQuestionHistoryFromQuestion(Question question);
}
