package iss.tim4.service;

import iss.tim4.domain.dto.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewServiceImpl implements ReviewService {
    private ReviewDTO mockReviewDTO() {
        return new ReviewDTO(1L, 5, "KEK", null, null);
    }

    @Override
    public Map<String, ReviewDTO> findReviewByRideId(Long id) {
        Map<String, ReviewDTO> answer = new HashMap<>();
        answer.put("vehicleReview", mockReviewDTO());
        answer.put("driverReview", mockReviewDTO());
        return answer;
    }

    @Override
    public Collection<ReviewDTO> findReviewByVehicleId(Long vehicleId) {
        return List.of(mockReviewDTO(), mockReviewDTO());
    }

    @Override
    public Page<ReviewDTO> findReviewByVehicleId(Long vehicleId, Pageable pageable) {
        return new PageImpl<ReviewDTO>(List.of(mockReviewDTO(), mockReviewDTO()));
    }

    @Override
    public Collection<ReviewDTO> findReviewByDriverId(Long driverId) {
        return List.of(mockReviewDTO(), mockReviewDTO());
    }

    @Override
    public Page<ReviewDTO> findReviewByDriverId(Long driverId, Pageable pageable) {
        return new PageImpl<ReviewDTO>(List.of(mockReviewDTO(), mockReviewDTO()));
    }

    @Override
    public ReviewDTO createForVehicle(ReviewDTO review, Long rideId, Long vehicleId) {
        return mockReviewDTO();
    }

    @Override
    public ReviewDTO createForDriver(ReviewDTO review, Long rideId, Long driverId) {
        return mockReviewDTO();
    }
}
