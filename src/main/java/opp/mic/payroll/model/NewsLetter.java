package opp.mic.payroll.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import opp.mic.payroll.util.LogEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsLetter extends LogEntity {

    @Id @GeneratedValue
    private Long id;
    private String email;

    public NewsLetter(String email){
        this.email = email;
    }
}
