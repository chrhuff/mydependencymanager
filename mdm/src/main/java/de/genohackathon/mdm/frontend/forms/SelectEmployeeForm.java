package de.genohackathon.mdm.frontend.forms;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.genohackathon.mdm.dao.DataService;
import de.genohackathon.mdm.model.Employee;
import de.genohackathon.mdm.model.Project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chuff on 30.05.2017.
 */
public class SelectEmployeeForm extends Window {

    private DataService<Employee> employeeDataService = new DataService<>(Employee.class);

    private VerticalLayout list = new VerticalLayout();
    private NativeSelect<Employee> selector = new NativeSelect<>();
    private Button add = new Button("Hinzufügen");

    private Button save = new Button("OK");
    private Set<Employee> employees = new HashSet<>();

    private Project project;

    public SelectEmployeeForm(Project project) {
        selector.setItems(employeeDataService.findAll());
        this.project = project;
        if(project.getEmployees()!=null) {
            employees = project.getEmployees();
        }
        center();
        setPositionY(0);
        setResizable(false);
        setClosable(false);
        setModal(true);
        setVisible(true);
        setSizeUndefined();

        HorizontalLayout main = new HorizontalLayout();
        main.addComponents(new VerticalLayout(selector,add,save), list);
        setContent(main);

        add.addClickListener(e -> {
            selector.getSelectedItem().ifPresent(em->employees.add(em));
            updateList();
        });

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        save.addClickListener(e -> this.save());
        updateList();
    }

    private void updateList(){
        list.removeAllComponents();
        for(Employee employee : employees){
            Button deleter = new Button("DEL");
            HorizontalLayout layout = new HorizontalLayout(new Label(employee.toString()),deleter);
            deleter.addClickListener(e->{
                employees.remove(employee);
                updateList();
            });
            list.addComponent(layout);
        }
    }

    private void save() {
        project.setEmployees(employees);
        setVisible(false);
    }
}
