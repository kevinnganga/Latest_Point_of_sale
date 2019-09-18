//package DB_CONNECTION;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//import javax.swing.JOptionPane;
//
//public class connection {
//	private static final String username="root";
//	private static final String pass="";
//	private static final String connection="jdbc:mysql://localhost/point_of_sale";
//	
//	
//public static Connection connect(){
//		
//		
//		try {
//			Connection con=null;
//			con=DriverManager.getConnection(connection, username, pass);
//			JOptionPane.showMessageDialog(null, "connected");
//			return con;
//		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "no connection found !!");
//			
//			return null;
//			
//		}
//		
//		
//	}
//
//}
