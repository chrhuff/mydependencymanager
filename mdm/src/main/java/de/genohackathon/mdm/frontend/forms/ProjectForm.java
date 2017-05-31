package de.genohackathon.mdm.frontend.forms;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import de.genohackathon.mdm.dao.DataService;
import de.genohackathon.mdm.frontend.MdmUI;
import de.genohackathon.mdm.model.Project;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by chuff on 30.05.2017.
 */
public class ProjectForm extends FormLayout {

    private DataService<Project> projectService = new DataService<>(Project.class);
    private TextField name = new TextField("Project name");
    private Button save = new Button("Save");
    private Button del = new Button("Delete");

    private Binder<Project> binder = new Binder<>(Project.class);
    private Project project = null;
    private final MdmUI ui;

    public ProjectForm(MdmUI ui) {
        this.ui = ui;
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save,del);
        addComponents(name, buttons);
        
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder.bindInstanceFields(this);

        save.addClickListener(e -> this.save());
        del.addClickListener(e -> this.del());
    }

    private void del() {
        if(StringUtils.isNotBlank(project.getId())) {
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
