package com.hexaware.petpals.service;

import com.hexaware.petpals.entity.CashDonation;

public interface DonationService {
    void processCashDonation(CashDonation donation) throws Exception;
}

