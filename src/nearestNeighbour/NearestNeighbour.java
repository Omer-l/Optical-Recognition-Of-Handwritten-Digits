package nearestNeighbour;

import toolKit.EuclideanDistanceMergeSorter;
import toolKit.HandwrittenDigitClassifierAlgorithm;
import toolKit.Row;

public class NearestNeighbour extends HandwrittenDigitClassifierAlgorithm {
    private final int numberOfPossibleClassifications; //digits vary from 0 to 9.
    public NearestNeighbour(String algorithmName, Row[] trainingRows, Row[] testRows, int numberOfPossibleClassifications) {
        super(algorithmName, trainingRows, testRows);
        this.numberOfPossibleClassifications = numberOfPossibleClassifications;
    }

    //runs the algorithm
    @Override
    public void run() {

        for(Row testRow : getTestRows()) {
            //gets classification based on euclidean distance
            int actualClassificationOfRow = classify(testRow);
            int expectedClassificationOfRow = testRow.getClassification();

            //tests whether learning approach gave the right classification.
            if(actualClassificationOfRow == expectedClassificationOfRow)
                add1Correct();
            else
                add1Incorrect();
        }
    }

    /** classifies new handwritten data by finding closest euclidean distance of the trained data, and then returns the index of that row.
     *
     * @param newRow    the row to classify
     * @return          the closest neighbour
     */
    private int classify(Row newRow) {
        int closestNeighbourIndex = -1;
        double closestNeighbourDistance = Double.MAX_VALUE;
        Row[] trainingRows = getTrainingRows();

        //gets closest euclidean distance
        for(int rowIndex = 0; rowIndex < trainingRows.length; rowIndex++) {
            Row neighbour = trainingRows[rowIndex];

            double distanceToNeighbour = newRow.getEuclideanDistanceTo(neighbour);

            //closer than current closest neighbour?
            if(distanceToNeighbour < closestNeighbourDistance) {
                closestNeighbourIndex = rowIndex;
                closestNeighbourDistance = distanceToNeighbour;
            }
        }

        return trainingRows[closestNeighbourIndex].getClassification();
    }

    /**
     * After getting a sorted array of distances to all data points, gets the first 'k'
     * neighbouring data points and counts their occurrences.
     * @param k     is the number of desired neighbouring occurences
     * @return      an array of integers, where each index represents the occurrence of a classification for a neighbour
     *              (i.e, if the classification is close to where fives' are usually classified, index 4 would ideally have
     *              the highest count.)
     */
    public int[] getKNearestNeighbours(int k) {
        int[] neighbourCounter = new int[numberOfPossibleClassifications];
        //sort all training data based on euclidean distance from point.

        return neighbourCounter;
    }

    /**
     * Calculates distance to all data points (Rows) in the dataset
     * @param fromDataPoint     is the row to calculate distance to all other datapoints from
     * @return                  a sorted array of distances to all data points.
     */
    public double[] getDistanceToAllDataPoints(Row fromDataPoint) {
        Row[] trainingRows = getTrainingRows();
        double[] distanceToAllDataPoints = new double[trainingRows.length];//distance to all points

        for(int dataPointIndex = 0; dataPointIndex < trainingRows.length; dataPointIndex++) {
            Row toDataPoint = trainingRows[dataPointIndex];
            distanceToAllDataPoints[dataPointIndex] = fromDataPoint.getEuclideanDistanceTo(toDataPoint);
        }

        return distanceToAllDataPoints;
    }

    @Override
    public String toString() {
        //output results
        double accuracy = getAccuracy();
        double errorRate = MAX_PERCENTAGE - accuracy;

        return "ALGORITHM NAME: \t\t\t" + algorithmName + "\nCORRECT CLASSIFICATIONS: \t" + correctClassificationCounter + "\nINCORRECT CLASSIFICATIONS: \t" + incorrectClassificationCounter + "\nACCURACY: \t\t\t\t\t" + accuracy + "%\nERROR RATE: \t\t\t\t" + accuracy;
    }
}
