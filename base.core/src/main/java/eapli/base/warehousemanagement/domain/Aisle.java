package eapli.base.warehousemanagement.domain;

import com.google.gson.annotations.SerializedName;
import eapli.base.clientusermanagement.domain.Customer;

import javax.persistence.*;
import java.util.List;


public class Aisle {

    @SerializedName("Id")
    private int id;

    @SerializedName("begin")
    private Begin begin;

    @SerializedName("end")
    private End end;

    @SerializedName("depth")
    private Depth depth;

    @SerializedName("accessibility")
    private String accessibility;

    @SerializedName("rows")
    private List<Row> row;

    public List<Row> rows() {
        return row;
    }

    @Override
    public String toString() {
        return "\nAisle:\n" +
                "ID = "+ id + "\n" +
                begin + "\n" +
                end + "\n" +
                depth + "\n" +
                "accessibility = " + accessibility + "\n" +
                "row = " + row + "\n";
    }

    public String aislePrinter () {
        return "ID - " + id +" Begin: " + "L Square = " + begin.lSquareBegin + " | " + "W Square = " + begin.wSquareBegin + " - " +
                "End: " + "L Square = " + end.lSquareEnd + " | " + "W Square = " + end.wSquareEnd + " - " +
                "Depth: " + "L Square = " + depth.lSquareDepth + " | " + "W Square = " + depth.wSquareDepth + " - " +
                "Acessibility = " + accessibility;
    }

    public int getId() {
        return id;
    }
}
