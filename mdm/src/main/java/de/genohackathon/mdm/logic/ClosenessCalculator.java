package de.genohackathon.mdm.logic;

import de.genohackathon.mdm.model.Project;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by chuff on 31.05.2017.
 */
public class ClosenessCalculator {

    private int getStaticConnections(Project a, Project b) {
        int closeness = 0;

        if (StringUtils.equals(a.getResultType(), b.getResultType())) {
            closeness++;
        }
        if (StringUtils.equals(a.getBusinessCase(), b.getBusinessCase())) {
            closeness++;
        }
        if (StringUtils.equals(a.getChannel(), b.getChannel())) {
            closeness++;
        }
        if (StringUtils.equals(a.getCustomerType(), b.getCustomerType())) {
            closeness++;
        }
        if (StringUtils.equals(a.getTargetGroup(), b.getTargetGroup())) {
            closeness++;
        }
        for (String value : a.getValues()) {
            if (b.getValues().contains(value)) {
                closeness++;
            }
        }
        for (String itSys : a.getItSystems()) {
            if (b.getItSystems().contains(itSys)) {
                closeness++;
            }
        }

        return closeness;
    }

    public List<Closeness> calculateCloseness(List<Project> projects) {
        List<Closeness> closenessList = new ArrayList<>();
        List<Project> innerProjects = Collections.unmodifiableList(projects);
        for (Project project : projects) {
            for (Project innerProject : innerProjects) {
                if (!project.equals(innerProject)) {
                    Closeness closeness = new Closeness(project, innerProject);
                    int connections = getStaticConnections(project, innerProject);
                    closeness.setConnections(connections);
                    closenessList.add(closeness);
                }
            }
        }
        return closenessList;
    }
}
