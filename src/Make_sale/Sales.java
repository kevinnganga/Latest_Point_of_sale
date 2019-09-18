/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Make_sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author kihumo
 */
public class Sales extends javax.swing.JFrame {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public Sales() {
        initComponents();
        con = DB_CONNECT.db_connection.db_con();
btnremove.setEnabled(false);
 jButton1.setEnabled(false);
 jButton4.setEnabled(false);
    }

    public void GrandTotalWhileSelling() {
        try {
            int setting = 0;
            int sum = 0;
            for (int i = 0; i < table3.getRowCount(); i++) {
                sum = sum + Integer.parseInt(table3.getValueAt(i, 3).toString());
                GrandTotal.setText(Integer.toString(sum));
              

            }
            
              if(sum==0){
                GrandTotal.setText(null);
                }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured  at GrandTotalWhileSelling please Contact 0722208086");
        }

    }

    public void sales_total() {
        int change = 0;
try{
           int Amount_Tendered = Integer.parseInt(amount_tendered.getText());//THE TEXT FIELD TO INPUT THE AMOUNT GIVEN
//            if(amount_tendered.getText()==null){
//               btnremove.setEnabled(false);
//            }
            int Bill = 0;
            for (int i = 0; i < table3.getRowCount(); i++) {
                Bill = Bill + Integer.parseInt(table3.getValueAt(i, 3).toString());
            }

             if (Amount_Tendered >= Bill) {
                 
                 change = Amount_Tendered - Bill;
                pricesell.setText(Integer.toString(Bill));
                txt_change.setText(Integer.toString(change));
                txt_cash.setText(Integer.toString(Amount_Tendered));
                JOptionPane.showMessageDialog(null, "CHANGE KSH :" + txt_change.getText());
            deductions();
            sales_insert_into_db();
            clear_table1();
            clear_table3();
            clear_textfields();
            clear_sales_calcs();
            GrandTotal.setText(null);
            btnremove.setEnabled(false);
                
            }else if(Amount_Tendered < Bill){
                clear_textfields();
                clear_sales_calcs();
                JOptionPane.showMessageDialog(null, "Insufficient cash");
                btnremove.setEnabled(false);
              
                
            }
            else if(amount_tendered.getText()==null){
                clear_textfields();
                clear_sales_calcs();
                JOptionPane.showMessageDialog(null, "Empty cash");
               sales_total();
                
            }
            
            else{
                JOptionPane.showMessageDialog(null, "Transaction cancelled Try Again");
                sales_total();
            }
}catch(Exception e){
    JOptionPane.showMessageDialog(null, "PLEASE ENTER A VALID AMOUNT");
    btnremove.setEnabled(false);
    amount_tendered.setText(null);
}
             
    }

    public void sales_deduction() {
        try {

            int sum = 0;
            int row = table3.getSelectedRow();
            String id_ = table3.getModel().getValueAt(row, 0).toString();
            int quantity = (int) table3.getModel().getValueAt(row, 5);
            quantity = quantity + 1;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured please Contact 0722208086");
        }
    }

