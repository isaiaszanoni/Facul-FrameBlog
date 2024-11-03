package com.descomplica.FrameBlog.services;

import com.descomplica.FrameBlog.requests.AuthRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {

    String getToken(AuthRequest auth);

    String validateJwtToken(String token);

}
