package dhbw.scanner.utils;

import dhbw.scanner.Configuration;
import dhbw.scanner.authentication.IDCard;
import dhbw.scanner.authentication.ProfileType;

import java.time.LocalDateTime;
import java.util.Arrays;

public class AuthUtils {

    public static boolean checkAuthorization(IDCard idCard, ProfileType... profileTypes) {
        return idCard.isValid() &&
                !idCard.isLocked() &&
                Arrays.asList(profileTypes).contains(idCard.getMagnetStripe().getProfileType(Configuration.SECRET_KEY));
    }
}
