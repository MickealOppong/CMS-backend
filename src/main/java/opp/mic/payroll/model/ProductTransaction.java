package opp.mic.payroll.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import opp.mic.payroll.util.LogEntity;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTransaction extends LogEntity {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "fk_pid",referencedColumnName = "id")
    private Product product;
    private Long quantityPurchased;
    private BigDecimal purchasePrice;
    private Long quantitySold;
    private BigDecimal salesPrice;
    private BigDecimal reductionPrice;

}
