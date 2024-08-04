package eapli.base.warehousemanagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eapli.base.warehousemanagement.domain.Warehouse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonParser {

    public Warehouse readJson(String fileName) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        if(!fileName.contains(".json")) {
            fileName = fileName + ".json";
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader("warehouse/"+ fileName));
        return gson.fromJson(bufferedReader, Warehouse.class);
    }
}
