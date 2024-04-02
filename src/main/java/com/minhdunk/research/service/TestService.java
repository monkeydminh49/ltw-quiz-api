package com.minhdunk.research.service;

import com.minhdunk.research.component.UserInfoUserDetails;
import com.minhdunk.research.dto.QuestionSubmitDTO;
import com.minhdunk.research.dto.TestInputDTO;
import com.minhdunk.research.entity.Question;
import com.minhdunk.research.entity.Test;
import com.minhdunk.research.entity.TestHistory;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.exception.TestTypeExistsForDocumentException;
import com.minhdunk.research.mapper.TestMapper;
import com.minhdunk.research.repository.*;
import com.minhdunk.research.utils.HintType;
import com.minhdunk.research.utils.TestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ChoiceRepository choiceRepository;
    @Autowired
    private HintRepository hintRepository;
    @Autowired
    private TestHistoryRepository testHistoryRepository;
    @Autowired
    private QuestionHistoryRepository questionHistoryRepository;
    @Autowired
    private ChoiceHistoryRepository choiceHistoryRepository;


    @Transactional
    public Test createTest(Authentication authentication, TestInputDTO testInputDTO) {

        Test test = testMapper.getTestFromTestInputDTO(testInputDTO);
        UserInfoUserDetails userInfoUserDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User author = userInfoUserDetails.getUser();
        test.setAuthor(author);

        Test savedTest =  testRepository.save(test);

        savedTest.getQuestions().forEach(question -> {
            question.setTest(savedTest);
            Question savedQuestion = questionRepository.save(question);

            question.getChoices().forEach(choice -> {
                choice.setQuestion(savedQuestion);
                choiceRepository.save(choice);
            });

            question.getHints().forEach(hint -> {
                hint.setType(HintType.HINT_REGULAR);
                hint.setQuestion(savedQuestion);
                hintRepository.save(hint);
            });

            question.getAnswerHints().forEach(hint -> {
                hint.setType(HintType.HINT_ANSWER);
                hint.setQuestion(savedQuestion);
                hintRepository.save(hint);
            });

        });

        return savedTest;


    }



    public Test getTestById(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new NotFoundException("Test not found"));
//        List<Question> questions = questionRepository.findByTestId(testId);
//        test.setQuestions(questions);
        return test;
    }

    public void deleteTest(Long testId) {
        testRepository.deleteById(testId);
    }

    public void submitTest(Long testId, List<QuestionSubmitDTO> questionSubmitDTO) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new NotFoundException("Test not found"));

        TestHistory testHistory = new TestHistory();

        Integer totalNumberOfQuestions = test.getQuestions().size();

        Integer countTotalNumberOfCorrectQuestions = 0;

        questionSubmitDTO.forEach(questionSubmit -> {
            Question question = questionRepository.findById(questionSubmit.getQuestionId()).orElseThrow(() -> new NotFoundException("Question not found"));
            question.getChoices().forEach(choice -> {
                questionSubmit.getChoices().forEach(choiceSubmit -> {
                    if (choice.getId().equals(choiceSubmit.getChoiceId())) {
                        ;
                    }
                });
            });
        });
    }
}
