package supportVectorMachine;

import toolKit.HandwrittenDigitClassifierAlgorithm;
import toolKit.Row;
import toolKit.MatrixUtilities;

import java.util.Arrays;

public class SupportVectorMachines extends HandwrittenDigitClassifierAlgorithm {
    private static final int NUMBER_OF_LINES_ATTEMPTS_PER_CLASSIFICATION = 1;
    private final int numberOfPossibleClassifications; //digits vary from 0 to 9.
    private double[] classifications;
    private double[][] X;
    private final int numberOfPerceptrons = 11;

    public SupportVectorMachines(String algorithmName, Row[] trainingRows, Row[] testRows, int numberOfPossibleClassifications) {
        super(algorithmName, trainingRows, testRows);
        this.numberOfPossibleClassifications = numberOfPossibleClassifications;
        initialiseClassificationsAndX(trainingRows);
    }

    /**
     * Augments the vectors, the inputs, the x and y by making x0 = 1
     *
     * @param x the vector to augment
     * @return an augmented vector, for which x0 = 1
     */
    private static double[] augmentX(double[] x) {
        int numberOfFeatures = x.length;
        double[] augmentedX = new double[numberOfFeatures + 1];
        int x0 = 1;
        augmentedX[0] = x0; //x0 = 1
        System.arraycopy(x, 0, augmentedX, 1, numberOfFeatures);
        return augmentedX;
    }

    //initialises the inputs and the classifications
    private void initialiseClassificationsAndX(Row[] trainingRows) {
        int numberOfInputs = trainingRows.length;
        this.X = new double[numberOfInputs][];
        this.classifications = new double[numberOfInputs];
        int rowIndex = 0;
        for (Row trainingRow : trainingRows) {
            double[] x = trainingRow.getInputs();
            double[] augmentedx = augmentX(x);
            int classification = trainingRow.getClassification();
            this.X[rowIndex] = augmentedx;
            this.classifications[rowIndex] = classification;
            rowIndex++;
        }
    }

    //Creates a binary classification for given desired classifications
    private Perceptron[] getClassification(double[] desiredClassification) {

        //Transform the NUMBER_OF_CLASSIFICATIONS classifications into NUMBER_OF_CLASSIFICATIONS binary class problems
        double[] binaryClassifications = MatrixUtilities.getBinaryClassifications(classifications, desiredClassification);
        Perceptron[] p = new Perceptron[NUMBER_OF_LINES_ATTEMPTS_PER_CLASSIFICATION];
        for (int i = 0; i < NUMBER_OF_LINES_ATTEMPTS_PER_CLASSIFICATION; i++) {
            Perceptron perceptron1 = new Perceptron(X, binaryClassifications);
            perceptron1.perceptronLearningAlgorithm();
            p[i] = perceptron1;
        }

        return p;
    }

    //Creates a tree of classifications for creating perceptrons
    private double[][] initialiseAllDesiredClassifications() {
        double[][] allDesiredClassifications = new double[numberOfPerceptrons][NUMBER_OF_LINES_ATTEMPTS_PER_CLASSIFICATION];
        //Branches from Level 0
        allDesiredClassifications[0] = new double[]{1, 2, 4, 5, 7}; //L
//        allDesiredClassifications[1] = new double[]{0, 3, 5, 6, 8, 9}; //R
        //Branches from Level 1
        allDesiredClassifications[1] = new double[]{1, 7}; //LL
//        allDesiredClassifications[3] = new double[]{2, 4, 5, 7};//LR
        allDesiredClassifications[2] = new double[]{5, 6};//RL
//        allDesiredClassifications[5] = new double[]{0, 3, 8, 9};//RR
        //Branches from Level 2
        allDesiredClassifications[3] = new double[]{1};//LLL
//        allDesiredClassifications[7] = new double[]{7};//LLR
        allDesiredClassifications[4] = new double[]{7, 4};//LRL
//        allDesiredClassifications[9] = new double[]{2, 5};//LRR
        allDesiredClassifications[5] = new double[]{5};//RLL
//        allDesiredClassifications[11] = new double[]{6};//RLR
        allDesiredClassifications[6] = new double[]{3, 8};//RRL
//        allDesiredClassifications[13] = new double[]{0, 9};//RRR
        //Branches from Level 3
        allDesiredClassifications[7] = new double[]{7};//LRLL
//        allDesiredClassifications[15] = new double[]{4};//LRLR
        allDesiredClassifications[8] = new double[]{2};//LRRL
//        allDesiredClassifications[17] = new double[]{5};//LRRR
        allDesiredClassifications[9] = new double[]{3};//RRLL
//        allDesiredClassifications[19] = new double[]{8};//RRLR
        allDesiredClassifications[10] = new double[]{0};//RRRL
//        allDesiredClassifications[21] = new double[]{9};//RRRR

        return allDesiredClassifications;
    }

