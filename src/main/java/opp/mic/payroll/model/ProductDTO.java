package opp.mic.payroll.model;

import java.util.List;

public record ProductDTO(Long id, String name, String description, Long qtyPurchased, Long qtySold, Double price,
                         List<String> imgs)  {
}
