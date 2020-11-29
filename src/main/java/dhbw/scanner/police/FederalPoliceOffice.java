package dhbw.scanner.police;

import dhbw.scanner.employees.FederalPoliceOfficer;

import java.util.ArrayList;

public class FederalPoliceOffice {

    private ArrayList<FederalPoliceOfficer> officers;
    private ArrayList<Robot> robots;

    public FederalPoliceOffice() {
        officers = new ArrayList<>();
        robots = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            robots.add(new Robot());
        }
    }

    public void register(FederalPoliceOfficer officer) {
        officers.add(officer);
    }

    public FederalPoliceOfficer getFederalPoliceOfficer() {
        for (FederalPoliceOfficer officer : officers) {
            if (!officer.isOnDuty()) {
                return officer;
            }
        }

        return null;
    }

    public Robot getRandomRobot(FederalPoliceOfficer owner) {
        Robot robot = robots.get((int) (Math.random() * robots.size()));
        robot.setOwner(owner);
        return robot;
    }
}