    //Creates 10 perceptrons, 1 for each classification
    private Perceptron[][] initialisePerceptrons() {
        Perceptron[][] perceptrons = new Perceptron[numberOfPossibleClassifications][NUMBER_OF_LINES_ATTEMPTS_PER_CLASSIFICATION];

        for (int desiredClassification = 0; desiredClassification < numberOfPossibleClassifications; desiredClassification++) {
            double[] classifications = {desiredClassification};
            perceptrons[desiredClassification] = getClassification(classifications); //find perceptron for desired classification
        }
        return perceptrons;
    }

    //Creates 10 perceptrons, 1 for each classification
    private Perceptron[][] myTestInitialisePerceptrons() {
        double[][] allDesiredClassifications = initialiseAllDesiredClassifications();
        Perceptron[][] testPerceptrons = new Perceptron[numberOfPerceptrons][NUMBER_OF_LINES_ATTEMPTS_PER_CLASSIFICATION];

        for (int allDesiredClassificationsIndex = 0; allDesiredClassificationsIndex < allDesiredClassifications.length; allDesiredClassificationsIndex++) {
            double[] desiredClassifications = allDesiredClassifications[allDesiredClassificationsIndex];
            testPerceptrons[allDesiredClassificationsIndex] = getClassification(desiredClassifications);
        }
        return testPerceptrons;
    }

    /**
     * The f. To determine how good the line of best fit of the classified data points is.
     *
     * @param gradient        AKA the weights, the 'm'
     * @param augmentedVector The augmented X, including x0 = 1, Note, this will not be necessary for this calculation
     * @param classification  the y, the output, the classification
     * @return The functional margin points, the higher, the better.
     */
    public static double getGeometricMargin(double[] gradient, double[] augmentedVector, double classification) {
        double b = gradient[0];
        double dotProductOfWeightsAndVector = MatrixUtilities.getDotProductExcludeX0W0(augmentedVector, gradient);
        return (classification / MatrixUtilities.getMagnitudeOrUnitNormalVector(gradient)) * (dotProductOfWeightsAndVector + b);
    }

    //Get weights of all given perceptrons into a 2D array
    private static double[][] getWeightsOfPerceptrons(Perceptron[] perceptrons) {
        double[][] weightsOfAllPerceptrons = new double[NUMBER_OF_LINES_ATTEMPTS_PER_CLASSIFICATION][];
        for (int perceptronIndex = 0; perceptronIndex < perceptrons.length; perceptronIndex++) {
            double[] weightOfPerceptron = perceptrons[perceptronIndex].getWeights();
            weightsOfAllPerceptrons[perceptronIndex] = weightOfPerceptron;
        }
        return weightsOfAllPerceptrons;
    }

    //Maximises the margin and returns the perceptron with the best margin
    private static int maximiseMargin(Perceptron[] perceptrons) {
        double maximumOfTheMinimumGeometricMargin = Double.MIN_VALUE;
        int bestHyperplaneIndex = 0;
        int numberOfLines = perceptrons.length;

        for (int perceptronIndex = 0; perceptronIndex < numberOfLines; perceptronIndex++) {
            double[][] weightsOfAllPerceptrons = getWeightsOfPerceptrons(perceptrons);
            double[][] vectorsOfAllPerceptrons = perceptrons[perceptronIndex].getX();
            double[] classificationsOfPerceptrons = perceptrons[perceptronIndex].getY();
            int numberOfVectors = vectorsOfAllPerceptrons.length;

            double[] weightsOfLine = weightsOfAllPerceptrons[perceptronIndex];
            double minimumGeometricMarginClass1 = Double.MAX_VALUE;
            double minimumGeometricMarginClassMinus1 = Double.MAX_VALUE;
            //Get geometric margin from points to line, choose minimum margin
            for (int vectorIndex = 0; vectorIndex < numberOfVectors; vectorIndex++) {
                double[] currentVector = vectorsOfAllPerceptrons[vectorIndex];
                double classification = classificationsOfPerceptrons[vectorIndex];
                double geometricMargin = getGeometricMargin(weightsOfLine, currentVector, classification);

                if (classification == 1 && geometricMargin < minimumGeometricMarginClass1)
                    minimumGeometricMarginClass1 = geometricMargin;
                else if (classification == -1 && geometricMargin < minimumGeometricMarginClassMinus1)
                    minimumGeometricMarginClassMinus1 = geometricMargin;
            }
            //The shorter margin is chosen
            double minimumOfTheTwoClassMargins = minimumGeometricMarginClass1 > minimumGeometricMarginClassMinus1 ? minimumGeometricMarginClassMinus1 : minimumGeometricMarginClass1;

            if (minimumOfTheTwoClassMargins > maximumOfTheMinimumGeometricMargin) {
                maximumOfTheMinimumGeometricMargin = minimumOfTheTwoClassMargins;
                bestHyperplaneIndex = perceptronIndex;
            }
        }
        return bestHyperplaneIndex;
    }


