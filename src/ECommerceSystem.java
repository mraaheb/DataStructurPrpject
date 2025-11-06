// Imports needed for File I/O and Date parsing
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date; 

/**
 * The main class that manages the entire e-commerce system.
 */
public class ECommerceSystem {

    // --- Master Lists ---
    private MyLinkedList<Product> allProducts;
    private MyLinkedList<Customer> allCustomers;
    private MyLinkedList<Order> allOrders;

    // --- NEW: Auto-Increment Counter for Order IDs ---
    // We start from 501 (assuming CSV orders are below this)
    private int orderCounter = 501;
    private int productCounter = 151; 
    private int customerCounter = 231;  

    /**
     * Constructor to initialize the system.
     */
    public ECommerceSystem() {
        this.allProducts = new MyLinkedList<>();
        this.allCustomers = new MyLinkedList<>();
        this.allOrders = new MyLinkedList<>();
    }
    
    // --- NEW: Helper method to get a new, unique order ID ---
    /**
     * Generates a new unique Order ID and increments the counter.
     * @return A new Order ID as a String.
     */
    public String getNewOrderId() {
        // Return the current counter value, then increment it for next time
        return String.valueOf(orderCounter++); 
    }
    
    // --- NEW: Helper method to get all products (for the menu) ---
    /**
     * Returns the entire list of products in the system.
     * @return The MyLinkedList of all products.
     */
    public MyLinkedList<Product> getAllProducts() {
        return this.allProducts;
    }

    /**
     * Fulfills: "Read data from CSV file"
     * This method reads all 4 CSV files to populate the system.
     * It handles file errors and parsing.
     */
    public void readDataFromCSV(String productsFile, String customersFile, String ordersFile, String reviewsFile) {
        
        // 1. Read Customers (customers.csv)
        try (Scanner scanner = new Scanner(new File(customersFile))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(","); // [0]=customerId, [1]=name, [2]=email
                Customer c = new Customer(data[0].trim(), data[1].trim(), data[2].trim());
                this.registerNewCustomer(c);
            }
            System.out.println("Loaded " + allCustomers.size() + " customers.");
        } catch (FileNotFoundException e) {
            System.err.println("Error reading customers.csv: " + e.getMessage());
        }

