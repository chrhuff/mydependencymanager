package de.genohackathon.mdm.frontend;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import de.genohackathon.mdm.frontend.views.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.swing.*;

/**
 * Created by chuff on 30.05.2017.
 */
public class MdmUI extends UI {

    Navigator navigator;

    DashboardView dashboardView = new DashboardView();
    HomeView homeView = new HomeView(this);
    EmployeesView employeesView = new EmployeesView(this);
    ProjectsView projectsView = new ProjectsView(this);
    DependenciesView dependenciesView = new DependenciesView(this);

    public void goTo(String viewName) {
        navigator.navigateTo(viewName);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final HorizontalLayout buttons = new HorizontalLayout();

        Button homeButton = new Button("Start", e -> navigator.navigateTo(""));
        Button dashboardButton = new Button("Dashboard", e -> navigator.navigateTo("dashboard"));
        Button projectsButton = new Button("Projekte", e -> navigator.navigateTo("projects"));
        Button employeesButton = new Button("Mitarbeiter", e -> navigator.navigateTo("employees"));
        Button dependenciesButton = new Button("Abhängigkeiten", e -> navigator.navigateTo("dependencies"));

        Label title = new Label("ProjektPartner");
        title.setStyleName("mdm-title");
        buttons.addComponents(title);
        buttons.addComponents(homeButton);
        buttons.addComponents(dashboardButton);
        buttons.addComponents(projectsButton);
        buttons.addComponents(employeesButton);
        buttons.addComponents(dependenciesButton);
        buttons.setMargin(false);
        buttons.setSizeUndefined();
        buttons.setWidth(100, Unit.PERCENTAGE);
        buttons.addStyleName("geno-navbar");

        final VerticalLayout main = new VerticalLayout();
        main.setMargin(false);
        final VerticalLayout verticalLayout = new VerticalLayout(buttons, main);
        verticalLayout.setMargin(false);

        navigator = new Navigator(this, main);

        navigator.addView("", homeView);
        navigator.addView("dashboard", dashboardView);
        navigator.addView("projects", projectsView);
        navigator.addView("employees", employeesView);
        navigator.addView("dependencies", dependenciesView);
        navigator.addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeEvent viewChangeEvent) {
                if(StringUtils.isBlank(viewChangeEvent.getViewName())){
                    buttons.setVisible(false);
                }else{
                    buttons.setVisible(true);
                }
                return true;
            }
        });

        setContent(verticalLayout);
        setNavigator(navigator);
        setTheme("mytheme");
    }

    public void reloadProjects() {
        dependenciesView.reloadProjects();
        projectsView.updateList();
    }

    public void reloadEmployees() {
        projectsView.reloadEmployees();
        employeesView.updateList();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MdmUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
