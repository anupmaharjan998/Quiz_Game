package np.edu.herald.quizzapp.dao.implementation;

/**
 * Implementation of the AvgCalculation interface.
 * Provides functionality to calculate the average score from a comma-separated string of scores.
 */
public class AvgCalculationImp {

    /**
     * Calculates the average score from a given comma-separated string of scores.
     *
     * @param scores A comma-separated string of numerical scores.
     * @return A float array where index 0 contains the rounded average score and index 1 contains the count of valid scores.
     */
    public float[] calculateAvgScore(String scores) {
        // Split the scores string by commas
        String[] scoreArray = scores.split(",");

        // Initialize variables for sum and count
        float sum = 0;
        int count = 0;

        // Iterate through the score array
        for (String scoreStr : scoreArray) {
            try {
                // Convert the score string to a float
                float score = Float.parseFloat(scoreStr.trim());
                sum += score; // Add to the sum
                count++; // Increment the count
            } catch (NumberFormatException e) {
                // Handle the case where the score is not a valid float
                System.err.println("Invalid score: " + scoreStr);
            }
        }

        // Calculate the average
        float average = count > 0 ? sum / count : 0; // Return 0 if no valid scores
        float roundedAvg = Math.round(average * 100.0f) / 100.0f;

        // Return the result as an array: [average, count]
        return new float[]{roundedAvg, count};
    }
}
