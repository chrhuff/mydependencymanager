package de.genohackathon.mdm.frontend;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;
import de.genohackathon.mdm.dao.DataService;
import de.genohackathon.mdm.frontend.forms.ProjectForm;
import de.genohackathon.mdm.model.Project;

import javax.servlet.annotation.WebServlet;
import java.util.List;

/**
 * Created by chuff on 30.05.2017.
 */
public class MdmUI extends UI {

    private DataService<Project> projectService = new DataService<Project>(Project.class);

    private ProjectForm form = new ProjectForm(this);
    private Grid<Project> grid = new Grid<>(Project.class);

    private Window popup = form;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        popup.center();
        popup.setPositionY(0);
        popup.setResizable(false);
        popup.setClosable(false);
        popup.setModal(true);
        popup.setVisible(false);

        Button addProjectBtn = new Button("Add new customer");
        addProjectBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setProject(new Project());
            popup.setVisible(true);
        });

        grid.setColumns("name", "projectLeader");

        final VerticalLayout layout = new VerticalLayout();
        addWindow(popup);
        HorizontalLayout main = new HorizontalLayout(grid);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);

        layout.addComponents(addProjectBtn, main);

        setContent(layout);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                popup.setVisible(false);
            } else {
                form.setProject(event.getValue());
                popup.setVisible(true);
            }
        });
        
        updateList();
    }

    public void updateList() {
        List<Project> projects = projectService.findAll();
        grid.setItems(projects);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MdmUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
