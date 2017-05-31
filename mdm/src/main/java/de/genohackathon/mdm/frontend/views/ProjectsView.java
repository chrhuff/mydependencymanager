package de.genohackathon.mdm.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import de.genohackathon.mdm.dao.DataService;
import de.genohackathon.mdm.frontend.MdmUI;
import de.genohackathon.mdm.frontend.forms.ProjectForm;
import de.genohackathon.mdm.model.Project;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by chuff on 31.05.2017.
 */
public class ProjectsView extends VerticalLayout implements View {

    private DataService<Project> projectService = new DataService<>(Project.class);

    TextField search = new TextField("Suche");

    private ProjectForm projectForm;
    private Grid<Project> grid = new Grid<>(Project.class);
    private final MdmUI ui;
    private Window popover;

    public ProjectsView(final MdmUI ui) {
        this.ui = ui;
        this.projectForm = new ProjectForm(ui, this);
        
        search.addValueChangeListener(e -> updateList());

        Button addProjectBtn = new Button("Neues Projekt anlegen");
        addProjectBtn.addClickListener(e -> ui.goTo("projects/create"));

        grid.setColumns("name", "projectLeader", "startDate", "endDate", "resultType", "resources", "budget", "businessCase", "channel", "customerType", "targetGroup", "itSystems", "values");
        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(event -> {
            Project value = event.getValue();
            if (value == null) {
                projectForm.setVisible(false);
            } else {
                ui.goTo(String.format("projects/edit/%s", value.getId().toString()));
            }
        });
        
        updateList();

        addComponents(addProjectBtn, search, grid);
    }

    public void updateList() {
        List<Project> projects;
        if(StringUtils.isBlank(search.getValue())) {
            projects = projectService.findAll();
        }else{
            projects = projectService.findFullText(search.getValue());
        }
        grid.setItems(projects);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String[] params = StringUtils.split(event.getParameters(), "/");
        if (params.length >= 1) {
            String mode = params[0];
            if ("create".equals(mode)) {
                grid.asSingleSelect().clear();
                projectForm.setProject(new Project());
                createPopover(projectForm);
            } else if ("edit".equals(mode) && params.length == 2) {
                String id = params[1];
                Project value = projectService.findById(new ObjectId(id));
                if (value != null) {
                    projectForm.setProject(value);
                    projectForm.setVisible(true);
                    createPopover(projectForm);
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
        popover.setContent(component);
        popover.addCloseListener(e -> {
            ui.goTo("projects");
        });
        component.setVisible(true);
        ui.addWindow(popover);
    }

    public void closeWindow() {
        if (popover != null) {
            popover.close();
            ui.removeWindow(popover);
            popover = null;
        }
    }

    public void reloadEmployees() {
        this.projectForm.reloadEmployees();
    }
}
