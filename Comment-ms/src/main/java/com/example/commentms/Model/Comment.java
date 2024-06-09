package com.example.commentms.Model;

import com.example.commonsms.Constants.ValidationConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank(message = ValidationConstants.TEXT_REQUIRED)
    String text;

    @NotBlank(message = ValidationConstants.CONTENT_REQUIRED)
    String content;

    @NotNull(message = ValidationConstants.COMMENTED_POST_ID_REQUIRED)
    Long commentedPostId;

    LocalDateTime creationTime;
    LocalDateTime updateTime;
}
