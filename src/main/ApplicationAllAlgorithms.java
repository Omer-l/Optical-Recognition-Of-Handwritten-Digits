package main;

import kNearestNeighbours.KNearestNeighbour; //Nearest Neighbour algorithm
import nearestNeighbour.NearestNeighbour;
import supportVectorMachine.SupportVectorMachines;
import toolKit.*; //For reading in the data and creating an array of Rows.

/**
 * Runs all algorithms attempted for this assignment.
 * Algorithms:
 * Support Vector Machines
 * K-Nearest Neighbours
 * Nearest Neighbour
 */
public class ApplicationAllAlgorithms {
    private final static FileReaderDataset TRAINING_FILE_READER = new FileReaderDataset("dataset1.csv"); //for training file reading
    private final static FileReaderDataset TESTING_FILE_READER = new FileReaderDataset("dataset2.csv"); //for testing file reading

    //1ST FOLD
    private final static Row[] TRAINING_DATA_SETS = TRAINING_FILE_READER.getData(); //training data
    private final static Row[] TESTING_DATA_SETS = TESTING_FILE_READER.getData(); //test data
    //2ND FOLD
    private final static Row[] TRAINING_DATA_SETS_2 = TESTING_FILE_READER.getData(); //training data
    private final static Row[] TESTING_DATA_SETS_2 = TRAINING_FILE_READER.getData(); //test data
    private final static int NUMBER_OF_POSSIBLE_CLASSIFICATIONS = 10;
    private final static double NUMBER_OF_FOLDS = 2;
    private final static int FIRST_FOLD = 1;
    private final static int SECOND_FOLD = 2;

    public static void main(String[] args) {
        System.out.println("Running K-Nearest Neighbours...");
//        run2FoldTestOnKnearestNeighbourAlgorithm();
        System.out.println("Running Nearest Neighbour...");
//        run2FoldTestOnNearestNeighbourAlgorithm();
        System.out.println("Running Support Vector Machine (SVM)...");
        run2FoldTestOnSVM();
    }

    /**                                         -----------------                                                   */
    /**                                         NEAREST NEIGHBOUR                                                   */
    /**                                         -----------------                                                   */
    //Performs 2-fold test on nearest neighbour algorithm
    private static void run2FoldTestOnNearestNeighbourAlgorithm() {
        double firstFoldAccuracy = runNearestNeighbourAlgorithm(TRAINING_DATA_SETS, TESTING_DATA_SETS, FIRST_FOLD); //Nearest neighbour algorithm run and output results
        double secondFoldAccuracy = runNearestNeighbourAlgorithm(TRAINING_DATA_SETS_2, TESTING_DATA_SETS_2, SECOND_FOLD); //Nearest neighbour algorithm run and output results
        double averageOfTheTwoNearestNeighbours = (firstFoldAccuracy + firstFoldAccuracy) / NUMBER_OF_FOLDS;
//        double averageOfTheTwoNearestNeighbours = (firstFoldAccuracy + secondFoldAccuracy) / NUMBER_OF_FOLDS;
        String resultOfNearestNeighbour = "Average of 2 fold test for Nearest Neighbour algorithm: " + averageOfTheTwoNearestNeighbours;
        System.out.println(resultOfNearestNeighbour);
    }

    //Runs k-nearest neighbour (closest euclidean distance).
    public static double runNearestNeighbourAlgorithm(Row[] dataset1, Row[] dataset2, int foldNumber) {
        //rerun nearest neighbour algorithm, each time increment the number of nearest neighbour
        String algorithmName = "Nearest Neighbour Fold " + foldNumber;
        NearestNeighbour nearestNeighbour = new NearestNeighbour(algorithmName, dataset2, dataset1, NUMBER_OF_POSSIBLE_CLASSIFICATIONS);
        nearestNeighbour.run();
        double accuracy = nearestNeighbour.getAccuracy();
        System.out.println(nearestNeighbour); //outputs result of the algorithm with the best number of nearest neighbours
        return accuracy;
    }

