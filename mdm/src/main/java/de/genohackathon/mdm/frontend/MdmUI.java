package de.genohackathon.mdm.frontend;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import de.genohackathon.mdm.frontend.forms.ProjectForm;

import javax.servlet.annotation.WebServlet;

/**
 * Created by chuff on 30.05.2017.
 */
public class MdmUI extends UI {

    private ProjectForm form = new ProjectForm(this);
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        HorizontalLayout main = new HorizontalLayout(form);
        main.setSizeFull();

        layout.addComponents(main);

        setContent(layout);

        form.setVisible(true);
        
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MdmUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
