package opp.mic.payroll.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import opp.mic.payroll.util.LogEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends LogEntity {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String category;
    private boolean isFeatured ;
    private boolean isOnSale;
    private boolean isNewArrival;
    private boolean status;
    private String company;
    private String gender;
    private BigDecimal purchasePrice;
    private BigDecimal regularPrice;
    private BigDecimal salePrice;
    private Long quantity;


    @OneToMany(mappedBy = "product")
    private List<AppImageDetails> appImageDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductTransaction> productTransaction = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "product_attribute",
            joinColumns=@JoinColumn(name = "product_id",referencedColumnName = "id") ,
            inverseJoinColumns=@JoinColumn(name = "attribute_id",referencedColumnName = "id"))
    private Set<Attributes> attributes =new HashSet<>();

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns=@JoinColumn(name = "product_id",referencedColumnName = "id") ,
            inverseJoinColumns=@JoinColumn(name = "category_id",referencedColumnName = "id"))
    private Set<Category> categoryList = new HashSet<>();
}
