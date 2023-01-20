package micro.proyect.shopping.service;

import lombok.extern.slf4j.Slf4j;
import micro.proyect.shopping.entity.Invoice;
import micro.proyect.shopping.repository.InvoiceItemsRepository;
import micro.proyect.shopping.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceItemsRepository invoiceItemsRepository;

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

        return invoiceRepository.save(invoice);
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
        return invoiceRepository.findById(id).orElse(null);
    }
}