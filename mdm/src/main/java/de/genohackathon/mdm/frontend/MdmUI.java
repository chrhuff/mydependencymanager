package de.genohackathon.mdm.frontend;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import de.genohackathon.mdm.frontend.views.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.annotation.WebServlet;

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
        
        getPage().setTitle("ProjektPartner");

        final MenuBar menuBar = new MenuBar();
        menuBar.addItem("Start", c -> navigator.navigateTo(""));
        menuBar.addItem("Meine Projektwelt", c -> navigator.navigateTo("dashboard"));
        menuBar.addItem("Alle Projekte", c -> navigator.navigateTo("projects"));
        menuBar.addItem("Mitarbeiter", c -> navigator.navigateTo("employees"));
        menuBar.addItem("Abhängigkeiten", c -> navigator.navigateTo("dependencies"));

        Label title = new Label("ProjektPartner");
        title.setStyleName("mdm-title");
        
        final VerticalLayout menu = new VerticalLayout(title, menuBar);
        menu.setMargin(false);
        menu.setSizeUndefined();
        menu.setWidth(100, Unit.PERCENTAGE);
        menu.addStyleName("geno-navbar");

        final VerticalLayout main = new VerticalLayout();
        main.setMargin(false);
        main.setSpacing(false);
        final VerticalLayout verticalLayout = new VerticalLayout(menu, main);
        verticalLayout.setMargin(false);
        verticalLayout.setSpacing(false);

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
                    menuBar.setVisible(false);
                }else{
                    menuBar.setVisible(true);
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
