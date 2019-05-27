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
 * @author KEVO
 */
public class Sales extends javax.swing.JFrame {

    Connection con=null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    public Sales() {
        initComponents();
        con=DB_CONNECT.db_connection.db_con();
        
        //refresh_table();
        
    }
    
    public void sales_total(){
       int change=0;
       
        // String Amount coz the input dialogue accepts strimgs from the user
       // String amount=JOptionPane.showInputDialog("Please Enter Tendered Amount");
        String amount=JOptionPane.showInputDialog("Please Enter Tendered Amount");
      
      //  int new_converted_amount_tointeger=Integer.parseInt(amount);
        try {
            int setting=0;
           int sum=0;
        for(int i=0;i<table3.getRowCount();i++){
            sum=sum+Integer.parseInt(table3.getValueAt(i,3).toString());
            
                 setting=Integer.parseInt(amount);
                
                 change=setting-sum;
                 
                    
                         pricesell.setText(Integer.toString(sum));
            
                        txt_change.setText(Integer.toString(change));
                           txt_cash.setText(amount);
          
        }
       if(setting<sum){
        clear_textfields();
       clear_sales_calcs();
       JOptionPane.showMessageDialog(null,"Insufficient cash");
       
       sales_total();
       }
        
        }
            catch (Exception e) {
            JOptionPane.showMessageDialog(null,"An error occured please Contact 0722208086");
        }
        
        
        
    }
    
