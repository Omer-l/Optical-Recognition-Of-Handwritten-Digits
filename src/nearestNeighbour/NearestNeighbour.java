package nearestNeighbour;

import toolKit.AdelsonVelskiiLandisTree;
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
        int classification = -1;
        AdelsonVelskiiLandisTree avlTree = buildAVLTree(newRow);
        getKNearestNeighbours(avlTree);

        return classification;
    }

    /**
     * Builds an AVL Tree for getting the nearest neighbours.
     * The tree will be sorted based on distance from the datapoint/row.
     * @return  the AVL tree containing all the training rows from test row
     */
    private AdelsonVelskiiLandisTree buildAVLTree(Row testRow) {
        AdelsonVelskiiLandisTree avlTree = new AdelsonVelskiiLandisTree();
        Row[] trainingRows = getTrainingRows();

        for (Row row : trainingRows) {
            row.setDistance(testRow.getEuclideanDistanceTo(row));
            avlTree.insert(row);
        }

        return avlTree;
    }

    /**
     * After getting a sorted array of distances to all data points, gets the first 'K'
     * neighbouring data points and counts their occurrences.
     *
     * @param avlTree is a balanced tree built to search
     * @return an array of integers, where each index represents the occurrence of a classification for a neighbour
     * (i.e, if the classification is close to where fives' are usually classified, index 4 would ideally have
     * the highest count.)
     */
    private int[] getKNearestNeighbours(AdelsonVelskiiLandisTree avlTree) {
        int[] neighbourClassificationCounter = new int[numberOfPossibleClassifications];

        Row[] neighbours = avlTree.getNearestNeighbours(this.K);

        return neighbourClassificationCounter;
    }
}
