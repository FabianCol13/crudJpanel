package com.fabiancol.user;
 // @author FabianCol
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;





public class Conexion {

    Connection conectar = null;
    String usuario = "root";
    String pass = "";
    String url = "jdbc:mysql://localhost:3306/demo";

    public Connection stablecerConnection(){
    
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(url,usuario,pass);
            //JOptionPane.showMessageDialog(null, "Conexion Exitosa");
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null,"Error de conxion" + e.toString());
        }
        return conectar;
    } 
    
}