    public void sales_deduction(){
         try {
              
            
            int sum=0;
        int row=table3.getSelectedRow();
            String id_=table3.getModel().getValueAt(row,0).toString();
            int quantity=(int) table3.getModel().getValueAt(row,5); 
           
            quantity=quantity+1;
            //String query="UPDATE stock SET stock_quantity="
           // JOptionPane.showMessageDialog(null,quantity);
             
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"An error occured please Contact 0722208086");
        }
    }
    public void deductions(){
        
        try {
             int sum=0;
             int sum2=0;
             
        for(int i=0;i<table3.getRowCount();i++){
         sum=0;
            sum2=0;
            sum=(int) table3.getModel().getValueAt(i,5);//to get the stock quantity
            sum2=(int) table3.getModel().getValueAt(i,0);//to get row the index
            
            int sum3=1;
            //insert all the table3 records in BD the retrieve them and deduct from the stock table using rs
            int new_quantity=sum-sum3;
       
           String kevin="UPDATE stock SET stock_quantity='"+new_quantity+"' WHERE id='"+sum2+"'";
            //String kevin="INSERT INTO stock (stock_quantity) VALUES("+new_quantity+")";
              //String kevin="UPDATE stock SET stock_quantity='"+new_quantity+"' WHERE id='"+e[1]+"'";
            pst=con.prepareStatement(kevin);
                pst.execute();
                
        
        }
       
         if(sum<=0){   
                            
          JOptionPane.showMessageDialog(null,"INSUFFICIENT STOCK !! PLEASE ADD MORE STOCK QUANTITY TO CONTINUE SELLING !!","ATTENTION !!",JOptionPane.ERROR_MESSAGE);
          
                            
                        }
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
 //now create a method to replace the sold stck if the user usese the remove button 
    }
     public void addition(){
        
        try {
             int sum=0;
        for(int i=0;i<table3.getRowCount();i++){
            sum=(int) table3.getModel().getValueAt(i,5);
            int sum2=(int) table3.getModel().getValueAt(i,0);
            
            sum=sum+1;
            

            String kevin="UPDATE stock SET stock_quantity='"+sum+"' WHERE id='"+sum2+"'";
            pst=con.prepareStatement(kevin);
            pst.execute();
            
           // pricesell.setText(Integer.toString(sum));
           // pricesell2.setText(Integer.toString(sum2));
       
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"An error occured please Contact 0722208086");
        }
 //now create a method to replace the sold stck if the user usese the remove button 
    }
     
     public void remove_row(){
        
       // NGASH getting selected row index
        try {
             DefaultTableModel kevz=(DefaultTableModel) table3.getModel();
            
            int selected_row=table3.getSelectedRow();
            kevz.removeRow(selected_row);
            
          // String ngash=table3.getModel().getValueAt(selected_row,6).toString();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Please select records to remove !!");
        }
     }
   
public void clear_table1(){
    DefaultTableModel model6=(DefaultTableModel) table1.getModel();
    model6.setRowCount(0);
  
}
public void clear_table3(){
    DefaultTableModel model5=(DefaultTableModel) table3.getModel();
    model5.setRowCount(0);
  
}
public void clear_textfields(){
    
    pri_key6.setText(null);
    itemname7.setText(null);
    newprice8.setText(null);
    textField11.setText(null);
    textField22.setText(null);
}

public void clear_sales_calcs(){
    pricesell.setText(null);
    txt_cash.setText(null);
    txt_change.setText(null);
           
}
public void sales_insert_into_db(){
    
    for(int i=0;i<table3.getRowCount();i++){
        //GETTING ALL THE ROWS IN THE TABLE 3 IE SOLD ITEMS THEN INSERT IN DB
       
        String b= (String) table3.getModel().getValueAt(i,1);
        String c=(String) table3.getModel().getValueAt(i,2);
        int d=(int) table3.getModel().getValueAt(i,3);
        int e=(int) table3.getModel().getValueAt(i,4);
        int f=(int) table3.getModel().getValueAt(i,5);
        
        try {
            String query="INSERT INTO sold (item_name,size,selling_price,buying_price,stock_quantity) VALUES ('"+b+"','"+c+"','"+d+"','"+e+"','"+f+"')";
        pst=con.prepareStatement(query);

        pst.execute();
       // JOptionPane.showMessageDialog(null,"inserted successfully");
        } catch (Exception ea) {
            JOptionPane.showMessageDialog(null,"There is an error please contact 0722208086");
        }
        
        
        
        
        
        
    }
    //JOptionPane.showMessageDialog(null,y);
    
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        textField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        table3 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        pricesell = new javax.swing.JTextField();
        txt_cash = new javax.swing.JTextField();
        btnremove = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_change = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        itemname7 = new javax.swing.JTextField();
        newprice8 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        pri_key6 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        textField11 = new javax.swing.JTextField();
        textField22 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textFieldKeyReleased(evt);
            }
        });
        getContentPane().add(textField, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 260, -1));

        table1.setBackground(new java.awt.Color(255, 255, 204));
        table1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 690, 160));

        table3.setBackground(new java.awt.Color(255, 255, 204));
        table3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        table3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "price", "Title 5", "Title 6", "QUANTITY"
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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 700, 270));

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
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 230, -1, 40));

        pricesell.setEditable(false);
        pricesell.setBackground(new java.awt.Color(0, 0, 0));
        pricesell.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        pricesell.setForeground(new java.awt.Color(102, 255, 102));
        pricesell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pricesellActionPerformed(evt);
            }
        });
        getContentPane().add(pricesell, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 450, 210, -1));

        txt_cash.setEditable(false);
        txt_cash.setBackground(new java.awt.Color(0, 0, 0));
        txt_cash.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txt_cash.setForeground(new java.awt.Color(102, 255, 102));
        getContentPane().add(txt_cash, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 520, 210, -1));

        btnremove.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        btnremove.setForeground(new java.awt.Color(0, 51, 153));
        btnremove.setText("Take Payment");
        btnremove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnremoveActionPerformed(evt);
            }
        });
        getContentPane().add(btnremove, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 380, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Total");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 460, 70, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Cash Tendered");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 530, 180, -1));

        txt_change.setEditable(false);
        txt_change.setBackground(new java.awt.Color(0, 0, 0));
        txt_change.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txt_change.setForeground(new java.awt.Color(102, 255, 102));
        getContentPane().add(txt_change, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 590, 210, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Change");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 600, 90, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("ITEM NAME");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 210, -1, 40));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("ENTER PRICE");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 260, 130, 30));

        itemname7.setEditable(false);
        itemname7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        getContentPane().add(itemname7, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 210, 140, -1));

        newprice8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        getContentPane().add(newprice8, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 260, 140, 30));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setText("Save");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 310, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("ID");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 170, -1, 30));

        pri_key6.setEditable(false);
        pri_key6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        getContentPane().add(pri_key6, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 170, 140, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("SIZE");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 130, -1, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("QUANTITY");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 80, -1, 30));

        textField11.setEditable(false);
        textField11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        getContentPane().add(textField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 80, 140, -1));

        textField22.setEditable(false);
        textField22.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        getContentPane().add(textField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 130, 140, -1));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 51, 153));
        jButton4.setText("Remove Item");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 570, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 204));
        jButton2.setText("ALL SALES HISTORY");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 20, 410, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 153));
        jLabel9.setText("KSH");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 460, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 153));
        jLabel10.setText("KSH");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 530, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 153));
        jLabel11.setText("KSH");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 600, -1, -1));

        jButton5.setText("ADD QUANTITY");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 310, -1, -1));
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 360, 80, -1));

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents
public void refresh_table1(){
    try {
         
        String query="SELECT * FROM stock";
 
        pst=con.prepareStatement(query);
        rs=pst.executeQuery();
      
        table1.setModel(DbUtils.resultSetToTableModel(rs));
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,"Error occured Contact 0722208086");
    }
}

