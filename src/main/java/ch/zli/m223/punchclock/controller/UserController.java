package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.UserService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestScoped
@RolesAllowed({"User"})
@Path("/users")
public class UserController {
    @Inject
    JsonWebToken jwt;
    @Inject
    UserService userService;

    /**
     * get all users
     * @return list of all users
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> list() {
        return userService.findAll();
    }

    /**
     * get single user
     * @param username: username of user to get
     * @return user
     */
    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public User get(@PathParam("username") String username) {
        return userService.find(username);
    }

    /**
     * update user
     * @param user: user to update
     * @return updated user
     */
    @PUT
    @RolesAllowed("Admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User update(User user) {
        return userService.updateUser(user);
    }
}