package iss.tim4.service;

import iss.tim4.domain.dto.ride.UnregisteredRideDTORequest;
import iss.tim4.domain.dto.ride.UnregisteredRideDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnregisteredUserServiceImpl implements UnregisteredUserService {
    @Autowired
    private RouteServiceJPA routeServiceJPA;

    @Override
    public UnregisteredRideDTOResponse buildRide(UnregisteredRideDTORequest rideDTORequest) {
        return new UnregisteredRideDTOResponse(
                1, 2
        );
    }
}
