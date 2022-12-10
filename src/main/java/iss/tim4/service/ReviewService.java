package iss.tim4.service;

import iss.tim4.domain.dto.CreateReviewDTO;
import iss.tim4.domain.dto.review.ReviewDTO;
import iss.tim4.domain.dto.RideReviewsDTO;
import iss.tim4.domain.dto.UberPageDTO;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface ReviewService {
    Collection<RideReviewsDTO> findReviewByRideId(Long id);

    Collection<ReviewDTO> findReviewByVehicleId(Long vehicleId);
    UberPageDTO<ReviewDTO> findReviewByVehicleId(Long vehicleId, Pageable pageable);

    Collection<ReviewDTO> findReviewByDriverId(Long driverId);
    UberPageDTO<ReviewDTO> findReviewByDriverId(Long driverId, Pageable pageable);

    ReviewDTO createForVehicle(CreateReviewDTO review, Long rideId, Long vehicleId);
    ReviewDTO createForDriver(CreateReviewDTO review, Long rideId, Long driverId);
}
