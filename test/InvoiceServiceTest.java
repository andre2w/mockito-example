import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by andre on 14/04/2017.
 */
public class InvoiceServiceTest {

    private Person customer;
    private Mailer mailer;
    private InvoiceDAO dao;
    private InvoiceService service;
    private TaxDAO taxDAO;

    @Before
    public void setup() {
        customer = new Person("Sterling Archer","5512345678","archer@figgsagency.com");
        mailer = mock(Mailer.class);
        dao = mock(InvoiceDAO.class);
        taxDAO = mock(TaxDAO.class);
        service = new InvoiceService(dao,mailer,taxDAO);
    }

    @Test
    public void confirmOpenInvoicesHasToChangeStatusToTrue() {
        Invoice invoice1 = new Invoice();
        invoice1.setCustomer(customer);

        Invoice invoice2 = new Invoice();
        invoice2.setCustomer(customer);

        Invoice invoice3 = new Invoice();
        invoice3.setCustomer(customer);

        List<Invoice> invoices = Arrays.asList(invoice1,invoice2,invoice3);

        when(dao.customerOpenInvoices(customer))
                .thenReturn(invoices);

        service.confirmCustomerInvoices(customer);
        invoices.forEach(invoice -> Assert.assertTrue(invoice.getConfirmed()));
    }

    @Test
    public void itHasToCallSaveAndMail() {
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);


        when(dao.customerOpenInvoices(customer))
                .thenReturn(Arrays.asList(invoice));

        service.confirmCustomerInvoices(customer);
        verify(dao).save(invoice);
        verify(mailer).confirmationEmail(invoice);
    }

    @Test
    public void taxHasToBeTenPercentOfInvoiceAmount() {
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);

        Item item = new Item();
        item.setName("Black Turtleneck");
        item.setValue(new BigDecimal(150.0));

        invoice.addItem(item);
        when(dao.customerOpenInvoices(customer)).thenReturn(Arrays.asList(invoice));
        service.confirmCustomerInvoices(customer);
        ArgumentCaptor<Tax> captor = ArgumentCaptor.forClass(Tax.class);
        verify(taxDAO).save(captor.capture());



        Tax tax = captor.getValue();
        assertEquals(tax.getValue(),invoice.getTotal() * 0.10,0.001);
    }

    @Test
    public void serviceShouldContinueInCaseOfError() {
        Invoice invoice1 = new Invoice();
        invoice1.setCustomer(customer);

        Invoice invoice2 = new Invoice();
        invoice2.setCustomer(customer);

        Invoice invoice3 = new Invoice();
        invoice3.setCustomer(customer);

        List<Invoice> invoices = Arrays.asList(invoice1,invoice2,invoice3);
        when(dao.customerOpenInvoices(customer)).thenReturn(invoices);

        doThrow(new RuntimeException()).when(dao).save(invoice1);
        service.confirmCustomerInvoices(customer);

        verify(dao).save(invoice2);
        verify(dao).save(invoice3);
        verify(mailer).confirmationEmail(invoice2);
        verify(mailer).confirmationEmail(invoice3);
    }
}
