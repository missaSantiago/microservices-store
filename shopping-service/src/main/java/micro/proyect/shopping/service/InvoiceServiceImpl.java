package micro.proyect.shopping.service;

import lombok.extern.slf4j.Slf4j;
import micro.proyect.shopping.client.CustomerClient;
import micro.proyect.shopping.client.ProductClient;
import micro.proyect.shopping.entity.Invoice;
import micro.proyect.shopping.entity.InvoiceItem;
import micro.proyect.shopping.model.Customer;
import micro.proyect.shopping.model.Product;
import micro.proyect.shopping.repository.InvoiceItemsRepository;
import micro.proyect.shopping.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceItemsRepository invoiceItemsRepository;

    @Autowired
    CustomerClient customerClient;

    @Autowired
    ProductClient productClient;

    @Override
    public List<Invoice> findInvoiceAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceInDB = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());

        if (invoiceInDB != null) {
            return invoiceInDB;
        }

        invoice.setState("CREATED");
        invoiceInDB = invoiceRepository.save(invoice);
        invoiceInDB.getItems().forEach(invoiceItem -> {
            productClient.updateStockProduct(invoiceItem.getProductId(), invoiceItem.getQuantity() * -1);
        });

        return invoiceInDB;
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceInDB = getInvoice(invoice.getId());

        if (invoiceInDB == null) {
            return null;
        }

        invoiceInDB.setCustomerId(invoice.getCustomerId());
        invoiceInDB.setDescription(invoice.getDescription());
        invoiceInDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceInDB.getItems().clear();
        invoiceInDB.setItems(invoice.getItems());

        return invoiceRepository.save(invoiceInDB);
    }

    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceInDB = getInvoice(invoice.getId());

        if (invoiceInDB == null) {
            return null;
        }

        invoiceInDB.setState("DELETED");

        return invoiceRepository.save(invoiceInDB);
    }

    @Override
    public Invoice getInvoice( Long id) {

        Invoice invoice = invoiceRepository.findById(id).orElse(null);

        if (invoice != null) {
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);
            List<InvoiceItem> itemList = invoice.getItems().stream().map(invoiceItem -> {
                Product product = productClient.getProduct(invoiceItem.getProductId()).getBody();
                invoiceItem.setProduct(product);

                return invoiceItem;
            }).collect(Collectors.toList());
            invoice.setItems(itemList);
        }

        return invoice;
    }
}