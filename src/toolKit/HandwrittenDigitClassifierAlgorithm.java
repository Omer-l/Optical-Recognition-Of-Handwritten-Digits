package toolKit;

public abstract class HandwrittenDigitClassifierAlgorithm {
    private Row[] trainingRows; //can vary if test rows are added as training rows.
    private final Row[] testRows;
    private final String algorithmName;
    private int correctClassificationCounter = 0; //counter for correct classifications
    private int incorrectClassificationCounter = 0;//counter for incorrect classifications

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

    @Override
    public String toString() {
        //output results
        double accuracy = ((double)correctClassificationCounter / testRows.length) * 100.00;
        double errorRate = 100.0 - accuracy;

        return "ALGORITHM NAME: \t\t\t" + algorithmName + "\nCORRECT CLASSIFICATIONS: \t" + correctClassificationCounter + "\nINCORRECT CLASSIFICATIONS: \t" + incorrectClassificationCounter + "\nACCURACY: \t\t\t\t\t" + accuracy + "%\nERROR RATE: \t\t\t\t" + errorRate;
    }
}
