package dhbw.scanner.employees;

import dhbw.scanner.Configuration;
import dhbw.scanner.authentication.IDCardBuilder;
import dhbw.scanner.authentication.IDCardType;
import dhbw.scanner.authentication.ProfileType;
import dhbw.scanner.system.Supervision;

import java.time.LocalDate;

public class Supervisor extends Employee {

    protected boolean isSenior;
    protected boolean isExecutive;

    protected Supervision supervision;

    public Supervisor(int id, String name, LocalDate birthDate, boolean isSenior, boolean isExecutive, String idCardPin) {
        super(id, name, birthDate, IDCardBuilder.buildIDCard(IDCardType.STAFF, ProfileType.S, idCardPin));
        this.isSenior = isSenior;
        this.isExecutive = isExecutive;
    }

    public void startOrStopBaggageScanner() {
        if (supervision == null) {
            System.out.println("Supervisor " + name + " is not at work!");
            return;
        }

        supervision.getButtonStartOrStop().push(this);
    }

    public void unlockBaggageScanner() {
        if (supervision == null) {
            System.out.println("Supervisor " + name + " is not at work!");
            return;
        }

        supervision.getBaggageScanner().unlock(this, idCard.getMagnetStripe().getPin(Configuration.SECRET_KEY));
    }

    // Getter and setter
    public boolean isSenior() {
        return isSenior;
    }

    public void setSenior(boolean isSenior) {
        this.isSenior = isSenior;
    }

    public boolean isExecutive() {
        return isExecutive;
    }

    public void setExecutive(boolean isExecutive) {
        this.isExecutive = isExecutive;
    }

    public void setSupervision(Supervision supervision) {
        this.supervision = supervision;
    }

    public Supervision getSupervision() {
        return supervision;
    }
}
