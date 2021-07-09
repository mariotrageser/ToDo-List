package de.mt.todolist.entry;


import de.mt.todolist.entity.ToDoEntry;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Arrays;
import java.util.List;

@Path("/")
@Consumes({"application/json"})
@Produces({"application/json"})
public class EntryEndpoint {

    @Inject
    private EntryBean entryBean;

    @GET
    @Path("/entries")
    public List<ToDoEntry> getEntries() {
        return entryBean.getEntries();
    }
}
