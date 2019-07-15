// package simulador;

// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.io.PrintStream;
// import java.io.StringReader;
// import java.net.InetAddress;
// import java.net.Socket;
// import java.net.URL;
// import java.net.URLEncoder;
// import java.text.DateFormat;
// import java.text.SimpleDateFormat;
// import java.time.Duration;
// import java.time.LocalDateTime;
// import java.time.ZoneId;
// import java.time.temporal.ChronoUnit;
// import java.util.Date;

// public class MainFrame extends javax.swing.JFrame {

//     private static final long serialVersionUID = 1L;
//     static MainFrame mainFrame;
//     static Transmissor transmissor;
//     public static int PERIODO_TRANSMISSAO = 1000;
//     public static String strURLdestino = "";
//     public Date date1;

//     public MainFrame() {
//         initComponents();
//         DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//         date1 = new Date();
//         DatahoraInicio_text.setText(dateFormat1.format(date1));
//     }

//     @SuppressWarnings("unchecked")
//     // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//     private void initComponents() {

//         comboBoxDestino = new javax.swing.JComboBox<>();
//         textDestino = new javax.swing.JTextField();
//         botaoParar = new javax.swing.JButton();
//         botaoTransmitir = new javax.swing.JButton();
//         javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
//         textArea = new javax.swing.JTextArea();
//         javax.swing.JLabel labelDestino = new javax.swing.JLabel();
//         javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
//         javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
//         javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
//         DatahoraInicio_text = new javax.swing.JTextField();
//         deltaTvirtual_text = new javax.swing.JTextField();
//         deltaTreal_text = new javax.swing.JTextField();
//         javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
//         javax.swing.JLabel jLabel5 = new javax.swing.JLabel();

//         setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//         setTitle("Simulador 01");
//         setMinimumSize(new java.awt.Dimension(840, 826));
//         setPreferredSize(new java.awt.Dimension(840, 826));
//         setResizable(false);
//         getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

//         comboBoxDestino.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
//         comboBoxDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "http://localhost:8080/servlet_de_leitura/leitura" }));
//         comboBoxDestino.addActionListener(new java.awt.event.ActionListener() {
//             public void actionPerformed(java.awt.event.ActionEvent evt) {
//                 comboBoxDestinoActionPerformed(evt);
//             }
//         });
//         getContentPane().add(comboBoxDestino, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 770, -1));

//         textDestino.setBackground(new java.awt.Color(51, 51, 51));
//         textDestino.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
//         textDestino.setForeground(new java.awt.Color(0, 255, 255));
//         getContentPane().add(textDestino, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 770, -1));

//         botaoParar.setBackground(new java.awt.Color(255, 0, 0));
//         botaoParar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
//         botaoParar.setForeground(new java.awt.Color(255, 255, 255));
//         botaoParar.setText("PARAR");
//         botaoParar.addActionListener(new java.awt.event.ActionListener() {
//             public void actionPerformed(java.awt.event.ActionEvent evt) {
//                 botaoPararActionPerformed(evt);
//             }
//         });
//         getContentPane().add(botaoParar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 150, 280, -1));

//         botaoTransmitir.setBackground(new java.awt.Color(0, 255, 0));
//         botaoTransmitir.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
//         botaoTransmitir.setText("TRANSMITIR");
//         botaoTransmitir.addActionListener(new java.awt.event.ActionListener() {
//             public void actionPerformed(java.awt.event.ActionEvent evt) {
//                 botaoTransmitirActionPerformed(evt);
//             }
//         });
//         getContentPane().add(botaoTransmitir, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 280, -1));

//         textArea.setColumns(20);
//         textArea.setRows(5);
//         jScrollPane1.setViewportView(textArea);

//         getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 770, 360));

//         labelDestino.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
//         labelDestino.setText("URL do receptor do servidor TempUmidade");
//         getContentPane().add(labelDestino, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 30, 500, -1));

//         jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
//         jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
//         jLabel1.setText("DataHora início:");
//         getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 180, -1));

//         jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
//         jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
//         jLabel2.setText("Delta t virtual:");
//         getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 180, -1));

//         jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
//         jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
//         jLabel3.setText("Delta t real:");
//         getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 180, -1));

//         DatahoraInicio_text.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
//         DatahoraInicio_text.setHorizontalAlignment(javax.swing.JTextField.CENTER);
//         DatahoraInicio_text.addActionListener(new java.awt.event.ActionListener() {
//             public void actionPerformed(java.awt.event.ActionEvent evt) {
//                 DatahoraInicio_textActionPerformed(evt);
//             }
//         });
//         getContentPane().add(DatahoraInicio_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 320, 270, -1));

