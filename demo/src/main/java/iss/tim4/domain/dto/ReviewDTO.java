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
    private Integer grade;
    private String comment;
    private RideDTO ride;
    private PassengerDTO passenger;

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.grade = review.getGrade();
        this.comment = review.getComment();
        this.ride = new RideDTO(review.getRide());
        this.passenger = new PassengerDTO(review.getPassenger());
    }
}
