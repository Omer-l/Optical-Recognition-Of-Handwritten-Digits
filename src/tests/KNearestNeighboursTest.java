package tests;

import nearestNeighbour.NearestNeighbour;
import org.junit.Test;
import toolKit.FileReaderDataset;
import toolKit.Row;

public class KNearestNeighboursTest {
    private final static FileReaderDataset TRAINING_FILE_READER = new FileReaderDataset("dataset1.csv"); //for training file reading
    private final static FileReaderDataset TESTING_FILE_READER = new FileReaderDataset("dataset2.csv"); //for testing file reading
    private final static Row[] TRAINING_DATA_SETS = TRAINING_FILE_READER.getData(); //training data
    private final static Row[] TESTING_DATA_SETS = TESTING_FILE_READER.getData(); //test data

    @Test
    public void getDistanceToAllDataPoints() {
        NearestNeighbour nearestNeighbour = new NearestNeighbour("Nearest Neigbour", TRAINING_DATA_SETS, TESTING_DATA_SETS);

        nearestNeighbour.run();
    }
}
