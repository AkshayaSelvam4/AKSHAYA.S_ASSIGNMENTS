package com.hexaware.petpals.service;

import com.hexaware.petpals.dao.DonationDAO;
import com.hexaware.petpals.dao.DonationDAOImpl;
import com.hexaware.petpals.entity.CashDonation;
import com.hexaware.petpals.exception.InsufficientFundsException;

public class DonationServiceImpl implements DonationService {

    private DonationDAO donationDAO;

    public DonationServiceImpl() {
        donationDAO = new DonationDAOImpl();
    }

    @Override
    public void processCashDonation(CashDonation donation) throws Exception {
        if (donation.getAmount() < 10.0) {
            throw new InsufficientFundsException("Donation amount must be at least $10.");
        }
        donationDAO.recordCashDonation(donation);
    }
}

