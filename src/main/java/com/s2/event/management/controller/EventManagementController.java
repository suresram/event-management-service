package com.s2.event.management.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.s2.event.management.model.Event;
import com.s2.event.management.service.EventManagementService;

@Controller
public class EventManagementController {

	@Autowired
	private EventManagementService eventManagementService;

	@RequestMapping(value = "admin/v1", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Event> save(@Valid @RequestBody Event event, @RequestHeader(value = "userId", required = true) String userId,
			@RequestHeader(value = "roles", required = true) String roles) {
		event.setStatus("open");
		event.setCreatedBy(userId);
		event.setCreatedDate(new Date());
		eventManagementService.save(event);
		return ResponseEntity.ok(event);
	}
	

}
