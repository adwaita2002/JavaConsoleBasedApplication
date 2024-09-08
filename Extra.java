import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Extra {

    // Method to validate the name (no digits allowed, must not be empty, and within
    // 50 characters)
    private static boolean validateName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("[a-zA-Z\\s]+") && name.length() <= 50;
    }

    // Method to validate the email (simple regex validation)
    private static boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    // Method to validate the phone number (only digits, must be 10 digits long)
    private static boolean validatePhone(String phone) {
        return phone.matches("\\d{10}");
    }

    private static boolean validateAddress(String address) {
        return address.length() <= 100;
    }

    private static boolean validatePassword(String password) {
        return password.length() <= 12 && password.length() >= 6;
    }

    // Method to update customer details
    private static void updateCustomerDetails(Customer customer, Scanner scanner, String Pass) {
        while (true) {
            System.out.print("Enter new name (leave blank to keep current name): ");
            String newName = scanner.nextLine();
            if (newName.isBlank()) {
                break;
            }
            if (!newName.isBlank() && validateName(newName)) {
                customer.setName(newName);

                break;
            }

            else {
                System.out.println("Invalid name. Please enter a valid name (no numbers, not empty).");
            }
        }

        while (true) {
            System.out.print("Enter new email (leave blank to keep current email): ");
            String newEmail = scanner.nextLine();
            if (newEmail.isBlank()) {
                break;
            }
            if (!newEmail.isBlank() && validateEmail(newEmail)) {
                customer.setEmail(newEmail);
                break;
            } else {
                System.out.println("Invalid name. Please enter a valid email or keep blank");
            }
        }

        while (true) {
            System.out.print("Enter new phone number (leave blank to keep current phone): ");
            String newPhone = scanner.nextLine();
            if (newPhone.isBlank()) {
                break;
            }
            if (!newPhone.isBlank() && validatePhone(newPhone)) {
                customer.setPhone(newPhone);
                break;
            } else {
                System.out.println("Invalid name. Please enter a valid phone number or keep blank");
            }

        }
        while (true) {
            System.out.print("Enter new address (leave blank to keep current address): ");
            String newAddress = scanner.nextLine();
            if (newAddress.isBlank()) {
                break;
            }
            if (!newAddress.isBlank() && validatePhone(newAddress)) {
                customer.setPhone(newAddress);
                break;
            } else {
                System.out.println("Invalid name. Please enter a valid phone number or keep blank");
            }

        }
        while (true) {

            System.out.print("Enter new password (leave blank to keep current password): ");
            String newPassword = scanner.nextLine();

            if (newPassword.isBlank()) {
                break;
            }

            if (!newPassword.isBlank() && validatePassword(newPassword)) {

                System.out.print("For confirmation enter old passWord : ");
                String oldPassword = scanner.nextLine();
                if (Pass.equals(oldPassword)) {
                    customer.setPassword(newPassword);
                    System.out.println("done");
                    break;
                } else {
                    System.out.println("Old password and new password does not matched please try again");
                }
            } else {
                System.out.println("Invalid Password. Please enter a valid password or keep blank");
            }

        }

        System.out.println("Your details updated successfully!");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Customer> customers = Customer.retrieveFromFile();
        List<Train> trains = Train.retrieveFromFile(); // Retrieve existing train
        String name, email, phone, address, password;

        System.out.println("----- Customer Registration System -----");
        System.out.println("1. Register new customer");
        System.out.println("2. View and update existing customer");
        System.out.println("3. Admin ");
        System.out.println("4. Searching and view train ");
        System.out.println("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            // Register a new customer
            // Get and validate name
            while (true) {
                System.out.print("Enter your name (up to 50 characters): ");
                name = scanner.nextLine();
                if (validateName(name)) {
                    break;
                } else {
                    System.out.println(
                            "Invalid name. Please enter a valid name (no numbers, not empty, within 50 characters).");
                }
            }

            // Get and validate email
            while (true) {
                System.out.print("Enter your email: ");
                email = scanner.nextLine();
                Customer customer = Customer.findCustomerByEmail(customers, email);
                if (validateEmail(email) && customer == null) {
                    break;
                } else if (customer != null) {
                    System.out.println("this email is alreay registered please try with another email");
                } else {
                    System.out.println("Invalid email. Please enter a valid email (e.g., example@domain.com).");
                }
            }

            // Get and validate phone number
            while (true) {
                System.out.print("Enter your phone number (10 digits): ");
                phone = scanner.nextLine();
                if (validatePhone(phone)) {
                    break;
                } else {
                    System.out.println("Invalid phone number. Please enter a 10-digit phone number.");
                }
            }
            // Get and validate address
            while (true) {
                System.out.print("Enter your address (ex: street , city like this within 100 charactres): ");
                address = scanner.nextLine();
                if (validateAddress(phone)) {
                    break;
                } else {
                    System.out.println("Invalid address. Please enter a valid address within 100 charactres.");
                }
            }
            // Get and validate password
            while (true) {
                System.out.print("Enter your password (minimum 6 and maximum 12 characters): ");
                password = scanner.nextLine();
                if (validatePassword(password)) {
                    break;
                } else {
                    System.out.println(
                            "Invalid password. Please enter a valid password. (minimum 6 and maximum 12 characters)");
                }
            }

            int userid = (int) (Math.random() * 100000);
            String userId = Integer.toString(userid);

            // Create a new customer and add to the list
            Customer newCustomer = new Customer(name, email, phone, address, password, userId);
            customers.add(newCustomer);
            newCustomer.displayDetails();
            Customer.saveToFile(customers); // Save to file

        } else if (choice == 2) {
            // View and update existing customer
            System.out.print("Enter the name of the customer to search: ");
            String searchName = scanner.nextLine();
            String Pass = "";
            Customer customer = Customer.findCustomerByName(customers, searchName);
            // String customar = Customer.findCustomerByPass(customers, Pass);
            Pass = customer.getPassword();

            if (customer != null) {
                customer.displayDetails();
                System.out.println("Do you want to update the details? (yes/no)");
                String updateChoice = scanner.nextLine();
                if (updateChoice.equalsIgnoreCase("yes")) {
                    updateCustomerDetails(customer, scanner, Pass);
                    Customer.saveToFile(customers); // Save updated details to file
                }
            } else {
                System.out.println("customer not found ! ");
            }
        } else if (choice == 3) {
            System.out.print("enter your admin password : ");
            String psw = scanner.nextLine();
            if (psw.equals("Admin123")) {

                System.out.println("--------Admin panel --------");
                System.out.println("1.Add train");
                System.out.println("2.View train");
                System.out.println("3.Update train");
                System.out.println("4.Delete train");
                System.out.println("5.choose an option ");
                int option = scanner.nextInt();
                scanner.nextLine();
                // for add train
                if (option == 1) {
                    System.out.println("Enter your train details");
                    System.out.print("Train Name: ");
                    String Trainname = scanner.nextLine();

                    System.out.print("Train Number: ");
                    String number = scanner.nextLine();
                    // scanner.nextLine(); // Consume newline

                    System.out.print("Source: ");
                    String source = scanner.nextLine();

                    System.out.print("Destination: ");
                    String destination = scanner.nextLine();

                    System.out.print("Seats Available: ");
                    String seats = scanner.nextLine();
                    // scanner.nextLine(); // Consume newline

                    Train newTrain = new Train(Trainname, number, source, destination, seats);
                    trains.add(newTrain);
                    newTrain.displayTrainDetails();
                    Train.saveToFile(trains);
                    System.out.println("Train add successfully & saved to file successfully!");

                }
                // view train
                else if (option == 2) {
                    System.out.println("All the avavilable train ---- ");
                    for (Train train : trains) {
                        System.out.println("Train name -> " + train.getTrainName() + " , " + "Source -> "
                                + train.getSource() + " & " + "destination -> " + train.getDestination() + " , "
                                + "seat avavilable : " + train.getSeatsAvailable());
                    }
                } else if (option == 3) {
                    System.out.print("Enter the train number to find the train to update: ");
                    String number = scanner.nextLine();

                    Train trainToUpdate = findTrainByNumber(trains, number);
                    if (trainToUpdate != null) {
                        System.out.println("Updating details for train: " + trainToUpdate.getTrainName());

                        System.out.print("Enter new Train Name (leave blank to keep current): ");
                        String newName = scanner.nextLine();
                        if (!newName.isBlank()) {
                            trainToUpdate.setTrainName(newName);
                        }

                        System.out.print("Enter new Source (leave blank to keep current): ");
                        String newSource = scanner.nextLine();
                        if (!newSource.isBlank()) {
                            trainToUpdate.setSource(newSource);
                        }

                        System.out.print("Enter new Destination (leave blank to keep current): ");
                        String newDestination = scanner.nextLine();
                        if (!newDestination.isBlank()) {
                            trainToUpdate.setDestination(newDestination);
                        }

                        System.out.print("Enter new Seats Available (leave blank to keep current): ");
                        String newSeats = scanner.nextLine();
                        if (!newSeats.isBlank()) {
                            trainToUpdate.setSeatsAvailable(newSeats);
                        }

                        Train.saveToFile(trains); // Save to file after updating a train
                        System.out.println("Train details updated successfully!");
                    }
                } else if (option == 4) {
                    System.out.print("Enter the train number to delete: ");
                    String temp = scanner.nextLine();
                    Train trainToDelete = findTrainByNumber(trains, temp);
                    if (trainToDelete != null) {
                        trains.remove(trainToDelete);
                        Train.saveToFile(trains); // Save to file after deleting a train
                        System.out.println("Train deleted successfully!");
                    } else {
                        System.out.println("Train not found.");
                    }
                }

            } else {
                System.out.println("incorrect admin password !! please try again");
            }
        }else if(choice==4){
            System.out.print("Enter your registered email id : ");
            String enterMail=scanner.nextLine();
            Customer valid=findMail( customers,enterMail);
            if(valid != null){
                System.out.println("---Your account details---");
                System.out.println("UserId : " + valid.getuserId());
                System.out.println("Name : " + valid.getName());
                System.out.println("Email : " + valid.getEmail());
                
                System.out.println("---- Enter Train details ----");
                System.out.print("Enter source station (Form): ");
                String source=scanner.nextLine();
                System.out.print("Enter destination station (to): ");
                String destination=scanner.nextLine();

                List<Train>tr=findTrainBySD(trains,source,destination);
                if(tr.size() >= 1){
                    int i=1;
                    for(Train tra:tr){
                        System.out.println( i++ + "." +" Train name -> " + tra.getTrainName() + " : " + " train number -> " + tra.getTrainNumber() + " : " +"available seat -> " + tra.getSeatsAvailable());
                    }
                   

                }else {
                    System.out.println("Sorry do not have any available train !!");
                }

            }else if(valid == null){
                System.out.println("this email is not registered !! please register before searching train...");
            }
        } else {
            System.out.println("Invalid option selected.");
        }

        scanner.close();
    }

    public static Train findTrainByNumber(List<Train> trains, String trainNumber) {
        for (Train train : trains) {
            String temp = train.getTrainNumber();
            if (temp.equals(trainNumber)) {
                return train;
            }
        }
        return null;
    }

    public static Customer findMail(List<Customer> customers,String email){
             for(Customer cus:customers){
                if(cus.getEmail().equals(email)){
                    return cus;
                }
             }
             return null;
    }

    public static List<Train> findTrainBySD(List<Train>trains,String source , String destination){
        List<Train>tra=new ArrayList<>();
        for(Train train : trains){
            if(train.getSource().equals(source) && train.getDestination().equals(destination)){
                tra.add(train);
            }
        }
        return tra;
    }

}