    public void deductions() {
        int sum = 0;
        try {

//             int sum2=0;
            for (int i = 0; i < table3.getRowCount(); i++) {

                sum = (int) table3.getModel().getValueAt(i, 5);//to get the stock quantity
                int sum2 = (int) table3.getModel().getValueAt(i, 0);//to get row the index

                int sum3 = 1;
                
                int new_quantity = sum - sum3;
                String kevin = "UPDATE stock SET stock_quantity='" + new_quantity + "' WHERE id='" + sum2 + "'";
                pst = con.prepareStatement(kevin);
                pst.execute();
            }

            if (sum <= 0) {

                JOptionPane.showMessageDialog(null, "INSUFFICIENT STOCK !! PLEASE ADD MORE STOCK QUANTITY TO CONTINUE SELLING !!", "ATTENTION !!", JOptionPane.ERROR_MESSAGE);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        //now create a method to replace the sold stck if the user usese the remove button 
    }
   

    public void addition() {

        try {
            int sum = 0;
            for (int i = 0; i < table3.getRowCount(); i++) {
                sum = (int) table3.getModel().getValueAt(i, 5);
                int sum2 = (int) table3.getModel().getValueAt(i, 0);

                sum = sum + 1;

                String kevin = "UPDATE stock SET stock_quantity='" + sum + "' WHERE id='" + sum2 + "'";
                pst = con.prepareStatement(kevin);
                pst.execute();

                // pricesell.setText(Integer.toString(sum));
                // pricesell2.setText(Integer.toString(sum2));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured please Contact 0722208086");
        }
        //now create a method to replace the sold stck if the user usese the remove button 
    }

    public void remove_row() {

        try {
            DefaultTableModel kevz = (DefaultTableModel) table3.getModel();

            int selected_row = table3.getSelectedRow();
            kevz.removeRow(selected_row);
            GrandTotalWhileSelling();
            // String ngash=table3.getModel().getValueAt(selected_row,6).toString();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please select records to remove !!");
        }
    }

    public void remove_one_row() {

        // NGASH getting selected row index
        try {
            DefaultTableModel kevz = (DefaultTableModel) table3.getModel();

            kevz.removeRow(0);
            GrandTotalWhileSelling();
            // String ngash=table3.getModel().getValueAt(selected_row,6).toString();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please select records to remove !!");
        }
    }

    public void clear_table1() {
        DefaultTableModel model6 = (DefaultTableModel) table1.getModel();
        model6.setRowCount(0);

    }

    public void clear_table3() {
        DefaultTableModel model5 = (DefaultTableModel) table3.getModel();
        model5.setRowCount(0);

    }

    public void clear_textfields() {

        pri_key6.setText(null);
        itemname7.setText(null);
        newprice8.setText(null);
        textField11.setText(null);
        textField22.setText(null);
        amount_tendered.setText(null);
    }

    public void clear_sales_calcs() {
        pricesell.setText(null);
        txt_cash.setText(null);
        txt_change.setText(null);
//        GrandTotal.setText(null);

    }

    public void sales_insert_into_db() {

        for (int i = 0; i < table3.getRowCount(); i++) {
            //GETTING ALL THE ROWS IN THE TABLE 3 IE SOLD ITEMS THEN INSERT IN DB

            String b = (String) table3.getModel().getValueAt(i, 1);
            String c = (String) table3.getModel().getValueAt(i, 2);
            int d = (int) table3.getModel().getValueAt(i, 3);
            int e = (int) table3.getModel().getValueAt(i, 4);
            int f = (int) table3.getModel().getValueAt(i, 5);
            
            
            //DATE STAMP
            java.util.Date date;
            java.sql.Date sqlDate;
   
            date=new java.util.Date();
            sqlDate=new java.sql.Date(date.getTime());
            sqlDate.toString();
            java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime());
            sqlTime.toString();
            System.out.println(sqlDate);
            System.out.println(sqlTime);

            try {
                String query = "INSERT INTO sold (item_name,size,selling_price,buying_price,stock_quantity,date,time) VALUES ('" + b + "','" + c + "','" + d + "','" + e + "','" + f + "','" + sqlDate + "','" + sqlTime+ "')";
//                pst.setDate(1, sqlDate);
//                pst.setTimestamp(2, sqlTime);
                
                pst = con.prepareStatement(query);

                pst.execute();

            } catch (Exception ea) {
                JOptionPane.showMessageDialog(null, "There is an error please contact 0722208086");
                ea.printStackTrace();
            }

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        textField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table3 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        pri_key6 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        itemname7 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        textField11 = new javax.swing.JTextField();
        textField22 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        newprice8 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pricesell = new javax.swing.JTextField();
        btnremove = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        amount_tendered = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_change = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        GrandTotal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_cash = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        table1.setBackground(new java.awt.Color(255, 255, 204));
        table1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Item Name", "Size", "Selling Price"
            }
        ));
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table1);

