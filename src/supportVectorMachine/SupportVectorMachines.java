package supportVectorMachine;

import toolKit.HandwrittenDigitClassifierAlgorithm;
import toolKit.Row;
import toolKit.MatrixUtilities;

public class SupportVectorMachines extends HandwrittenDigitClassifierAlgorithm {
    private static final int NUMBER_OF_LINES_ATTEMPTS_PER_CLASSIFICATION = 1;
    private final int numberOfPossibleClassifications; //digits vary from 0 to 9.
    private double[] classifications;
    private double[][] X;

    public SupportVectorMachines(String algorithmName, Row[] trainingRows, Row[] testRows, int numberOfPossibleClassifications) {
        super(algorithmName, trainingRows, testRows);
        this.numberOfPossibleClassifications = numberOfPossibleClassifications;
        initialiseClassificationsAndX(trainingRows);
    }



    /**
     * Augments the vectors, the inputs, the x and y by making x0 = 1
     * @param x     the vector to augment
     * @return      an augmented vector, for which x0 = 1
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
        for(Row trainingRow : trainingRows) {
            double[] x = trainingRow.getInputs();
            double[] augmentedx = augmentX(x);
            int classification = trainingRow.getClassification();
            this.X[rowIndex] = augmentedx;
            this.classifications[rowIndex] = classification;
            rowIndex++;
        }
    }

    //Creates a binary classification for given desired classifications
    private Perceptron[] getClassification(double desiredClassification) {

        //Transform the NUMBER_OF_CLASSIFICATIONS classifications into NUMBER_OF_CLASSIFICATIONS binary class problems
        double[] binaryClassifications = MatrixUtilities.getBinaryClassifications(classifications, desiredClassification);
        Perceptron[] p = new Perceptron[NUMBER_OF_LINES_ATTEMPTS_PER_CLASSIFICATION];
        for(int i = 0; i < NUMBER_OF_LINES_ATTEMPTS_PER_CLASSIFICATION; i++) {
            Perceptron perceptron1 = new Perceptron(X, binaryClassifications);
            perceptron1.perceptronLearningAlgorithm();
            p[i] = perceptron1;
        }

        return p;
    }

    //Creates 10 perceptrons, 1 for each classification
    private Perceptron[][] initialisePerceptrons() {
        Perceptron[][] perceptrons = new Perceptron[numberOfPossibleClassifications][NUMBER_OF_LINES_ATTEMPTS_PER_CLASSIFICATION];

        for(int desiredClassification = 0; desiredClassification < numberOfPossibleClassifications; desiredClassification++) {
            perceptrons[desiredClassification] = getClassification(desiredClassification); //find perceptron for desired classification
            System.out.println("NEXT");
        }
        return perceptrons;
    }

    @Override
    public void run() {
        //initialise each perceptron
        Perceptron[][] perceptrons = initialisePerceptrons();

    }
}
