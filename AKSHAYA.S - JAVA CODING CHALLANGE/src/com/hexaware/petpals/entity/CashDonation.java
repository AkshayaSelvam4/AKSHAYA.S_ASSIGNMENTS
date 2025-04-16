package com.hexaware.petpals.entity;

import java.time.LocalDate;

public class CashDonation extends Donation {
    private LocalDate donationDate;

    public CashDonation(String donorName, double amount, LocalDate donationDate) {
        super(donorName, amount);
        this.donationDate = donationDate;
    }

    public LocalDate getDonationDate() { return donationDate; }
    public void setDonationDate(LocalDate donationDate) { this.donationDate = donationDate; }

    @Override
    public void recordDonation() {
        System.out.println("Cash donation recorded from " + donorName + " on " + donationDate + " of amount $" + amount);
    }
}

