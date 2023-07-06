package orchestrationandchoreography.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "stock")
public class StockItem {

    private String id;
    private String itemNumber;
    @Indexed(unique = true)
    private String description;
    private long quantity;

}
