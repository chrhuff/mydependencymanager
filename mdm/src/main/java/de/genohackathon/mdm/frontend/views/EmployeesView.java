package de.genohackathon.mdm.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import de.genohackathon.mdm.dao.DataService;
import de.genohackathon.mdm.frontend.MdmUI;
import de.genohackathon.mdm.frontend.forms.EmployeeForm;
import de.genohackathon.mdm.model.Employee;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by chuff on 31.05.2017.
 */
public class EmployeesView extends VerticalLayout implements View {

    private Window popover;

    private DataService<Employee> employeeDataService = new DataService<>(Employee.class);

    private EmployeeForm employeeForm;

    private Grid<Employee> employees = new Grid<>(Employee.class);
    
    private final MdmUI ui;
    
    public EmployeesView(final MdmUI ui) {
        this.ui = ui;
        this.employeeForm = new EmployeeForm(ui, this);

        Button addEmployeeBtn = new Button("Mitarbeiter hinzufügen");
        addEmployeeBtn.addClickListener(e -> {
            ui.goTo("employees/create");
        });

        employees.setSizeFull();

        employees.asSingleSelect().addValueChangeListener(event -> {
            Employee value = event.getValue();
            if (value != null) {
                ui.goTo(String.format("employees/edit/%s", value.getId().toString()));
            }
        });
        
        updateList();
        
        addComponents(addEmployeeBtn, employees);
    }

    public void updateList() {
        List<Employee> employees = employeeDataService.findAll();
        this.employees.setItems(employees);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        employees.setColumns("firstName", "lastName", "organisation", "skills", "avatar");
        String[] params = StringUtils.split(event.getParameters(), "/");
        if (params.length >= 1) {
            String mode = params[0];
            if ("create".equals(mode)) {
                employees.asSingleSelect().clear();
                employeeForm.setEmployee(new Employee());
                createPopover(employeeForm);
            } else if ("edit".equals(mode) && params.length == 2) {
                String id = params[1];
                Employee value = employeeDataService.findById(new ObjectId(id));
                if (value != null) {
                    employeeForm.setEmployee(value);
                    employeeForm.setVisible(true);
                    createPopover(employeeForm);
                }
            }
        }
    }

    public void createPopover(Component component) {
        popover = new Window();
        popover.center();
        popover.setPositionY(0);
        popover.setResizable(false);
        popover.setClosable(true);
        popover.setModal(true);
        popover.setVisible(true);
        popover.setSizeUndefined();
        popover.setContent(component);
        component.setVisible(true);
        popover.addCloseListener(e -> {
            ui.goTo("employees");
        });
        ui.addWindow(popover);
    }

    public void closeWindow() {
        if (popover != null) {
            popover.close();
            ui.removeWindow(popover);
            popover = null;
        }
    }
}
