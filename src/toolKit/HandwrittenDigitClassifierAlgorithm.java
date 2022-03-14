package toolKit;

public abstract class HandwrittenDigitClassifierAlgorithm {
    private Row[] trainingRows; //can vary if test rows are added as training rows.
    private final Row[] testRows;
    public final String algorithmName;
    public int correctClassificationCounter = 0; //counter for correct classifications
    public int incorrectClassificationCounter = 0;//counter for incorrect classifications
    public final static double MAX_PERCENTAGE = 100;

    public HandwrittenDigitClassifierAlgorithm(String algorithmName, Row[] trainingRows, Row[] testRows) {
        this.algorithmName = algorithmName.toUpperCase();
        this.trainingRows = trainingRows;
        this.testRows = testRows;
    }

    //to run the algorithm
    public void run() {

    }

    //adds 1 to correct classification counter
    public void add1Correct() {
        correctClassificationCounter++;
    }

    //adds 1 to incorrect classification counter
    public void add1Incorrect() {
        incorrectClassificationCounter++;
    }

    public Row[] getTrainingRows() {
        return trainingRows;
    }

    public Row[] getTestRows() {
        return testRows;
    }

    public void setTrainingRows(Row[] trainingRows) {
        this.trainingRows = trainingRows;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getCorrectClassificationCounter() {
        return correctClassificationCounter;
    }

    public int getIncorrectClassificationCounter() {
        return incorrectClassificationCounter;
    }

    public void setCorrectClassificationCounter(int correctClassificationCounter) {
        this.correctClassificationCounter = correctClassificationCounter;
    }

    public void setIncorrectClassificationCounter(int incorrectClassificationCounter) {
        this.incorrectClassificationCounter = incorrectClassificationCounter;
    }

    public double getAccuracy() {
        return ((double)correctClassificationCounter / testRows.length) * MAX_PERCENTAGE;
    }

    @Override
    public String toString() {
        //output results
        double accuracy = getAccuracy();
        double errorRate = MAX_PERCENTAGE - accuracy;

        return "ALGORITHM NAME: \t\t\t" + algorithmName + "\nCORRECT CLASSIFICATIONS: \t" + correctClassificationCounter + "\nINCORRECT CLASSIFICATIONS: \t" + incorrectClassificationCounter + "\nACCURACY: \t\t\t\t\t" + accuracy + "%\nERROR RATE: \t\t\t\t" + errorRate;
    }
}
