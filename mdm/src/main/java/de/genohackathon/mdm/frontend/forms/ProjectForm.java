package de.genohackathon.mdm.frontend.forms;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.genohackathon.mdm.dao.DataService;
import de.genohackathon.mdm.frontend.MdmUI;
import de.genohackathon.mdm.model.Employee;
import de.genohackathon.mdm.model.Project;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Created by chuff on 30.05.2017.
 */
public class ProjectForm extends Window {

    private DataService<Project> projectService = new DataService<>(Project.class);
    private DataService<Employee> employeeDataService = new DataService<>(Employee.class);
    private TextField name = new TextField("Projektname");
    private NativeSelect<Employee> projectLeader = new NativeSelect<>("Projektleiter");
    private Button save = new Button("Sichern");
    private Button del = new Button("Löschen");
    private Button employees = new Button("Mitarbeiter auswählen");
    private DateField startDate = new DateField("Projektstart");
    private DateField endDate = new DateField("Projektende");
    private ComboBox<String> resultType = new ComboBox<>("Ergebnis-Typ", Arrays.asList("Prototyp","Release","Intern"));
    private Slider budget = new Slider("Budget", 0, 100);
    private Slider resources = new Slider("Resourcen", 0,100);
    private TextField businessCase = new TextField("Business Case");
    private ListSelect<String> values = new ListSelect<>("Genossenschaftliche Werte", Arrays.asList("Mitgliederverpflichtung","Partnerschaftlichkeit","Transparenz","Solidarität","Bodenständigkeit"));
    private ComboBox<String> channel = new ComboBox<>("Kanal",Arrays.asList("digital","offline","hybrid"));
    private ComboBox<String> customerType = new ComboBox<>("Kunde",Arrays.asList("Unternehmen","Privatkunden","Firmenkunden"));
    private TextField targetGroup = new TextField("Zielgruppe");
    private Button it = new Button("IT System");

    private Binder<Project> binder = new Binder<>(Project.class);
    private Project project = null;
    private final MdmUI ui;

    public ProjectForm(MdmUI ui) {
        this.ui = ui;
        setSizeUndefined();
        HorizontalLayout main = new HorizontalLayout();
        HorizontalLayout buttons = new HorizontalLayout(save,del);
        VerticalLayout all = new VerticalLayout(main,buttons);
        setContent(all);
        VerticalLayout left = new VerticalLayout(name, projectLeader, employees, startDate,endDate, resultType,budget,resources);
        VerticalLayout right = new VerticalLayout(businessCase,values,channel, customerType, targetGroup, it);
        main.addComponents(left,right);

        projectLeader.setItems(employeeDataService.findAll());

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder.bindInstanceFields(this);

        save.addClickListener(e -> this.save());
        del.addClickListener(e -> this.del());
    }

    private void del() {
        if(project.getId() != null) {
            this.projectService.delete(project);
            project = new Project();
        }
        ui.updateList();
        name.setValue("");
    }

    private void save() {
        this.projectService.updateOrCreate(project);
        ui.updateList();
        name.setValue("");
        setVisible(false);
    }

    public void setProject(Project project) {
        this.project = project;
        binder.setBean(project);

        setVisible(true);
        name.selectAll();
    }
}
