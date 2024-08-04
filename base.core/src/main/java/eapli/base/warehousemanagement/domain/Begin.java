package eapli.base.warehousemanagement.domain;

import com.google.gson.annotations.SerializedName;

import javax.persistence.Embeddable;

@Embeddable
public class Begin {
    @SerializedName("lsquare")
    int lSquareBegin;
    @SerializedName("wsquare")
    int wSquareBegin;

    @Override
    public String toString() {
        return "Begin: " +
                "lSquareBegin = " + lSquareBegin +
                " | wSquareBegin = " + wSquareBegin;
    }
}
