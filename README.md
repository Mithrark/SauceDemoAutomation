# SauceDemo E-commerce Automation Project

## 📌 Project Overview
This project automates end-to-end test scenarios of the [SauceDemo](https://www.saucedemo.com/) e-commerce application using **Selenium WebDriver**, **Java**, and **TestNG**.  
It is designed to simulate real-world automation practices with a clean, reusable framework structure.

---

## 🛠 Tech Stack
- **Programming Language**: Java  
- **Automation Tool**: Selenium WebDriver  
- **Test Framework**: TestNG  
- **Build Tool**: Maven  
- **IDE**: Eclipse  
- **Version Control**: Git & GitHub   

---

## 📂 Project Structure
SauceDemoAutomation/
│── src/main/java/
│ └── pages/ # Page classes (LoginPage, ProductsPage, CartPage, CheckoutPage, etc.)
│ └── utils/ # Utility classes (e.g., WaitUtils, ConfigReader, DriverFactory)
│
│── src/test/java/
│ └── tests/ # TestNG test classes
│
│── testng.xml # Test suite configuration
│── pom.xml # Maven dependencies
│── README.md # Project documentation

---

## ✅ Test Scenarios Covered
- **Login**
  - Valid login
  - Invalid login (wrong credentials)
  - Locked user scenario  

- **Product Page**
  - Validate product listing
  - Sort functionality (low-high, high-low, A–Z, Z–A)

- **Cart**
  - Add product to cart
  - Remove product from cart
  - Validate cart badge count  

- **Checkout Flow**
  - Enter checkout details
  - Order summary verification
  - Complete purchase  

- **Post-Order**
  - Order confirmation message 
  - Navigate back to products  

- **Edge Cases**
  - Empty username/password
  - Special characters in fields  

