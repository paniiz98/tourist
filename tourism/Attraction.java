package tourism;

import java.sql.*;

public class Attraction {
    private Connection atCon;
    //barghari ertebat ba database
    public Attraction(Connection atCon) {
        this.atCon = atCon;
    }
    //namayeshe list jazebe haye toristi
    public void print(){
        try {
            Statement stm = this.atCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "SELECT * FROM ATTRACTIONSTBL";
            ResultSet rs = stm.executeQuery(query);
            rs.first();
            while(true) {
                for (int i = 1; i <= 4; i++) 
                    System.out.print(rs.getObject(i)+"\t");
                System.out.println();
                if(!rs.next()){
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    //namayeshe liste rezerve jazebehaye toristi
    public void reservedBy(String cname){
        try {
            Statement stm = this.atCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "select At_name, At_Cost from ATTRACTIONSTBL Inner join RESERVATIONTBL On RESERVATIONTBL.At_ID = ATTRACTIONSTBL.At_ID and RESERVATIONTBL.Us_UserName = '"+cname+"'";
            ResultSet rs = stm.executeQuery(query);
            rs.first();
            System.out.println(cname + " reserved :");
            while(true) {
                for (int i = 1; i <= 2; i++) 
                    System.out.print(rs.getObject(i)+"\t");
                System.out.println();
                if(!rs.next()){
                    break;
                }
            }
        }catch (SQLException ex){
            System.out.println(cname +" has not resereved or we dont have any user by this Uname!!!");
        }
    }
    //farayanede rezer
    public void reserve(String cname , int aid){
        try {
            Statement stm = this.atCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "Select Count(Res_Id) As UsCount From RESERVATIONTBL Where At_ID = "+aid+ " And Us_UserName='"+cname+"'";
            ResultSet rs2 = stm.executeQuery(query);
            rs2.first();
            if (rs2.getInt("UsCount")>=1){
                System.out.println("This place has already been reserved");
                return;
            }
            String query1 = "Select At_Capacity From ATTRACTIONSTBL Where At_ID = "+aid;
            ResultSet rs = stm.executeQuery(query1);
            rs.first();
            int capacity= rs.getInt("At_Capacity");
            if (capacity==0){
                System.out.println("Capacity is Full");
                return;
            }
            capacity --;
            String query2 = "Update ATTRACTIONSTBL Set At_Capacity = "+capacity+"where At_ID="+aid;
            stm.executeUpdate(query2);
            String query3 = "Select Count(Res_Id) As Count From RESERVATIONTBL ";
            ResultSet rs1 = stm.executeQuery(query3);
            rs1.first();
            int id = rs1.getInt("Count");
            id ++;
            String query4 = "Insert into RESERVATIONTBL (Res_ID,At_ID,Us_Username) Values ("+id+","+aid+",'"+cname+"')";
            stm.executeUpdate(query4);
            System.out.println("Successfully reserved!!!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
