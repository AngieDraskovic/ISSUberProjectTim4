package iss.tim4.controller;

import iss.tim4.domain.dto.security.EmailPasswordDTO;
import iss.tim4.domain.dto.security.TokenDTO;
import iss.tim4.domain.model.Role;
import iss.tim4.domain.model.User;
import iss.tim4.security.jwt.JwtTokenUtil;
import iss.tim4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping()
	public TokenDTO login(@RequestBody EmailPasswordDTO passwordDTO) {
		UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
				passwordDTO.getEmail(),
				passwordDTO.getPassword()
		);
		Authentication auth = authenticationManager.authenticate(authReq);

		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(auth);

		User user = userService.getUser(passwordDTO.getEmail());
		String token = jwtTokenUtil.generateToken(passwordDTO.getEmail(), user.getRole(), user.getId());

		return new TokenDTO(token, null);
	}

}
