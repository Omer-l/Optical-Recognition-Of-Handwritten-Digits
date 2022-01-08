package nearestNeighbour;

import toolKit.AdelsonVelskiiLandisTree;
import toolKit.EuclideanDistanceMergeSorter;
import toolKit.HandwrittenDigitClassifierAlgorithm; //To ensure this class contains necessary functions and variables to be a machine learning algorithm
import toolKit.Row; //For assigning each row from the dataset

/**
 * This class has the functions to get the training row (nearest neighbour) for each test row.
 * Uses Euclidean distance to achieve this.
 * OUTPUT: correct: 2755... incorrect: 55... = 98.04% accuracy
 */
public class NearestNeighbour extends HandwrittenDigitClassifierAlgorithm {
    private final int numberOfPossibleClassifications; //digits vary from 0 to 9.
    private final int K; //number of nearest neighbours

    public NearestNeighbour(String algorithmName, Row[] trainingRows, Row[] testRows, int numberOfPossibleClassifications, int K) {
        super(algorithmName, trainingRows, testRows);
        this.numberOfPossibleClassifications = numberOfPossibleClassifications;
        this.K = K;
    }

    //runs the algorithm
    @Override
    public void run() {

        for (Row testRow : getTestRows()) {
            //gets classification based on euclidean distance
            int actualClassificationOfRow = classify(testRow);
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
     * @param newRow the row to classify
     * @return the closest neighbour
     */
    private int classify(Row newRow) {
        int classification = 0;
        int highestClassificationCount = 0;
        EuclideanDistanceMergeSorter.sort(newRow, getTrainingRows());
        int[] nearestNeigboursClassifications = getKNearestNeighboursClassifications();

        for(int neighbourIndex = 0; neighbourIndex < numberOfPossibleClassifications; neighbourIndex++) {
            int classificationCount = nearestNeigboursClassifications[neighbourIndex];

            if(classificationCount > highestClassificationCount) {
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
     * @return                      an array of integers, where each index represents the occurrence of a classification for a neighbour
     * (i.e, if the classification is close to where fives' are usually classified, index 4 would ideally have
     * the highest count.)
     */
    private int[] getKNearestNeighboursClassifications() {
        int[] neighbourClassificationCounter = new int[numberOfPossibleClassifications];
        Row[] neighbours = getTrainingRows();

        for(int neighbourIndex = 0; neighbourIndex < K; neighbourIndex++) {
            Row neighbour = neighbours[neighbourIndex];
            int classificationOfNeighbour = neighbour.getClassification();
            neighbourClassificationCounter[classificationOfNeighbour]++;
        }

        return neighbourClassificationCounter;
    }
}
