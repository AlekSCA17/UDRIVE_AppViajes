/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaviaje;

/**
 *
 * @author Alexander
 */
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
//import static practicaviaje.PracticaViaje.viajar;
//import sun.applet.Main;

public class Modulo extends javax.swing.JFrame {

    /**
     * Creates new form Modulo
     */
     public JLabel moto= new JLabel();
     public JLabel carro1= new JLabel();
     public JLabel carro2= new JLabel();
     private JFileChooser fileChooser;
    private File JFileSelected;
     //aca inicia video
    

        
        
        
//aca finaliza
    
      
    public Modulo() {
        initComponents();
        setLocationRelativeTo(null); 
        
         moto.setBounds(5, 120, 100, 60);
         
        ImageIcon mot = new ImageIcon(getClass().getResource("../imagenes/moto1.png"));
        Icon moto1 = new ImageIcon(mot.getImage().getScaledInstance(moto.getWidth(), moto.getHeight(), Image.SCALE_DEFAULT));
        moto.setIcon(moto1);
        jPanel2.add(moto);
        
         carro1.setBounds(5, 270, 100, 60);
         
        ImageIcon car = new ImageIcon(getClass().getResource("../imagenes/dragon.png"));
        Icon dragon = new ImageIcon(car.getImage().getScaledInstance(carro1.getWidth(), carro1.getHeight(), Image.SCALE_DEFAULT));
        carro1.setIcon(dragon);
        jPanel2.add(carro1);
        
        carro2.setBounds(5, 430, 100, 60);
         
        ImageIcon car2 = new ImageIcon(getClass().getResource("../imagenes/mario.png"));
        Icon mario = new ImageIcon(car2.getImage().getScaledInstance(carro2.getWidth(), carro2.getHeight(), Image.SCALE_DEFAULT));
        carro2.setIcon(mario);
        jPanel2.add(carro2);
    }

    private void loadRoutes() {
        // Store the selected items
        String startSelected = (String) starBox.getSelectedItem();
        String endSelected = (String) endBox.getSelectedItem();

        // Create a model for startBox
        DefaultComboBoxModel<String> startModel = new DefaultComboBoxModel<>();
        // Add selected item back if exists
        if (startSelected != null) {
            startModel.addElement(startSelected);
        }
        // Add elements from inicioList to startModel
        for (int i = 0; i < PracticaViaje.inicioList.size(); i++) {
            startModel.addElement(PracticaViaje.inicioList.get(i));
        }
        // Set the startModel to startBox
        starBox.setModel(startModel);

        // Create a model for endBox
        DefaultComboBoxModel<String> endModel = new DefaultComboBoxModel<>();
        // Add selected item back if exists
        if (endSelected != null) {
            endModel.addElement(endSelected);
        }
        // Add elements from finList to endModel
        for (int i = 0; i < PracticaViaje.finList.size(); i++) {
            endModel.addElement(PracticaViaje.finList.get(i));
        }
        // Set the endModel to endBox
        endBox.setModel(endModel);
    }
    public void refreshRoutesTable() {
        DefaultTableModel DT = new DefaultTableModel(new String[]{"ID", "Inicio", "Final", "Distancia"}, PracticaViaje.viajar.size());
        jTable2.setModel(DT);
        TableModel tbModel = jTable2.getModel();

        for (int i = 0; i < PracticaViaje.viajar.size(); i++) {
            viaje ruta = PracticaViaje.viajar.get(i);
            tbModel.setValueAt(ruta.getId(), i, 0);
            tbModel.setValueAt(ruta.getStart(), i, 1);
            tbModel.setValueAt(ruta.getEnd(), i, 2);
            tbModel.setValueAt(ruta.getDistance(), i, 3);
        }

    }