//         deltaTvirtual_text.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
//         deltaTvirtual_text.setHorizontalAlignment(javax.swing.JTextField.CENTER);
//         deltaTvirtual_text.setText("30");
//         deltaTvirtual_text.addActionListener(new java.awt.event.ActionListener() {
//             public void actionPerformed(java.awt.event.ActionEvent evt) {
//                 deltaTvirtual_textActionPerformed(evt);
//             }
//         });
//         getContentPane().add(deltaTvirtual_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 220, 270, -1));

//         deltaTreal_text.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
//         deltaTreal_text.setHorizontalAlignment(javax.swing.JTextField.CENTER);
//         deltaTreal_text.setText("30");
//         deltaTreal_text.addActionListener(new java.awt.event.ActionListener() {
//             public void actionPerformed(java.awt.event.ActionEvent evt) {
//                 deltaTreal_textActionPerformed(evt);
//             }
//         });
//         getContentPane().add(deltaTreal_text, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 270, 270, -1));

//         jLabel4.setText("(segundos)");
//         getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 280, -1, -1));

//         jLabel5.setText("(segundos)");
//         getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 230, -1, -1));

//         pack();
//     }// </editor-fold>//GEN-END:initComponents

//     private void botaoTransmitirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoTransmitirActionPerformed
//         strURLdestino = textDestino.getText();
//         URL urlDestino = null;
//         try {
//             urlDestino = new URL(strURLdestino);
//         } catch (Exception e) {
//             //e.printStackTrace();
//             textArea.append("\n=== NÃO CONSEGUIU LER A URL DE DESTINO!\n\n");
//             return;
//         }
//         transmissor = new Transmissor(strURLdestino);
//         transmissor.start();
//     }//GEN-LAST:event_botaoTransmitirActionPerformed

//     private void botaoPararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoPararActionPerformed
//         transmissor.terminar();
//     }//GEN-LAST:event_botaoPararActionPerformed

//     private void comboBoxDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxDestinoActionPerformed
//         textDestino.setText((String) comboBoxDestino.getSelectedItem());
//     }//GEN-LAST:event_comboBoxDestinoActionPerformed

//     private void DatahoraInicio_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DatahoraInicio_textActionPerformed
//         // TODO add your handling code here:
//     }//GEN-LAST:event_DatahoraInicio_textActionPerformed

//     private void deltaTvirtual_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deltaTvirtual_textActionPerformed
//         // TODO add your handling code here:
//     }//GEN-LAST:event_deltaTvirtual_textActionPerformed

//     private void deltaTreal_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deltaTreal_textActionPerformed
//         // TODO add your handling code here:
//     }//GEN-LAST:event_deltaTreal_textActionPerformed

//     public static void main(String args[]) {
//         /* Set the Nimbus look and feel */
//         //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//         /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//          * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//          */
//         try {
//             for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                 if ("Nimbus".equals(info.getName())) {
//                     javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                     break;
//                 }
//             }
//         } catch (ClassNotFoundException ex) {
//             java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//         } catch (InstantiationException ex) {
//             java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//         } catch (IllegalAccessException ex) {
//             java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//         } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//             java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//         }
//         //</editor-fold>

//         java.awt.EventQueue.invokeLater(new Runnable() {
//             public void run() {
//                 mainFrame = new MainFrame();
//                 mainFrame.setVisible(true);
//                 MainFrame.PERIODO_TRANSMISSAO
//                         = 1000 * Integer.parseInt(mainFrame.deltaTreal_text.getText());
//             }
//         });
//     }

//     // Variables declaration - do not modify//GEN-BEGIN:variables
//     public javax.swing.JTextField DatahoraInicio_text;
//     public javax.swing.JButton botaoParar;
//     public javax.swing.JButton botaoTransmitir;
//     public javax.swing.JComboBox<String> comboBoxDestino;
//     public javax.swing.JTextField deltaTreal_text;
//     public javax.swing.JTextField deltaTvirtual_text;
//     public javax.swing.JTextArea textArea;
//     public javax.swing.JTextField textDestino;
//     // End of variables declaration//GEN-END:variables
// }

// class Transmissor extends Thread {

//     URL urlDestino = null;
//     String strURLdestino;
//     boolean continuar = true;
//     long serial = 0;
//     InetAddress remoteAddress;
//     BufferedReader entrada;
//     PrintStream saida;

//     public void terminar() {
//         continuar = false;
//     }

//     public Transmissor(String strURLdestino) {
//         this.strURLdestino = strURLdestino;
//         try {
//             this.urlDestino = new URL(strURLdestino);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     @Override
//     public void run() {
//         try {
//             Socket sCliente;

//             MainFrame.mainFrame.textArea.setText("- Iniciou transmissor para "
//                     + strURLdestino + "\n");
//             MainFrame.PERIODO_TRANSMISSAO = 
//                     1000 * Integer.parseInt(MainFrame.mainFrame.deltaTreal_text.getText());

