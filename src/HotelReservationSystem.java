import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;

public class HotelReservationSystem {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hotel_DB";
    private static final String DB_USER = "root";   
    private static final String DB_PASSWORD = "root";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            while(true){
                System.out.println();
                System.out.println("Hotel Management System");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get room number");
                System.out.println("4. Update reservation");
                System.out.println("5. Delete reservation");
                System.out.println("0. Exit");
                System.out.println("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1: 
                        reserveRoom(connection, scanner);
                        break;
                    
                    case 2: 
                        viewReservation(connection);
                        break;

                    case 3: 
                        getRoomNumber(connection, scanner);
                        break;

                    case 4: 
                        UpdateReservation(connection, scanner);
                        break;

                    case 5:
                        deleteReservation(connection, scanner);
                        break;
                    case 0: 
                        exit();
                        scanner.close();
                        break;
                
                    default:
                        System.out.println("Invalid Choice. Try again");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //  catch(InterruptedException e){
        //     throw new RuntimeException(e);
        // }
    }


    private static void reserveRoom(Connection connection, Scanner scanner){
        System.out.println("Enter guest name: ");
        String guestName = scanner.next();
        scanner.nextLine();
        System.out.println("Enter room number: ");
        int roomNumber = scanner.nextInt();
        System.out.println("Enter contact number: ");
        String contact = scanner.next();

        String sql = "Insert into reservation (guest_name, room_number, contact_number)" +
             "values('"+guestName+"',"+roomNumber+", '"+contact+"')";

        try (Statement statement = connection.createStatement()){
            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows>0){
                System.out.println("Reservation Successful!");
            } else{
                System.out.println("Reservation Failed!");
            }

        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewReservation(Connection connection) throws SQLException{
        String sql = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservation;";

        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
                
                while(resultSet.next()){
                    int reservationID = resultSet.getInt("reservation_id");
                    String guestName = resultSet.getNString("guest_name");
                    int roomNumber = resultSet.getInt("room_number");
                    String contactNumber = resultSet.getString("contact_number");
                    String reservationDate = resultSet.getString("reservation_date").toString();

                    System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s |\n",
                     reservationID, guestName, roomNumber, contactNumber, reservationDate);
                }

            }
    }

    private static void getRoomNumber(Connection connection, Scanner scanner){
        System.out.println("Enter reservation ID: ");
        int reservationId = scanner.nextInt();
        System.out.println("Enter guest name: ");
        String guestName = scanner.next();

        String sql = "select room_number from reservation" + 
        " where reservation_id = " + reservationId + 
        " and guest_name= '"+ guestName +"'" ;

        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

                if(resultSet.next()){
                    int roomNumber = resultSet.getInt("room_number");
                    System.out.println("Room number for reservation id: "+reservationId+" and guest "+guestName+" is: "+roomNumber);
                } else{
                    System.out.println("No reservation for this guest!");
                }

            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void UpdateReservation(Connection connection, Scanner scanner){

    }

    private static void deleteReservation(Connection connection, Scanner scanner){

    }

    private static void exit(){

    }
}
 