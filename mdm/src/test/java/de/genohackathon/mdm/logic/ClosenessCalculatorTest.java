package de.genohackathon.mdm.logic;

import de.genohackathon.mdm.model.Project;
import org.bson.types.ObjectId;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chuff on 31.05.2017.
 */
public class ClosenessCalculatorTest {

    private ClosenessCalculator calculator = new ClosenessCalculator();

    //@Test
    public void calculateCloseness() throws Exception {
        Project projectA = new Project();
        projectA.setName("A");
        projectA.setId(new ObjectId("592eb986f878b31e780f2dde"));
        projectA.setChannel("Filiale");

        Project projectB = new Project();
        projectB.setName("B");
        projectB.setId(new ObjectId("592eb986f878b31e780f2ddf"));
        projectB.setChannel("Filiale");

        List<Closeness> closenesses = calculator.calculateCloseness(Arrays.asList(projectA, projectB));
        Assert.assertEquals(2, closenesses.size());
    }

}