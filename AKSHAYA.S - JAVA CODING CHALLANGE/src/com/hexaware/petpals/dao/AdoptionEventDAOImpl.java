package com.hexaware.petpals.dao;

import com.hexaware.petpals.util.DBConnUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdoptionEventDAOImpl implements AdoptionEventDAO {

    private Connection conn;

    public AdoptionEventDAOImpl() {
        conn = DBConnUtil.getConnection();
    }

    @Override
    public void registerParticipant(String participantName, String role) {
        String sql = "INSERT INTO participants (name, role) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, participantName);
            stmt.setString(2, role);
            stmt.executeUpdate();
            System.out.println("Participant registered.");
        } catch (SQLException e) {
            System.out.println("DB Error (RegisterParticipant): " + e.getMessage());
        }
    }
}

