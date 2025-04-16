package com.hexaware.petpals.entity;

public abstract class Donation {
    protected String donorName;
    protected double amount;

    public Donation(String donorName, double amount) {
        this.donorName = donorName;
        this.amount = amount;
    }

    public String getDonorName() { return donorName; }
    public void setDonorName(String donorName) { this.donorName = donorName; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public abstract void recordDonation();
}
