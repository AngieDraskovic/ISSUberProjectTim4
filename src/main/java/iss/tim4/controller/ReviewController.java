package iss.tim4.controller;

import iss.tim4.domain.dto.*;
import iss.tim4.domain.dto.review.ReviewDTO;
import iss.tim4.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/review")
@AllArgsConstructor
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @GetMapping(value = "/{rideId}")
    public ResponseEntity<Collection<RideReviewsDTO>> findReviewByRideId(@PathVariable("rideId") Long id) {
        return new ResponseEntity<>(reviewService.findReviewByRideId(id),  HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{id}")
    public ResponseEntity<UberPageDTO<ReviewDTO>> findReviewByDriverId(
            @PathVariable("id") Long id, Pageable pageable
    ) {
        return new ResponseEntity<>(reviewService.findReviewByDriverId(id, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/vehicle/{id}")
    public ResponseEntity<UberPageDTO<ReviewDTO>> findReviewByVehicleId(
            @PathVariable("id") Long id, Pageable pageable
    ) {
        return new ResponseEntity<>(reviewService.findReviewByVehicleId(id, pageable), HttpStatus.OK);
    }

    @PostMapping(value = "/{rideId}/vehicle/{id}")
    public ResponseEntity<ReviewDTO> createForVehicle(
            @PathVariable("rideId") Long rideId, @PathVariable("id") Long vehicleId, CreateReviewDTO review
    ) {
        return new ResponseEntity<>(reviewService.createForVehicle(review, rideId, vehicleId), HttpStatus.OK);
    }

    @PostMapping(value = "/{rideId}/driver/{id}")
    public ResponseEntity<ReviewDTO> createForDriver(
            @PathVariable("rideId") Long rideId, @PathVariable("id") Long driverId, CreateReviewDTO review
    ) {
        return new ResponseEntity<>(reviewService.createForDriver(review, rideId, driverId), HttpStatus.OK);
    }
}
