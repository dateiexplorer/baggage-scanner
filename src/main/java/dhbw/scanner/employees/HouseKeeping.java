package dhbw.scanner.employees;

import dhbw.scanner.authentication.IDCardBuilder;
import dhbw.scanner.authentication.IDCardType;
import dhbw.scanner.authentication.ProfileType;

import java.time.LocalDate;

public class HouseKeeping extends Employee {

    public HouseKeeping(int id, String name, LocalDate birthDate, String idCardPin) {
        super(id, name, birthDate, IDCardBuilder.buildIDCard(IDCardType.STAFF, ProfileType.K, idCardPin));
    }
}