class Customer {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
    private String userId;

    // Constructor
    public Customer(String name, String email, String phone, String address, String password, String userId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.userId = userId;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getuserId() {
        return userId;
    }

    

    // Display customer details
    public void displayDetails() {
        System.out.println("\nCustomer Details:");
        System.out.println("Your UserId: " + userId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
    }

    // Format customer data for saving to file
    @Override
    public String toString() {
        return name + "," + email + "," + phone + "," + address + "," + password + "," + userId;
    }

    // Method to save customer list to a file
    public static void saveToFile(List<Customer> customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt"))) {
            for (Customer customer : customers) {
                writer.write(customer.toString());
                writer.newLine();
            }
            System.out.println("You have successfully registered & Your details saved to file successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the details to file.");
            e.printStackTrace();
        }
    }

    String[] str = new String[6];

    // Method to retrieve customers from the file
    public static List<Customer> retrieveFromFile() {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 6) {
                    customers.add(new Customer(details[0], details[1], details[2], details[3], details[4], details[5]));
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while retrieving customer details.");
            e.printStackTrace();
        }
        return customers;
    }

    // Method to find a customer by name
    public static Customer findCustomerByName(List<Customer> customers, String name) {

        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;

            }

        }
        return null;
    }

    public static Customer findCustomerByEmail(List<Customer> customers, String name) {
        for (Customer customer : customers) {
            if (customer.getEmail().equalsIgnoreCase(name)) {
                return customer;
            }

        }
        return null;
    }

}
class Train {
    private String trainName;
    private String trainNumber;
    private String source;
    private String destination;
    private String seatsAvailable;

    // Constructor
    public Train(String trainName, String trainNumber, String source, String destination, String seatsAvailable) {
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.source = source;
        this.destination = destination;
        this.seatsAvailable = seatsAvailable;
    }

    // Getters and Setters
    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(String seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    // Display train details
    public void displayTrainDetails() {
        System.out.println("Train Number: " + trainNumber);
        System.out.println("Train Name: " + trainName);
        System.out.println("Source: " + source);
        System.out.println("Destination: " + destination);
        System.out.println("Seats Available: " + seatsAvailable);
        System.out.println("--------------------------");
    }

    @Override
    public String toString() {
        return trainName + "," + trainNumber + "," + destination + "," + source + "," + seatsAvailable;
    }

    public static void saveToFile(List<Train> trains) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("trains.txt"))) {
            for (Train train : trains) {
                writer.write(train.toString());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("An error occurred while saving the details to file.");
            e.printStackTrace();
        }
    }

    public static List<Train> retrieveFromFile() {
        List<Train> trains = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("trains.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 5) {
                    trains.add(new Train(details[0], details[1], details[2], details[3], details[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while retrieving customer details.");
            e.printStackTrace();
        }
        return trains;
    }

}
