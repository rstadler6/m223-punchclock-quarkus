package ch.zli.m223.punchclock.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.service.CategoryService;
import ch.zli.m223.punchclock.service.EntryService;
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
    CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Entry> list() {
        return entryService.findAll();
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public String delete(Long id) {
        if (entryService.tryDeleteEntry(id)) {
            return "delete successful";
        }

        throw new BadRequestException("delete failed");
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Entry update(Entry entry) {
        if (entryService.find(entry.getId()) == null)
            throw new BadRequestException("Entry not found");
        if (categoryService.find(entry.getCategory().getName()) == null)
            throw new BadRequestException("Category not found");

        return entryService.updateEntry(entry);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Entry add(Entry entry) {
        return entryService.createEntry(entry);
    }
}
