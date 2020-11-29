package dhbw.scanner.authentication;

public class IDCardBuilder {

    private static int id = 0;

    public static IDCard buildIDCard(IDCardType type, ProfileType profile, String pin) {
        return new IDCard(id++, type, new MagnetStripe(profile, pin));
    }
}
