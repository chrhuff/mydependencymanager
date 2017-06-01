package de.genohackathon.mdm.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClassResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.hezamu.canvas.Canvas;

/**
 * Created by chuff on 31.05.2017.
 */
public class DashboardView extends VerticalLayout implements View {

    public DashboardView() {
       /* Canvas canvas = new Canvas();
        canvas.setWidth("750px");
        canvas.setHeight("750px");
        canvas.addMouseMoveListener(e->{
            double height = canvas.getHeight();
            double width = canvas.getWidth();
            canvas.setLineWidth(5);

            canvas.fillRect(width/2,height/2,10,10);
            canvas.fillText("My Project", width/2-20,height/2-10,40);
            canvas.moveTo(0,0);
            canvas.lineTo(width,0);
            canvas.lineTo(width,height);
            canvas.lineTo(0,height);
            canvas.lineTo(0,0);
            canvas.stroke();

        });

        addComponent(canvas);*/
        Image image = new Image("", new ClassResource("/dashboard.png"));
        image.setWidth("1250px");
        this.setSpacing(false);
        this.setMargin(new MarginInfo(false,true,false,true));
        addComponent(image);
        setSpacing(false);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }


}