    /**                                         --------------------                                                 */
    /**                                         K-NEAREST NEIGHBOURS                                                */
    /**                                         --------------------                                                */
    //Performs 2-fold test on k-nearest neighbour algorithm
    private static void run2FoldTestOnKnearestNeighbourAlgorithm() {
        double firstFoldAccuracy = runKNearestNeighbourAlgorithm(TRAINING_DATA_SETS, TESTING_DATA_SETS, FIRST_FOLD); //K Nearest neighbour algorithm run and output results
        double secondFoldAccuracy = runKNearestNeighbourAlgorithm(TRAINING_DATA_SETS_2, TESTING_DATA_SETS_2, SECOND_FOLD); //K Nearest neighbour algorithm run and output results
        double averageOfTheTwoKNearestNeighbours = (firstFoldAccuracy + secondFoldAccuracy) / NUMBER_OF_FOLDS;
        String resultOfNearestNeighbour = "Average of 2 fold test for K-nearest neighbours algorithm: " + averageOfTheTwoKNearestNeighbours;
        System.out.println(resultOfNearestNeighbour);
    }

    //Runs k-nearest neighbour (closest euclidean distance).
    public static double runKNearestNeighbourAlgorithm(Row[] dataset1, Row[] dataset2, int foldNumber) {
        KNearestNeighbour bestKNearestNeighbour = null;
        double bestAccuracy = Double.MIN_VALUE;
        int maximumNumberOfNeighbours = 11;
        //rerun nearest neighbour algorithm, each time increment the number of nearest neighbour
        for (int numberOfNearestNeighbours = 1; numberOfNearestNeighbours <= maximumNumberOfNeighbours; numberOfNearestNeighbours++) {
            String algorithmName = "K-Nearest Neighbours Fold " + foldNumber;
            KNearestNeighbour KNearestNeighbour = new KNearestNeighbour(algorithmName, dataset2, dataset1, NUMBER_OF_POSSIBLE_CLASSIFICATIONS, numberOfNearestNeighbours);
            KNearestNeighbour.run();
            double accuracy = KNearestNeighbour.getAccuracy();
            if (accuracy > bestAccuracy) { //updates nearest neighbour algorithm that found a better result
                bestKNearestNeighbour = KNearestNeighbour;
                bestAccuracy = accuracy;
            }
        }
        System.out.println(bestKNearestNeighbour); //outputs result of the algorithm with the best number of nearest neighbours
        return bestAccuracy;
    }

    /**                                         -----------------------------                                       */
    /**                                         SUPPORT VECTOR MACHINES (SVM)                                       */
    /**                                         -----------------------------                                       */
    //Performs 2-fold test on support vector machines algorithm
    private static void run2FoldTestOnSVM() {
        double firstFoldAccuracy = runSVM(TRAINING_DATA_SETS, TESTING_DATA_SETS, FIRST_FOLD); //SVM algorithm run and output results
        double secondFoldAccuracy = runSVM(TRAINING_DATA_SETS_2, TESTING_DATA_SETS_2, SECOND_FOLD); //SVM algorithm run and output results
        double averageOfTheTwoSVMs = (firstFoldAccuracy + secondFoldAccuracy) / NUMBER_OF_FOLDS;
        String resultOfNearestNeighbour = "Average of 2 fold test for Support Vector Machines: " + averageOfTheTwoSVMs;
        System.out.println(resultOfNearestNeighbour);
    }

    //Runs SVM algorithm
    public static double runSVM(Row[] dataset1, Row[] dataset2, int foldNumber) {
        //rerun nearest neighbour algorithm, each time increment the number of nearest neighbour
        String algorithmName = "Support Vector Machines fold " + foldNumber;
        SupportVectorMachines supportVectorMachines = new SupportVectorMachines(algorithmName, dataset2, dataset1, NUMBER_OF_POSSIBLE_CLASSIFICATIONS);
//        supportVectorMachines.run();
//        supportVectorMachines.myTestRun();
        supportVectorMachines.myTestRun2();
        double accuracy = supportVectorMachines.getAccuracy();
        System.out.println(supportVectorMachines); //outputs result of the algorithm with the best number of nearest neighbours
        return accuracy;
    }
}
