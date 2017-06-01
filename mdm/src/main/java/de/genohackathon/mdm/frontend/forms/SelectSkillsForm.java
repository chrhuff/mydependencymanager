package de.genohackathon.mdm.frontend.forms;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.genohackathon.mdm.model.Employee;
import de.genohackathon.mdm.model.Project;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by chuff on 30.05.2017.
 */
public class SelectSkillsForm extends Window {


    private VerticalLayout list = new VerticalLayout();
    private TextField value = new TextField();
    private Button add = new Button("Hinzufügen");

    private Button save = new Button("OK");
    private Set<String> skills = new HashSet<>();

    private Employee employee;

    public SelectSkillsForm(Employee employee) {
        this.employee = employee;
        if(employee.getSkills()!=null) {
            skills = employee.getSkills();
        }
        center();
        setPositionY(0);
        setResizable(false);
        setClosable(false);
        setModal(true);
        setVisible(true);
        setSizeUndefined();

        HorizontalLayout main = new HorizontalLayout();
        main.addComponents(new VerticalLayout(value,add,save), list);
        setContent(main);

        add.addClickListener(e -> {
            skills.add(value.getValue());
            updateList();
        });

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        save.addClickListener(e -> this.save());
        updateList();
    }

    private void updateList(){
        list.removeAllComponents();
        for(String str : skills){
            Button deleter = new Button("DEL");
            HorizontalLayout layout = new HorizontalLayout(deleter,new Label(str));
            deleter.addClickListener(e->{
                skills.remove(str);
                updateList();
            });
            list.addComponent(layout);
        }
    }

    private void save() {
        employee.setSkills(skills);
        setVisible(false);
    }
}
