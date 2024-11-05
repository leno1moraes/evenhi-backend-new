package com.backend.evenhi.service;


import com.backend.evenhi.dto.EventDTO;
import com.backend.evenhi.model.Event;
import com.backend.evenhi.model.User;
import com.backend.evenhi.repository.EventRepository;
import com.backend.evenhi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository; // Repositório para buscar o usuário

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<EventDTO> getEventById(Long id) {
        return eventRepository.findById(id).map(this::convertToDTO);
    }

    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = convertToEntity(eventDTO);
        Event savedEvent = eventRepository.save(event);
        return convertToDTO(savedEvent);
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        return eventRepository.findById(id)
                .map(event -> {
                    event.setNameEvent(eventDTO.getNameEvent());
                    event.setPathImage(eventDTO.getPathImage());
                    event.setZipcode(eventDTO.getZipcode());
                    event.setDistrict(eventDTO.getDistrict());
                    event.setStreet(eventDTO.getStreet());
                    event.setState(eventDTO.getState());
                    event.setCity(eventDTO.getCity());
                    event.setCountry(eventDTO.getCountry());
                    event.setSubscribers(eventDTO.getSubscribers());
                    event.setStatus(eventDTO.getStatus());
                    event.setDate(eventDTO.getDate());

                    User user = userRepository.findById(eventDTO.getUserId())
                            .orElseThrow(() -> new RuntimeException("User not found with id " + eventDTO.getUserId()));
                    event.setUser(user);

                    Event updatedEvent = eventRepository.save(event);
                    return convertToDTO(updatedEvent);
                }).orElseThrow(() -> new RuntimeException("Event not found with id " + id));
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setNameEvent(event.getNameEvent());
        dto.setPathImage(event.getPathImage());
        dto.setZipcode(event.getZipcode());
        dto.setDistrict(event.getDistrict());
        dto.setStreet(event.getStreet());
        dto.setState(event.getState());
        dto.setCity(event.getCity());
        dto.setCountry(event.getCountry());
        dto.setSubscribers(event.getSubscribers());
        dto.setUserId(event.getUser() != null ? event.getUser().getUserId() : null);
        dto.setStatus(event.getStatus());
        dto.setDate(event.getDate());
        return dto;
    }

    private Event convertToEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setId(eventDTO.getId());
        event.setNameEvent(eventDTO.getNameEvent());
        event.setPathImage(eventDTO.getPathImage());
        event.setZipcode(eventDTO.getZipcode());
        event.setDistrict(eventDTO.getDistrict());
        event.setStreet(eventDTO.getStreet());
        event.setState(eventDTO.getState());
        event.setCity(eventDTO.getCity());
        event.setCountry(eventDTO.getCountry());
        event.setSubscribers(eventDTO.getSubscribers());
        event.setStatus(eventDTO.getStatus());
        event.setDate(eventDTO.getDate());

        if (eventDTO.getUserId() != null) {
            User user = userRepository.findById(eventDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id " + eventDTO.getUserId()));
            event.setUser(user);
        }
        return event;
    }
}

