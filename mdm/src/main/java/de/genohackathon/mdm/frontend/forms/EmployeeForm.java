package de.genohackathon.mdm.frontend.forms;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.genohackathon.mdm.dao.DataService;
import de.genohackathon.mdm.frontend.MdmUI;
import de.genohackathon.mdm.frontend.views.EmployeesView;
import de.genohackathon.mdm.model.Employee;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * Created by chuff on 30.05.2017.
 */
public class EmployeeForm extends FormLayout {
    
    private DataService<Employee> employeeDataService = new DataService<>(Employee.class);
    private TextField firstName = new TextField("Vorname");
    private TextField lastName = new TextField("Nachname");
    private TextField organisation = new TextField("Organisation");
    private ListSelect<String> skills = new ListSelect<>("Fähigkeiten", Arrays.asList(
            "Projektmanagment",
            "Digital Leader",
            "Scrum",
            "Systemisches Coaching",
            "Immobilienfinanzierung",
            "Backendentwickler",
            "Big Data",
            "Design Thinking",
            "Mentalcoach",
            "Bankorganisator",
            "Statistik",
            "MongoDB",
            "Azure",
            "Java",
            ".NET"

    ));
    private Upload avatar = new Upload("Avatar", (filename, mimetype) -> {
        try {
            return new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            return null;
        }
    });
    private Button save = new Button("Sichern");
    private Button del = new Button("Löschen");

    private Binder<Employee> binder = new Binder<>(Employee.class);
    private Employee employee = new Employee();
    private final EmployeesView view;
    private final MdmUI ui;

    public EmployeeForm(MdmUI ui, EmployeesView view) {
        this.ui = ui;
        this.view = view;
        setSpacing(true);
        setMargin(true);
        HorizontalLayout buttons = new HorizontalLayout(save,del);
        addComponents(firstName, lastName, organisation, skills, avatar, buttons);
        
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder.bindInstanceFields(this);
        binder.setBean(employee);

        save.addClickListener(e -> this.save());
        del.addClickListener(e -> this.del());
    }

    private void del() {
        if(employee.getId() != null) {
            this.employeeDataService.delete(employee);
            employee = new Employee();
        }
        view.updateList();
        firstName.setValue("");
        view.closeWindow();
    }

    private void save() {
        this.employeeDataService.updateOrCreate(employee);
        setVisible(false);
        view.updateList();
        view.closeWindow();
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        binder.setBean(employee);

        setVisible(true);
        firstName.selectAll();
        if (employee.getId() == null) {
            del.setVisible(false);
        } else {
            del.setVisible(true);
        }
    }
}
