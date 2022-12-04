package iss.tim4.service;

import iss.tim4.domain.dto.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Map;

public interface ReviewService {
    Map<String, ReviewDTO> findReviewByRideId(Long id);

    Collection<ReviewDTO> findReviewByVehicleId(Long vehicleId);
    Page<ReviewDTO> findReviewByVehicleId(Long vehicleId, Pageable pageable);

    Collection<ReviewDTO> findReviewByDriverId(Long driverId);
    Page<ReviewDTO> findReviewByDriverId(Long driverId, Pageable pageable);

    ReviewDTO createForVehicle(ReviewDTO review, Long rideId, Long vehicleId);
    ReviewDTO createForDriver(ReviewDTO review, Long rideId, Long driverId);
}
