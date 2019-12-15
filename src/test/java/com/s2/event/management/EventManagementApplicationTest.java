package com.s2.event.management;

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.s2.event.management.model.Event;
import com.s2.event.management.model.EventLocation;
import com.s2.event.management.repository.EventManagementRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class EventManagementApplicationTest {

	@LocalServerPort
	private int port;

	@MockBean
	private EventManagementRepository eventManagementRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testSaveEvent() throws Exception {
		Mockito.when(eventManagementRepository.save(Mockito.any(Event.class))).thenReturn(mockEvent());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", "admin1");
		headers.set("roles", "ADMIN");
		ResponseEntity<Event> response = restTemplate.exchange(
				new URL("http://localhost:" + port + "/admin/v1").toString(), HttpMethod.POST,
				new HttpEntity<>(mockEvent(), headers), Event.class);
		assertTrue(StringUtils.contains(response.getBody().getName(), "eventName"));
	}

	@Test
	public void testSaveEventInvalid() throws Exception {
		Mockito.when(eventManagementRepository.save(Mockito.any(Event.class))).thenReturn(mockEvent());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", "admin1");
		headers.set("roles", "ADMIN");
		ResponseEntity<Event> response = restTemplate.exchange(
				new URL("http://localhost:" + port + "/admin/v1").toString(), HttpMethod.POST,
				new HttpEntity<>(new Event(), headers), Event.class);
		assertTrue(StringUtils.contains(response.getStatusCode().name(), HttpStatus.BAD_REQUEST.name()));
	}
	
	public static Event mockEvent() {
		Event event = new Event();
		event.setId("123");
		event.setName("eventName");
		event.setDescription("description");
		event.setPointOfContact("pointOfContact");
		event.setFromDateTime("01/01/2019 09:00:00");
		event.setToDateTime("01/01/2019 10:00:00");
		event.setStatus("OPEN");
		EventLocation eventLocation = new EventLocation();
		eventLocation.setAddress1("Address1");
		eventLocation.setAddress2("address2");
		eventLocation.setCity("city");
		eventLocation.setPinCode("12345");
		eventLocation.setState("TN");
		event.setEventLocation(eventLocation);
		return event;
	}
}
