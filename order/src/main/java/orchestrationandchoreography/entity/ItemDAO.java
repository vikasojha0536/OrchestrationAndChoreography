package orchestrationandchoreography.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ItemDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String itemNumber;

    @ManyToOne
    @JoinColumn(name = "order_id")

    // As this is cyclic reference order contains Item and Item contains order
    // field marked with @JsonBackReference will not be serialized
    @JsonBackReference
    private OrderDAO order;

    private long quantity;
}
