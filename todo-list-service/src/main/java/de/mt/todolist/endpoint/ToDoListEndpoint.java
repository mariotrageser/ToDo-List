package de.mt.todolist.endpoint;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.List;

@Path("/")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class ToDoListEndpoint {

    @GET
    @Path("/entries")
    public List<String> getEntries() {
        return Collections.singletonList("Katze füttern");
    }
}
