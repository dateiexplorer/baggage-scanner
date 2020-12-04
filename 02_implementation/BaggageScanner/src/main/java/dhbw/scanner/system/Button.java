package dhbw.scanner.system;

import dhbw.scanner.employees.Employee;

public class Button {

    private IButton button;

    public Button(IButton button) {
        this.button = button;
    }

    public void push(Employee employee) {
        button.onButtonPush(employee);
    }
}
