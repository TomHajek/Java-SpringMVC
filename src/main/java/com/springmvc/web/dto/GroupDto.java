package com.springmvc.web.dto;

import com.springmvc.persistence.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotEmpty;


/**
 * Bean validation constraints:
 * @NotNull  - a constrained field (CharSequence, Collection, Map, or Array) is valid as long as it's not null, but it can be empty.
 * @NotEmpty - a constrained field (CharSequence, Collection, Map, or Array) is valid as long as it's not null, and its size/length is greater than zero.
 * @NotBlank - a constrained field (String) is valid as long as it's not null, and the trimmed length is greater than zero.
 */
@Data @Builder
public class GroupDto {

    private Long id;
    @NotEmpty(message = "Group title should not be empty")
    private String title;
    @NotEmpty(message = "Photo link should not be empty")
    private String photoUrl;
    @NotEmpty(message = "Content should not be empty")
    private String content;
    private User createdBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<EventDto> events;

}
