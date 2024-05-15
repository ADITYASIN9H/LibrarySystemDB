import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystem{
    private static final String URL = "jdbc:postgresql://localhost/LibrarySystem";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            System.out.println("Connected to the database");

            while (true) {
                System.out.println("\nMain Menu:");
                System.out.println("1. Add New Users / Insert Sample Data");
                System.out.println("2. Search Books");
                System.out.println("3. Manage Loans");
                System.out.println("4. Generate Reports");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addNewUsersOrInsertSampleData(connection, scanner);
                        break;
                    case 2:
                        searchBooks(connection, scanner);
                        break;
                    case 3:
                        manageLoans(connection, scanner);
                        break;
                    case 4:
                        generateReports(connection, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addNewUsersOrInsertSampleData(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\nAdd New Users / Insert Sample Data:");
        System.out.println("1. Add New Users");
        System.out.println("2. Insert Sample Data");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addNewUsers(connection, scanner);
                break;
            case 2:
                insertSampleData(connection);
                break;
            case 3:
                System.out.println("Returning to the main menu.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addNewUsers(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the number of new users to add: ");
        int numberOfUsers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String sql = "INSERT INTO Customers (customer_id, name) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < numberOfUsers; i++) {
                System.out.print("Enter customer ID: ");
                int customerId = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter customer name: ");
                String customerName = scanner.nextLine();

                statement.setInt(1, customerId);
                statement.setString(2, customerName);
                statement.executeUpdate();

                System.out.println("Customer added successfully.");
            }
        }

        System.out.print("Do you want to insert sample data? (yes/no): ");
        String insertSampleDataResponse = scanner.nextLine();

        if (insertSampleDataResponse.equalsIgnoreCase("yes")) {
            insertSampleData(connection);
        }
    }

    private static void insertSampleData(Connection connection) throws SQLException {
        String insertAuthorsSQL = "INSERT INTO Authors (name, biography) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertAuthorsSQL)) {
            statement.setString(1, "J.K. Rowling");
            statement.setString(2, "British author best known for her Harry Potter series");
            statement.executeUpdate();

            statement.setString(1, "Stephen King");
            statement.setString(2, "American author of horror, supernatural fiction, suspense, crime, science-fiction, and fantasy novels");
            statement.executeUpdate();

            statement.setString(1, "Jane Austen");
            statement.setString(2, "English novelist known primarily for her six major novels, which interpret, critique and comment upon the British landed gentry at the end of the 18th century");
            statement.executeUpdate();

            statement.setString(1, "George Orwell");
            statement.setString(2, "English novelist, essayist, journalist, and critic, whose work is marked by lucid prose, awareness of social injustice, opposition to totalitarianism, and outspoken support of democratic socialism");
            statement.executeUpdate();

            statement.setString(1, "Agatha Christie");
            statement.setString(2, "English writer known for her 66 detective novels and 14 short story collections");
            statement.executeUpdate();
        }

        String insertBooksSQL = "INSERT INTO Books (title, author_id, genre) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertBooksSQL)) {
            statement.setString(1, "Harry Potter and the Philosopher's Stone");
            statement.setInt(2, 66);
            statement.setString(3, "Fantasy");
            statement.executeUpdate();

            statement.setString(1, "The Shining");
            statement.setInt(2, 67);
            statement.setString(3, "Horror");
            statement.executeUpdate();

            statement.setString(1, "Pride and Prejudice");
            statement.setInt(2, 68);
            statement.setString(3, "Romance");
            statement.executeUpdate();

            statement.setString(1, "1984");
            statement.setInt(2, 69);
            statement.setString(3, "Dystopian");
            statement.executeUpdate();

            statement.setString(1, "Murder on the Orient Express");
            statement.setInt(2, 70);
            statement.setString(3, "Mystery");
            statement.executeUpdate();
        }

        String insertCustomersSQL = "INSERT INTO Customers (name, email) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertCustomersSQL)) {
            statement.setString(1, "John Doe");
            statement.setString(2, "john.doe@sis.com");
            statement.executeUpdate();

            statement.setString(1, "Alice Smith");
            statement.setString(2, "alice.smith@sis.com");
            statement.executeUpdate();

            statement.setString(1, "Bob Johnson");
            statement.setString(2, "bob.johnson@sis.com");
            statement.executeUpdate();

            statement.setString(1, "Emily Brown");
            statement.setString(2, "emily.brown@sis.com");
            statement.executeUpdate();

            statement.setString(1, "Michael Wilson");
            statement.setString(2, "michael.wilson@sis.com");
            statement.executeUpdate();
        }

        String insertLoansSQL = "INSERT INTO Loans (book_id, customer_id, loan_date, due_date, return_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertLoansSQL)) {
            statement.setInt(1, 39);
            statement.setInt(2, 36);
            statement.setDate(3, Date.valueOf("2024-05-01"));
            statement.setDate(4, Date.valueOf("2024-06-01"));
            statement.setNull(5, Types.DATE);
            statement.executeUpdate();

            statement.setInt(1, 40);
            statement.setInt(2, 37);
            statement.setDate(3, Date.valueOf("2024-05-02"));
            statement.setDate(4, Date.valueOf("2024-06-02"));
            statement.setNull(5, Types.DATE);
            statement.executeUpdate();

            statement.setInt(1, 41);
            statement.setInt(2, 38);
            statement.setDate(3, Date.valueOf("2024-05-03"));
            statement.setDate(4, Date.valueOf("2024-06-03"));
            statement.setNull(5, Types.DATE);
            statement.executeUpdate();

            statement.setInt(1, 42);
            statement.setInt(2, 39);
            statement.setDate(3, Date.valueOf("2024-05-04"));
            statement.setDate(4, Date.valueOf("2024-06-04"));
            statement.setNull(5, Types.DATE);
            statement.executeUpdate();

            statement.setInt(1, 43);
            statement.setInt(2, 40);
            statement.setDate(3, Date.valueOf("2024-05-05"));
            statement.setDate(4, Date.valueOf("2024-06-05"));
            statement.setNull(5, Types.DATE);
            statement.executeUpdate();
        }
    }

    private static void searchBooks(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        String sql = "SELECT * FROM Books WHERE title LIKE ? OR genre LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("title") + " by Author ID: " + resultSet.getInt("author_id"));
            }
        }
    }

    private static void manageLoans(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\nLoan Management Menu:");
        System.out.println("1. Borrow a Book");
        System.out.println("2. Return a Book");
        System.out.println("3. Renew a Book Loan");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                borrowBook(connection, scanner);
                break;
            case 2:
                returnBook(connection, scanner);
                break;
            case 3:
                renewBookLoan(connection, scanner);
                break;
            case 4:
                System.out.println("Returning to the main menu.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private static void borrowBook(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        if (!isBookOnLoan(connection, bookId)) {
            String sql = "INSERT INTO Loans (book_id, customer_id, loan_date, due_date) VALUES (?, ?, CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days')";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, bookId);
                statement.setInt(2, customerId);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Book borrowed successfully.");
                } else {
                    System.out.println("Failed to borrow the book.");
                }
            }
        } else {
            System.out.println("The book is already on loan.");
        }
    }

    private static void returnBook(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        if (isBookOnLoan(connection, bookId)) {

            String sql = "UPDATE Loans SET return_date = CURRENT_DATE WHERE book_id = ? AND return_date IS NULL";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, bookId);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Book returned successfully.");
                } else {
                    System.out.println("Failed to return the book.");
                }
            }
        } else {
            System.out.println("The book is not on loan.");
        }
    }

    private static boolean isBookOnLoan(Connection connection, int bookId) throws SQLException {
        String sql = "SELECT * FROM Loans WHERE book_id = ? AND return_date IS NULL";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }
    
    private static void renewBookLoan(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        if (isBookOnLoanByCustomer(connection, bookId, customerId)) {
            String sql = "UPDATE Loans SET due_date = due_date + INTERVAL '30 days' WHERE book_id = ? AND customer_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, bookId);
                statement.setInt(2, customerId);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Book loan renewed successfully.");
                } else {
                    System.out.println("Failed to renew the book loan.");
                }
            }
        } else {
            System.out.println("The book is not currently on loan by the specified customer.");
        }
    }

    private static boolean isBookOnLoanByCustomer(Connection connection, int bookId, int customerId) throws SQLException {
        String sql = "SELECT * FROM Loans WHERE book_id = ? AND customer_id = ? AND return_date IS NULL";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            statement.setInt(2, customerId);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    private static void generateReports(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\nReport Generation Menu:");
        System.out.println("1. Books Currently on Loan");
        System.out.println("2. Book Borrowing History");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                generateBooksOnLoanReport(connection);
                break;
            case 2:
                generateBookBorrowingHistoryReport(connection);
                break;
            case 3:
                System.out.println("Returning to the main menu.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private static void generateBooksOnLoanReport(Connection connection) throws SQLException {
        System.out.println("\nBooks Currently on Loan:");
        String sql = "SELECT b.title, c.name AS customer_name FROM Books b " +
                "JOIN Loans l ON b.book_id = l.book_id " +
                "JOIN Customers c ON l.customer_id = c.customer_id " +
                "WHERE l.return_date IS NULL";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String customerName = resultSet.getString("customer_name");
                System.out.println("Title: " + title + ", Customer: " + customerName);
            }
        }
    }

    private static void generateBookBorrowingHistoryReport(Connection connection) throws SQLException {
        System.out.println("\nBook Borrowing History:");
        String sql = "SELECT b.title, COUNT(l.book_id) AS borrow_count " +
                "FROM Books b " +
                "JOIN Loans l ON b.book_id = l.book_id " +
                "GROUP BY b.title " +
                "ORDER BY borrow_count DESC";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int borrowCount = resultSet.getInt("borrow_count");
                System.out.println("Title: " + title + ", Times Borrowed: " + borrowCount);
            }
        }
    }
}