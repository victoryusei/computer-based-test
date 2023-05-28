package User;

import Auth.Login;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aditya Syawal, Muhamad Talim, Mikail Asada, Rafi Fajrul
 */
public class DashboardUser extends javax.swing.JFrame {
    private java.sql.Connection connectionDB;
    private String userID;
    /**
     * Creates new form NewJFrame
     */
    public DashboardUser(java.sql.Connection connectionDB, String userID) {
        this.userID = userID;
        this.connectionDB = connectionDB;
        initComponents();
        initAlgos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listExam = new javax.swing.JTable();
        mulaiButton = new javax.swing.JButton();
        userLabel = new javax.swing.JLabel();
        quitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        listExam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "No", "Title", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listExam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listExamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listExam);
        if (listExam.getColumnModel().getColumnCount() > 0) {
            listExam.getColumnModel().getColumn(1).setResizable(false);
            listExam.getColumnModel().getColumn(2).setResizable(false);
        }

        mulaiButton.setText("mulai");
        mulaiButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mulaiButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        userLabel.setText("jLabel1");

        quitButton.setText("quit");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(quitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mulaiButton))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(userLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(mulaiButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(quitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(171, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
        Login lgn = new Login(this.connectionDB);
        lgn.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_quitButtonActionPerformed

    private void listExamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listExamMouseClicked
        int selectedRow = listExam.getSelectedRow();
        if (selectedRow != -1) {
            String Status = (String) listExam.getValueAt(selectedRow, 2);
            if(Status == "Mulai"){
                this.mulaiButton.setVisible(true);
            }else{
                this.mulaiButton.setVisible(false);
            }
        
        }
        
    }//GEN-LAST:event_listExamMouseClicked

    private void loadDataFromDatabase() {
        try {
            String sql = "SELECT * FROM exam JOIN exam_access ON exam.id = exam_id WHERE user_id = ? ORDER BY exam.id";
            PreparedStatement statement = this.connectionDB.prepareStatement(sql);
            statement.setString(1, this.userID);
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel tableModel = (DefaultTableModel) listExam.getModel();
            tableModel.setRowCount(0);
            Integer tableCount = 1;

            while (resultSet.next()) {
                Object[] rowData = {
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        isOpen(resultSet.getBoolean("isOpen"))
                        
                };
                
                System.out.print(Arrays.toString(rowData));
                tableModel.addRow(rowData);
                tableCount++;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void initAlgos(){
        this.userLabel.setText(this.userID);
        this.mulaiButton.setVisible(false);
        loadDataFromDatabase();

    }

     private String isOpen(boolean isOpen) {
        if(isOpen){
        return "Mulai";
        }
        return "Habis";
    }
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ManageUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ManageUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ManageUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ManageUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new DashboardUser().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable listExam;
    private javax.swing.JButton mulaiButton;
    private javax.swing.JButton quitButton;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
