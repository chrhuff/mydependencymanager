package de.genohackathon.mdm.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import de.genohackathon.mdm.frontend.MdmUI;
import org.vaadin.hezamu.canvas.Canvas;

/**
 * Created by chuff on 31.05.2017.
 */
public class HomeView extends VerticalLayout implements View {

    public HomeView(MdmUI ui) {
        Image image = new Image("",new ClassResource("/landingpage.png"));
        addComponent(image);
        image.addClickListener(e->{
            ui.goTo("projects");
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }


}
