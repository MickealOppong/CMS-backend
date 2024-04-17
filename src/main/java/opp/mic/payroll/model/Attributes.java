package opp.mic.payroll.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import opp.mic.payroll.util.LogEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attributes extends LogEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER)
    private List<ProductSKU> productSKU = new ArrayList<>();

    @ManyToMany(mappedBy = "attributes")
    private Set<Product> product = new HashSet<>();


    public Attributes(String name) {
        this.name = name;
    }

    public Attributes(String name, List<ProductSKU> productSKU) {
        this.name = name;
        this.productSKU = productSKU;
    }


}
