import java.util.List;

/**
 * Created by andre on 14/04/2017.
 */
public class InvoiceService {

    private InvoiceDAO dao;
    private Mailer mailer;
    private TaxDAO taxDAO;

    public InvoiceService(InvoiceDAO dao, Mailer mailer,TaxDAO taxDAO) {
        this.dao = dao;
        this.mailer = mailer;
        this.taxDAO = taxDAO;
    }

    public void confirmCustomerInvoices(Person customer) {
        List<Invoice> invoices = dao.customerOpenInvoices(customer);
        invoices.forEach(this::confirmInvoice);
    }

    private void confirmInvoice(Invoice invoice) {
        try {
            invoice.setConfirmed(true);
            dao.save(invoice);
            mailer.confirmationEmail(invoice);

            Tax tax = new Tax(invoice);
            taxDAO.save(tax);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }




}
