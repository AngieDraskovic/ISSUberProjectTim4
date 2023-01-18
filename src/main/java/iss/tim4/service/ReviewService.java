package iss.tim4.service;

import iss.tim4.domain.dto.CreateReviewDTO;
import iss.tim4.domain.dto.review.ReviewDTO;
import iss.tim4.domain.dto.RideReviewsDTO;
import iss.tim4.domain.dto.UberPageDTO;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface ReviewService {
    Collection<RideReviewsDTO> findReviewByRideId(Integer id);

    Collection<ReviewDTO> findReviewByVehicleId(Integer vehicleId);
    UberPageDTO<ReviewDTO> findReviewByVehicleId(Integer vehicleId, Pageable pageable);

    Collection<ReviewDTO> findReviewByDriverId(Integer driverId);
    UberPageDTO<ReviewDTO> findReviewByDriverId(Integer driverId, Pageable pageable);

    ReviewDTO createForVehicle(CreateReviewDTO review, Integer rideId, Integer vehicleId);
    ReviewDTO createForDriver(CreateReviewDTO review, Integer rideId, Integer driverId);
}
