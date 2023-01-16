package iss.tim4.domain.dto.review;

import iss.tim4.domain.dto.passenger.PassengerDTOResponse;
import iss.tim4.domain.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.IMessage;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Number id;
    @Min(value = 1, message = "Field rating format is not valid!")
    @Max(value = 5, message = "Field rating format is not valid!")
    private Integer rating;
    @Size(max = 300, message = "Field comment cannot be longer than 300 characters!")
    private String comment;
    private PassengerDTOResponse passenger;

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.rating = review.getGrade();
        this.comment = review.getComment();
        this.passenger = new PassengerDTOResponse(review.getPassenger());
    }
}
