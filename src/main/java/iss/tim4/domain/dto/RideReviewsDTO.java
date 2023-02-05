package iss.tim4.domain.dto;

import iss.tim4.domain.dto.review.ReviewDTOResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideReviewsDTO {
    @NotNull (message = "Field vehicleReview is required!")
    private ReviewDTOResult vehicleReview;
    @NotNull (message = "Field driverReview is required!")
    private ReviewDTOResult driverReview;
}
