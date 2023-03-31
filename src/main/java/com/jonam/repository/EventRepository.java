package com.jonam.repository;

import com.jonam.community.Event;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EventRepository implements PanacheMongoRepository<Event> {
    public Event findByName(String name) {
        return find("name", name).firstResult();
    }

    public List<Event> findOrderedName() {
        return listAll(Sort.by("name"));
    }
}