    /**
     * Extracts the weights from each perceptron
     *
     * @param perceptrons
     * @return
     */
    private double[][] getWeightsOfEachPerceptron(Perceptron[][] perceptrons) {
        double[][] weightsOfEachPerceptron = new double[perceptrons.length][];
        for (int perceptronIndex = 0; perceptronIndex < perceptrons.length; perceptronIndex++) {
            Perceptron[] perceptronAttempts = perceptrons[perceptronIndex];
            int bestPerceptronIndex = maximiseMargin(perceptronAttempts); //for now just first perceptron, TODO GET BEST PERCEPTRON IN ARRAY
            Perceptron bestPerceptron = perceptronAttempts[bestPerceptronIndex];
            weightsOfEachPerceptron[perceptronIndex] = bestPerceptron.getWeights();
        }

        return weightsOfEachPerceptron;
    }

    //Calculates the classification using the SVM
    private int getActualClassification(Perceptron[][] perceptrons, double[] augX) {
        int actualClassification = 0; //temporarily 0
        //count up the votes
        for (int perceptronIndex = 0; perceptronIndex < perceptrons.length; perceptronIndex++) {
            double[] weights = perceptrons[perceptronIndex][0].getWeights(); //for now attempt 1 line. so index 0
            int prediction = MatrixUtilities.getHypothesis(augX, weights); //if -1, then not that category, if 1, then it is possibly that digit

            if (prediction == 1)
                actualClassification = perceptronIndex;
        }

        return actualClassification;
    }

    //Runs the algorithm
    @Override
    public void run() {
        //initialise each perceptron
        Perceptron[][] perceptrons = initialisePerceptrons();
        double[][] weightsOfEachPerceptron = getWeightsOfEachPerceptron(perceptrons);
        Row[] testRows = getTestRows();
        for (int testRowIndex = 0; testRowIndex < getTestRows().length; testRowIndex++) {
            Row testRow = testRows[testRowIndex];
            double[] rowInputs = testRow.getInputs();
            double[] augX = augmentX(rowInputs);
            int expectedClassification = (int) testRow.getClassification();
            int actualClassification = getActualClassification(perceptrons, augX);

            if (expectedClassification == actualClassification)
                correctClassificationCounter++;
            else
                incorrectClassificationCounter++;
        }
    }

