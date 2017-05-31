package de.genohackathon.mdm.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import de.genohackathon.mdm.dao.DataService;
import de.genohackathon.mdm.frontend.MdmUI;
import de.genohackathon.mdm.frontend.forms.DependencyForm;
import de.genohackathon.mdm.model.Dependency;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by chuff on 31.05.2017.
 */
public class DependenciesView extends VerticalLayout implements View {

    private DataService<Dependency> dependencyDataService = new DataService<>(Dependency.class);

    private DependencyForm dependencyForm;
    private Grid<Dependency> grid = new Grid<>(Dependency.class);
    private final MdmUI ui;
    private Window popover;

    public DependenciesView(final MdmUI ui) {
        this.dependencyForm = new DependencyForm(ui, this);
        this.ui = ui;

        Button addDependencyButton = new Button("Neue Abhängigkeit anlegen");
        addDependencyButton.addClickListener(e -> ui.goTo("dependencies/create"));

        grid.setColumns("sourceProject", "targetProject", "comment");
        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(event -> {
            Dependency value = event.getValue();
            if (value == null) {
                dependencyForm.setVisible(false);
            } else {
                ui.goTo(String.format("dependencies/edit/%s", value.getId().toString()));
            }
        });

        updateList();

        addComponents(addDependencyButton, grid);
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String[] params = StringUtils.split(event.getParameters(), "/");
        if (params.length >= 1) {
            String mode = params[0];
            if ("create".equals(mode)) {
                grid.asSingleSelect().clear();
                dependencyForm.setDependency(new Dependency());
                createPopover(dependencyForm);
            } else if ("edit".equals(mode) && params.length == 2) {
                String id = params[1];
                Dependency value = dependencyDataService.findById(new ObjectId(id));
                if (value != null) {
                    dependencyForm.setDependency(value);
                    dependencyForm.setVisible(true);
                    createPopover(dependencyForm);
                }
            }
        } else {
            updateList();
        }
    }

    public void updateList() {
        List<Dependency> dependencies = dependencyDataService.findAll();
        grid.setItems(dependencies);
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
        popover.setWidth(40, Unit.PERCENTAGE);
        popover.setContent(component);
        popover.addCloseListener(e -> {
            ui.goTo("dependencies");
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

    public void reloadProjects() {
        this.dependencyForm.reloadProjects();
    }
}
