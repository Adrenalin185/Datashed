package techtest.fileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import techtest.entity.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private static String Read_File = "src/main/read_file_location/DataShed_Technical_Test.csv";
    private static String Write_File = "src/main/write_file_location/DataShed_Duplicates_List.csv";
    private static final Logger logger = LogManager.getLogger(DataReader.class);

    private List<Person> Duplicate_Person_List = new ArrayList<>();
    private List<Person> Person_List = new ArrayList<>();

    private int recordCheck = 1;

    public void readFromFile(){

        getFromFile();

        createDuplicateList();

        logger.info("Unique number of persons: {}", Person_List.size());
        logger.info("Duplicate number of persons: {}", Duplicate_Person_List.size());

    }

    private void getFromFile() {

        logger.info("Reading data from file: {}", Read_File);

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Read_File))){

            boolean firstLine = true;
            String line;

            while((line = bufferedReader.readLine()) != null){


                if (firstLine){
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                Person person = createPerson(data);

                if (!duplicateCheck(person)){
                    Person_List.add(person);
                } else {
                    Duplicate_Person_List.add(person);
                }

            }



        } catch (IOException e){
            logger.error("File not found", e);
        }
    }

    private void createDuplicateList(){

        logger.info("Writing duplicate data to file: {}", Write_File);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Write_File))){

            for (Person person: Duplicate_Person_List){
                bufferedWriter.write(person.toString());
                bufferedWriter.newLine();
            }

        } catch (IOException e){
            logger.error("Could not create File", e);
        }
    }

    private boolean duplicateCheck(Person person){

        for (Person listPerson : Person_List){

            int matches = 0;

            if (stringChecker(person.getGivenName(), listPerson.getGivenName())){
                matches++;
            }

            if (stringChecker(person.getSurname(), listPerson.getSurname())){
                matches++;
            }

            if (person.getDateOfBirth().equals(listPerson.getDateOfBirth())){
                matches++;
            }

            if (person.getSex().equals(listPerson.getSex())){
                matches++;
            }

            if (matches > 2){
                return true;
            }
        }

        return false;
    }

    private boolean stringChecker(String name, String listName){

        int nameLength = listName.length();
        int comparisonMatches = 0;

        if (name.length() < 3 || listName.length() < 3){
            return true;
        }

        if (name.charAt(0) != listName.charAt(0) || name.charAt(1) != listName.charAt(1) || name.charAt(2) != listName.charAt(2)){
            return false;
        }
        if (name.length() > listName.length()){
            nameLength = name.length();
        }

        int i = 0;

        while(i <= nameLength){

            if (i == (name.length() - 1) || i == (listName.length() - 1)){
                break;
            }

            if (name.charAt(i) == listName.charAt(i)){
                comparisonMatches++;
            }  if (name.charAt(i + 1) == listName.charAt(i)){
                comparisonMatches++;
            }

            i++;
        }

        if (comparisonMatches <= (nameLength - 2)){
            return true;
        }

        return false;
    }

    private Person createPerson(String[] data){
        return new Person(data[0], data[1], data[2], data[3]);
    }


}
