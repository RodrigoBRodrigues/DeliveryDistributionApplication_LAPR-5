package eapli.base.agv.domain;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AGVTest {
    private final Integer AUTONOMY = 1000;
    private final Double CAPACITY = 100.0;
    private final Double WEIGHT = 100.0;
    private final Double VOLUME = 100.0;
    private final String SHORTDESCRIPTION = "AGV DESCRIBED";
    private final DockingPoint AGVDOCK = new DockingPoint("1111");

    @Test
    public void agv(){
        new AGV(AUTONOMY,CAPACITY,WEIGHT,VOLUME,SHORTDESCRIPTION,AGVDOCK);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed() {
        new AGV(null,null,null,null,null,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveAutonomy(){
        new AGV(null,CAPACITY,WEIGHT,VOLUME,SHORTDESCRIPTION,AGVDOCK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveCapacity(){
        new AGV(AUTONOMY,null,WEIGHT,VOLUME,SHORTDESCRIPTION,AGVDOCK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveWeight(){
        new AGV(AUTONOMY,CAPACITY,null,VOLUME,SHORTDESCRIPTION,AGVDOCK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveVolume(){
        new AGV(AUTONOMY,CAPACITY,WEIGHT,null,SHORTDESCRIPTION,AGVDOCK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveShortDescription(){
        new AGV(AUTONOMY,CAPACITY,WEIGHT,VOLUME,null,AGVDOCK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveAGVDock(){
        new AGV(AUTONOMY,CAPACITY,WEIGHT,VOLUME,SHORTDESCRIPTION,null);
    }

    @Test
    public void ensureAGVIsSameAsItsInstance(){
        AGV a = new AGV(AUTONOMY,CAPACITY,WEIGHT,VOLUME,SHORTDESCRIPTION,AGVDOCK);
        boolean expected = a.sameAs(a);
        assertTrue(expected);
    }


}