package iss.tim4.controller;

import iss.tim4.domain.dto.PanicDTO;
import iss.tim4.service.PanicServiceJPA;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/panic")
@AllArgsConstructor
public class PanicController {
    @Autowired
    private PanicServiceJPA panicServiceJPA;

    @GetMapping
    public ResponseEntity<List<PanicDTO>> getDrivers() {
        return new ResponseEntity<>(panicServiceJPA.findAll().stream().map(PanicDTO::new).toList(), HttpStatus.OK);
    }
}
