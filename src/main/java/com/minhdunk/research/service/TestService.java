package com.minhdunk.research.service;

import com.minhdunk.research.component.UserInfoUserDetails;
import com.minhdunk.research.dto.ChoiceSubmitDTO;
import com.minhdunk.research.dto.QuestionSubmitDTO;
import com.minhdunk.research.dto.TestInputDTO;
import com.minhdunk.research.entity.*;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.exception.TestTypeExistsForDocumentException;
import com.minhdunk.research.mapper.ChoiceMapper;
import com.minhdunk.research.mapper.QuestionMapper;
import com.minhdunk.research.mapper.TestMapper;
import com.minhdunk.research.repository.*;
import com.minhdunk.research.utils.HintType;
import com.minhdunk.research.utils.TestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ChoiceMapper choiceMapper;
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

    public TestHistory submitTest(Long testId, List<QuestionSubmitDTO> questionSubmitDTO) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new NotFoundException("Test not found"));

        TestHistory testHistory = testMapper.getTestHistoryFromTest(test);
        testHistory.setSubmitAt(LocalDateTime.now());

        int totalNumberOfQuestions = test.getQuestions().size();
        TestHistory savedTest = testHistoryRepository.save(testHistory);

        int countTotalNumberOfCorrectQuestions = 0;
        List<QuestionHistory> questionHistories = new ArrayList<>();
        for (QuestionSubmitDTO questionSubmit : questionSubmitDTO) {
            Question question = questionRepository.findById(questionSubmit.getQuestionId()).orElseThrow(() -> new NotFoundException("Question not found"));
            QuestionHistory questionHistory = questionMapper.getQuestionHistoryFromQuestion(question);
            questionHistory.setTest(savedTest);
            QuestionHistory questionHistory1 = questionHistoryRepository.save(questionHistory);
            questionHistories.add(questionHistory1);

            boolean isACorrectQuestion = true;
            for (Choice choice : question.getChoices()) {
                for (ChoiceSubmitDTO choiceSubmit : questionSubmit.getChoices()) {
                    if (choice.getId().equals(choiceSubmit.getChoiceId())) {
                        if (!choice.getIsAnswer().equals(choiceSubmit.getIsPicked())) {
                            isACorrectQuestion = false;
                        }
                        ChoiceHistory choiceHistory = choiceMapper.getChoiceHistoryFromChoice(choice);
                        choiceHistory.setIsPicked(choiceSubmit.getIsPicked());
                        choiceHistory.setQuestion(questionHistory1);

                        choiceHistoryRepository.save(choiceHistory);
                    }
                }
            }
            if (isACorrectQuestion) {
                countTotalNumberOfCorrectQuestions++;
            }
        }

        double score = (double) countTotalNumberOfCorrectQuestions / totalNumberOfQuestions * 10;

        savedTest.setQuestions(questionHistories);
        savedTest.setTotalScore(score);
        return testHistoryRepository.save(testHistory);

    }

    public TestHistory getTestHistory(Long testId) {
        return testHistoryRepository.findByTestId(testId).orElseThrow(() -> new NotFoundException("Test history not found"));
    }

    public TestHistory getUserTestHistory(Long testId, Authentication authentication) {
        UserInfoUserDetails userInfoUserDetails = (UserInfoUserDetails) authentication.getPrincipal();
        User user = userInfoUserDetails.getUser();
        return testHistoryRepository.findByTestIdAndSubmitterId(testId, user.getId()).orElseThrow(() -> new NotFoundException("Test history not found"));
    }

    @Transactional
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }
}
