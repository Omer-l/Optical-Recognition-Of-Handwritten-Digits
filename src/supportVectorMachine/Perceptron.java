package supportVectorMachine;

import toolKit.MatrixUtilities;
import java.util.Random;

public class Perceptron {
    private double[][] X;
    private double[] y;
    private Random random = new Random(); //for testing purposes
    private int numberOfFeatures;
    private double[] weights;
    private double[] zetas; //for soft margin, there is a zeta for each point

    public Perceptron(double[][] X, double[] y, long seed) {
        int numberOfFeatures = X[0].length;
        this.numberOfFeatures = numberOfFeatures;
        this.X = X;
        this.zetas = new double[this.numberOfFeatures];
        this.y = y;
        this.random.setSeed(seed);
        this.weights = randomiseWeights();
    }

    //no seed
    public Perceptron(double[][] X, double[] y) {
        int numberOfFeatures = X[0].length;
        this.numberOfFeatures = numberOfFeatures;
        this.X = X;
//        this.zetas = new double[this.numberOfFeatures];
        this.y = y;
        this.weights = randomiseWeights();
    }

    //Runs the algorithm
    public void perceptronLearningAlgorithm() {
        weights = randomiseWeights();
        int[] misclassifiedExamples = predict(X, y, weights); //indexes of misclassified
//        System.out.println(Arrays.toString(weights));
        while (misclassifiedExamples.length != 0) {
//            System.out.println(Arrays.toString(misclassifiedExamples));
//            System.out.println("NO. OF MIS: " + misclassifiedExamples.length);
            int misclassifiedIndex = pickOneFrom(misclassifiedExamples); //chooses a random example.
            double[] x = X[misclassifiedIndex];
            double actualClassification = y[misclassifiedIndex];
            weights = updateWeights(actualClassification, weights, x);
//            System.out.println(Arrays.toString(weights));
            misclassifiedExamples = predict(X, y, weights); //indexes of misclassified
        }

    }

    //initialises weights
    private double[] randomiseWeights() {
        double[] weights = new double[numberOfFeatures];

        for (int weightIndex = 0; weightIndex < numberOfFeatures; weightIndex++)
            weights[weightIndex] = random.nextDouble();

        return weights;
    }

    //calculates whether the points are under or over the hyperplane
    public int[] initialiseHypothesis(double[][] X, double[] weights) {
        int[] hypothesis = new int[X.length];

        for (int pointNumber = 0; pointNumber < X.length; pointNumber++) {
            double[] features = X[pointNumber];
//            double zetaForPoint = zetas[pointNumber];
            hypothesis[pointNumber] = MatrixUtilities.getHypothesis(features, weights);
//            hypothesis[pointNumber] = MatrixUtilities.getHypothesis(features, weights, zetaForPoint);
//            hypothesis[pointNumber] = MatrixUtilities.getHypothesisSoftMargin(features, weights, y[pointNumber], 0);
        }

        return hypothesis;
    }

    //calculates whether the points are misclassified
    public int[] predict(double[][] X, double[] y, double[] weights) {
        int[] predict = new int[X.length];
        int[] hypothesis = initialiseHypothesis(X, weights);
//        System.out.println(Arrays.toString(hypothesis));
        int numberOfMisclassified = 0;
        //get number of misclassified
        for (int predictionNumber = 0; predictionNumber < predict.length; predictionNumber++) {
            int prediction = hypothesis[predictionNumber];
            double actual = y[predictionNumber];

            if (prediction != actual)
                numberOfMisclassified++;
        }

        //After counting, finally provide the indexes of those misclassified.
        int[] misclassifiedIndexes = new int[numberOfMisclassified];
        for (int predictionNumber = 0, misclassifiedIndex = 0; predictionNumber < predict.length; predictionNumber++) {
            int prediction = hypothesis[predictionNumber];
            double actual = y[predictionNumber];

            if (prediction != actual)
                misclassifiedIndexes[misclassifiedIndex++] = predictionNumber;
        }

        return misclassifiedIndexes;
    }

    //Returns a random misclassified example's index
    private int pickOneFrom(int[] misclassifiedExamples) {
        int index = (int) (Math.random() * misclassifiedExamples.length); //any
        return misclassifiedExamples[index];
    }

    //updates the weights depending on whether the misclassified example is on top or below the line
    private double[] updateWeights(double actualClassification, double[] weights, double[] x) {
        double[] newWeights = new double[weights.length];
        if (actualClassification == 1) //the angle is larger? than 90 degrees
            newWeights = MatrixUtilities.add1DVectors(weights, x);
        else
            newWeights = MatrixUtilities.subtract1DVectors(weights, x);
        return newWeights;
    }

    public double[] getWeights() {
        return weights;
    }

    public double[] getY() {
        return y;
    }

    public double[][] getX() {
        return X;
    }

    public void setX(double[][] x) {
        X = x;
    }

    public void setY(double[] y) {
        this.y = y;
    }

    @Override
    public String toString() {
        String result = "";
        for (double w : weights)
            result += w + ", \t";
        return result;
    }
}
