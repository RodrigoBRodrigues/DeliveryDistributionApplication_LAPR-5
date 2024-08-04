package eapli.base.warehousemanagement.domain;

import com.google.gson.annotations.SerializedName;

import javax.persistence.Embeddable;

@Embeddable
public class Depth {
    @SerializedName("lsquare")
    int lSquareDepth;
    @SerializedName("wsquare")
    int wSquareDepth;

    @Override
    public String toString() {
        return "Depth: " +
                "lSquareDepth = " + lSquareDepth +
                " | wSquareDepth = " + wSquareDepth;
    }
}
