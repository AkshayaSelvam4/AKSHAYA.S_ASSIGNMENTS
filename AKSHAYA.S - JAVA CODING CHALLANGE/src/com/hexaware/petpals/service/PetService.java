package com.hexaware.petpals.service;

import com.hexaware.petpals.entity.Pet;
import java.util.List;

public interface PetService {
    void addPet(Pet pet) throws Exception;
    List<Pet> getAllPets();
    void removePet(String name);
}