        // 2. Read Products (prodcuts.csv)
        try (Scanner scanner = new Scanner(new File(productsFile))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(","); // [0]=productId, [1]=name, [2]=price, [3]=stock
                Product p = new Product(
                    data[0].trim(),                     // productId
                    data[1].trim(),                     // name
                    Double.parseDouble(data[2].trim()), // price
                    Integer.parseInt(data[3].trim())    // stock
                );
                this.addProduct(p);
            }
            System.out.println("Loaded " + allProducts.size() + " products.");
        } catch (FileNotFoundException e) {
            System.err.println("Error reading prodcuts.csv: " + e.getMessage());
        }

        // 3. Read Reviews (reviews.csv)
        try (Scanner scanner = new Scanner(new File(reviewsFile))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                // --- THIS IS THE FIX ---
                // We limit the split to 5 parts.
                // The 5th part (data[4]) will contain "everything else"
                // including the commas inside the comment.
                String[] data = line.split(",", 5); 
                
                String productId = data[1].trim();
                String customerId = data[2].trim();
                int rating = Integer.parseInt(data[3].trim());
                String comment = data[4].trim(); // This now has the full comment

                // Also, let's remove the quotes ("") if they exist
                if (comment.startsWith("\"") && comment.endsWith("\"")) {
                    comment = comment.substring(1, comment.length() - 1);
                }
                // --- END OF FIX ---
                
                Review r = new Review(customerId, rating, comment);
                Product p = this.findProductById(productId);
                if (p != null) {
                    p.addReview(r); 
                }
            }
            System.out.println("Loaded reviews.");
        } catch (FileNotFoundException e) {
            System.err.println("Error reading reviews.csv: " + e.getMessage());
        }

        // 4. Read Orders (orders.csv)
        try (Scanner scanner = new Scanner(new File(ordersFile))) {
            scanner.nextLine(); // Skip header
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(","); 
                
                String orderId = data[0].trim();
                String customerId = data[1].trim();
                String productIdsString = data[2].trim(); 
                Date orderDate = dateFormat.parse(data[4].trim());
                String status = data[5].trim();
                
                Order order = new Order(orderId, customerId, orderDate);
                order.updateStatus(status);
                
                String[] productIds = productIdsString.split(";");
                for (String pid : productIds) {
                    Product p = this.findProductById(pid.trim());
                    if (p != null) {
                        order.addProductToOrder(p);
                    }
                }
                this.placeNewOrder(customerId, order);
            }
            System.out.println("Loaded " + allOrders.size() + " orders.");
        } catch (FileNotFoundException e) {
            System.err.println("Error reading orders.csv: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Error parsing date in orders.csv: " + e.getMessage());
        }
    }

    // --- (Rest of the methods: addProduct, findProductById, getTop3Products, etc...) ---
    // =================================================================
    // --- SECTION 2: Core Operations (from PDF Page 1) ---
    // =================================================================

    // --- Product Operations ---
    public void addProduct(Product product) {
        allProducts.add(product);
    }
    public void removeProduct(String productId) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductId().equals(productId)) {
                allProducts.remove(i);
                break;
            }
        }
    }
    public Product findProductById(String productId) {
        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            if (p.getProductId().equals(productId)) {
                return p;
            }
        }
        return null; // Not found
    }
    public MyLinkedList<Product> getOutOfStockProducts() {
        MyLinkedList<Product> outOfStock = new MyLinkedList<>();
        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            if (p.getStock() == 0) {
                outOfStock.add(p);
            }
        }
        return outOfStock;
    }

    // --- Customer Operations ---
    public void registerNewCustomer(Customer customer) {
        allCustomers.add(customer);
    }
    public Customer findCustomerById(String customerId) {
        for (int i = 0; i < allCustomers.size(); i++) {
            Customer c = allCustomers.get(i);
            if (c.getCustomerId().equals(customerId)) {
                return c;
            }
        }
        return null; // Not found
    }

    // --- Order Operations ---
    public boolean placeNewOrder(String customerId, Order order) {
        Customer c = findCustomerById(customerId);
        if (c != null) {
            c.addOrderToHistory(order);
            allOrders.add(order);
            return true;
        }
        return false; // Customer not found
    }
    public Order findOrderById(String orderId) {
        for (int i = 0; i < allOrders.size(); i++) {
            Order o = allOrders.get(i);
            if (o.getOrderId().equals(orderId)) {
                return o;
            }
        }
        return null; // Not found
    }

    // =================================================================
    // --- SECTION 3: Complex Queries (from PDF Page 2) ---
    // =================================================================

    public MyLinkedList<Review> extractCustomerReviews(String customerId) {
        MyLinkedList<Review> customerReviews = new MyLinkedList<>();
        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            MyLinkedList<Review> productReviews = p.getReviews();
            for (int j = 0; j < productReviews.size(); j++) {
                Review r = productReviews.get(j);
                if (r.getCustomerId().equals(customerId)) {
                    customerReviews.add(r);
                }
            }
        }
        return customerReviews;
    }

    public MyLinkedList<Product> getTop3Products() {
        Product top1 = null, top2 = null, top3 = null;
        double avg1 = -1, avg2 = -1, avg3 = -1;

        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            double currentAvg = p.getAverageRating(); 

            if (currentAvg > avg1) {
                top3 = top2; avg3 = avg2;
                top2 = top1; avg2 = avg1;
                top1 = p; avg1 = currentAvg;
            } else if (currentAvg > avg2) {
                top3 = top2; avg3 = avg2;
                top2 = p; avg2 = currentAvg;
            } else if (currentAvg > avg3) {
                top3 = p; avg3 = currentAvg;
            }
        }
        MyLinkedList<Product> top3List = new MyLinkedList<>();
        if (top1 != null) top3List.add(top1);
        if (top2 != null) top3List.add(top2);
        if (top3 != null) top3List.add(top3);
        return top3List;
    }

    public MyLinkedList<Order> getOrdersBetweenDates(Date startDate, Date endDate) {
        MyLinkedList<Order> result = new MyLinkedList<>();
        for (int i = 0; i < allOrders.size(); i++) {
            Order order = allOrders.get(i);
            Date orderDate = order.getOrderDate();
            if (orderDate.after(startDate) && orderDate.before(endDate)) {
                result.add(order);
            }
        }
        return result;
    }

    public MyLinkedList<Product> getCommonReviewedProducts(String customerId1, String customerId2) {
        MyLinkedList<Product> finalResult = new MyLinkedList<>();
        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            
            if (p.getAverageRating() > 4.0) {
                boolean customer1Reviewed = false;
                boolean customer2Reviewed = false;
                MyLinkedList<Review> reviews = p.getReviews();
                
                for (int j = 0; j < reviews.size(); j++) {
                    String reviewerId = reviews.get(j).getCustomerId();
                    if (reviewerId.equals(customerId1)) {
                        customer1Reviewed = true;
                    }
                    if (reviewerId.equals(customerId2)) {
                        customer2Reviewed = true;
                    }
                    if (customer1Reviewed && customer2Reviewed) {
                        break;
                    }
                }
                
                if (customer1Reviewed && customer2Reviewed) {
                    finalResult.add(p);
                }
            }
        }
        return finalResult;
    }
    
/**
 * Returns the entire list of customers in the system.
 * @return The MyLinkedList of all customers.
 */
public MyLinkedList<Customer> getAllCustomers() {
    return this.allCustomers;
}
public String getNewProductId() {
        return String.valueOf(productCounter++);
    }
public String getNewCustomerId() {
        return String.valueOf(customerCounter++);
    }
public MyLinkedList<Order> getAllOrders() {
        return this.allOrders;
    }
}