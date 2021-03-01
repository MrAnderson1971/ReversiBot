package persistence;

import model.GameHistory;
import model.MoveHistory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.KeyException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameHistory wr = (GameHistory) reader.load();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyObject() {
        JsonReader reader = new JsonReader("./data/emptySave.json");
        try {
            GameHistory gh = (GameHistory) reader.load();
            assertEquals(0, gh.getKeyset().size());
            assertNull(gh.get(0));
        } catch (IOException e) {
            fail("Error in file.");
        }
    }

    @Test
    void testReaderGeneral() {
        String f = "./data/testSave.json";
        try {
            JsonReader reader = new JsonReader(f);
            GameHistory gh = (GameHistory) reader.load();
            assertEquals(2, gh.getKeyset().size());
            assertEquals("1. Wimpy War | Manos won\n" +
                    "2. Detroit: Become Skynet | RK800 won\n", gh.toString());
            assertEquals("1. Manos made the move C3\n" +
                    "2. Thanny made the move C2\n" +
                    "3. Manos made the move C1\n" +
                    "4. Thanny made the move B1\n" +
                    "5. Manos made the move D2\n" +
                    "6. Thanny made the move E2\n" +
                    "7. Manos made the move A0\n" +
                    "8. Thanny made the move B4\n" +
                    "9. Manos made the move C4\n" +
                    "10. Thanny made the move B2\n" +
                    "11. Manos made the move F1\n" +
                    "12. Thanny made the move F2\n" +
                    "13. Manos made the move F5\n" +
                    "14. Thanny made the move F4\n" +
                    "15. Manos made the move A3\n" +
                    "16. Thanny made the move F0\n" +
                    "17. Manos made the move E1\n" +
                    "18. Thanny made the move D0\n" +
                    "19. Manos made the move E5\n" +
                    "20. Thanny made the move C5\n" +
                    "21. Manos made the move B5\n" +
                    "22. Thanny made the move B3\n" +
                    "23. Manos made the move A5\n" +
                    "24. Thanny made the move C0\n" +
                    "25. Manos made the move B0\n" +
                    "26. Thanny made the move D6\n" +
                    "27. Manos made the move E0\n" +
                    "28. Thanny made the move F6\n" +
                    "29. Manos made the move G2\n" +
                    "30. Thanny made the move D1\n" +
                    "31. Manos made the move A4\n" +
                    "32. Thanny made the move F3\n" +
                    "33. Manos made the move G4\n" +
                    "34. Thanny made the move H3\n" +
                    "35. Manos made the move H2\n" +
                    "36. Thanny made the move A2\n" +
                    "37. Manos made the move A1\n" +
                    "38. Thanny made the move D5\n" +
                    "39. Manos made the move D7\n" +
                    "40. Thanny made the move E7\n" +
                    "41. Manos made the move F7\n" +
                    "42. Thanny made the move G5\n" +
                    "43. Manos made the move E6\n" +
                    "44. Thanny made the move G3\n" +
                    "45. Manos made the move G0\n" +
                    "46. Thanny made the move G6\n" +
                    "47. Manos made the move C7\n" +
                    "48. Thanny made the move H1\n" +
                    "49. Manos made the move H4\n" +
                    "50. Thanny made the move H5\n" +
                    "51. Manos made the move C6\n" +
                    "52. Thanny made the move G1\n" +
                    "53. Manos made the move G7\n" +
                    "54. Thanny made the move H6\n" +
                    "55. Manos made the move H0\n" +
                    "56. Thanny made the move B7\n" +
                    "57. Manos made the move A7\n", gh.get(1).toString());
            assertEquals("1. T-800 made the move F4\n" +
                    "2. RK800 made the move D5\n" +
                    "3. T-800 made the move C5\n" +
                    "4. RK800 made the move F5\n" +
                    "5. T-800 made the move E5\n" +
                    "6. RK800 made the move B5\n" +
                    "7. T-800 made the move C3\n" +
                    "8. RK800 made the move B2\n" +
                    "9. T-800 made the move F6\n" +
                    "10. RK800 made the move F3\n" +
                    "11. T-800 made the move F2\n" +
                    "12. RK800 made the move G4\n" +
                    "13. T-800 made the move B6\n" +
                    "14. RK800 made the move F1\n" +
                    "15. T-800 made the move G1\n" +
                    "16. RK800 made the move D2\n" +
                    "17. T-800 made the move D6\n" +
                    "18. RK800 made the move C4\n" +
                    "19. T-800 made the move A1\n" +
                    "20. RK800 made the move E6\n" +
                    "21. T-800 made the move E1\n" +
                    "22. RK800 made the move C2\n" +
                    "23. T-800 made the move H4\n" +
                    "24. RK800 made the move A7\n" +
                    "25. T-800 made the move B4\n" +
                    "26. RK800 made the move H0\n" +
                    "27. T-800 made the move D7\n" +
                    "28. RK800 made the move A2\n" +
                    "29. T-800 made the move B1\n" +
                    "30. RK800 made the move E2\n" +
                    "31. T-800 made the move C1\n" +
                    "32. RK800 made the move E7\n" +
                    "33. T-800 made the move F7\n" +
                    "34. RK800 made the move A0\n" +
                    "35. T-800 made the move C6\n" +
                    "36. RK800 made the move H3\n" +
                    "37. T-800 made the move G2\n" +
                    "38. RK800 made the move G3\n" +
                    "39. T-800 made the move A4\n" +
                    "40. RK800 made the move B0\n" +
                    "41. T-800 made the move B7\n" +
                    "42. RK800 made the move C7\n" +
                    "43. T-800 made the move G6\n" +
                    "44. RK800 made the move A6\n" +
                    "45. T-800 made the move A5\n" +
                    "46. RK800 made the move A3\n" +
                    "47. T-800 made the move D1\n" +
                    "48. RK800 made the move B3\n" +
                    "49. T-800 made the move H1\n" +
                    "50. RK800 made the move C0\n" +
                    "51. T-800 made the move H2\n" +
                    "52. RK800 made the move D0\n" +
                    "53. T-800 made the move F0\n" +
                    "54. RK800 made the move E0\n" +
                    "55. T-800 made the move G5\n" +
                    "56. RK800 made the move G0\n", gh.get(2).toString());
        } catch (IOException e) {
            fail(e);
        }
    }
}
