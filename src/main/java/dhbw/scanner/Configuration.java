package dhbw.scanner;

import dhbw.scanner.search.BoyerMoore;
import dhbw.scanner.search.IStringMatching;

import java.time.Period;
import java.time.temporal.TemporalAmount;

public class Configuration {

    public static final String DIRECTORY_PATH = "src/main/resources/";

    public static final TemporalAmount TIME_UNTIL_ID_CARD_IS_INVALID = Period.ofMonths(6);

    public static final int FAILED_ATTEMPTS_TO_LOCK = 3;

    public static final String SECRET_KEY = "BaggageScanner$20^20";

    public static final String INITIAL_PW = "1234";

    public static final int MAX_NUMBER_OF_TRAYS = 100;

    public static final IStringMatching SEARCH_ALGORITHM = new BoyerMoore();
}
