package eapli.base.product.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class Price implements ValueObject,Comparable<Price> {

    private Double priceWoTaxes;

    private Double priceWiTaxes;

    public Price(Double priceWoTaxes, Double priceWiTaxes) {
        this.priceWoTaxes = priceWoTaxes;
        this.priceWiTaxes = priceWiTaxes;
    }

    public Price() {

    }

    public void addPrices(Price price){
        this.priceWoTaxes += price.priceWithoutTaxes();
        this.priceWiTaxes += price.priceWithTaxes();
    }

    public void clear(){
        this.priceWoTaxes = 0.00;
        this.priceWiTaxes = 0.00;
    }

    public Double priceWithTaxes(){
        return this.priceWiTaxes;
    }

    public Double priceWithoutTaxes(){
        return this.priceWoTaxes;
    }

    @Override
    public int compareTo(final Price o1){
        return this.priceWoTaxes.compareTo(o1.priceWithoutTaxes());
    }

}
