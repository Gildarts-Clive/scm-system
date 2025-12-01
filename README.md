# **📦 Enterprise Supply Chain Management (SCM) System**

**A full-stack inventory solution featuring intelligent "Predictive Stock Analysis" and automated PDF invoicing.**

## **📖 Table of Contents**

* [Project Overview](https://www.google.com/search?q=%23-project-overview)  
* [Key Features](https://www.google.com/search?q=%23-key-features)  
* [The "Predictive" Twist](https://www.google.com/search?q=%23-the-predictive-twist)  
* [Technical Architecture](https://www.google.com/search?q=%23-technical-architecture)  
* [Technology Stack](https://www.google.com/search?q=%23-technology-stack)  
* [Getting Started](https://www.google.com/search?q=%23-getting-started)  
* [Future Roadmap](https://www.google.com/search?q=%23-future-roadmap)

## **🔍 Project Overview**

This project simulates a real-world **Supply Chain Management System** designed to solve the classic problem of inventory tracking vs. demand. Unlike standard CRUD applications that only track what is *currently* available, this system implements **Business Intelligence** logic to predict supply chain breaks before they happen.

It demonstrates mastery of complex enterprise data relationships (**One-to-Many**, **Many-to-Many**) and automated document generation (**PDFs**).

## **✨ Key Features**

### **1\. 📊 Interactive Dashboard**

* Real-time view of all products, prices, and current stock levels.  
* Visual indicators (badges) for "Healthy" vs. "Low Stock" status.

### **2\. 🚨 Predictive Analytics Engine**

* **Smart Alerts:** The system doesn't wait for stock to hit 0\.  
* **Threshold Logic:** Each product has a unique reorderThreshold. If currentStock \<= reorderThreshold, a high-priority alert is generated immediately.  
* **UI Integration:** Alerts are pushed to the top of the dashboard in a distinct warning container.

### **3\. 📄 Automated Invoice Generation**

* **Dynamic PDF Creation:** Uses the **OpenPDF** library to draw documents programmatically.  
* **Financial Calculation:** Automatically calculates line-item totals and grand totals based on historical shipment data.  
* **Instant Download:** Users can download legal-format invoices directly from the shipment history page.

### **4\. 🚚 Comprehensive Shipment Tracking**

* maintains a history of all transactions.  
* Drill-down capability to see exactly which items (and quantities) were sent to specific customers.

## **🧠 The "Predictive" Twist**

Most inventory apps simply check if (stock \== 0). This system is smarter. It introduces a **Reorder Point (ROP)** logic.

The Code Logic:  
Inside ScmService.java, we filter the stream of products based on their individual safety buffers:  
public List\<Product\> getPredictiveStockAlerts() {  
    List\<Product\> allProducts \= productRepo.findAll();  
      
    // Filter: Keep only products where Stock hits the "Danger Zone"  
    return allProducts.stream()  
            .filter(p \-\> p.getCurrentStock() \<= p.getReorderThreshold())  
            .collect(Collectors.toList());  
}

This allows the Dashboard to warn the warehouse manager **days before** the stock actually runs out.

## **🏗 Technical Architecture**

The application follows the industry-standard **MVC (Model-View-Controller)** layered architecture to ensure separation of concerns.

| Layer | Component | Responsibility |
| :---- | :---- | :---- |
| **Presentation** | Thymeleaf Templates | Renders HTML/CSS UI for the user. |
| **Controller** | ScmController | Handles HTTP requests (GET, POST) and manages navigation. |
| **Service** | ScmService | The "Brain". Handles calculations, PDF generation, and predictive logic. |
| **Data Access** | ProductRepository | Interfaces with the database using Spring Data JPA. |
| **Database** | MySQL | Persists relational data tables. |

Data Model (ERD)

* **Product:** Holds inventory details (price, stock, threshold).  
* **Shipment:** Represents a transaction header (customer, date).  
* **ShipmentItem:** The Junction Entity linking Products to Shipments with specific quantities.

## **🛠 Technology Stack**

* **Language:** Java 21 (LTS)  
* **Framework:** Spring Boot 3.4  
* **ORM:** Hibernate / Spring Data JPA  
* **Database:** MySQL 8  
* **Frontend:** Thymeleaf \+ Bootstrap 5  
* **PDF Generation:** OpenPDF (LibrePDF)  
* **Build Tool:** Maven

## **🚀 Getting Started**

Follow these instructions to get the project up and running on your local machine.

### **Prerequisites**

1. **Java JDK 17 or 21** installed.  
2. **MySQL Server** installed and running on port 3306\.  
3. **Maven** (Optional, as the project includes the maven wrapper).

### **Installation Steps**

1. **Clone the Repository**  
   git clone \[https://github.com/Gildarts-Clive/scm-system.git\](https://github.com/Gildarts-Clive/scm-system.git)  
   cd scm-system

2. **Database Configuration**  
   * Open your MySQL Workbench or Terminal and create the database:  
     CREATE DATABASE scm\_db;

   * Open src/main/resources/application.properties and edit your password:  
     spring.datasource.username=root  
     spring.datasource.password=YOUR\_MYSQL\_PASSWORD

3. **Run the Application**  
   * **Using Terminal:**  
     ./mvnw spring-boot:run

   * **Using VS Code:** Open ScmSystemApplication.java and click "Run".  
4. Access the App  
   Open your browser and navigate to:http://localhost:8080

## **🔮 Future Roadmap**

* \[ \] **Authentication:** Add Spring Security login pages for Admins vs. Viewers.  
* \[ \] **Form Input:** Add a UI to Create New Shipments and Add Products dynamically.  
* \[ \] **Email Integration:** Automatically email the PDF invoice to the customer upon shipment creation.  
* \[ \] **Charts:** Use Chart.js to visualize stock trends over time.

## **👤 Author**

**Gildarts Clive**

* GitHub: [@Gildarts-Clive](https://www.google.com/search?q=https://github.com/Gildarts-Clive)

*This project is for educational purposes, demonstrating enterprise Java architecture.*