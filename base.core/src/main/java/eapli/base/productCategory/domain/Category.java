package eapli.base.productCategory.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.Description;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
@Entity
@XmlRootElement
@Table(name = "CATEGORY")
public class Category implements AggregateRoot<String> {
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Long id;
    @Column(nullable = false,unique = true)
    private AlphaNumericCode alphanumericCode;

    @Column(nullable = false,unique = true)
    private Description description;

    @Column(nullable = false,unique = true)
    private Designation name;

    @XmlElement
    @JsonProperty
    private boolean active;

    protected Category(){

    }

    public Category(AlphaNumericCode alphanumericCode,Description description,Designation name){
        Preconditions.noneNull(alphanumericCode,description,name);
        Preconditions.nonEmpty(alphanumericCode.toString());
        Preconditions.nonEmpty(description.toString());
        Preconditions.nonEmpty(name.toString());
        this.alphanumericCode=alphanumericCode;
        this.description=description;
        this.name=name;
        this.active=true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(alphanumericCode, category.alphanumericCode) && Objects.equals(description, category.description) && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alphanumericCode, description, name);
    }

    public boolean sameAs(final Object other) {
        final Category category = (Category) other;
        return this.equals(category);
    }
    public  boolean isActive(){
        return this.active;
    }
    public String identity(){
        return this.name.toString();
    }

    @Override
    public String toString(){return this.name.toString();}
}
