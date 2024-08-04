package eapli.base.warehousemanagement.domain;

import com.google.gson.annotations.SerializedName;
import eapli.base.agv.domain.AGV;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Warehouse implements AggregateRoot<Long>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String jsonPath;

    @Lob
    @Column
    private String warehouse;

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getWarehouse() {
        return warehouse;
    }

    @Transient
    @SerializedName("Warehouse")
    private String name;

    @Transient
    @SerializedName("Length")
    private Double length;

    @Transient
    @SerializedName("Width")
    private Double width;

    @Transient
    @SerializedName("Square")
    private Double square;

    @Transient
    @SerializedName("Unit")
    private String unit;

    @Transient
    @SerializedName("Aisles")
    private List<Aisle> aisle;
    @Transient
    @SerializedName("WarehouseEmployees")
    private List<SystemUser> lWarehouseEmployee;
    @Transient
    @SerializedName("AGVs")
    private List<AGV> agvs;
    @Transient
    @SerializedName("AGVDocks")
    private List<AGVDocks> agvDocks;

    public Long getId() {
        return id;
    }


    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public Long identity() {
        return null;
    }

    public List<Aisle> aisles() {
        return aisle;
    }

    public List<AGVDocks> agvDocks() {
        return agvDocks;
    }

    public List<SystemUser> getlWarehouseEmployee() {
        return this.lWarehouseEmployee;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    @Override
    public String toString() {
        return "Warehouse:\n" +
                "jsonpath=" + jsonPath + "\n" +
                "name='" + name + "\n" +
                "length=" + length + "\n" +
                "width=" + width + "\n" +
                "square=" + square + "\n" +
                "unit=" + unit + "\n" +
                "-----------------------\n" +
                "Aisles:" + aisle + "\n" +
                "-----------------------\n" +
                "AGV Dock:" + agvDocks + "\n" +
                "-----------------------\n";

    }
}
