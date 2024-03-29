package iss.tim4.domain.dto.review;

import iss.tim4.domain.dto.passenger.PassengerDTOPost;
import iss.tim4.domain.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTOResult {

    private Number id;
    @Min(value = 1, message = "Field driverGrade format is not valid!")
    @Max(value = 5, message = "Field driverGrade format is not valid!")
    private Integer driverGrade;

    @Min(value = 1, message = "Field vehicleGrade format is not valid!")
    @Max(value = 5, message = "Field vehicleGrade format is not valid!")
    private Integer vehicleGrade;

    @Size(max = 300, message = "Field comment cannot be longer than 300 characters!")
    private String comment;
    @NotNull (message = "Field passenger is required!")
    private PassengerDTOPost passenger;

    public ReviewDTOResult(Review review) {
        this.id = review.getId();
        this.driverGrade = review.getDriverGrade();
        this.vehicleGrade = review.getVehicleGrade();
        this.comment = review.getComment();
        this.passenger = new PassengerDTOPost(review.getPassenger());
    }
}
