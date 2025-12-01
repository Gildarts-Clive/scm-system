package gildarts.scm.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import gildarts.scm.entity.*;
import gildarts.scm.repository.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScmService {

    @Autowired
    private ProductRepository productRepo;
    
    @Autowired
    private ShipmentRepository shipmentRepo;

    // --- STANDARD DATA METHODS ---

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepo.findAll();
    }

    // --- THE TWIST: PREDICTIVE LOGIC ---
    
    // This logic flags stock NOT just when it's zero, but when it hits the "danger zone" (threshold)
    public List<Product> getPredictiveStockAlerts() {
        List<Product> allProducts = productRepo.findAll();
        
        // Filter the list: Keep only products where Stock <= Threshold
        return allProducts.stream()
                .filter(p -> p.getCurrentStock() <= p.getReorderThreshold())
                .collect(Collectors.toList());
    }

    // --- PDF GENERATION LOGIC ---
    
    public void generateInvoicePdf(HttpServletResponse response, Long shipmentId) throws IOException {
        // 1. Get the shipment data
        Shipment shipment = shipmentRepo.findById(shipmentId).orElseThrow();

        // 2. Create the PDF Document
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // 3. Add Title
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Paragraph paragraph = new Paragraph("INVOICE #" + shipment.getId(), fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        
        // 4. Add Customer Info
        document.add(new Paragraph("Customer: " + shipment.getCustomerName()));
        document.add(new Paragraph("Date: " + shipment.getShipmentDate()));
        document.add(new Paragraph(" ")); // Empty line for spacing

        // 5. Create Table with 4 Columns
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        
        // Table Headers
        table.addCell("Product");
        table.addCell("Price");
        table.addCell("Quantity");
        table.addCell("Total");

        // Table Rows (Loop through items)
        for (ShipmentItem item : shipment.getItems()) {
            table.addCell(item.getProduct().getName());
            table.addCell("$" + item.getProduct().getPrice());
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell("$" + (item.getProduct().getPrice() * item.getQuantity()));
        }

        document.add(table);
        
        // 6. Add Grand Total
        Paragraph totalPara = new Paragraph("Grand Total: $" + shipment.getTotalAmount());
        totalPara.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(totalPara);

        document.close();
    }
    
    // --- HELPER: SEED DATA ---
    // This runs when the dashboard loads to make sure you have data to look at
    public void seedData() {
        if (productRepo.count() == 0) {
            // 1. Create Products
            Product p1 = new Product();
            p1.setName("Laptop");
            p1.setPrice(1200.0);
            p1.setCurrentStock(5);
            p1.setReorderThreshold(10); // Low Stock

            Product p2 = new Product();
            p2.setName("Mouse");
            p2.setPrice(25.0);
            p2.setCurrentStock(100);
            p2.setReorderThreshold(20);

            productRepo.saveAll(List.of(p1, p2));

            // 2. Create a Fake Shipment
            Shipment s1 = new Shipment();
            s1.setCustomerName("Tech Corp Inc.");
            s1.setShipmentDate(java.time.LocalDate.now());

            ShipmentItem item1 = new ShipmentItem();
            item1.setProduct(p1);
            item1.setQuantity(2);
            item1.setShipment(s1);

            ShipmentItem item2 = new ShipmentItem();
            item2.setProduct(p2);
            item2.setQuantity(10);
            item2.setShipment(s1);

            s1.getItems().add(item1);
            s1.getItems().add(item2);

            shipmentRepo.save(s1);
        }
    }
}