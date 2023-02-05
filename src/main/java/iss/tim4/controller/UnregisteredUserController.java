package iss.tim4.controller;

import iss.tim4.domain.dto.ride.UnregisteredRideDTORequest;
import iss.tim4.domain.dto.ride.UnregisteredRideDTOResponse;
import iss.tim4.service.UnregisteredUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unregisteredUser")
@AllArgsConstructor
public class UnregisteredUserController {
    @Autowired
    private UnregisteredUserService unregisteredUserService;

    @PostMapping
    public ResponseEntity<UnregisteredRideDTOResponse> buildTrack(UnregisteredRideDTORequest request) {
        return new ResponseEntity<>(unregisteredUserService.buildRide(request), HttpStatus.OK);
    }
}
