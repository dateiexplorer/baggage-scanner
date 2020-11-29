package dhbw.scanner.system;

import dhbw.scanner.Configuration;
import dhbw.scanner.authentication.IDCard;
import dhbw.scanner.authentication.ProfileType;
import dhbw.scanner.utils.AuthUtils;

public class Reader {

    private OperatingStation operatingStation;

    public Reader(OperatingStation operatingStation) {
        this.operatingStation = operatingStation;
    }

    public boolean verify(IDCard idCard, String pin) {
        if (!isAccepted(idCard)) {
            System.out.println("Your IDCard is not accepted.");
            return false;
        }

        if (idCard.isLocked()) {
            System.out.println("Your IDCard is locked.");
            return false;
        }

        String correctPin = idCard.getMagnetStripe().getPin(Configuration.SECRET_KEY);
        boolean successful = correctPin == pin;
        if (successful) {
            System.out.println("Verification succeeded.");
            operatingStation.getBaggageScanner().setState(State.ACTIVATED);
            idCard.reset();
            return true;
        } else {
            System.out.println("Verification failed.");
            idCard.onVerificationFailed();
        }

        return false;
    }

    private boolean isAccepted(IDCard idCard) {
        return !AuthUtils.checkAuthorization(idCard, ProfileType.K, ProfileType.O);
    }
}
