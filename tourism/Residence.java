package tourism;

import java.sql.*;

public class Residence {
    
    private Connection resCon;
    //bargharari ertebat ba database
    public Residence(Connection resCon) {
        this.resCon = resCon;        
    }
    //namayeshe list eghamatgah ha
    public void print(){
        try {
            Statement stm = this.resCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); #?
            String query = "SELECT * FROM RESIDENCETBL";  #?
            ResultSet rs = stm.executeQuery(query); #?
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
    //namayeshe list eghamatgah haye reserv shode
    public void reservedBy(String cname){
        try {
            Statement stm = this.resCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "select Re_name, Re_Cost from RESIDENCETBL Inner join RESERVATIONTBL On RESERVATIONTBL.Re_ID = RESIDENCETBL.Re_ID and RESERVATIONTBL.Us_UserName = '"+cname+"'";
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
    //farayande rezerv shode
    public void reserve(String cname , int rid){
        try {
            Statement stm = this.resCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "Select Count(Res_Id) As UsCount From RESERVATIONTBL Where Re_ID = "+rid+ " And Us_UserName='"+cname+"'";
            ResultSet rs2 = stm.executeQuery(query);
            rs2.first();
            if (rs2.getInt("UsCount")>=1){
                System.out.println("This place has already been reserved");
                return;
            }
            String query1 = "Select Re_Capacity From RESIDENCETBL Where Re_ID = "+rid;
            ResultSet rs = stm.executeQuery(query1);
            rs.first();
            int capacity= rs.getInt("Re_Capacity");
            if (capacity==0){
                System.out.println("Capacity is Full");
                return;
            }
            capacity --;
            String query2 = "Update RESIDENCETBL Set Re_Capacity = "+capacity+"where Re_ID="+rid;
            stm.executeUpdate(query2);
            String query3 = "Select Count(Res_Id) As Count From RESERVATIONTBL ";
            ResultSet rs1 = stm.executeQuery(query3);
            rs1.first();
            int id = rs1.getInt("Count");
            id ++;
            String query4 = "Insert into RESERVATIONTBL (Res_ID,Re_ID,Us_Username) Values ("+id+","+rid+",'"+cname+"')";
            stm.executeUpdate(query4);
            System.out.println("Successfully reserved!!!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
}
