package eapli.base.warehousemanagement.domain;

import com.google.gson.annotations.SerializedName;

public class AGVDocks {

    @SerializedName("Id")
    private String id;

    @SerializedName("begin")
    private Begin begin;

    @SerializedName("end")
    private End end;

    @SerializedName("depth")
    private Depth depth;

    @SerializedName("accessibility")
    private String accessibility;

    public String getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "\nAGVDock: \n" +
                "----------------------------" + "\n" +
                "id: " + id + "\n" +
                begin + "\n" +
                end + "\n" +
                depth + "\n" +
                "accessibility=" + accessibility + "\n" +
                "----------------------------" + "\n" ;
    }
}
