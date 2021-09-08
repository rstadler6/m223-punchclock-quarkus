package ch.zli.m223.punchclock.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.service.CategoryService;
import ch.zli.m223.punchclock.service.EntryService;

@Path("/entries")
public class EntryController {

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
