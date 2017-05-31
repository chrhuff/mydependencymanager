package de.genohackathon.mdm.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by chuff on 31.05.2017.
 */
public class DashboardView extends VerticalLayout implements View {

    public DashboardView() {
        Image image = new Image("Dashboard", new ClassResource("/dashboard.png"));
        addComponent(image);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }


}
