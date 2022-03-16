package kNearestNeighbours;

import toolKit.EuclideanDistanceMergeSorter; //for sorting the data points
import toolKit.HandwrittenDigitClassifierAlgorithm; //To ensure this class contains necessary functions and variables to be a machine learning algorithm
import toolKit.Row; //For assigning each row from the dataset

/**
 * This class can calculate how close training data is to given test data. Then it will vote on a set number
 * of neighbours to classify the test data.
 */
public class KNearestNeighbour extends HandwrittenDigitClassifierAlgorithm {
    private final int numberOfPossibleClassifications; //digits vary from 0 to 9.
    private final int K; //number of nearest neighbours

    public KNearestNeighbour(String algorithmName, Row[] trainingRows, Row[] testRows, int numberOfPossibleClassifications, int K) {
        super(algorithmName, trainingRows, testRows);
        this.numberOfPossibleClassifications = numberOfPossibleClassifications;
        this.K = K;
    }

    //runs the algorithm
    @Override
    public void run() {
        Row[] allTrainingRows = getTrainingRows();
        for (Row testRow : getTestRows()) {
            //gets classification based on euclidean distance
            int actualClassificationOfRow = classify(testRow, allTrainingRows);
            int expectedClassificationOfRow = testRow.getClassification();

            //tests whether learning approach gave the right classification.
            if (actualClassificationOfRow == expectedClassificationOfRow)
                add1Correct();
            else
                add1Incorrect();
        }
}

    /**
     * classifies new handwritten data by finding closest euclidean distance of the trained data, and then returns the index of that row.
     *
     * @param newRow       the row to classify
     * @param trainingRows are the training rows
     * @return the closest neighbour
     */
    private int classify(Row newRow, Row[] trainingRows) {
        int classification = 0;
        int highestClassificationCount = 0;
        EuclideanDistanceMergeSorter.sort(newRow, trainingRows);
        int[] nearestNeigboursClassifications = getKNearestNeighboursClassifications(trainingRows);

        for (int neighbourIndex = 0; neighbourIndex < numberOfPossibleClassifications; neighbourIndex++) {
            int classificationCount = nearestNeigboursClassifications[neighbourIndex];

            if (classificationCount > highestClassificationCount) {
                highestClassificationCount = classificationCount;
                classification = neighbourIndex;
            }
        }

        return classification;
    }

    /**
     * After getting a sorted array of distances to all data points, gets the first 'K'
     * neighbouring data points and counts their occurrences.
     *
     * @param neighbours the training rows in sorted order
     * @return an array of integers, where each index represents the occurrence of a classification for a neighbour
     * (i.e, if the classification is close to where fives' are usually classified, index 4 would ideally have
     * the highest count.)
     */
    private int[] getKNearestNeighboursClassifications(Row[] neighbours) {
        int[] neighbourClassificationCounter = new int[numberOfPossibleClassifications];

        for (int neighbourIndex = 0; neighbourIndex < K; neighbourIndex++) {
            Row neighbour = neighbours[neighbourIndex];
            int classificationOfNeighbour = neighbour.getClassification();
            neighbourClassificationCounter[classificationOfNeighbour]++;
        }

        return neighbourClassificationCounter;
    }

    @Override
    public String toString() {
        //output results
        double accuracy = getAccuracy();
        double errorRate = MAX_PERCENTAGE - accuracy;

        return "ALGORITHM NAME: \t\t\t" + algorithmName + "\nNUMBER OF NEIGHBOURS: \t\t" + K + "\nCORRECT CLASSIFICATIONS: \t" + correctClassificationCounter + "\nINCORRECT CLASSIFICATIONS: \t" + incorrectClassificationCounter + "\nACCURACY: \t\t\t\t\t" + accuracy + "%\nERROR RATE: \t\t\t\t" + accuracy;
    }
}
