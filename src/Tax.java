/**
 * Created by andre on 15/04/2017.
 */
public class Tax {

    public Tax(Invoice invoice) {
        this.value = invoice.getTotal() * 0.10;
    }

    private Double value;

    public Double getValue() {
        return value;
    }
}
