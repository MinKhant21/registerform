import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.TableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;


public class register {
    private JPanel Main;
    private JTextField txtname;
    private JTextField txtemail;
    private JTable table1;
    private JButton btncreate;
    private JButton btnupdate;
    private JButton btndelete;
    private JTextField txtid;
    private JButton btnsearch;
    private JPasswordField txtpwd;


    public static void main(String[] args) {
        JFrame frame = new JFrame("register");
        frame.setContentPane(new register().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement stmt;
    public void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost/crud","root","");
            System.out.println("connected");
        }catch (Exception ce){
            System.out.println(ce);
        }

    }

    public void table(){
        try{
            stmt = con.prepareStatement("select * from register");
            ResultSet rs = stmt.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }catch (SQLException ss){
            System.out.println(ss);
        }
    }

    public register(){
        connect();
        table();
        btncreate.addActionListener(e -> {
            try {
                String name , email , pwd;
                name  = txtname.getText();
                email = txtemail.getText();
                pwd = String.valueOf(txtpwd.getPassword());
                stmt = con.prepareStatement("INSERT INTO register (NAME,EMAIL,PASSWORD) VALUES (?,?,?)");
                stmt.setString(1,name);
                stmt.setString(2,email);
                stmt.setString(3,pwd);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null,"Added Register");

                txtname.setText("");
                txtemail.setText("");
                txtpwd.setText("");
                txtname.requestFocus();
                table();


            }catch (SQLException create){
                System.out.println(create);
            }
        });
        btnsearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String id = txtid.getText();
                    stmt = con.prepareStatement("SELECT * from register where id=?");
                    stmt.setString(1,id);
                    ResultSet rs = stmt.executeQuery();
                    if(rs.next()==true){
                        String NAME = rs.getString(2);
                        String EMAIL = rs.getString(3);
                        String PASSWORD = rs.getString(4);
                        txtname.setText(NAME);
                        txtemail.setText(EMAIL);
                        txtpwd.setText(PASSWORD);


                    }else{
                        System.out.println("Out of number");
                    }


                }catch (SQLException sr){
                    System.out.println(sr);
                }
            }
        });
        btnupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String id = txtid.getText();
                    String name , email , pwd;
                    name  = txtname.getText();
                    email = txtemail.getText();
                    pwd = String.valueOf(txtpwd.getPassword());
                    stmt = con.prepareStatement("update register set NAME=? , EMAIL=?,PASSWORD=?");
                    stmt.setString(1,name);
                    stmt.setString(2,email);
                    stmt.setString(3,pwd);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Updated");
                    txtname.setText("");
                    txtemail.setText("");
                    txtpwd.setText("");
                    txtname.requestFocus();
                    table();
                }catch (SQLException ue){
                    System.out.println(ue);
                }


            }
        });
        btndelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String id = txtid.getText();
                    stmt = con.prepareStatement("delete from register where id=?");
                    stmt.setString(1,id);
                    stmt.executeUpdate();
                    table();
                }catch (SQLException se){
                    System.out.println(se);
                }

            }
        });
    }



}
