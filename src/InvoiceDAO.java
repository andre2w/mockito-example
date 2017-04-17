import java.util.List;

/**
 * Created by andre on 14/04/2017.
 */
public interface InvoiceDAO {

    List<Invoice> customerOpenInvoices(Person customer);
    void save(Invoice invoice);

}
