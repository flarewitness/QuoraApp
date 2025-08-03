package com.flare.quora.mapper;

import com.flare.quora.dto.QuestionResponseDTO;
import com.flare.quora.models.Question;

public class QuestionMapper {
    public static QuestionResponseDTO toQuestionResponseDTO(Question question) {
        if (question == null) {
            return null;
        }
        return QuestionResponseDTO.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .CreatedAt(question.getCreatedAt())
                .build();
    }
}
