package tourism;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Tourism {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String host = "jdbc:derby://localhost:1527/TourismDB";
        try {
            Connection con = DriverManager.getConnection( host, "test", "test");
            Place myPlace = new Place("Tehran", con);
            Residence myResidence = myPlace.getResidence();
            Attraction myAttraction = myPlace.getAttraction();
            System.out.println("Welcome To "+ myPlace.getCityName()+"'s Tourist Guide Application");
            System.out.println("please enter your username: ");
            String username = sc.next();
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("enter a number to show list:");
            System.out.println("1. Residence");
            System.out.println("2. Attraction");
            int flag = sc.nextInt();
            
                if(flag == 1){
                    while(true){
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println("enter a number to reserve or 0 to quit or -1 to show reserve list :");
                        myResidence.print();
                        System.out.println("0. quit");
                        System.out.println("-1. reserve list");
                        int flag2 = sc.nextInt();
                        if(flag2 == 0)
                            return;
                        else if (flag2 == -1)
                            myResidence.reservedBy(username);
                        else
                            myResidence.reserve(username, flag2);
                    }
                }

                else{
                    while(true){
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println("enter a number to reserve or 0 to quit or -1 to show reserve list :");
                        myAttraction.print();
                        System.out.println("0. quit");
                        System.out.println("-1. reserve list");
                        int flag2 = sc.nextInt();
                        if(flag2 == 0)
                            return;
                        else if (flag2 == -1)
                            myAttraction.reservedBy(username); #?
                        else
                            myAttraction.reserve(username, flag2);
                    }
                }
        } catch (SQLException ex) {  #?
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        
    }
    
}
