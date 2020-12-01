package dhbw.scanner.employees;

import dhbw.scanner.authentication.IDCard;

import java.time.LocalDate;

public class Employee {

    protected int id;
    protected String name;
    protected LocalDate birthDate;

    protected IDCard idCard;

    public Employee(int id, String name, LocalDate birthDate, IDCard idCard) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.idCard = idCard;
    }

    // Getter and setter
    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public IDCard getIDCard() {
        return idCard;
    }
}
