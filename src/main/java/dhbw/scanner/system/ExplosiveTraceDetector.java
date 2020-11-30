package dhbw.scanner.system;

import dhbw.scanner.search.BruteForce;
import dhbw.scanner.search.IStringMatching;

public class ExplosiveTraceDetector {

    private IStringMatching searchAlgorithm;

    public ExplosiveTraceDetector() {
        searchAlgorithm = new BruteForce();
    }

    public boolean read(String testStripe) {
        int index = searchAlgorithm.search(testStripe, "exp");
        return index >= 0;
    }
}
