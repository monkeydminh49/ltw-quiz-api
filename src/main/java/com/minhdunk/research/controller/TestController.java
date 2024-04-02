package com.minhdunk.research.controller;

import com.minhdunk.research.dto.BaseResponse;
import com.minhdunk.research.dto.QuestionSubmitDTO;
import com.minhdunk.research.dto.TestDTO;
import com.minhdunk.research.dto.TestInputDTO;
import com.minhdunk.research.entity.Test;
import com.minhdunk.research.entity.TestHistory;
import com.minhdunk.research.mapper.TestMapper;
import com.minhdunk.research.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@Controller
@RequestMapping("/api/v1/tests")
public class TestController {
    @Autowired
    private TestService testService;
    @Autowired
    private TestMapper testMapper;

    @PostMapping()
    public BaseResponse createTest(Authentication authentication, @RequestBody TestInputDTO testInputDTO) {
        Test test = testService.createTest(authentication, testInputDTO);
        return new BaseResponse( "ok",  "Create test successfully", testMapper.getTestDTOFromTest(test));
    }

    @GetMapping("/{testId}")
    public TestDTO getTest(@PathVariable Long testId) {
        return testMapper.getTestDTOFromTest(testService.getTestById(testId));
    }

    @DeleteMapping("/{testId}")
    public BaseResponse deleteTest(@PathVariable Long testId) {
        testService.deleteTest(testId);
        return new BaseResponse("ok", "Delete test successfully", null);
    }


    @PostMapping("/{testId}/submit")
    public BaseResponse submitTest(@PathVariable Long testId, @RequestBody List<QuestionSubmitDTO> questionSubmitDTO) {
        TestHistory testHistory =  testService.submitTest(testId, questionSubmitDTO);
        return new BaseResponse("ok", "Submit test successfully", testHistory);
    }

    @GetMapping("/{testId}/history/")
    public TestHistory getTestHistory(@PathVariable Long testId) {
        return testService.getTestHistory(testId);
    }

    @GetMapping("/history/")
    public TestHistory getUserTestHistory(@PathVariable Long testId, Authentication authentication) {
        return testService.getUserTestHistory(testId, authentication);
    }


}
