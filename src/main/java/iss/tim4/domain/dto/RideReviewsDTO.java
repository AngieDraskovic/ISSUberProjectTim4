package iss.tim4.domain.dto;

import iss.tim4.domain.dto.review.ReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideReviewsDTO {
    private ReviewDTO vehicleReview;
    private ReviewDTO driverReview;
}
