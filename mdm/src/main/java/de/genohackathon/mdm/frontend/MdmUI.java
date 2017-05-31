package de.genohackathon.mdm.frontend;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import de.genohackathon.mdm.dao.DataService;
import de.genohackathon.mdm.frontend.forms.EmployeeForm;
import de.genohackathon.mdm.frontend.forms.ProjectForm;
import de.genohackathon.mdm.model.Employee;
import de.genohackathon.mdm.model.Project;

import javax.servlet.annotation.WebServlet;
import java.util.List;

/**
 * Created by chuff on 30.05.2017.
 */
public class MdmUI extends UI {

    private DataService<Project> projectService = new DataService<>(Project.class);
    private DataService<Employee> employeeDataService = new DataService<>(Employee.class);

    private ProjectForm projectForm = new ProjectForm(this);
    private EmployeeForm employeeForm = new EmployeeForm(this);
    private Grid<Project> grid = new Grid<>(Project.class);
    private Grid<Employee> employees = new Grid<>(Employee.class);

    Window popover;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Button addProjectBtn = new Button("Add new customer");
        addProjectBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            projectForm.setProject(new Project());
            createPopover(projectForm);
        });

        Button addEmployeeBtn = new Button("Mitarbeiter hinzufügen");
        addEmployeeBtn.addClickListener(e -> {
            employees.asSingleSelect().clear();
            employeeForm.setEmployee(new Employee());
            createPopover(employeeForm);
        });

        grid.setColumns("name", "projectLeader");
        employees.setColumns("firstName", "lastName");

        final VerticalLayout layout = new VerticalLayout();

        final VerticalLayout grids = new VerticalLayout(grid, employees);
        HorizontalLayout main = new HorizontalLayout(grids);
        
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grids, 1);

        final HorizontalLayout buttonLayout = new HorizontalLayout(addProjectBtn, addEmployeeBtn);
        buttonLayout.setSizeFull();

        layout.addComponent(buttonLayout);
        layout.addComponents(main);
        layout.addComponents(addProjectBtn, main);

        setContent(layout);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                projectForm.setVisible(false);
            } else {
                projectForm.setProject(event.getValue());
                projectForm.setVisible(true);
                createPopover(projectForm);
        }
        });        
        
        employees.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                employeeForm.setVisible(false);
            } else {
                employeeForm.setEmployee(event.getValue());
                createPopover(employeeForm);
            }
        });
        
        updateList();
        updateEmployees();
    }

    private void createPopover(Component component) {
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
        addWindow(popover);
    }
    
    public void closeWindow() {
        if (popover != null) {
            popover.close();
            removeWindow(popover);
            popover = null;
        }
    }

    public void updateList() {
        List<Project> projects = projectService.findAll();
        grid.setItems(projects);
    }    
    
    public void updateEmployees() {
        List<Employee> employees = employeeDataService.findAll();
        this.employees.setItems(employees);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MdmUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
