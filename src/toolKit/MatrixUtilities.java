package toolKit;

public class MatrixUtilities {

    /**
     * Calculates the dot product of two vectors/points
     * @param vector1       is a vector/point
     * @param vector2       is a vector/point
     * @return              dot product of the given vectors
     */
    public static double getDotProduct(double[] vector1, double[] vector2) {
        double dotProduct = 0;

        for(int vectorNumber = 0; vectorNumber < vector1.length && vectorNumber < vector2.length; vectorNumber++) {
            double vectorPoint1 = vector1[vectorNumber];
            double vectorPoint2 = vector2[vectorNumber];

            dotProduct += (vectorPoint1 * vectorPoint2);
        }
        return dotProduct;
    }

    /**
     * Calculates the dot product of two vectors/points excludes x_0 and w_0 where x_0 = 1 and w_0 is 'b' or yIntercept
     * @param weights           is the weights
     * @param vector            is a vector/point
     * @return                  dot product of the given vectors
     */
    public static double getDotProductExcludeX0W0(double[] weights, double[] vector) {
        double dotProduct = 0;

        for(int vectorNumber = 1; vectorNumber < weights.length && vectorNumber < vector.length; vectorNumber++) {
            double weight = weights[vectorNumber];
            double vectorPoint2 = vector[vectorNumber];
            dotProduct += (weight * vectorPoint2);
        }
        return dotProduct;
    }

    /**
     * function that returns the result of a dot product performed in another space
     * @param vector1   is vector 1 to multiply
     * @param vector2   is vector 2 that will be multiplied with vector 1
     * @return
     */
    public static double polynomialKernel(double[] vector1, double[] vector2, int degree) {
        int numberOfIterations = vector1.length; //the number of iterations for the value inside the bracket
        double kernel = 0; //could be 1 to follow lecture vid on youtube.

        for(int index = 0; index < numberOfIterations; index++) {
            kernel += (vector1[index] * vector2[index]);
        }

        return Math.pow(kernel, degree);
    }

    /**
     * Calculates the hypothesis
     * @param augmentedVector           is the inputs, a augmentedVector on the graph where x0 = 1
     * @param gradient                  is the gradient, which is an augmentedVector, AKA weights for perceptron. w0 = yIntercept initially.
     * @return                          +1 if hypothesis is more than or equal to 0, otherwise returns -1
     */
    public static int getHypothesis(double[] augmentedVector, double[] gradient) {
        double dotProductOfWeightsAndVector = getDotProduct(gradient, augmentedVector);
        double hypothesisResult = polynomialKernel(augmentedVector, gradient, 1);
//        System.out.println(hypothesisResult);
        if(hypothesisResult >= 1)
            return 1;
        else
            return -1;
    }

    /**
     * Adds two vectors together. In this context, this is used to update the weights.
     * @param vector1       is vector 1
     * @param vector2       is vector 2
     * @return              the vector of adding the two given vectors
     */
    public static double[] add1DVectors(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];

        for(int vectorNumber = 0; vectorNumber < vector1.length && vectorNumber < vector2.length; vectorNumber++) {
            double vectorPoint1 = vector1[vectorNumber];
            double vectorPoint2 = vector2[vectorNumber];

            result[vectorNumber] = (vectorPoint1 + vectorPoint2);
        }

        return result;
    }

    /**
     * Subtracts two vectors. In this context, this is used to update the weights.
     * @param vector1       is vector 1
     * @param vector2       is vector 2
     * @return              the vector of subtracting the two given vectors
     */
    public static double[] subtract1DVectors(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];

        for(int vectorNumber = 0; vectorNumber < vector1.length && vectorNumber < vector2.length; vectorNumber++) {
            double vectorPoint1 = vector1[vectorNumber];
            double vectorPoint2 = vector2[vectorNumber];

            result[vectorNumber] = (vectorPoint1 - vectorPoint2);
        }

        return result;
    }

    /**
     * Transforms a classification array into -1 or 1, depending on the classification to keep as 1, if not classification, -1.
     * @param allClassifications    the array containing all the classifications
     * @param desiredClass          the desired classification to turn into 1 in the classification array.
     * @return                      an array containing 1s for desiredClassification, -1 for other classifications
     */
    public static double[] getBinaryClassifications(double[] allClassifications, double desiredClass) {
        int size = allClassifications.length;
        double[] binaryClassifications = new double[size];

        for(int index = 0; index < size; index++) {
            int classification = (int)allClassifications[index];

            if(classification == desiredClass)
                binaryClassifications[index] = 1;
            else
                binaryClassifications[index] = -1;
        }
        return binaryClassifications;
    }
}
