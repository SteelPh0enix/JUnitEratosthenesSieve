import com.steelph0enix.Calculator;
import com.steelph0enix.EratosthenesSieve;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class EratosthenesSieveTests {
    // Test assumptions: the sieve uses Calculator's
    // method isPrime to check if the number is prime or not.
    // Hence, we have to provide custom implementations of
    // isPrime to check whether the function itself works
    // correctly or not.

    // This, default implementations will be used in some tests that
    // will check if the sieve returns correct values with correct isPrime
    // implementation
    private final EratosthenesSieve sieve = new EratosthenesSieve();
    private final int[] firstPrimeNumbers = new int[]{
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541
    };

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 10, 20, 50, 100})
    void testGenerateTable_returnsCorrectResult_correctParametersGiven(int numberCount) {
        int[][] generatedTable = EratosthenesSieve.generateTable(numberCount);

        assertNotNull(generatedTable);
        assertEquals(generatedTable.length, numberCount);
        for (int i = 0; i < numberCount; i++) {
            assertEquals(generatedTable[i][0], i + 2);
            // the other value doesn't matter as it always should be set
            // to correct one in markPrimesInTable
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -5, -1, 0})
    void testGenerateTable_throwsIllegalArgumentException_incorrectParametersGiven(int numberCount) {
        assertThrows(IllegalArgumentException.class, () -> EratosthenesSieve.generateTable(numberCount));
    }

    @Test
    void testMarkPrimesInTable_throwsNPE_nullTableGiven() {
        assertThrows(NullPointerException.class, () -> sieve.markPrimesInTable(null));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 10, 20, 50, 100})
    void testMarkPrimesInTable_marksPrimesCorrectly_customIsPrimeImplementation(int numberCount) {
        final Calculator testCalc = new Calculator() {
            @Override
            public boolean isPrime(int number) {
                return true;
            }
        };

        final EratosthenesSieve testSieve = new EratosthenesSieve(testCalc);

        // We assume this works correctly, otherwise other tests will fail and notify us about it.
        // generateTable should not use isPrime, so I can use either default `sieve` or `testSieve`
        int[][] table = EratosthenesSieve.generateTable(numberCount);

        int[][] calculatedResult = testSieve.markPrimesInTable(table);
        assertNotNull(calculatedResult);

        for (int[] calculatedResultRow : calculatedResult) {
            assertEquals(calculatedResultRow[1], 1);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 10, 20, 50, 100})
    void testMarkPrimesInTable_marksNonPrimesCorrectly_customIsPrimeImplementation(int numberCount) {
        final Calculator testCalc = new Calculator() {
            @Override
            public boolean isPrime(int number) {
                return false;
            }
        };

        final EratosthenesSieve testSieve = new EratosthenesSieve(testCalc);

        // We assume this works correctly, otherwise other tests will fail and notify us about it.
        // generateTable should not use isPrime, so I can use either default `sieve` or `testSieve`
        int[][] table = EratosthenesSieve.generateTable(numberCount);

        int[][] calculatedResult = testSieve.markPrimesInTable(table);
        assertNotNull(calculatedResult);

        for (int[] calculatedResultRow : calculatedResult) {
            assertEquals(calculatedResultRow[1], 0);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 10, 20, 50, 100})
    void testMarkPrimesInTable_returnsCorrectResult_defaultImplAndCorrectParametersGiven(int numberCount) {
        // We assume this works correctly, otherwise other tests will fail and notify us about it.
        int[][] table = EratosthenesSieve.generateTable(numberCount);

        int[][] calculatedResult = sieve.markPrimesInTable(table);
        assertNotNull(calculatedResult);

        // Check if the data in returned array is correct
        for (int[] calculatedResultRow : calculatedResult) {
            if (Arrays.stream(firstPrimeNumbers).anyMatch(x -> x == calculatedResultRow[0])) {
                assertEquals(calculatedResultRow[1], 1);
            } else {
                assertEquals(calculatedResultRow[1], 0);
            }
        }
    }

    @Test
    void testSieveTableToString_throwsNPE_argumentIsNull() {
        assertThrows(NullPointerException.class, () -> EratosthenesSieve.sieveTableToString(null));
    }
}
