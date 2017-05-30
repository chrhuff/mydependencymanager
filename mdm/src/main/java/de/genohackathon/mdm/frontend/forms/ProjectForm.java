package de.genohackathon.mdm.frontend.forms;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
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

    public ProjectForm(MdmUI ui) {
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
        setVisible(false);
    }
}
