package ch.zli.m223.punchclock.controller;


import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.AuthService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/auth")
public class AuthController {
    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public void register(User user) {
        authService.createUser(user);
    }

    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String login(User user) {
        if (authService.validateCredentials(user))
            return authService.generateToken(user);
        throw new BadRequestException();
    }
}
