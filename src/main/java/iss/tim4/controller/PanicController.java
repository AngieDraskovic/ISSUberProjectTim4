package iss.tim4.controller;

import iss.tim4.domain.dto.PanicDTO;
import iss.tim4.domain.dto.UberPageDTO;
import iss.tim4.service.PanicService;
import iss.tim4.service.PanicServiceJPA;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/panic")
@CrossOrigin(origins = "http://localhost:4200")
public class PanicController {
    @Autowired
    @Qualifier("panicServiceImpl")
    private PanicService panicService;

//    @GetMapping
//    public ResponseEntity<Collection<PanicDTO>> getDrivers() {
//        return new ResponseEntity<>(panicServiceJPA.findAll(), HttpStatus.OK);
//    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UberPageDTO<PanicDTO>> getDrivers(Pageable pageable) {
        return new ResponseEntity<>(panicService.findAll(pageable), HttpStatus.OK);
    }
}
