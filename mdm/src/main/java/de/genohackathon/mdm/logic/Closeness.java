package de.genohackathon.mdm.logic;

import de.genohackathon.mdm.model.Project;

/**
 * Created by chuff on 31.05.2017.
 */
public class Closeness {
    
    private final Project projectA;
    private final Project projectB;
    private int connections;

    public Closeness(Project projectA, Project projectB) {
        this.projectA = projectA;
        this.projectB = projectB;
    }

    public int getConnections() {
        return connections;
    }

    public void setConnections(int connections) {
        this.connections = connections;
    }
}
