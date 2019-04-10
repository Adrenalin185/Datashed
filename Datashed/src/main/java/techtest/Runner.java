package techtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import techtest.fileReader.DataReader;

public class Runner {

    private static final Logger logger = LogManager.getLogger(Runner.class);
    private static final DataReader dataReader = new DataReader();

    public static void main(String[] args) {

        logger.info("Application Started");
        dataReader.readFromFile();


    }
}
