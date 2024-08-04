package eapli.base.app.backoffice.console.presentation.products;

import eapli.base.product.domain.Product;
import eapli.framework.visitor.Visitor;

public class ProductPrinter implements Visitor<Product> {

    @Override
    public void visit(final Product visitee){
        System.out.printf("Name: %s, Code: %s, Short Description:%s, Brand: %s, Category: %s,Unit Price:%.2f EUR",visitee.getName(),visitee.getBarcodeString(),visitee.getShortDescriptionString(),visitee.getBrandString(),visitee.getCategoryString(),visitee.getUnitPriceDouble());

    }
}
