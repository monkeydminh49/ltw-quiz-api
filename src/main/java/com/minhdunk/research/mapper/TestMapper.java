package com.minhdunk.research.mapper;

import com.minhdunk.research.dto.TestDTO;
import com.minhdunk.research.dto.TestInputDTO;
import com.minhdunk.research.dto.TestWithoutAnswerDTO;
import com.minhdunk.research.entity.Test;
import com.minhdunk.research.entity.TestHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TestMapper {

    @Mapping(target = "authorId", source = "author.id")
    TestDTO getTestDTOFromTest(Test test);


    @Mapping(target = "authorId", source = "author.id")
    TestWithoutAnswerDTO getTestWithoutAnswerDTOFromTest(Test test);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    Test getTestFromTestInputDTO(TestInputDTO testInputDTO);

    List<TestDTO> getTestDTOsFromTests(List<Test> testsByDocumentId);

    @Mapping(target = "totalScore", ignore = true)
    @Mapping(target = "test", ignore = true)
    @Mapping(target = "submitter", ignore = true)
    @Mapping(target = "submitAt", ignore = true)
    TestHistory getTestHistoryFromTest(Test test);


}
