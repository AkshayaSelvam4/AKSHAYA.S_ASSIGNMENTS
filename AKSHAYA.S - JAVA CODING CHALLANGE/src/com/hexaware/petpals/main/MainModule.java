package com.hexaware.petpals.main;

import com.hexaware.petpals.entity.*;
import com.hexaware.petpals.exception.*;
import com.hexaware.petpals.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PetService petService = new PetServiceImpl();
        DonationService donationService = new DonationServiceImpl();
        AdoptionEventService eventService = new AdoptionEventServiceImpl();

        int choice;

        do {
            System.out.println("\n--- PetPals Menu ---");
            System.out.println("1. Add Pet");
            System.out.println("2. View All Pets");
            System.out.println("3. Remove Pet");
            System.out.println("4. Make a Cash Donation");
            System.out.println("5. Register for Adoption Event");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Pet Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Age: ");
                        int age = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Breed: ");
                        String breed = sc.nextLine();

                        Pet pet = new Pet(name, age, breed);
                        petService.addPet(pet);
                        System.out.println("Pet added successfully!");
                        break;

                    case 2:
                        List<Pet> pets = petService.getAllPets();
                        if (pets.isEmpty()) {
                            System.out.println("No pets available.");
                        } else {
                            pets.forEach(System.out::println);
                        }
                        break;

                    case 3:
                        System.out.print("Enter Pet Name to Remove: ");
                        String removeName = sc.nextLine();
                        petService.removePet(removeName);
                        System.out.println("Pet removed successfully!");
                        break;

                    case 4:
                        System.out.print("Enter Donor Name: ");
                        String donorName = sc.nextLine();

                        System.out.print("Enter Donation Amount: ");
                        double amount = Double.parseDouble(sc.nextLine());

                        CashDonation donation = new CashDonation(donorName, amount, LocalDate.now());
                        donationService.processCashDonation(donation);
                        System.out.println("Donation processed successfully!");
                        break;

                    case 5:
                        System.out.print("Enter Participant Name: ");
                        String participantName = sc.nextLine();

                        System.out.print("Enter Role (Shelter/Adopter): ");
                        String role = sc.nextLine();

                        eventService.registerForEvent(participantName, role);
                        System.out.println("Successfully registered for the adoption event!");
                        break;

                    case 6:
                        System.out.println("Thank you for using PetPals!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InvalidPetAgeException | InsufficientFundsException | AdoptionException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected Error: " + e.getMessage());
            }
        } while (choice != 6);

        sc.close();
    }
}
