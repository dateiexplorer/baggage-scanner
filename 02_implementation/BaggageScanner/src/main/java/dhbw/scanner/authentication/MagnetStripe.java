package dhbw.scanner.authentication;

import dhbw.scanner.Configuration;
import dhbw.scanner.utils.AESUtils;

public class MagnetStripe {

    // A String format like ***[type]***[pin]***
    private String stripe;

    public MagnetStripe(ProfileType type, String pin) {
        stripe = AESUtils.encrypt("***" + type.name() + "***" + pin + "***", Configuration.SECRET_KEY);
    }

    public ProfileType getProfileType(String secretKey) {
        return ProfileType.valueOf(AESUtils.decrypt(stripe, secretKey).split("\\*+")[1]);
    }

    public String getPin(String secretKey) {
        return AESUtils.decrypt(stripe, secretKey).split("\\*+")[2];
    }

    @Override
    public String toString() {
        return stripe;
    }
}
