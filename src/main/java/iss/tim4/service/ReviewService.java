package iss.tim4.service;

import iss.tim4.domain.dto.CreateReviewDTO;
import iss.tim4.domain.dto.review.ReviewDTOResult;
import iss.tim4.domain.dto.RideReviewsDTO;
import iss.tim4.domain.dto.UberPageDTO;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface ReviewService {
    Collection<RideReviewsDTO> findReviewByRideId(Integer id);

    Collection<ReviewDTOResult> findReviewByVehicleId(Integer vehicleId);
    UberPageDTO<ReviewDTOResult> findReviewByVehicleId(Integer vehicleId, Pageable pageable);

    Collection<ReviewDTOResult> findReviewByDriverId(Integer driverId);
    UberPageDTO<ReviewDTOResult> findReviewByDriverId(Integer driverId, Pageable pageable);

    ReviewDTOResult createForVehicle(CreateReviewDTO review, Integer rideId, Integer vehicleId);
    ReviewDTOResult createForDriver(CreateReviewDTO review, Integer rideId, Integer driverId);
}
