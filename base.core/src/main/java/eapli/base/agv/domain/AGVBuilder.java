package eapli.base.agv.domain;

import eapli.base.warehousemanagement.domain.AGVDocks;
import eapli.base.warehousemanagement.domain.Warehouse;
import eapli.framework.domain.model.DomainFactory;

public class AGVBuilder implements DomainFactory<AGV> {

    private Integer autonomy;
    private Double capacity;
    private String shortDescription;
    private Double weight;
    private Double volume;

    private AGVState agvState = null;

    private DockingPoint agvDocks;
    //private AGVModel agvModel = null;

    public AGVBuilder(){

    }

    public AGVBuilder createAGV(Integer autonomy, Double capacity, Double weight, Double volume, String shortDescription
                                , DockingPoint dockingPoint){
        this.shortDescription = shortDescription;
        this.capacity = capacity;
        this.autonomy = autonomy;
        this.weight = weight;
        this.volume = volume;
        this.agvDocks = dockingPoint;
        return this;
    }

    public AGVBuilder withAGVState(AGVState agvState){
        this.agvState = agvState;
        return this;
    }

    public AGVBuilder withAGVDock(DockingPoint agvDock){
        this.agvDocks = agvDock;
        return this;
    }

    /*public AGVBuilder withAGVModel(AGVModel agvModel){
        this.agvModel = agvModel;
        return this;
    }
    */

    @Override
    public AGV build() {
        return new AGV(this.autonomy,this.capacity,this.weight,this.volume,this.shortDescription, this.agvDocks);
    }

    @Override
    public String toString(){
        return "AGV: " + "\n" +
                "----------------------------" + "\n" +
                "Autonomy (minutes):         " + this.autonomy +"\n" +
                "Capacity (kg):              " + String.format("%.2f", this.capacity) + "\n" +
                "Weight (kg):                " + String.format("%.2f", this.weight) + "\n" +
                "Volume (dm^3):              " + String.format("%.2f", this.volume) + "\n" +
                "Short Description:          " + shortDescription + "\n" +
                "Base Location (AGV Dock):   " + this.agvDocks + "\n" +
                "----------------------------" + "\n";
    }
}
