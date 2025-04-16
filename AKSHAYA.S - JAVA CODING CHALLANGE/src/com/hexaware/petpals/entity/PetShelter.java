package com.hexaware.petpals.entity;

import java.util.ArrayList;
import java.util.List;

public class PetShelter {
    private List<Pet> availablePets;

    public PetShelter() {
        availablePets = new ArrayList<>();
    }

    public void addPet(Pet pet) {
        availablePets.add(pet);
    }

    public void removePet(Pet pet) {
        availablePets.remove(pet);
    }

    public List<Pet> listAvailablePets() {
        return availablePets;
    }
}

