package dhbw.scanner.employees;

import dhbw.scanner.authentication.IDCardBuilder;
import dhbw.scanner.authentication.IDCardType;
import dhbw.scanner.authentication.ProfileType;

import java.time.LocalDate;

public class Technician extends Employee {

    public Technician(int id, String name, LocalDate birthDate, String idCardPin) {
        super(id, name, birthDate, IDCardBuilder.buildIDCard(IDCardType.EXTERNAL, ProfileType.T, idCardPin));
    }
}
