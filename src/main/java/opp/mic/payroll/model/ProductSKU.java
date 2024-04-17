package opp.mic.payroll.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import opp.mic.payroll.util.LogEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSKU extends LogEntity {

    @Id @GeneratedValue
    private Long id;
    private String skuValue;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_attr",referencedColumnName = "id")
    private Attributes attributes;


    public ProductSKU(Attributes attributes, String skuValue) {
        this.attributes = attributes;
        this.skuValue = skuValue;
    }

}
