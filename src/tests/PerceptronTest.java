package tests;

import org.junit.Test;
import perceptron.Perceptron;
import toolKit.FileReaderDataset;
import toolKit.Row;

import java.util.Arrays;

public class PerceptronTest {
    private final static FileReaderDataset TRAINING_FILE_READER = new FileReaderDataset("dataset1.csv"); //for training file reading
    private final static FileReaderDataset TESTING_FILE_READER = new FileReaderDataset("dataset2.csv"); //for testing file reading
    private final static Row[] TRAINING_DATA_SETS = TRAINING_FILE_READER.getData(); //training data
    private final static Row[] TESTING_DATA_SETS = TESTING_FILE_READER.getData(); //test data

    private final long seed = 8721966996867022518L;
    private Perceptron perceptron = new Perceptron("Perceptron", TRAINING_DATA_SETS, TESTING_DATA_SETS, seed);

    @Test
    public void assignWeights() {
        System.out.println("WEIGHTS: " + Arrays.toString(perceptron.getWeights()));
    }

    @Test
    public void getOutput() {
        int output = perceptron.getOutput();
        System.out.println(output);
    }
}
