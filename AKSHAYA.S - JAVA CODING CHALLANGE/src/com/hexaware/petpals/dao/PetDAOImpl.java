package com.hexaware.petpals.dao;

import com.hexaware.petpals.entity.Pet;
import com.hexaware.petpals.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAOImpl implements PetDAO {

    private Connection conn;

    public PetDAOImpl() {
        this.conn = DBConnUtil.getConnection();
    }

    @Override
    public void addPet(Pet pet) {
        String sql = "INSERT INTO pets (name, age, breed) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pet.getName());
            stmt.setInt(2, pet.getAge());
            stmt.setString(3, pet.getBreed());
            stmt.executeUpdate();
            System.out.println("Pet added to database.");
        } catch (SQLException e) {
            System.out.println("DB Error (AddPet): " + e.getMessage());
        }
    }

    @Override
    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pets.add(new Pet(rs.getString("name"), rs.getInt("age"), rs.getString("breed")));
            }
        } catch (SQLException e) {
            System.out.println("DB Error (GetAllPets): " + e.getMessage());
        }
        return pets;
    }

    @Override
    public void removePet(String name) {
        String sql = "DELETE FROM pets WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            System.out.println("Pet removed from database.");
        } catch (SQLException e) {
            System.out.println("DB Error (RemovePet): " + e.getMessage());
        }
    }
}

