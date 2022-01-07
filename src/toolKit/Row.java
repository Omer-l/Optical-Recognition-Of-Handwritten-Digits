package toolKit;

/**
 * Each Row consists of 64 inputs and a classification (digit 0-9).
 * Each Row can be represented as a data point on a 64 dimensional graph.
 */
public class Row {

    private double [] inputs; //inputs
    private int classification; //classification
    private double distance; //distance to get to this row

    public Row(double[] inputs, int classification) {
        this.inputs = inputs;
        this.classification = classification;
    }

    //calculates euclidean distance to another data point/row
    public double getEuclideanDistanceTo(Row otherDataPoint) {
        double sum = 0;

        //get expression inside square root
        for(int inputIndex = 0; inputIndex < inputs.length; inputIndex++)
            sum += Math.pow(this.inputs[inputIndex] - otherDataPoint.inputs[inputIndex], 2);

        return Math.sqrt(sum);
    }

    //Returns the sum of all the inputs in the inputs array.
    public double getSumOfInputs() {
        double sum = 0;
        for(double input : inputs)
            sum += input;

        return sum;
    }

    public double[] getInputs() {
        return inputs;
    }

    public void setInputs(double[] inputs) {
        this.inputs = inputs;
    }

    public int getClassification() {
		return classification;
	}

	public void setClassification(int classification) {
		this.classification = classification;
	}

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        String output = "";

        for(int inputNumber = 0; inputNumber < inputs.length; inputNumber++)
            output += inputs[inputNumber] + " ";

        output += classification;

        return output;
    }
}