        textField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textFieldKeyReleased(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 204));
        jButton1.setText("Sell");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 51, 153));
        jButton4.setText("Remove Item");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        table3.setBackground(new java.awt.Color(255, 255, 204));
        table3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        table3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        table3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Item Name", "Size", "Selling Price", "Buying Price", "Stock Quantity"
            }
        ));
        table3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                table3MouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(table3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(jButton4))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton4)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        pri_key6.setEditable(false);
        pri_key6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("ID");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("SIZE");

        itemname7.setEditable(false);
        itemname7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setText("Save");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("ITEM NAME");

        textField11.setEditable(false);
        textField11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        textField22.setEditable(false);
        textField22.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jButton2.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 204));
        jButton2.setText("ALL SALES HISTORY");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("QUANTITY");

        newprice8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("ENTER PRICE");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(18, 18, 18)
                            .addComponent(textField11, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(110, 110, 110)
                                    .addComponent(jLabel7))
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(pri_key6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField22, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(itemname7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jLabel6))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(newprice8, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)))
                .addGap(31, 31, 31))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pri_key6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(itemname7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(newprice8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Total");

        pricesell.setEditable(false);
        pricesell.setBackground(new java.awt.Color(0, 0, 0));
        pricesell.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        pricesell.setForeground(new java.awt.Color(102, 255, 102));
        pricesell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pricesellActionPerformed(evt);
            }
        });

        btnremove.setBackground(new java.awt.Color(204, 0, 204));
        btnremove.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnremove.setForeground(new java.awt.Color(0, 0, 255));
        btnremove.setText("Take Payment");
        btnremove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnremoveActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 153));
        jLabel11.setText("KSH");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 153));
        jLabel10.setText("KSH");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Change");

        amount_tendered.setBackground(new java.awt.Color(0, 255, 255));
        amount_tendered.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        amount_tendered.setForeground(new java.awt.Color(255, 0, 0));
        amount_tendered.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                amount_tenderedKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 153));
        jLabel9.setText("KSH");

        txt_change.setEditable(false);
        txt_change.setBackground(new java.awt.Color(0, 0, 0));
        txt_change.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txt_change.setForeground(new java.awt.Color(102, 255, 102));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 51));
        jLabel12.setText("Ksh");

        GrandTotal.setEditable(false);
        GrandTotal.setBackground(new java.awt.Color(0, 0, 0));
        GrandTotal.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        GrandTotal.setForeground(new java.awt.Color(102, 255, 102));
        GrandTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrandTotalActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Cash ");

        txt_cash.setEditable(false);
        txt_cash.setBackground(new java.awt.Color(0, 0, 0));
        txt_cash.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txt_cash.setForeground(new java.awt.Color(102, 255, 102));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel9))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel11))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel10))))
                            .addComponent(GrandTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_cash, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_change, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pricesell, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnremove, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(amount_tendered, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(amount_tendered, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnremove)
                    .addComponent(GrandTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pricesell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel1))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_cash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_change, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel3))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldKeyReleased
        try {
            jButton1.setEnabled(false);
            jButton4.setEnabled(false);
            String query = "SELECT * FROM stock WHERE item_name LIKE ? ";
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement(query);
            ps.setString(1, textField.getText() + "%");
            rs = ps.executeQuery();

            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            System.out.println("NO DB CONNECTION");
            e.printStackTrace();
        }
    }//GEN-LAST:event_textFieldKeyReleased

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        try {
            int row = table1.getSelectedRow();
            String id_ = table1.getModel().getValueAt(row, 0).toString();
            String query = "SELECT * FROM stock WHERE id='" + id_ + "'";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                //setting the values from the DB to the textfields
                pri_key6.setText(rs.getString("id"));
                itemname7.setText(rs.getString("item_name"));
                newprice8.setText(rs.getString("selling_price"));
                textField11.setText(rs.getString("stock_quantity"));
                textField22.setText(rs.getString("size"));
                 jButton1.setEnabled(true);
                 jButton4.setEnabled(true);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_table1MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked

    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            
            TableModel model1 = table1.getModel();
            int[] index = table1.getSelectedRows();
            
            Object[] row = new Object[6];
            DefaultTableModel model2 = (DefaultTableModel) table3.getModel();
            for (int i = 0; i < index.length; i++) {
                Object s = row[0] = model1.getValueAt(index[i], 0);
                row[1] = model1.getValueAt(index[i], 1);
                row[2] = model1.getValueAt(index[i], 2);
                row[3] = model1.getValueAt(index[i], 3);
                row[4] = model1.getValueAt(index[i], 4);
                Object j = row[5] = model1.getValueAt(index[i], 5);
                int converted = (int) s;//CONVERTINT THE PRIMARY KEY INTO AN INT
                int reduce = 1;
                int a = (int) j - reduce;
                String kevin = "UPDATE stock SET stock_quantity='" + a + "' WHERE id='" + converted + "'";
                pst = con.prepareStatement(kevin);
                pst.execute();
               
                model2.addRow(row);
                refresh_table1();
                
                
            }

            GrandTotalWhileSelling();
//btnremove.setEnabled(true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void table3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table3MouseClicked

    }//GEN-LAST:event_table3MouseClicked

    private void table3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_table3MouseEntered

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            int Adding = 0;

            DefaultTableModel kevz = (DefaultTableModel) table3.getModel();
            int selected_row = table3.getSelectedRow();
