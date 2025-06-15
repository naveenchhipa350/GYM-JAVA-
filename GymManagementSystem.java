import java.time.LocalDate;
import java.util.Scanner; // for date handling

// Class representing a Gym Member
class Member {
    int memberId;
    String name;
    int age;
    String membershipType; // Monthly, Quarterly, Yearly
    double fee;
    LocalDate joinDate;
    LocalDate expiryDate;

    // Constructor
    Member(int memberId, String name, int age, String membershipType) {
        this.memberId = memberId;
        this.name = name;
        this.age = age;
        this.membershipType = membershipType;
        this.fee = calculateFee();
        this.joinDate = LocalDate.now();
        this.expiryDate = calculateExpiryDate();
    }

    // Method to calculate fee based on membership type
    double calculateFee() {
        if (membershipType.equalsIgnoreCase("Monthly")) {
            return 1000.0;
        } else if (membershipType.equalsIgnoreCase("Quarterly")) {
            return 2500.0;
        } else if (membershipType.equalsIgnoreCase("Yearly")) {
            return 9000.0;
        } else {
            return 0.0;
        }
    }

    // Method to calculate expiry date
    LocalDate calculateExpiryDate() {
        if (membershipType.equalsIgnoreCase("Monthly")) {
            return joinDate.plusMonths(1);
        } else if (membershipType.equalsIgnoreCase("Quarterly")) {
            return joinDate.plusMonths(3);
        } else if (membershipType.equalsIgnoreCase("Yearly")) {
            return joinDate.plusYears(1);
        } else {
            return joinDate;
        }
    }

    // Method to check if membership expired
    boolean isExpired() {
        LocalDate today = LocalDate.now();
        return today.isAfter(expiryDate);
    }

    // Method to display member details
    void display() {
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Membership Type: " + membershipType);
        System.out.println("Fee: Rs. " + fee);
        System.out.println("Join Date: " + joinDate);
        System.out.println("Expiry Date: " + expiryDate);
        System.out.println("Status: " + (isExpired() ? "Expired" : "Active"));
    }
}

// Main class
public class GymManagementSystem {
    static Scanner sc = new Scanner(System.in);
    static Member[] members = new Member[100]; // Max 100 members
    static int count = 0;

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n** Welcome to Gym Management System **");
            System.out.println("1. Register New Member");
            System.out.println("2. Display All Members");
            System.out.println("3. Update Member Information");
            System.out.println("4. Check Expired Memberships");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    registerMember();
                    break;
                case 2:
                    displayAllMembers();
                    break;
                case 3:
                    updateMember();
                    break;
                case 4:
                    checkExpiredMemberships();
                    break;
                case 5:
                    System.out.println("Thank you for using Gym Management System!");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 5);
    }

    // Method to register new member
    static void registerMember() {
        System.out.print("Enter Member ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Membership Type (Monthly/Quarterly/Yearly): ");
        String type = sc.nextLine();

        members[count] = new Member(id, name, age, type);
        System.out.println("Member registered successfully!");
        members[count].display();
        count++;
    }

    // Method to display all members
    static void displayAllMembers() {
        if (count == 0) {
            System.out.println("No members registered yet.");
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.println("\nMember " + (i + 1) + ":");
            members[i].display();
        }
    }

    // Method to update member info
    static void updateMember() {
        System.out.print("Enter Member ID to update: ");
        int id = sc.nextInt();
        boolean found = false;

        for (int i = 0; i < count; i++) {
            if (members[i].memberId == id) {
                found = true;
                sc.nextLine(); // consume newline
                System.out.print("Enter new name: ");
                String name = sc.nextLine();
                System.out.print("Enter new age: ");
                int age = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter new membership type (Monthly/Quarterly/Yearly): ");
                String type = sc.nextLine();

                members[i].name = name;
                members[i].age = age;
                members[i].membershipType = type;
                members[i].fee = members[i].calculateFee();
                members[i].joinDate = LocalDate.now();
                members[i].expiryDate = members[i].calculateExpiryDate();
                System.out.println("Member info updated successfully!");
                members[i].display();
                break;
            }
        }

        if (!found) {
            System.out.println("Member ID not found!");
        }
    }

    // Method to check and notify expired memberships
    static void checkExpiredMemberships() {
        boolean anyExpired = false;
        for (int i = 0; i < count; i++) {
            if (members[i].isExpired()) {
                System.out.println("\n** ALERT: Membership Expired **");
                members[i].display();
                anyExpired = true;
            }
        }
        if (!anyExpired) {
            System.out.println("No expired memberships found.");
        }
    }
}