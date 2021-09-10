package ch.zli.m223.punchclock.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import ch.zli.m223.punchclock.domain.Comment;
import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.service.CategoryService;
import ch.zli.m223.punchclock.service.EntryService;
import ch.zli.m223.punchclock.service.UserService;
import org.eclipse.microprofile.jwt.JsonWebToken;

@RequestScoped
@RolesAllowed({"User"})
@Path("/entries")
public class EntryController {
    @Inject
    JsonWebToken jwt;
    @Inject
    EntryService entryService;
    @Inject
    UserService userService;
    @Inject
    CategoryService categoryService;

    /**
     * gets all entries
     * @return list of all entries
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Entry> list() {
        return entryService.findAll();
    }

    /**
     * gets single entry
     * @param id: id of entry to get
     * @return entry
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Entry get(@PathParam("id") Long id) {
        return entryService.find(id);
    }

    /**
     * deletes entry
     * @param id: id of entry to delete
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        var dbEntry = entryService.find(id);

        if (dbEntry == null) {
            throw new BadRequestException("Entry not found");
        }

        if (!jwt.getGroups().contains("Admin")) {
            var user = userService.find(jwt.getName());

            if (!dbEntry.getUser().equals(user))
                throw new ForbiddenException();
        }

        entryService.deleteEntry(dbEntry);
    }

    /**
     * updates entry
     * @param entry: entry to update
     * @return updated entry
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Entry update(Entry entry) {
        var dbEntry = entryService.find(entry.getId());

        if (entryService.find(entry.getId()) == null) {
            throw new BadRequestException("Entry not found");
        }

        if (!jwt.getGroups().contains("Admin")) {
            var user = userService.find(jwt.getName());

            if (!dbEntry.getUser().equals(user))
                throw new ForbiddenException();
        }

        if (categoryService.find(entry.getCategory().getId()) == null) {
            throw new BadRequestException("Category not found");
        }

        return entryService.updateEntry(entry);
    }

    /**
     * creates entry
     * @param entry: entry to create
     * @return created entry
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Entry add(Entry entry) {
        System.out.println(jwt.getName());
        var user = userService.find(jwt.getName().trim());
        entry.setUser(user);

        if (entry.getCategory() != null && categoryService.find(entry.getCategory().getId()) == null) {
            throw new BadRequestException("Category not found");
        }

        return entryService.createEntry(entry);
    }

    /**
     * adds comment to entry
     * @param id: id of entry to comment
     * @param comment: comment to add
     */
    @POST
    @Path("/{id}/comment")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void comment(@PathParam("id") Long id, Comment comment) {
        var user = userService.find(jwt.getName());
        comment.setUser(user);
        comment.setTimestamp(LocalDateTime.now());

        var dbEntry = entryService.find(id);

        if (dbEntry == null) {
            throw new BadRequestException("Entry not found");
        }

        entryService.comment(dbEntry, comment);
    }
}
