package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.Category;
import ch.zli.m223.punchclock.service.CategoryService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestScoped
@Path("/categories")
public class CategoryController {
    @Inject
    JsonWebToken jwt;
    @Inject
    CategoryService categoryService;

    /**
     * get all categories
     * @return list of all categories
     */
    @GET
    @RolesAllowed({"User"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> list() {
        return categoryService.findAll();
    }

    /**
     * gets single category
     * @param id: id of category to get
     * @return category
     */
    @GET
    @RolesAllowed({"User"})
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category get(@PathParam("id") Long id) {
        return categoryService.find(id);
    }

    /**
     * deletes category
     * @param id: id of category to delete
     */
    @DELETE
    @RolesAllowed({"Admin"})
    @Produces(MediaType.TEXT_PLAIN)
    public void delete(Long id) {
        var dbCategory = categoryService.find(id);

        if (dbCategory == null) {
            throw new BadRequestException("Entry not found");
        }

        if (!jwt.getGroups().contains("Admin")) {
            throw new ForbiddenException();
        }

        categoryService.deleteCategory(dbCategory);
    }

    /**
     * creates new category
     * @param category: category to create
     * @return created category
     */
    @POST
    @RolesAllowed({"Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Category add(Category category) {
       if (categoryService.find(category.getId()) != null || categoryService.find(category.getName()) != null) {
           throw new BadRequestException();
       }

       return categoryService.createCategory(category);
    }

    /**
     * updates category
     * @param category: category to update
     * @return updated category
     */
    @PUT
    @RolesAllowed({"Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Category update(Category category) {
        if (categoryService.find(category.getId()) == null) {
            throw new BadRequestException();
        }

        return categoryService.updateCategory(category);
    }
}
