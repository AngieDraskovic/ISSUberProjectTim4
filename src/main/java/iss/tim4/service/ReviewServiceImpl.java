package iss.tim4.service;

import iss.tim4.domain.dto.*;
import iss.tim4.domain.dto.passenger.PassengerDTOResponse;
import iss.tim4.domain.dto.review.ReviewDTOResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private PassengerServiceJPA passengerServiceJPA;

    private ReviewDTOResult mockReviewDTO() {
        return new ReviewDTOResult(
                1L,
                5,
                5,
                "KEK",
                new PassengerDTOResponse(passengerServiceJPA.findAll().get(0)));
    }

    @Override
    public Collection<RideReviewsDTO> findReviewByRideId(Integer id) {
        return List.of(new RideReviewsDTO(mockReviewDTO(), mockReviewDTO()));
    }

    @Override
    public Collection<ReviewDTOResult> findReviewByVehicleId(Integer vehicleId) {
        return List.of(mockReviewDTO(), mockReviewDTO());
    }

    @Override
    public UberPageDTO<ReviewDTOResult> findReviewByVehicleId(Integer vehicleId, Pageable pageable) {
        return new UberPageDTO<ReviewDTOResult>(2L, List.of(mockReviewDTO(), mockReviewDTO()));
    }

    @Override
    public Collection<ReviewDTOResult> findReviewByDriverId(Integer driverId) {
        return List.of(mockReviewDTO(), mockReviewDTO());
    }

    @Override
    public UberPageDTO<ReviewDTOResult> findReviewByDriverId(Integer driverId, Pageable pageable) {
        return new UberPageDTO<>(2L, List.of(mockReviewDTO(), mockReviewDTO()));
    }

    @Override
    public ReviewDTOResult createForVehicle(CreateReviewDTO review, Integer rideId, Integer vehicleId) {
        return mockReviewDTO();
    }

    @Override
    public ReviewDTOResult createForDriver(CreateReviewDTO review, Integer rideId, Integer driverId) {
        return mockReviewDTO();
    }
}
