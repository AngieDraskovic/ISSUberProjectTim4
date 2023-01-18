package iss.tim4.controller;

import iss.tim4.domain.dto.*;
import iss.tim4.domain.dto.review.ReviewDTO;
import iss.tim4.domain.model.Review;
import iss.tim4.domain.model.Ride;
import iss.tim4.service.ReviewService;
import iss.tim4.service.RideServiceJPA;
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

    @Autowired
    RideServiceJPA rideServiceJPA;
    @GetMapping(value = "/{rideId}")
    public ResponseEntity<Collection<RideReviewsDTO>> findReviewByRideId(@PathVariable("rideId") Integer id) {
        Ride ride = rideServiceJPA.findOne(id);
        if(ride==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reviewService.findReviewByRideId(id),  HttpStatus.OK);
    }
    class CustomResponseEntity<T> extends ResponseEntity<T> {
        public CustomResponseEntity(T body, HttpStatus status) {
            super(body, status);
        }
    }
    @GetMapping(value = "/driver/{id}")
    public ResponseEntity<UberPageDTO<ReviewDTO>> findReviewByDriverId(
            @PathVariable("id") Integer id, Pageable pageable
    ) {
        return new ResponseEntity<>(reviewService.findReviewByDriverId(id, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/vehicle/{id}")
    public ResponseEntity<UberPageDTO<ReviewDTO>> findReviewByVehicleId(
            @PathVariable("id") Integer id, Pageable pageable
    ) {
        return new ResponseEntity<>(reviewService.findReviewByVehicleId(id, pageable), HttpStatus.OK);
    }

    @PostMapping(value = "/{rideId}/vehicle/{id}")
    public ResponseEntity<ReviewDTO> createForVehicle(
            @PathVariable("rideId") Integer rideId, @PathVariable("id") Integer vehicleId, CreateReviewDTO review
    ) {
        return new ResponseEntity<>(reviewService.createForVehicle(review, rideId, vehicleId), HttpStatus.OK);
    }

    @PostMapping(value = "/{rideId}/driver/{id}")
    public ResponseEntity<ReviewDTO> createForDriver(
            @PathVariable("rideId") Integer rideId, @PathVariable("id") Integer driverId, CreateReviewDTO review
    ) {
        return new ResponseEntity<>(reviewService.createForDriver(review, rideId, driverId), HttpStatus.OK);
    }
}