public void refresh_table1_only_selected_row(){
//REFRESHING TABLE 3 AT RUN TIME...................
    try {
        // WE FIRST GET THE SELECTED A ROW IN THE TABLE
            int row=table1.getSelectedRow();
            //int row=table3.getRowCount();
            String id_=table1.getModel().getValueAt(row,0).toString();
            int quantity=(int) table1.getModel().getValueAt(row,5);
            String query="SELECT * FROM stock WHERE id='"+id_+"'";
            pst=con.prepareStatement(query);
            rs=pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
            
    } catch (Exception e) {
    }
            
    
    
}
    
    private void textFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldKeyReleased
        try {
            String query="SELECT * FROM stock WHERE item_name LIKE ? ";
					PreparedStatement ps=null;
					ResultSet rs=null;
					ps=con.prepareStatement(query);
					ps.setString(1,textField.getText()+"%");
					rs=ps.executeQuery();
					
					table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            System.out.println("NO DB CONNECTION");
					e.printStackTrace();
        }
    }//GEN-LAST:event_textFieldKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            TableModel model1=table1.getModel();
					int[] index=table1.getSelectedRows();
                                       
                                        
					Object[] row=new Object[6];
					
					DefaultTableModel model2=(DefaultTableModel) table3.getModel();
					for(int i=0;i<index.length;i++){
						Object s=row[0]=model1.getValueAt(index[i], 0);
						row[1]=model1.getValueAt(index[i], 1);
						row[2]=model1.getValueAt(index[i], 2);
						row[3]=model1.getValueAt(index[i], 3);
						row[4]=model1.getValueAt(index[i], 4);
						row[5]=model1.getValueAt(index[i], 5);
					
						
                                                model2.addRow(row);
                                               // deductions();
                                                
                          // BY KEVO!!!!Please dont comment the sales_total inorder for automatic sales calculations to take place
                                              // sales_total();
                                                
                         //BY KEVO!!!! Please dont comment the deductions inorder for stock deductions to take place
                         //NB: CALL THE DEDUCTION METHOD AFTER CLICKING ACCEPT PAYMENT!!!!1
                                               // deductions();
                                               // refresh_table();
                                               
                                                
                         //NB: CALL THE CLEAR METHOD AFTER CLICKING ACCEPT PAYMENT!!!!1
                                              // clear_table1();
                                               
             			
					}
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void table3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table3MouseClicked
     
    }//GEN-LAST:event_table3MouseClicked

    private void pricesellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pricesellActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pricesellActionPerformed

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        try {
             int row=table1.getSelectedRow();
            String id_=table1.getModel().getValueAt(row,0).toString();
            String query="SELECT * FROM stock WHERE id='"+id_+"'";
            pst=con.prepareStatement(query);
            rs=pst.executeQuery();
            while(rs.next()){
                //setting the values from the DB to the textfields
                pri_key6.setText(rs.getString("id"));
                itemname7.setText(rs.getString("item_name"));
                newprice8.setText(rs.getString("selling_price"));
                textField11.setText(rs.getString("stock_quantity"));
                textField22.setText(rs.getString("size"));
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_table1MouseClicked

    private void btnremoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnremoveActionPerformed
        
       sales_total();
        deductions();
        JOptionPane.showMessageDialog(null,"CHANGE KSH :"+txt_change.getText());
       sales_insert_into_db();
       clear_table1();
       clear_table3();
       clear_textfields();
       clear_sales_calcs();
      
    }//GEN-LAST:event_btnremoveActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            String value_1=pri_key6.getText();
            String value_2=itemname7.getText();
            String value_3=newprice8.getText();
            String value_4=textField22.getText();
            String value_5=textField11.getText();
            String sql="UPDATE stock SET selling_price='"+value_3+"' WHERE id='"+value_1+"'";
            //String sql="INSERT INTO stock (selling_price) VALUES (?) WHERE id='"+value_1+"'";
            pst=con.prepareStatement(sql);
            //pst.setString(1,value_3);
            pst.execute();
            refresh_table1_only_selected_row();
           
            
            JOptionPane.showMessageDialog(null,"saved successfully !!"); 
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        remove_row();
        //sales_deduction();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void table3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_table3MouseEntered

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    All_sales kevz=new All_sales();
    All_sales.main(null);
        

        
        
        

       
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
         try {
            int row=table3.getSelectedRow();
            String id_=table3.getModel().getValueAt(row,0).toString();
            int quantity=(int) table3.getModel().getValueAt(row,5); 
            quantity=quantity+1;
//            String kevin6="UPDATE stock SET Quantity='"+quantity+"' WHERE id='"+id_+"'";
//            pst=con.prepareStatement(kevin6);
//            pst.execute();
               int y=0;
               y=y+1;
               
            
           
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        
    }//GEN-LAST:event_jButton1MouseClicked

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
    private javax.swing.JButton btnremove;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField itemname7;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
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
