package eapli.base.warehousemanagement.domain;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Row {
    @SerializedName("Id")
    private int id;

    @SerializedName("begin")
    private Begin begin;

    @SerializedName("end")
    private End end;

    @SerializedName("shelves")
    private Integer numberShelves;

    public Row() {
        //ORM and GSON only
    }

    private final List<Shelf> shelfList = new ArrayList<>();

    public List<Shelf> shelves() {
        return shelfList;
    }

    public void convertShelveNumberToPosition() {
        for (int i = 1; i <= numberShelves; i++) {
            Shelf shelf = new Shelf(i);
            shelfList.add(shelf);
        }
    }

    @Override
    public String toString() {
        return "\nRow:\n" +
                begin + "\n" +
                end + "\n" +
                "Shelves:\n" + shelfList + "\n";
    }

    public int getId() {
        return id;
    }

    public String rowPrinter () {
        return "ID - " + id + " Begin: " + "L Square = " + begin.lSquareBegin + " | " + "W Square = " + begin.wSquareBegin + " - " +
                "End: " + "L Square = " + end.lSquareEnd + " | " + "W Square = " + end.wSquareEnd + " - Number of Shelves = "
                + numberShelves;
    }
}
