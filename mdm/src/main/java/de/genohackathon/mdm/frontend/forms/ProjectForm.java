package de.genohackathon.mdm.frontend.forms;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.genohackathon.mdm.dao.DataService;
import de.genohackathon.mdm.frontend.MdmUI;
import de.genohackathon.mdm.model.Employee;
import de.genohackathon.mdm.model.Project;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by chuff on 30.05.2017.
 */
public class ProjectForm extends FormLayout {

    private DataService<Project> projectService = new DataService<>(Project.class);
    private DataService<Employee> employeeDataService = new DataService<>(Employee.class);
    private TextField name = new TextField("Projektname");
    private NativeSelect<Employee> projectLeader = new NativeSelect<>("Projektleiter");
    private Button save = new Button("Sichern");
    private Button del = new Button("Löschen");

    private Binder<Project> binder = new Binder<>(Project.class);
    private Project project = null;
    private final MdmUI ui;

    public ProjectForm(MdmUI ui) {
        this.ui = ui;
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save,del);
        addComponents(name, projectLeader, buttons);
        
        projectLeader.setItems(employeeDataService.findAll());
        
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder.bindInstanceFields(this);

        save.addClickListener(e -> this.save());
        del.addClickListener(e -> this.del());
    }

    private void del() {
        if(project.getId() != null) {
            this.projectService.delete(project);
            project = new Project();
        }
        ui.updateList();
        name.setValue("");
    }

    private void save() {
        //project.setName(name.getValue());
        this.projectService.updateOrCreate(project);
        ui.updateList();
        name.setValue("");
    }

    public void setProject(Project project) {
        this.project = project;
        binder.setBean(project);

        setVisible(true);
        name.selectAll();
    }
}
