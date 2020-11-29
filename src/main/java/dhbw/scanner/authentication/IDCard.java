package dhbw.scanner.authentication;

import dhbw.scanner.Configuration;
import dhbw.scanner.utils.AuthUtils;
import dhbw.scanner.utils.Utils;

import java.time.LocalDateTime;

public class IDCard {

    private int id;
    private LocalDateTime validUntil;
    private boolean isLocked;
    private IDCardType type;

    private MagnetStripe magnetStripe;
    private int failedAttempts;

    public IDCard(int id, IDCardType type, MagnetStripe magnetStripe) {
        this.id = id;
        this.type = type;

        // Gets invalid after an amount of time
        this.validUntil = Utils.getCurrentDateTime().plus(Configuration.TIME_UNTIL_ID_CARD_IS_INVALID);

        this.magnetStripe = magnetStripe;
    }

    public boolean isValid() {
        return Utils.getCurrentDateTime().isBefore(validUntil);
    }

    public void onVerificationFailed() {
        failedAttempts++;
        if (failedAttempts >= Configuration.FAILED_ATTEMPTS_TO_LOCK) {
            lock();
        }
    }

    public void reset() {
        failedAttempts = 0;
        unlock();
    }

    private void lock() {
        isLocked = true;
    }

    private void unlock() {
        isLocked = false;
    }

    // Getter and setter
    public int getID() {
        return id;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public IDCardType getType() {
        return type;
    }

    public MagnetStripe getMagnetStripe() {
        return magnetStripe;
    }
}
