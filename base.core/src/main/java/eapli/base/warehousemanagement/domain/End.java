package eapli.base.warehousemanagement.domain;

import com.google.gson.annotations.SerializedName;

import javax.persistence.Embeddable;

@Embeddable
public class End {
    @SerializedName("lsquare")
    int lSquareEnd;
    @SerializedName("wsquare")
    int wSquareEnd;

    @Override
    public String toString() {
        return "End: " +
                "lSquareEnd = " + lSquareEnd +
                " | wSquareEnd = " + wSquareEnd;
    }
}
