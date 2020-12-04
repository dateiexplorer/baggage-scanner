package dhbw.scanner.employees;

import dhbw.scanner.authentication.*;

import java.time.LocalDate;

public class Inspector extends Employee {

    protected boolean isSenior;

    public Inspector(int id, String name, LocalDate birthDate, boolean isSenior, String idCardPin) {
        super(id, name, birthDate, IDCardBuilder.buildIDCard(IDCardType.STAFF, ProfileType.I, idCardPin));
        this.isSenior = isSenior;
    }

    public boolean isSenior() {
        return isSenior;
    }

    public void setSenior(boolean isSenior) {
        this.isSenior = isSenior;
    }
}
