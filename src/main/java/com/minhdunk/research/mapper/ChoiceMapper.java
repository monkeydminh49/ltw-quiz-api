package com.minhdunk.research.mapper;

import com.minhdunk.research.entity.Choice;
import com.minhdunk.research.entity.ChoiceHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChoiceMapper {
    @Mapping(target = "isPicked", ignore = true)
    ChoiceHistory getChoiceHistoryFromChoice(Choice choice);
}
