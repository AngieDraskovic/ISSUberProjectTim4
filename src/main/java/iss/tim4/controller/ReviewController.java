package iss.tim4.controller;

import iss.tim4.domain.dto.*;
import iss.tim4.domain.dto.review.ReviewDTORequest;
import iss.tim4.domain.dto.review.ReviewDTOResult;
import iss.tim4.domain.model.Passenger;
import iss.tim4.domain.model.Review;
import iss.tim4.domain.model.Ride;
import iss.tim4.service.PassengerServiceJPA;
import iss.tim4.service.ReviewService;
import iss.tim4.service.ReviewServiceJPA;
import iss.tim4.service.RideServiceJPA;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/review")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @Autowired
    private ReviewServiceJPA reviewServiceJPA;

    @Autowired
    private PassengerServiceJPA passengerServiceJPA;

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
    public ResponseEntity<UberPageDTO<ReviewDTOResult>> findReviewByDriverId(
            @PathVariable("id") Integer id, Pageable pageable
    ) {
        return new ResponseEntity<>(reviewService.findReviewByDriverId(id, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/vehicle/{id}")
    public ResponseEntity<UberPageDTO<ReviewDTOResult>> findReviewByVehicleId(
            @PathVariable("id") Integer id, Pageable pageable
    ) {
        return new ResponseEntity<>(reviewService.findReviewByVehicleId(id, pageable), HttpStatus.OK);
    }

    @PostMapping(value = "/{rideId}/vehicle/{id}")
    public ResponseEntity<ReviewDTOResult> createForVehicle(
            @PathVariable("rideId") Integer rideId, @PathVariable("id") Integer vehicleId, CreateReviewDTO review
    ) {
        return new ResponseEntity<>(reviewService.createForVehicle(review, rideId, vehicleId), HttpStatus.OK);
    }

    @PostMapping(value = "/{rideId}/driver/{id}")
    public ResponseEntity<ReviewDTOResult> createForDriver(
            @PathVariable("rideId") Integer rideId, @PathVariable("id") Integer driverId, CreateReviewDTO review
    ) {
        return new ResponseEntity<>(reviewService.createForDriver(review, rideId, driverId), HttpStatus.OK);
    }

    @PostMapping(value = "/{rideId}/{passengerId}")
    public ResponseEntity<ReviewDTOResult> createReview(@Valid @RequestBody ReviewDTORequest reviewDTORequest, @PathVariable("rideId") Integer rideId, @PathVariable("passengerId") Integer passengerId) {

        Ride ride = rideServiceJPA.findOne(rideId);
        Passenger passenger = passengerServiceJPA.findOne(passengerId);

        Review review = new Review(reviewDTORequest);
        review.setRide(ride);
        review.setPassenger(passenger);
        review.setDriver(ride.getDriver());
        review.setVehicle(ride.getDriver().getVehicle());
        reviewServiceJPA.save(review);

        ReviewDTOResult reviewDTOResult = new ReviewDTOResult(review);
        return new ResponseEntity<ReviewDTOResult>(reviewDTOResult, HttpStatus.OK);
    }
}
