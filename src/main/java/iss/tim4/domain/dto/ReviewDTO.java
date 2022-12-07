package iss.tim4.domain.dto;

import iss.tim4.domain.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;
    private Integer rating;
    private String comment;
    private PassengerDTOResponse passenger;

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.rating = review.getGrade();
        this.comment = review.getComment();
        this.passenger = new PassengerDTOResponse(review.getPassenger());
    }
}
