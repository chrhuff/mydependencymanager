package de.genohackathon.mdm.frontend.forms;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.genohackathon.mdm.dao.DataService;
import de.genohackathon.mdm.frontend.MdmUI;
import de.genohackathon.mdm.frontend.views.DependenciesView;
import de.genohackathon.mdm.model.Dependency;
import de.genohackathon.mdm.model.Project;

import java.util.List;

/**
 * Created by chuff on 30.05.2017.
 */
public class DependencyForm extends FormLayout {

    private DataService<Project> projectService = new DataService<>(Project.class);
    private DataService<Dependency> dependencyService = new DataService<>(Dependency.class);
    
    private TextField comment = new TextField("Kommentar");
    private NativeSelect<Project> sourceProject = new NativeSelect<>("Projekt");
    private NativeSelect<Project> targetProject = new NativeSelect<>("Abhängiges Projekt");
    private Button save = new Button("Sichern");
    private Button del = new Button("Löschen");

    private Binder<Dependency> binder = new Binder<>(Dependency.class);
    private Dependency dependency = null;
    private final MdmUI ui;
    private final DependenciesView view;

    public DependencyForm(MdmUI ui, DependenciesView view) {
        this.ui = ui;
        this.view = view;
        VerticalLayout main = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout(save, del);
        addComponents(main, buttons);
        main.addComponents(sourceProject, targetProject, comment);
        main.setMargin(true);

        reloadProjects();

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder.bindInstanceFields(this);

        save.addClickListener(e -> this.save());
        del.addClickListener(e -> this.del());
    }

    private void del() {
        if (dependency.getId() != null) {
            this.dependencyService.delete(dependency);
            dependency = new Dependency();
        }
        view.updateList();
        view.closeWindow();
        comment.setValue("");
    }

    private void save() {
        this.dependencyService.updateOrCreate(dependency);
        view.updateList();
        comment.setValue("");
        setVisible(false);
        view.closeWindow();
    }

    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
        binder.setBean(dependency);
        targetProject.setSelectedItem(dependency.getTargetProject());
        sourceProject.setSelectedItem(dependency.getSourceProject());

        setVisible(true);
        comment.selectAll();
        if (dependency.getId() == null) {
            del.setVisible(false);
        } else {
            del.setVisible(true);
        }
    }

    public void reloadProjects() {
        List<Project> projects = projectService.findAll();
        sourceProject.setItems(projects);
        targetProject.setItems(projects);
    }
}
