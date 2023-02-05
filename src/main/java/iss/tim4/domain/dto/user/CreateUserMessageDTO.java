package iss.tim4.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserMessageDTO {
    @NotNull (message = "Field message is required!")
    private String message;
    @NotNull (message = "Field type is required!")
    private String type;
    private Integer rideId;    // Ne mora biti poruka u vezi voznje
}
