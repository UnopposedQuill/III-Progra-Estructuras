/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

/**
 *
 * @author Esteban
 */
public class JFrameServidor extends javax.swing.JFrame {

    ServidorMundos servidor;
    /**
     * Creates new form JFrameServidor
     */
    public JFrameServidor() {
        initComponents();
        servidor = new ServidorMundos(this);
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
        txaMensajesServidor = new javax.swing.JTextArea();
        b2Jugadores = new javax.swing.JButton();
        b3Jugadores = new javax.swing.JButton();
        b4Jugadores = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txaMensajesServidor.setColumns(20);
        txaMensajesServidor.setRows(5);
        jScrollPane1.setViewportView(txaMensajesServidor);

        b2Jugadores.setText("2 Jugadores");
        b2Jugadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2JugadoresActionPerformed(evt);
            }
        });

        b3Jugadores.setText("3 Jugadores");
        b3Jugadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3JugadoresActionPerformed(evt);
            }
        });

        b4Jugadores.setText("4 Jugadores");
        b4Jugadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b4JugadoresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(b2Jugadores)
                        .addGap(51, 51, 51)
                        .addComponent(b3Jugadores)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addComponent(b4Jugadores)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b2Jugadores)
                    .addComponent(b3Jugadores)
                    .addComponent(b4Jugadores))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b2JugadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b2JugadoresActionPerformed
        // TODO add your handling code here:
        b2Jugadores.setEnabled(false);
        b3Jugadores.setEnabled(false);
        b4Jugadores.setEnabled(false);
    }//GEN-LAST:event_b2JugadoresActionPerformed

    private void b3JugadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3JugadoresActionPerformed
        // TODO add your handling code here:        
        b2Jugadores.setEnabled(false);
        b3Jugadores.setEnabled(false);
        b4Jugadores.setEnabled(false);
    }//GEN-LAST:event_b3JugadoresActionPerformed

    private void b4JugadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b4JugadoresActionPerformed
        // TODO add your handling code here:
        b2Jugadores.setEnabled(false);
        b3Jugadores.setEnabled(false);
        b4Jugadores.setEnabled(false);
    }//GEN-LAST:event_b4JugadoresActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        int cantidadJugadores = 2;
        DecidirJugador decision = new DecidirJugador();
        cantidadJugadores = decision.decidirCantidadJugadores();
        System.out.print(cantidadJugadores);
        
        JFrameServidor server = new JFrameServidor();
        server.setVisible(true);
        //corre el servidor
        server.servidor.runServer(cantidadJugadores);
    }
    
    public void desplegarMensaje (String texto)
    {
        txaMensajesServidor.append(texto+"\n");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b2Jugadores;
    private javax.swing.JButton b3Jugadores;
    private javax.swing.JButton b4Jugadores;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txaMensajesServidor;
    // End of variables declaration//GEN-END:variables
}


