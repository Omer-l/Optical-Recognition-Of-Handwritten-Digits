package toolKit;

import static supportVectorMachine.Perceptron.polynomialKernel;

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
     * Calculates the hypothesis
     * @param augmentedVector           is the inputs, a augmentedVector on the graph where x0 = 1
     * @param gradient                  is the gradient, which is an augmentedVector, AKA weights for perceptron. w0 = yIntercept initially.
     * @return                          +1 if hypothesis is more than or equal to 0, otherwise returns -1
     */
    public static int getHypothesis(double[] augmentedVector, double[] gradient) {
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
     * @param desiredClasses        the desired classifications to turn into 1 in the classification array.
     * @return                      an array containing 1s for desiredClassification, -1 for other classifications
     */
    public static double[] getBinaryClassifications(double[] allClassifications, double[] desiredClasses) {
        int size = allClassifications.length;
        double[] binaryClassifications = new double[size];

        for(int classificationIndex = 0; classificationIndex < size; classificationIndex++) {
            int classification = (int)allClassifications[classificationIndex];
            binaryClassifications[classificationIndex] = -1; //-1, unless it's the desired
            //Ensures binary classification is 1 if classification is the desired class
            for(int desiredClassificationIndex = 0; desiredClassificationIndex < desiredClasses.length; desiredClassificationIndex++) {
                double desiredClass = desiredClasses[desiredClassificationIndex];
                if (classification == desiredClass) {
                    binaryClassifications[classificationIndex] = 1;
                    break;
                }
            }
        }
        return binaryClassifications;
    }

    //Gets the normal to the vector
    public static double getMagnitudeOrUnitNormalVector(double[] vector) {
        double sum = 0;
        for(int elementIndex = 1; elementIndex < vector.length; elementIndex++) { //starts from 1, excludes 'b'
            double element = vector[elementIndex];
            sum += (element * element);
        }

        return Math.sqrt(sum);
    }
}
