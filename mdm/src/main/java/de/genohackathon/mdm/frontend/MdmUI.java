package de.genohackathon.mdm.frontend;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import de.genohackathon.mdm.frontend.views.DashboardView;
import de.genohackathon.mdm.frontend.views.EmployeesView;
import de.genohackathon.mdm.frontend.views.ProjectsView;

import javax.servlet.annotation.WebServlet;

/**
 * Created by chuff on 30.05.2017.
 */
public class MdmUI extends UI {

    Navigator navigator;

    View dashboardView = new DashboardView();
    View employeesView = new EmployeesView(this);
    View projectsView = new ProjectsView(this);

    public void goTo(String viewName) {
        navigator.navigateTo(viewName);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final HorizontalLayout buttons = new HorizontalLayout();
        Button homeButton = new Button("Start", e -> navigator.navigateTo(""));
        Button projectsButton = new Button("Projekte", e -> navigator.navigateTo("projects"));
        Button employeesButton = new Button("Mitarbeiter", e -> navigator.navigateTo("employees"));

        buttons.addComponents(homeButton);
        buttons.addComponents(projectsButton);
        buttons.addComponents(employeesButton);
        buttons.setMargin(false);
        buttons.setSizeUndefined();
        buttons.setWidth(100, Unit.PERCENTAGE);
        buttons.addStyleName("geno-navbar");

        final VerticalLayout main = new VerticalLayout();
        main.setMargin(false);
        final VerticalLayout verticalLayout = new VerticalLayout(buttons, main);
        verticalLayout.setMargin(false);

        navigator = new Navigator(this, main);

        navigator.addView("", dashboardView);
        navigator.addView("projects", projectsView);
        navigator.addView("employees", employeesView);

        setContent(verticalLayout);
        setNavigator(navigator);
        setTheme("mytheme");
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MdmUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