//             DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//             MainFrame.mainFrame.date1 = dateFormat.parse(
//                     MainFrame.mainFrame.DatahoraInicio_text.getText());

//             while (continuar) {
//                 serial++;
//                 try {
//                     Date date = new Date();
//                     Double d_temperatura = 30.0D + 10.0D * (1.0D
//                             + (double) Math.sin(Math.PI * (1D / 16D) * Double.parseDouble(
//                                     Long.toString((long) MainFrame.mainFrame.date1.getTime() / 1000))));
//                     Double d_umidade = 65.0D + 15.0D * (1.0D
//                             + (double) Math.sin(Math.PI * (1D / 16D) * Double.parseDouble(
//                                     Long.toString((long) MainFrame.mainFrame.date1.getTime() / 1000))));

//                     MainFrame.mainFrame.textArea.append(
//                             "serial:" + serial
//                             + "  datahora: " + dateFormat.format(MainFrame.mainFrame.date1)
//                             + "  temperatura: " + d_temperatura + "\n");
//                     String requestData
//                             = "medidor=medidor001"
//                             + "&temperatura=" + Double.toString(d_temperatura)
//                             + "&umidade=" + Double.toString(d_umidade)
//                             + "&datahora=" + URLEncoder.encode(
//                                     dateFormat.format(MainFrame.mainFrame.date1), "UTF-8")
//                             + "&serial=" + Long.toString(serial);

//                     LocalDateTime ldt = LocalDateTime.ofInstant(
//                             MainFrame.mainFrame.date1.toInstant(), ZoneId.systemDefault());
//                     LocalDateTime dateTime = ldt.plus(
//                             Duration.of(Integer.parseInt(
//                                     MainFrame.mainFrame.deltaTvirtual_text.getText()),
//                                     ChronoUnit.SECONDS));
//                     MainFrame.mainFrame.date1 = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

//                     int port = urlDestino.getPort();
//                     String strPort = "";
//                     if (port != -1) {
//                         strPort = ":" + Integer.toString(port);
//                     } else {
//                         port = 80;
//                     }

//                     remoteAddress = InetAddress.getByName(urlDestino.getHost());
//                     sCliente = new Socket(remoteAddress, port);
//                     saida = new PrintStream(sCliente.getOutputStream());
//                     entrada = new BufferedReader(
//                             new InputStreamReader(
//                                     sCliente.getInputStream()));

//                     String request
//                             = "POST " + strURLdestino + " HTTP/1.1\n"
//                             + "Host: " + urlDestino.getHost() + strPort + "\n"
//                             + "Keep-Alive: 115\n"
//                             + "Connection: keep-alive\n"
//                             + "Content-Type: application/x-www-form-urlencoded\n"
//                             + "Content-Length: " + requestData.length() + "\n"
//                             + "\n"
//                             + requestData;
//                     System.out.println("=== request HTTP:\n" + request);
//                     saida.print(request);
//                     saida.flush();
// //                sCliente.shutdownOutput();
// //System.out.println("\n============== ENVIOU POST");

//                     char[] buff = new char[4096];
//                     int tam = 0;
//                     int contador = 0;
//                     while (!entrada.ready() && contador < 200) {
//                         contador++;
//                         MainFrame.mainFrame.textArea.append(".");
//                         Thread.sleep(100);
//                     }
//                     MainFrame.mainFrame.textArea.append("--- " + contador + "\n");
//                     tam = entrada.read(buff, 0, 4096);
//                     if (tam == -1) {
//                         // break;
//                         MainFrame.mainFrame.textArea.append("===== NÃO OBTEVE RESPOSTA\n");
// //System.out.println("===== -1 ===== NÃO OBTEVE RESPOSTA");
//                     } else {
//                         String linha = new String(buff, 0, tam);
//                         linha = linha + "\n";
//                         BufferedReader br = new BufferedReader(new StringReader(linha));
//                         String sublinha = "";
//                         while (true) {
//                             sublinha = br.readLine();
//                             if (sublinha.isEmpty()) {
//                                 sublinha = br.readLine();
//                                 MainFrame.mainFrame.textArea.append("Resposta: " + sublinha + "\n");
//                                 break;
//                             }
//                         }
//                         System.out.println("=== Resposta\n" + linha + "\n");
//                     }
//                     sCliente.close();
//                     Thread.sleep(MainFrame.PERIODO_TRANSMISSAO);
//                 } catch (Exception e) {
//                     e.printStackTrace();
//                     MainFrame.mainFrame.textArea.append("- terminou transmissor com ERRO.\n");
//                     return;
//                 }
//             }
//             MainFrame.mainFrame.textArea.append("- terminou transmissor.\n");
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
