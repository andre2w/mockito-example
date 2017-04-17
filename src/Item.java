import java.math.BigDecimal;

/**
 * Created by andre on 14/04/2017.
 */
public class Item {

    public Item() {}

    public Item(String name,BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private BigDecimal value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
