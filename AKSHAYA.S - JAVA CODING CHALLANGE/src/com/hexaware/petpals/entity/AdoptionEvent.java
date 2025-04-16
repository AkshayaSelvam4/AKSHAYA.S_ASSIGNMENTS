package com.hexaware.petpals.entity;

import java.util.ArrayList;
import java.util.List;

public class AdoptionEvent {
    private List<IAdoptable> participants;

    public AdoptionEvent() {
        participants = new ArrayList<>();
    }

    public void registerParticipant(IAdoptable participant) {
        participants.add(participant);
    }

    public void hostEvent() {
        System.out.println("Hosting adoption event with " + participants.size() + " participant(s).");
        for (IAdoptable p : participants) {
            p.adopt();
        }
    }
}

