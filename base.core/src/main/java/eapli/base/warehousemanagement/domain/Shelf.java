package eapli.base.warehousemanagement.domain;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Shelf {
    private Integer position;
    private int id;
    private Row row;
    private Product product;
    private final ProductRepository repo = PersistenceContext.repositories().products();


    private List<Bin> bin = new ArrayList<>();

    public Shelf(int position) {
        this.position = position;
        id = position;
    }

    protected Shelf() {
        //ORM only
    }

    @Override
    public String toString() {
        return "\nShelve: " +
                "position=" + position + " " +
                "bin=" + bin + " ";
    }



    public Integer getPosition() {
        return position;
    }

    public String shelfPrint(int aisleId, int rowId) {
        String productString;
        Iterator<Product> it = repo.findProductsWithShelveNumber(aisleId, rowId, id).iterator();
        if (it.hasNext() && it.next().getName().toString() != null) {
            productString = "Product: " + repo.findProductsWithShelveNumber(aisleId, rowId, id)
                    .iterator()
                    .next()
                    .getName()
                    .toString();
        } else {
            return "Position: " + position;
        }
        return "Position: " + position + "   " +
                productString;
    }
}
