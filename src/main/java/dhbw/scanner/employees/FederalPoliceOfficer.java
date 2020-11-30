package dhbw.scanner.employees;


import dhbw.scanner.ProhibitedItem;
import dhbw.scanner.authentication.IDCardBuilder;
import dhbw.scanner.authentication.IDCardType;
import dhbw.scanner.authentication.ProfileType;
import dhbw.scanner.passengers.HandBaggage;
import dhbw.scanner.passengers.Passenger;
import dhbw.scanner.police.FederalPoliceOffice;
import dhbw.scanner.records.Record;
import dhbw.scanner.utils.Utils;

import java.time.LocalDate;

public class FederalPoliceOfficer extends Employee {

    private String grade;
    private FederalPoliceOffice office;
    private ProhibitedItem prohibitedItem;

    private boolean onDuty;

    public FederalPoliceOfficer(int id, String name, LocalDate birthDate, String grade, FederalPoliceOffice office, String idCardPin) {
        super(id, name, birthDate, IDCardBuilder.buildIDCard(IDCardType.EXTERNAL, ProfileType.O, idCardPin));
        this.grade = grade;

        office.register(this);
    }

    public void arrest(Passenger passenger) {
        passenger.setArrested(true);
    }

    public ProhibitedItem removeWeaponFromBaggage(HandBaggage handBaggage, Record record) {
        return Utils.removeItemFromHandBaggageAtPosition(ProhibitedItem.WEAPON, handBaggage, record.getResult().getPosition());
    }

    // Getter and setter
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setOnDuty(boolean onDuty) {
        this.onDuty = onDuty;
    }

    public boolean isOnDuty() {
        return onDuty;
    }

    public FederalPoliceOffice getFederalPoliceOffice() {
        return office;
    }

    public void setProhibitedItem(ProhibitedItem prohibitedItem) {
        this.prohibitedItem = prohibitedItem;
    }

    public ProhibitedItem getProhibitedItem() {
        return prohibitedItem;
    }
}
