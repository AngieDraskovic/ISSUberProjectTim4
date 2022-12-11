package iss.tim4.service;

import iss.tim4.domain.dto.*;
import iss.tim4.domain.dto.passenger.PassengerDTOResponse;
import iss.tim4.domain.dto.review.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private PassengerServiceJPA passengerServiceJPA;

    private ReviewDTO mockReviewDTO() {
        return new ReviewDTO(
                1L,
                5,
                "KEK",
                new PassengerDTOResponse(passengerServiceJPA.findAll().get(0)));
    }

    @Override
    public Collection<RideReviewsDTO> findReviewByRideId(Long id) {
        return List.of(new RideReviewsDTO(mockReviewDTO(), mockReviewDTO()));
    }

    @Override
    public Collection<ReviewDTO> findReviewByVehicleId(Long vehicleId) {
        return List.of(mockReviewDTO(), mockReviewDTO());
    }

    @Override
    public UberPageDTO<ReviewDTO> findReviewByVehicleId(Long vehicleId, Pageable pageable) {
        return new UberPageDTO<ReviewDTO>(2L, List.of(mockReviewDTO(), mockReviewDTO()));
    }

    @Override
    public Collection<ReviewDTO> findReviewByDriverId(Long driverId) {
        return List.of(mockReviewDTO(), mockReviewDTO());
    }

    @Override
    public UberPageDTO<ReviewDTO> findReviewByDriverId(Long driverId, Pageable pageable) {
        return new UberPageDTO<>(2L, List.of(mockReviewDTO(), mockReviewDTO()));
    }

    @Override
    public ReviewDTO createForVehicle(CreateReviewDTO review, Long rideId, Long vehicleId) {
        return mockReviewDTO();
    }

    @Override
    public ReviewDTO createForDriver(CreateReviewDTO review, Long rideId, Long driverId) {
        return mockReviewDTO();
    }
}
