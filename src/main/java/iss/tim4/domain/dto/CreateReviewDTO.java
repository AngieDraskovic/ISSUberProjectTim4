package iss.tim4.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewDTO {

    @NotNull (message = "Field rating is required!")
    private Integer rating;
    @Size (max = 100, message = "Field comment cannot be longer than 100 characters!")
    private String comment;
}
