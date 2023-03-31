package com.jonam;

import com.jonam.community.Event;
import com.jonam.repository.EventRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.net.URI;
import java.net.URISyntaxException;

@Path("/communities/{communityId}/events")
public class EventResource {
    @Inject
    EventRepository repository;

    @GET
    @Path("/{id}")
    @RolesAllowed("user")
    public Response get(@PathParam("communityId") String communityId, @PathParam("id") String id) {
        Event event = repository.findById(new ObjectId(id));
        return Response.ok(event).build();
    }

    @GET
    @RolesAllowed("user")
    public Response get(@PathParam("communityId") String communityId) {
        return Response.ok(repository.listAll()).build();
    }

    @GET
    @Path("/search/{name}")
    @RolesAllowed("user")
    public Response search(@PathParam("communityId") String communityId, @PathParam("name") String name) {
        Event event = repository.findByName(name);
        return event != null ? Response.ok(event).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @RolesAllowed("user")
    public Response create(@PathParam("communityId") String communityId, Event event) throws URISyntaxException {
        repository.persist(event);
        return Response.created(new URI("/" + event.id)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("user")
    public Response update(@PathParam("communityId") String communityId, @PathParam("id") String id, Event event) {
        event.id = new ObjectId(id);
        repository.update(event);
        return Response.ok(event).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("user")
    public Response delete(@PathParam("communityId") String communityId, @PathParam("id") String id) {
        Event event = repository.findById(new ObjectId(id));
        repository.delete(event);
        return Response.noContent().build();
    }
}