//            kevz.removeRow(selected_row);
//            GrandTotalWhileSelling();
            //increasing the stock after removing an item from selling table
            Object ngash = table3.getModel().getValueAt(selected_row, 5);
            Object x = table3.getModel().getValueAt(selected_row, 0);
            int conv = (int) x;
            int conv2 = (int) ngash;
            System.out.println(conv);
            //System.out.println(conv);
            String sql = "SELECT stock_quantity FROM stock WHERE id='" + conv + "' ";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                int c = rs.getInt("stock_quantity");
                Adding = c + 1;
                System.out.println(c);
                System.out.println(Adding);
            }
            System.out.println("ok");
            String query = "UPDATE stock SET stock_quantity='" + Adding + "' WHERE id='" + x + "'";
            pst = con.prepareStatement(query);
            pst.execute();
            kevz.removeRow(selected_row);
             GrandTotalWhileSelling();
            refresh_table1();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please select records to remove !!");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void pricesellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pricesellActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pricesellActionPerformed

    private void btnremoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnremoveActionPerformed

        try {
          //  String x=amount_tendered.getText().trim();
      if(amount_tendered.getText().trim().isEmpty()){
           btnremove.setEnabled(false);
           amount_tendered.setText(null);
           JOptionPane.showMessageDialog(null, "Amount Tendered Cannot be empty");
           
          
           
      }
      DefaultTableModel kevz = (DefaultTableModel) table3.getModel();
            int row_count = table3.getRowCount();
        if(row_count==0){
                   JOptionPane.showMessageDialog(null, "NO ITEMS SELECTED TO SELL ");
                   amount_tendered.setText(null);
                   }
      else{
          
      
            sales_total();
//            JOptionPane.showMessageDialog(null, "CHANGE KSH :" + txt_change.getText());
//            deductions();
//            sales_insert_into_db();
//            clear_table1();
//            clear_table3();
//            clear_textfields();
//            clear_sales_calcs();
//            btnremove.setEnabled(false);
      }
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Please Enter A Valid figure");
            sales_total();
        }
    }//GEN-LAST:event_btnremoveActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            String value_1 = pri_key6.getText();
            String value_2 = itemname7.getText();
            String value_3 = newprice8.getText();
            String value_4 = textField22.getText();
            String value_5 = textField11.getText();
            String sql = "UPDATE stock SET selling_price='" + value_3 + "' WHERE id='" + value_1 + "'";
            pst = con.prepareStatement(sql);
            //pst.setString(1,value_3);
            pst.execute();
            refresh_table1_only_selected_row();
            JOptionPane.showMessageDialog(null, "saved successfully !!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        All_sales kevz = new All_sales();
        All_sales.main(null);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void amount_tenderedKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amount_tenderedKeyReleased
       btnremove.setEnabled(true);
    }//GEN-LAST:event_amount_tenderedKeyReleased

    private void GrandTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrandTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GrandTotalActionPerformed

    public void refresh_table1() {
        try {

            String query = "SELECT * FROM stock";

            pst = con.prepareStatement(query);
            rs = pst.executeQuery();

            table1.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error occured Contact 0722208086");
        }
    }

    public void refresh_table1_only_selected_row() {
//REFRESHING TABLE 3 AT RUN TIME...................
        try {
            // WE FIRST GET THE SELECTED A ROW IN THE TABLE
            int row = table1.getSelectedRow();
            //int row=table3.getRowCount();
            String id_ = table1.getModel().getValueAt(row, 0).toString();
            int quantity = (int) table1.getModel().getValueAt(row, 5);
            String query = "SELECT * FROM stock WHERE id='" + id_ + "'";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField GrandTotal;
    private javax.swing.JTextField amount_tendered;
    private javax.swing.JButton btnremove;
    private javax.swing.JTextField itemname7;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField newprice8;
    private javax.swing.JTextField pri_key6;
    private javax.swing.JTextField pricesell;
    private javax.swing.JTable table1;
    private javax.swing.JTable table3;
    private javax.swing.JTextField textField;
    private javax.swing.JTextField textField11;
    private javax.swing.JTextField textField22;
    private javax.swing.JTextField txt_cash;
    private javax.swing.JTextField txt_change;
    // End of variables declaration//GEN-END:variables
}
