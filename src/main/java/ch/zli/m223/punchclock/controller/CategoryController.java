package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.Category;
import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.service.CategoryService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestScoped
@RolesAllowed({"User"})
@Path("/categories")
public class CategoryController {
    @Inject
    JsonWebToken jwt;
    @Inject
    CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> list() {
        return categoryService.findAll();
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public String delete(String name) {
        if (categoryService.tryDeleteCategory(name)) {
            return "delete successful";
        }

        throw new BadRequestException("delete failed");
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Category add(Category category) {
       return categoryService.createCategory(category);
    }
}
