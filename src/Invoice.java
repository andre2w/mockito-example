import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 14/04/2017.
 */
public class Invoice {

    public Invoice(Person customer, List<Item> items) {
        this.customer = customer;
        this.items = items;
    }

    public Invoice() {}

    private Person customer;
    private List<Item> items = new ArrayList<>();
    private Boolean confirmed = false;

    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public double getTotal() {
        return items.stream().mapToDouble(item -> item.getValue().doubleValue()).sum();
    }

    public void addItem(Item item) {
        items.add(item);
    }
}
