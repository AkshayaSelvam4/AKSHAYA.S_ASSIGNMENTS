package com.hexaware.petpals.service;

import com.hexaware.petpals.dao.PetDAO;
import com.hexaware.petpals.dao.PetDAOImpl;
import com.hexaware.petpals.entity.Pet;
import com.hexaware.petpals.exception.InvalidPetAgeException;

import java.util.List;

public class PetServiceImpl implements PetService {

    private PetDAO petDAO;

    public PetServiceImpl() {
        petDAO = new PetDAOImpl();
    }

    @Override
    public void addPet(Pet pet) throws Exception {
        if (pet.getAge() <= 0) {
            throw new InvalidPetAgeException("Pet age must be a positive integer.");
        }
        petDAO.addPet(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petDAO.getAllPets();
    }

    @Override
    public void removePet(String name) {
        petDAO.removePet(name);
    }
}

