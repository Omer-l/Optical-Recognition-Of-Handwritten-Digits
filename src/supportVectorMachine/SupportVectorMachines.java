package supportVectorMachine;

import toolKit.HandwrittenDigitClassifierAlgorithm;
import toolKit.Row;
import toolKit.MatrixUtilities;

public class SupportVectorMachines extends HandwrittenDigitClassifierAlgorithm {
    private final int numberOfPossibleClassifications; //digits vary from 0 to 9.

    public SupportVectorMachines(String algorithmName, Row[] trainingRows, Row[] testRows, int numberOfPossibleClassifications) {
        super(algorithmName, trainingRows, testRows);
        this.numberOfPossibleClassifications = numberOfPossibleClassifications;
    }

    @Override
    public void run() {
        Perceptron[] perceptrons = new Perceptron[numberOfPossibleClassifications];

    }
}
