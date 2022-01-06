package perceptron;

import toolKit.HandwrittenDigitClassifierAlgorithm; //To ensure this class contains necessary functions and variables to be a machine learning algorithm
import toolKit.Row; //For assigning each row from the dataset

import java.util.Random; //For seed to ensure same accuracy.

public class Perceptron extends HandwrittenDigitClassifierAlgorithm {
    private double learningRate = 1;
    private double[] weights;
    private final Random seedForRandomisingWeights = new Random();
    private final long seedUsed; //ensures same algorithm inputs are made (if specified)
    private double errorRate = 1; //error is at the highest it can be initially.

    //Constructor for a random seed
    public Perceptron(String algorithmName, Row[] trainingRows, Row[] testRows) {
        super(algorithmName, trainingRows, testRows);
        seedUsed = seedForRandomisingWeights.nextLong(); //assign a random seed
        seedForRandomisingWeights.setSeed(seedUsed);
        assignWeights();
    }

    //Constructor for a given seed
    public Perceptron(String algorithmName, Row[] trainingRows, Row[] testRows, long seedUsed) {
        super(algorithmName, trainingRows, testRows);
        this.seedUsed = seedUsed; //assign the given seed
        seedForRandomisingWeights.setSeed(seedUsed);
        assignWeights();
    }

    //assigns a random double value for each index in te weights array
    private void assignWeights() {
        weights = new double[getTrainingRows().length + 1]; //as many weights as there are inputs + 1 for x0 w0.
        for(int weightNumber = 0; weightNumber < weights.length; weightNumber++)
            weights[weightNumber] = seedForRandomisingWeights.nextDouble();
    }

    /**
     * Runs the Perceptron algorithm
     */
    @Override
    public void run() {

    }

    /**
     * sums the dot product of the weights and the sumOfInputs of each row
     * @return  a 1 if sum of dot product is more than or equal to 0, otherwise returns a 0.
     */
    public int getOutput() {
        Row[] trainingRows = getTrainingRows();
        double sum = weights[0]; //dot product of weight0 is with x0 (which is 1).

        for(int dotProductIndex = 0; dotProductIndex < trainingRows.length; dotProductIndex++) {
            Row trainingRow = trainingRows[dotProductIndex];

            double sumOfInputs = trainingRow.getSumOfInputs(); // the 'x'
            double weight = weights[dotProductIndex+1]; // the 'w'
            sum += (weight * sumOfInputs);
        }

        return sum >= 0 ? 1 : 0;
    }

    public double[] getWeights() {
        return weights;
    }

    @Override
    public String toString() {
        String algorithmName = getAlgorithmName();
        int correctClassificationCounter = getCorrectClassificationCounter();
        int incorrectClassificationCounter = getIncorrectClassificationCounter();
        //output results
        double accuracy = ((double)correctClassificationCounter / getTestRows().length) * 100.00;
        double errorRate = 100.0 - accuracy;

        return "ALGORITHM NAME: \t\t\t" + algorithmName + "\nCORRECT CLASSIFICATIONS: \t" + correctClassificationCounter + "\nINCORRECT CLASSIFICATIONS: \t" + incorrectClassificationCounter + "\nACCURACY: \t\t\t\t\t" + accuracy + "%\nERROR RATE: \t\t\t\t" + errorRate + accuracy + "%\nSEED USED: \t\t\t\t\t" + seedUsed;
    }
}
