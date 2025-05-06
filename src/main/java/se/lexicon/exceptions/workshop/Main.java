package se.lexicon.exceptions.workshop;

import java.io.IOException;
import java.util.List;

import se.lexicon.exceptions.NoDuplicatedNameExceptions;
import se.lexicon.exceptions.workshop.data_access.NameService;
import se.lexicon.exceptions.workshop.domain.Person;
import se.lexicon.exceptions.workshop.fileIO.CSVReader_Writer;

public class Main {

    public static void main(String[] args) throws IOException, NoDuplicatedNameExceptions {

        List<String> maleFirstNames = CSVReader_Writer.getMaleFirstNames();
        List<String> femaleFirstNames = CSVReader_Writer.getFemaleFirstNames();

        // Declare lastNames outside the try block to ensure it's in scope
        List<String> lastNames = CSVReader_Writer.getLastNames();

        // Initialize NameService with the loaded lists
        NameService nameService = new NameService(maleFirstNames, femaleFirstNames, lastNames);

        // Finding last names from file
        try {
            lastNames = CSVReader_Writer.getLastNames();
            System.out.println(lastNames);
        } catch (IOException e) {
            System.out.println("Error reading last names: " + e.getMessage());
        }
        // Test adding male first names
        try {
            // Add a new male first name
            nameService.addMaleFirstName("John");
            System.out.println("Successfully added John");

            // Try adding a duplicate male first name
            nameService.addMaleFirstName("John");  // This will throw an exception
        } catch (NoDuplicatedNameExceptions e) {
            System.out.println("Error adding male first name: " + e.getMessage());
        }

        // Test adding female first names
        try {
            // Add a new female first name
            nameService.addFemaleFirstName("Alice");
            System.out.println("Successfully added Alice");

            // Try adding a duplicate female first name
            nameService.addFemaleFirstName("Alice");  // This will throw an exception
        } catch (NoDuplicatedNameExceptions e) {
            System.out.println("Error adding female first name: " + e.getMessage());
        }

        // Test adding last names
        try {
            // Add a new last name
            nameService.addLastName("Smith");
            System.out.println("Successfully added last name Smith");

            // Try adding a duplicate last name
            nameService.addLastName("Smith");  // This will throw an exception
        } catch (NoDuplicatedNameExceptions e) {
            System.out.println("Error adding last name: " + e.getMessage());
        }


        // Ensure lastNames is not null before using it
        if (lastNames != null) {


            // Generating a random person using the NameService
            Person test = nameService.getNewRandomPerson();
            System.out.println(test);

            try {
                CSVReader_Writer.saveMaleNames(maleFirstNames);
                CSVReader_Writer.saveFemaleNames(femaleFirstNames);
                CSVReader_Writer.saveLastNames(lastNames);
                System.out.println("Names saved successfully.");
            } catch (Exception e) {
                System.out.println("Error saving names: " + e.getMessage());
            }
        } else {
            System.out.println("Last names could not be loaded. Cannot create NameService.");
        }
    }

}
