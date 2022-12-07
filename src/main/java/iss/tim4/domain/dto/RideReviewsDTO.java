package iss.tim4.domain.dto;

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
