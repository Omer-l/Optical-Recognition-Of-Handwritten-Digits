package main;

import nearestNeighbour.NearestNeighbour; //Nearest Neighbour algorithm
import toolKit.*; //For reading in the data and creating an array of Rows.

/**
 * Runs all algorithms attempted for this assignment.
 * Algorithms:
 * Nearest Neighbour
 */
public class ApplicationAllAlgorithms {
    private final static FileReaderDataset TRAINING_FILE_READER = new FileReaderDataset("dataset1.csv"); //for training file reading
    private final static FileReaderDataset TESTING_FILE_READER = new FileReaderDataset("dataset2.csv"); //for testing file reading
    private final static Row[] TRAINING_DATA_SETS = TRAINING_FILE_READER.getData(); //training data
    private final static Row[] TESTING_DATA_SETS = TESTING_FILE_READER.getData(); //test data

    public static void main(String[] args) {
        runNearestNeighbourAlgorithm(); //Nearest neighbour algorithm run and output results
    }

    //Runs nearest neighbour (closest euclidean distance).
    public static void runNearestNeighbourAlgorithm() {
        String algorithmName = "Nearest Neighbour";
        NearestNeighbour nearestNeighbour = new NearestNeighbour(algorithmName, TRAINING_DATA_SETS, TESTING_DATA_SETS);
        nearestNeighbour.run();
        System.out.println(nearestNeighbour);
    }

}
