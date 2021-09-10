package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.AuthService;
import ch.zli.m223.punchclock.service.UserService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/auth")
public class AuthController {
    @Inject
    AuthService authService;
    @Inject
    UserService userService;

    /**
     * register new user
     * @param user: user to register
     * @return jwt token
     */
    @POST
    @Path("/register")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String register(User user) {
        if (authService.userExists(user.getUsername()))
            throw new BadRequestException();
        authService.createUser(user);
        return login(user);
    }

    /**
     * login
     * @param user: user to login
     * @return jwt token
     */
    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public String login(User user) {
        if (authService.validateCredentials(user))
            return authService.generateToken(userService.find(user.getUsername()));
        throw new BadRequestException();
    }
}
