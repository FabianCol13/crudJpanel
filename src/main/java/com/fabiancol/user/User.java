package com.fabiancol.user;
// @author FabianCol

import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class User {

    int idUSer;
    String nombre;
    String apellido;
    String telefono;
    String email;

    public int getIdUSer() {
        return idUSer;
    }

    public void setIdUSer(int idUSer) {
        this.idUSer = idUSer;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void InsertarUser(JTextField pNombre, JTextField pApellido, JTextField pTelefono, JTextField pEmail) {
        Conexion objConexion = new Conexion();
        objConexion.stablecerConnection();

        setNombre(pNombre.getText());
        setApellido(pApellido.getText());
        setTelefono(pTelefono.getText());
        setEmail(pEmail.getText());

        String query = "INSERT INTO users (nameUser, lastnameUser, phoneUser, emailUser) VALUES (?, ?, ?, ?);";

        try {
            CallableStatement cs = objConexion.stablecerConnection().prepareCall(query);
            cs.setString(1, getNombre());
            cs.setString(2, getApellido());
            cs.setString(3, getTelefono());
            cs.setString(4, getEmail());

            cs.execute();

            JOptionPane.showMessageDialog(null, "Registro exitoso!");

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo el registro!" + e.toString());
        }

    }

    public void mostrar(JTable tbUser) {
        Conexion objConexion = new Conexion();
        objConexion.stablecerConnection();
        String query = "SELECT * FROM users;";
        DefaultTableModel modelo = new DefaultTableModel();

        TableRowSorter<TableModel> ordenaTabla = new TableRowSorter<>(modelo);
        tbUser.setRowSorter(ordenaTabla);

        modelo.addColumn("id");
        modelo.addColumn("nombre");
        modelo.addColumn("apellido");
        modelo.addColumn("telefono");
        modelo.addColumn("email");

        tbUser.setModel(modelo);

        String[] dataUSer = new String[5];
        Statement st;

        try {
            st = objConexion.stablecerConnection().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                dataUSer[0] = rs.getString(1);
                dataUSer[1] = rs.getString(2);
                dataUSer[2] = rs.getString(3);
                dataUSer[3] = rs.getString(4);
                dataUSer[4] = rs.getString(5);

                modelo.addRow(dataUSer);
            }

            tbUser.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo AL MOSTRAR registros!" + e.toString());
        }
    }

    public void seleccionarId(JTable tbUser, JTextField pId, JTextField pNombre, JTextField pApellidos, JTextField pTelefono, JTextField pEmail) {

        try {
            int fila = tbUser.getSelectedRow();
            if (fila >= 0) {

                pId.setText((String) (tbUser.getValueAt(fila, 0)));
                pNombre.setText((String) (tbUser.getValueAt(fila, 1)));
                pApellidos.setText((String) (tbUser.getValueAt(fila, 2)));
                pTelefono.setText((String) (tbUser.getValueAt(fila, 3)));
                pEmail.setText((String) (tbUser.getValueAt(fila, 4)));

            } else {
                JOptionPane.showMessageDialog(null, "Fallo fila!");
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Fallo de seleccion registros!" + e.toString());

        }
    }

    public void modificar(JTextField pId, JTextField pNombre, JTextField pApellidos, JTextField pTelefono, JTextField pEmail) {
        setIdUSer(Integer.parseInt(pId.getText()));
        setNombre(pNombre.getText());
        setApellido(pApellidos.getText());
        setTelefono(pTelefono.getText());
        setEmail(pEmail.getText());

        Conexion objConexion = new Conexion();
        objConexion.stablecerConnection();
        String query = "UPDATE users SET nameUser = ?, lastnameUser = ?,phoneUser = ?, emailUser = ? WHERE idUser = ?;";

        try {
            CallableStatement cs;
            cs = objConexion.stablecerConnection().prepareCall(query);

            cs.setString(1, getNombre());
            cs.setString(2, getApellido());
            cs.setString(3, getTelefono());
            cs.setString(4, getEmail());
            cs.setInt(5, getIdUSer());

            cs.execute();

            JOptionPane.showMessageDialog(null, "Modificación exitosa!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo el cambio!" + e.toString());
        }

    }

    public void borrarUser(JTextField id) {
        setIdUSer(Integer.parseInt(id.getText()));

        Conexion objConexion = new Conexion();
        objConexion.stablecerConnection();
        String query = "DELETE FROM users WHERE idUser = ?;";

        try {
            CallableStatement cs;
            cs = objConexion.stablecerConnection().prepareCall(query);
            cs.setInt(1, getIdUSer());
            cs.execute();

            JOptionPane.showMessageDialog(null, "Eliminacion exitosa!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo al borrar!" + e.toString());
        }
    }
}
