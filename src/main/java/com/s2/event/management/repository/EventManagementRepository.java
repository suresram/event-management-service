package com.s2.event.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.s2.event.management.model.Event;

public interface EventManagementRepository extends MongoRepository<Event, String> {


}