package com.hexaware.petpals.service;

import com.hexaware.petpals.dao.AdoptionEventDAO;
import com.hexaware.petpals.dao.AdoptionEventDAOImpl;

public class AdoptionEventServiceImpl implements AdoptionEventService {

    private AdoptionEventDAO adoptionEventDAO;

    public AdoptionEventServiceImpl() {
        adoptionEventDAO = new AdoptionEventDAOImpl();
    }

    @Override
    public void registerForEvent(String participantName, String role) {
        adoptionEventDAO.registerParticipant(participantName, role);
    }
}

