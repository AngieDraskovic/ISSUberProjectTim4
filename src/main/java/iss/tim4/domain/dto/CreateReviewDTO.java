package iss.tim4.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewDTO {
    /* Ne bih nista dirao jer passenger kad ocjenjuje moze unijeti ili jedno ili drugo ili oboje*/
    private Integer rating;
    private String comment;
}
