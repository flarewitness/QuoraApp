package com.flare.quora.services;

import com.flare.quora.models.Question;
import com.flare.quora.models.QuestionElasticDocument;
import com.flare.quora.repositories.QuestionDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionIndexService implements IQuestionIndexService{

    private final QuestionDocumentRepository questionDocumentRepository;

    @Override
    public void createQuestionIndex(Question question) {

        QuestionElasticDocument questionElasticDocument = QuestionElasticDocument.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .build();

        questionDocumentRepository.save(questionElasticDocument);
    }
}
