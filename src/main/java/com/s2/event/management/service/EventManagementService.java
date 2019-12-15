package com.s2.event.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s2.event.management.model.Event;
import com.s2.event.management.repository.EventManagementRepository;

@Service
public class EventManagementService {

	@Autowired
	private EventManagementRepository eventManagementRepository;

	public Event save(Event event) {
		return eventManagementRepository.save(event);
	}
}
