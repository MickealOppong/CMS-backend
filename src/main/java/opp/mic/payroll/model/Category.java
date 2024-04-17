package opp.mic.payroll.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import opp.mic.payroll.util.LogEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends LogEntity {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Long quantity;
    private Long sale;

    @ManyToMany(mappedBy = "categoryList")
    private Set<Product> product = new HashSet<>();

    public Category(String name, String description, Long quantity) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    public Category(String name, String description, Long quantity, Long sale) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.sale = sale;
    }


}
