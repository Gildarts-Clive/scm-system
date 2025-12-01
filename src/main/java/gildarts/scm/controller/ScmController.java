package gildarts.scm.controller;

import gildarts.scm.service.ScmService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class ScmController {

    @Autowired
    private ScmService service;

    // 1. DASHBOARD PAGE
    @GetMapping("/")
    public String dashboard(Model model) {
        // Run seed data just in case the DB is empty
        service.seedData(); 
        
        // Add data to the frontend model
        model.addAttribute("products", service.getAllProducts());
        
        // THE TWIST: Add the alerts separately so we can show them in a red box
        model.addAttribute("alerts", service.getPredictiveStockAlerts()); 
        
        return "dashboard"; // Points to dashboard.html
    }

    // 2. SHIPMENTS PAGE
    @GetMapping("/shipments")
    public String shipments(Model model) {
        model.addAttribute("shipments", service.getAllShipments());
        return "shipments"; // Points to shipments.html
    }

    // 3. INVOICE DOWNLOAD (This doesn't return a page, it returns a file)
    @GetMapping("/shipments/invoice/{id}")
    public void exportToPDF(HttpServletResponse response, @PathVariable Long id) throws IOException {
        // Set the file type to PDF
        response.setContentType("application/pdf");
        
        // Force the browser to download it with a specific name
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=invoice_" + id + ".pdf";
        response.setHeader(headerKey, headerValue);

        // Call our service to write the bytes
        service.generateInvoicePdf(response, id);
    }
}