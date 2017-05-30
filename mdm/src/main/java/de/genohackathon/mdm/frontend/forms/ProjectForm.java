package de.genohackathon.mdm.frontend.forms;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.genohackathon.mdm.dao.ProjectService;
import de.genohackathon.mdm.frontend.MdmUI;
import de.genohackathon.mdm.model.Project;

/**
 * Created by chuff on 30.05.2017.
 */
public class ProjectForm extends FormLayout {

    private ProjectService projectService = new ProjectService();
    private TextField name = new TextField("Project name");
    private Button save = new Button("Save");

    private Binder<Project> binder = new Binder<>(Project.class);
    private Project project;
    private final MdmUI ui;

    public ProjectForm(MdmUI ui) {
        this.ui = ui;
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save);
        addComponents(name, buttons);
        
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder.bindInstanceFields(this);

        save.addClickListener(e -> this.save());
    }

    private void save() {
        this.projectService.create(name.getValue());
        ui.updateList();
    }

    public void setProject(Project project) {
        this.project = project;
        binder.setBean(project);

        setVisible(true);
        name.selectAll();
    }
}