    //Choose CSV File
    private void chooseCSVFile() throws IOException {
        // Create a file chooser
        fileChooser = new JFileChooser();

        // Set a file filter if needed (optional)
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
        fileChooser.setFileFilter(filter);

        // Show the file chooser dialog
        int result = fileChooser.showOpenDialog(this);

        // Check if a file was chosen
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            JFileSelected = fileChooser.getSelectedFile();

            // Print the path of the selected file
            System.out.println("Selected File: " + JFileSelected.getAbsolutePath());

            // Perform CSV read
            // Perform CSV read and pass the file name
            readCSV();
            
            loadRoutes();
        } else {
            System.out.println("No file selected");
        }
    }

// Check if the selected file is a CSV file
    private void readCSV() {
        if (!JFileSelected.getName().toLowerCase().endsWith(".csv")) {
            JOptionPane.showMessageDialog(null, "The selected file is not a CSV file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the name of the selected file
        String name = JFileSelected.getName();
        int routesCount = 0; // Counter for the number of routes added
        int ignoredRoutesCount = 0; // Counter for ignored routes
        boolean hadDuplicates = false; // Flag indicating if duplicates were found
        boolean hadLessThanThreeElements = false; // Flag indicating if a route had less than three elements

        try (BufferedReader br = new BufferedReader(new FileReader(JFileSelected.getAbsolutePath()))) {
            String line; // Store each line of the CSV file
            boolean firstLine = true; // Flag to skip the header
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the header
                }

                String[] values = line.split(","); // Split the line into parts using comma as delimiter
                if (values.length >= 3) { // Check if the line has at least three elements
                    String start = values[0]; // Extract start from the CSV
                    String end = values[1]; // Extract end from the CSV
                    int distance = Integer.parseInt(values[2]); // Extract distance from the CSV

                    if (!routeExists(start, end, distance)) { // Check if the route already exists
                        PracticaViaje.inicioList.add(start); // Add start to inicioList
                        PracticaViaje.finList.add(end); // Add end to finList
                        PracticaViaje.viajar.add(new viaje(PracticaViaje.generateId("route"), start, end, distance)); // Add a new Route object to routes
                        routesCount++; // Increment the routes counter
                    } else {
                        System.out.println("Route already exists: " + start + " to " + end);
                        ignoredRoutesCount++; // Increment ignored routes counter
                        hadDuplicates = true; // Set flag indicating duplicates were found
                    }
                } else {
                    System.out.println("The line doesn't have enough values: " + line);
                    ignoredRoutesCount++; // Increment ignored routes counter
                    hadLessThanThreeElements = true; // Set flag indicating a route had less than three elements
                }
                
            }

            // Make our lists bidirectional
            int totalRoutes = PracticaViaje.inicioList.size(); // Get the total number of routes
            for (int i = 0; i < totalRoutes; i++) {
                PracticaViaje.inicioList.add(PracticaViaje.finList.get(i)); // Add end to inicioList
               PracticaViaje.finList.add(PracticaViaje.inicioList.get(i)); // Add start to finList
            }

            // Create message for successful loading
            String message = "Loaded " + routesCount + " routes from (" + name + ") successfully.";
            // Append information about ignored routes if any
            if (ignoredRoutesCount > 0) {
                if (hadDuplicates && hadLessThanThreeElements) {
                    message += "\nIgnored " + ignoredRoutesCount + " routes because they were duplicates and had less than 3 elements.";
                } else if (hadDuplicates) {
                    message += "\nIgnored " + ignoredRoutesCount + " routes because they were duplicates.";
                } else if (hadLessThanThreeElements) {
                    message += "\nIgnored " + ignoredRoutesCount + " routes because they had less than 3 elements.";
                }
            }

            // Show a success message dialog
            JOptionPane.showMessageDialog(null, message, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            // Refresh the routes table
            refreshRoutesTable();
            // Load the routes into the ComboBoxes after reading the CSV file
            loadRoutes();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading the file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number format: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public DefaultTableModel getTableModel() {
        return (DefaultTableModel) jTable2.getModel();
    }

    private int findDistance(String startLocation, String endLocation) {
        // Buscar la distancia en la tabla de rutas
        for (viaje route : PracticaViaje.viajar) {
            if (route.getStart().equals(startLocation) && route.getEnd().equals(endLocation)) {
                return route.getDistance();
            }
        }
        return -1; // Retornar -1 si no se encuentra la ruta
    }
private boolean routeExists(String start, String end, int distance) {
        for (viaje viajar : PracticaViaje.viajar) {
            if (viajar.getStart().equals(start) && viajar.getEnd().equals(end) && viajar.getDistance() == distance) {
                return true;
            }
        }
        return false;
    }
    // Método para verificar si hay pilotos disponibles
    /*private boolean checkPilotsAvailability() {
        for (boolean available : pilotsAvailable) {
            if (available) {
                return true;
            }
        }
        return false;
    }*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        topestop = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        starBox = new javax.swing.JComboBox<>();
        endBox = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(200, 221, 242));
        jTabbedPane1.setForeground(new java.awt.Color(0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(18, 21, 38));

        jButton3.setForeground(new java.awt.Color(0, 0, 0));
        jButton3.setText("Carga rutas (.csv)");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 789, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton3.getAccessibleContext().setAccessibleName("");

        jTabbedPane1.addTab("Carga De Rutas", jPanel1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 835, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 688, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Historial de Viajes", jPanel3);

        jPanel2.setBackground(new java.awt.Color(234, 221, 215));
        jPanel2.setForeground(new java.awt.Color(234, 221, 215));
        jPanel2.setToolTipText("");

        topestop.setText("Tope");

        jButton2.setBackground(new java.awt.Color(0, 153, 255));
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setText("Iniciar");
        jButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Iniciar Todos ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 153, 255));
        jButton5.setForeground(new java.awt.Color(0, 0, 0));
        jButton5.setText("Iniciar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 153, 255));
        jButton6.setForeground(new java.awt.Color(0, 0, 0));
        jButton6.setText("Iniciar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 640, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(topestop, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topestop, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(138, 138, 138)
                        .addComponent(jButton2)
                        .addGap(142, 142, 142)
                        .addComponent(jButton5)
                        .addGap(110, 110, 110)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        topestop.getAccessibleContext().setAccessibleName("TOPE");

        jTabbedPane1.addTab("Iniciar Viajes", jPanel2);

        starBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        starBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                starBoxActionPerformed(evt);
            }
        });

        endBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Punto de inicio");

        jLabel2.setText("Punto Final");

        jLabel3.setText("Seleccionar Transporte");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(starBox, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(endBox, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(125, 125, 125))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(329, 329, 329)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(372, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(starBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(104, 104, 104)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(417, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Generar Viajes", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         try {
            chooseCSVFile(); // Attempt to open a file chooser dialog to select a CSV file.
        } catch (IOException ex) {
            // If an IOException occurs during file selection,
            // log the exception using the Java logging framework.
            java.util.logging.Logger.getLogger(Modulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
                
               
            
           
            
             
    }//GEN-LAST:event_jButton3ActionPerformed

//Boton inicio moto
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Moto moti= new Moto(this,moto.getX());
        moti.start();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        dragon carrito1= new dragon(this,carro1.getX());
        carrito1.start();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        mario carrito2= new mario(this,carro2.getX());
        carrito2.start();
    }//GEN-LAST:event_jButton6ActionPerformed
//boton iniciar todos 
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Moto moti= new Moto(this,moto.getX());
        moti.start();
        
         dragon carrito1= new dragon(this,carro1.getX());
        carrito1.start();
        
         mario carrito2= new mario(this,carro2.getX());
        carrito2.start();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void starBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_starBoxActionPerformed
        
    }//GEN-LAST:event_starBoxActionPerformed

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
            java.util.logging.Logger.getLogger(Modulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Modulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Modulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Modulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               new Modulo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> endBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JComboBox<String> starBox;
    public javax.swing.JLabel topestop;
    // End of variables declaration//GEN-END:variables
}