    public void myTestRun() {
        //initialise each perceptron
        Perceptron[][] perceptrons = myTestInitialisePerceptrons();
        double[][] weightsOfEachPerceptron = getWeightsOfEachPerceptron(perceptrons);
        Row[] testRows = getTestRows();
        //Branches from Level 0
        Perceptron L = perceptrons[0][0];
//        Perceptron R = perceptrons[1][0];
        //Branches from Level 1
        Perceptron LL = perceptrons[1][0];
//        Perceptron LR = perceptrons[3][0];
        Perceptron RL = perceptrons[2][0];
//        Perceptron RR = perceptrons[5][0];
        //Branches from Level 2
        Perceptron LLL = perceptrons[3][0];
//        Perceptron LLR = perceptrons[7] [0];
        Perceptron LRL = perceptrons[4][0];
//        Perceptron LRR = perceptrons[9] [0];
        Perceptron RLL = perceptrons[5][0];
//        Perceptron RLR = perceptrons[11][0];
        Perceptron RRL = perceptrons[6][0];
//        Perceptron RRR = perceptrons[13][0];
        //Branches from Level 3
        Perceptron LRLL = perceptrons[7][0];
//        Perceptron LRLR = perceptrons[15][0];
        Perceptron LRRL = perceptrons[8][0];
//        Perceptron LRRR = perceptrons[17][0];
        Perceptron RRLL = perceptrons[9][0];
//        Perceptron RRLR = perceptrons[19][0];
        Perceptron RRRL = perceptrons[10][0];
//        Perceptron RRRR = perceptrons[21][0];
        for (int testRowIndex = 0; testRowIndex < getTestRows().length; testRowIndex++) {
            Row testRow = testRows[testRowIndex];
            double[] rowInputs = testRow.getInputs();
            double[] augX = augmentX(rowInputs);
            int expectedClassification = (int) testRow.getClassification();
            int actualClassification = -1;

            //TRAVERSE TREE
            //From Level 0

            double[] weightsL = L.getWeights(); //for now attempt 1 line. so index 0
            int predictionL = MatrixUtilities.getHypothesis(augX, weightsL); //if -1, then not that category, if 1, then it is possibly that digit
            if (predictionL == 1) {//L
                double[] weightsLL = LL.getWeights(); //for now attempt 1 line. so index 0
                int predictionLL = MatrixUtilities.getHypothesis(augX, weightsLL); //if -1, then not that category, if 1, then it is possibly that digit
                if (predictionLL == 1) { // LL
                    double[] weightsLLL = LLL.getWeights(); //for now attempt 1 line. so index 0
                    int predictionLLL = MatrixUtilities.getHypothesis(augX, weightsLLL); //if -1, then not that category, if 1, then it is possibly that digit
                    if (predictionLLL == 1) //LLL
                        actualClassification = 1;
                    else  //LLR
                        actualClassification = 7;
                } else { //LR
                    double[] weightsLRL = LRL.getWeights(); //for now attempt 1 line. so index 0
                    int predictionLRL = MatrixUtilities.getHypothesis(augX, weightsLRL); //if -1, then not that category, if 1, then it is possibly that digit
                    if (predictionLRL == 1) { //LRL
                        double[] weightsLRLL = LRLL.getWeights(); //for now attempt 1 line. so index 0
                        int predictionLRLL = MatrixUtilities.getHypothesis(augX, weightsLRLL); //if -1, then not that category, if 1, then it is possibly that digit
                        if (predictionLRLL == 1) //LRLL
                            actualClassification = 7;
                        else //LRLR
                            actualClassification = 4;
                    } else { //LRR
                        double[] weightsLRRL = LRRL.getWeights(); //for now attempt 1 line. so index 0
                        int predictionLRRL = MatrixUtilities.getHypothesis(augX, weightsLRRL); //if -1, then not that category, if 1, then it is possibly that digit
                        if (predictionLRRL == 1) //LRRL
                            actualClassification = 2;
                        else //LRRR
                            actualClassification = 5;
                    }
                }
            } else { //R
                double[] weightsRL = RL.getWeights(); //for now attempt 1 line. so index 0
                int predictionRL = MatrixUtilities.getHypothesis(augX, weightsRL); //if -1, then not that category, if 1, then it is possibly that digit
                if (predictionRL == 1) { //RL
                    double[] weightsRLL = RLL.getWeights(); //for now attempt 1 line. so index 0
                    int predictionRLL = MatrixUtilities.getHypothesis(augX, weightsRLL); //if -1, then not that category, if 1, then it is possibly that digit
                    if (predictionRLL == 1)  //RLL
                        actualClassification = 5;
                    else //RLR
                        actualClassification = 6;
                } else { //RR
                    double[] weightsRRL = RRL.getWeights(); //for now attempt 1 line. so index 0
                    int predictionRRL = MatrixUtilities.getHypothesis(augX, weightsRRL); //if -1, then not that category, if 1, then it is possibly that digit
                    if (predictionRRL == 1) { //RRL
                        double[] weightsRRLL = RRLL.getWeights(); //for now attempt 1 line. so index 0
                        int predictionRRLL = MatrixUtilities.getHypothesis(augX, weightsRRLL); //if -1, then not that category, if 1, then it is possibly that digit
                        if (predictionRRLL == 1) //RRLL
                            actualClassification = 3;
                        else //RRLR
                            actualClassification = 8;
                    } else { //RRR
                        double[] weightsRRRL = RRRL.getWeights(); //for now attempt 1 line. so index 0
                        int predictionRRRL = MatrixUtilities.getHypothesis(augX, weightsRRRL); //if -1, then not that category, if 1, then it is possibly that digit
                        if (predictionRRRL == 1) //RRRL
                            actualClassification = 0;
                        else //RRRR
                            actualClassification = 9;
                    }
                }
            }
            if (expectedClassification == actualClassification)
                correctClassificationCounter++;
            else
                incorrectClassificationCounter++;
        }
    }

}