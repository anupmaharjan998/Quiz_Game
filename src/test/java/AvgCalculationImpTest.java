import np.edu.herald.quizzapp.dao.implementation.AvgCalculationImp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvgCalculationImpTest {

    AvgCalculationImp avgClass = new AvgCalculationImp();

    @Test
    void testCalculateAvgScore_ValidScores() {
        // Test case with valid scores
        String scores = "10, 20, 30, 40, 50";
        float[] result = avgClass.calculateAvgScore(scores);

        // Expected average is (10 + 20 + 30 + 40 + 50) / 5 = 30.0
        assertEquals(30.0, result[0], "Average should be 30.0");
        assertEquals(5, result[1], "Count of scores should be 5");
    }

    @Test
    void testCalculateAvgScore_InvalidScores() {
        // Test case with invalid scores
        String scores = "10, abc, 30, , 50";
        float[] result = avgClass.calculateAvgScore(scores);

        // Expected average is (10 + 30 + 50) / 3 = 30.0
        assertEquals(30.0, result[0], "Average should be 30.0");
        assertEquals(3, result[1], "Count of valid scores should be 3");
    }

    @Test
    void testCalculateAvgScore_EmptyString() {
        // Test case with empty scores
        String scores = "";
        float[] result = avgClass.calculateAvgScore(scores);

        // Since there are no valid scores, average should be 0.0 and count should be 0
        assertEquals(0.0, result[0], "Average should be 0.0");
        assertEquals(0, result[1], "Count of scores should be 0");
    }

    @Test
    void testCalculateAvgScore_SingleScore() {
        // Test case with a single score
        String scores = "15";
        float[] result = avgClass.calculateAvgScore(scores);

        // The average should be the same as the single score, which is 15.0
        assertEquals(15.0, result[0], "Average should be 15.0");
        assertEquals(1, result[1], "Count of scores should be 1");
    }
    
}
