/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_CONNECT;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author KEVO
 */
public class db_connection {
    private static final String user_name="root";
     private static final String password="";
      private static final String con_string="jdbc:mysql://localhost/point_of_sale";
      
      public static Connection db_con(){
          try {
              Connection con=null;
              con=DriverManager.getConnection(con_string, user_name, password);
             // JOptionPane.showMessageDialog(null, "connected");
              return con;
          } catch (Exception e) {
              //JOptionPane.showMessageDialog(null, "no connection found !!");
              JOptionPane.showMessageDialog(null,e);
              return null;
          }
      }
}
