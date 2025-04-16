package com.hexaware.petpals.dao;

import com.hexaware.petpals.entity.Pet;
import java.util.List;

public interface PetDAO {
    void addPet(Pet pet);
    List<Pet> getAllPets();
    void removePet(String name);
}

