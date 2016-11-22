/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuerraMundos;

import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;

/**
 *
 * @author Esteban
 */
public class JFrameGuerraMundos extends javax.swing.JFrame {

    
    /**
     * Creates new form JFrameGuerraMundos
     */
    public JFrameGuerraMundos() {
        try {
            // esto es parte del gato
            initComponents();
            generarTablero();
            
            // Cra una cliente que es su coenxion al server
            cliente = new Cliente(this);
            cliente.conexion();
            
            // pide el status al server, el server le enviara
            // al cliente el numero jugador que es y el nombre
            // enemigo
            cliente.salida.writeInt(3);
            
        } catch (IOException ex) {
           
        }
    }
    
    //----------------------------------
    Cliente cliente;
    //----------------------------------
    
    
    // cambiar este valor para dimensiones
    public static int DIMENSIONES = 15;
    // Tablero con objetos JButton
    JButton[][] tableroLabels = new JButton[DIMENSIONES][DIMENSIONES];
    // tablero logico, indica el status del boton, si disparado o no
    int[][] tableroLogico = new int[DIMENSIONES][DIMENSIONES];
    // crea imagen blanco
    ImageIcon iconoVacio = new ImageIcon(getClass().getResource("cvacio.GIF"));
    // crea imagen X
    ImageIcon iconoEquiz = new ImageIcon(getClass().getResource("cequiz.GIF"));
    // crea la imagen circulo
    ImageIcon iconoCirculo = new ImageIcon(getClass().getResource("ccirculo.GIF"));
    
    int turnoJugador=1;
    
    //numero de jugador 1 o 2
    int numeroJugador = 0;
    
    void generarTablero()
    {
        for(int i=0;i<DIMENSIONES;i++)
        {
            for(int j=0;j<DIMENSIONES;j++)
            {
                // coloca imagen a todos vacio
                tableroLabels[i][j] = new JButton(iconoVacio);
                //añade al panel el boton;
                pTableroJuego.add(tableroLabels[i][j]);
                // coloca dimensiones y localidad
                tableroLabels[i][j].setBounds(50*i, 50*j, 50, 50);
                // coloca el comand como i , j 
                tableroLabels[i][j].setActionCommand(i+","+j);//i+","+j
                
                //aclickSobreTablero(evt);ñade el listener al boton
                tableroLabels[i][j].addMouseListener(new MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                        
                    clickSobreTablero(evt);
                    
                }
                });
                // en logico indica estado en disponible
                tableroLogico[i][j]=0;
            }
        }
    }
    
    // reiniciar el juego es poner todo como en un inicio
    public void reiniciarJuego()
    {
        turnoJugador=1;
        for(int i=0;i<DIMENSIONES;i++)
        {
            for(int j=0;j<DIMENSIONES;j++)
            {
                tableroLabels[i][j].setIcon(iconoVacio);
                tableroLogico[i][j]=0;
            }
        }
    }
    
    // este metodo es la respuesta del cliente al clic del enemigo
    public void marcar(int columna, int fila)
    {
        // marca el tablero con num de jugador
        tableroLogico[columna][fila]=turnoJugador;
        // si soy el 1, marco con o que es el 2, sino con X
        // pues es el turno del enemigo que estoy marcando
        if (numeroJugador == 1)
            tableroLabels[columna][fila].setIcon(iconoCirculo);
        else
            tableroLabels[columna][fila].setIcon(iconoEquiz);
            
        // pregunta si gano el enemigo
            if(haGanado())
            {
                JOptionPane.showMessageDialog(null, "Ha ganado el jugador "+turnoJugador);
                
                reiniciarJuego();
            }          
        // este fue el clic del enemigo, marco ahora mi turno
        turnoJugador = numeroJugador;
        jLabel1.setText("Turno del Jugador "+turnoJugador);
        
        
//        // es similar a validar si el disparo es bomba o barco
//        if (Integer.parseInt(txfColumna.getText()) == columna && 
//                Integer.parseInt(txfFila.getText()) == fila)
//        {
//            try {
//                //escribe la opcion 5 al server
//                // para que la pase al enemigo
//                // y haga el metodo de generar bombas
//                cliente.salida.writeInt(5);
//                cliente.salida.writeInt(columna);
//                cliente.salida.writeInt(fila);
//                
//            } catch (IOException ex) {
//                
//            }
//        
//        }
        
    }
    
    public void bomba(int col, int fila)
    {
        JOptionPane.showMessageDialog(this, "Generar bombas y enviarlas una " +
                "a una al enemigo ("+col+","+fila+")");
    }
    
    public void clickSobreTablero(java.awt.event.MouseEvent evt)
    {
        // obtiene el boton 
        JButton botonTemp = (JButton)evt.getComponent();
        // obtiene el i,j de action command del boton
        String identificadorBoton = botonTemp.getActionCommand();
        
        // separa el string del action comand para obtener columnas
        int columna = 
          Integer.parseInt(identificadorBoton.substring(0,identificadorBoton.indexOf(",")));
        int fila = 
          Integer.parseInt(identificadorBoton.substring(1+identificadorBoton.indexOf(",")));
        
        // si ya se disparo entonces nada
        if(tableroLogico[columna][fila]!=0)
            return;
        
        // si es mi turno continua, si no return
        if (numeroJugador != turnoJugador)
            return;
        
        // como es turno del cliente marca el logico con su numero
        tableroLogico[columna][fila]=turnoJugador;
        // si era el jugador 1 marca con x y cambia el turno a jugador 2
        if (numeroJugador == 1)
        {
            
            tableroLabels[columna][fila].setIcon(iconoEquiz);
            turnoJugador=2;
        }
        else
        {
            // si era jugador 3, marca circulo y turno jugador 1
            tableroLabels[columna][fila].setIcon(iconoCirculo);
            turnoJugador=1;
        }
        // muestra el turno del jugador
         jLabel1.setText("Turno del Jugador "+turnoJugador);
        
        try {
            // como el cliente dio clic debe enviar al servidor las coordenadas
            // el servidor se las pasara al thread cliente para que este
            // las muestre (haga el marcar)
            // envia las coordenadas
            cliente.salida.writeInt(1);
            cliente.salida.writeInt(columna);
            cliente.salida.writeInt(fila);
        } catch (IOException ex) {
            
        }
         
        // si gano el jugador 1 lo indica
        if(haGanado())
        {
            JOptionPane.showMessageDialog(null, "Ha ganado el jugador 1");
            reiniciarJuego();
        }      
    }
    
    
    boolean haGanado()
    {
        
        //Ganó en las filas
        for(int i=0;i<3;i++)
        {
        if ((tableroLogico[i][0]==tableroLogico[i][1])
                &&(tableroLogico[i][1]==tableroLogico[i][2])
                && !(tableroLogico[i][0]==0))
        {
            return true;
        }
        }
        
        //Gano en las columnas
        for(int i=0;i<3;i++)
        {
        if ((tableroLogico[0][i]==tableroLogico[1][i])
                &&(tableroLogico[1][i]==tableroLogico[2][i])
                && !(tableroLogico[0][i]==0))
        {
            return true;
        }
        }
        //Verificar diagonal 1
        if ((tableroLogico[0][0]==tableroLogico[1][1])
                &&(tableroLogico[1][1]==tableroLogico[2][2])
                && !(tableroLogico[0][0]==0))
            return true;
        
        //Verificar diagonal 2
        if ((tableroLogico[2][0]==tableroLogico[1][1])
                &&(tableroLogico[1][1]==tableroLogico[0][2])
                && !(tableroLogico[2][0]==0))
            return true;
        
        return false;
    }
    
    // set el nombre del enemigo
    public void setEnemigo(String enem)
    {
        lblEnemigo.setText("vs. "+enem);
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
        jLabel1 = new javax.swing.JLabel();
        lblEnemigo = new javax.swing.JLabel();
        txfMensaje = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaMensajes = new javax.swing.JTextArea();
        btnEnviar = new javax.swing.JButton();
        enviarMensajeJ1 = new javax.swing.JButton();
        enviarMensajeJ2 = new javax.swing.JButton();
        enviarMensajeJ3 = new javax.swing.JButton();
        bMundo = new javax.swing.JButton();
        bConector = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        bMisil = new javax.swing.JButton();
        bMultiShot = new javax.swing.JButton();
        bBomba = new javax.swing.JButton();
        bComboShot = new javax.swing.JButton();
        pTableroJuego = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        bMercado = new javax.swing.JButton();
        bMina = new javax.swing.JButton();
        bArmeria = new javax.swing.JButton();
        bTemplo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Turno del Jugador: Jugador 1");

        lblEnemigo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblEnemigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txaMensajes.setColumns(20);
        txaMensajes.setRows(5);
        jScrollPane1.setViewportView(txaMensajes);

        btnEnviar.setText("Enviar Todos");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        enviarMensajeJ1.setText("Enviar Jugador 1");
        enviarMensajeJ1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarMensajeJ1ActionPerformed(evt);
            }
        });

        enviarMensajeJ2.setText("Enviar Jugador 2");
        enviarMensajeJ2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarMensajeJ2ActionPerformed(evt);
            }
        });

        enviarMensajeJ3.setText("Enviar Jugador 3");
        enviarMensajeJ3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarMensajeJ3ActionPerformed(evt);
            }
        });

        bMundo.setText("Mundo");
        bMundo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMundoActionPerformed(evt);
            }
        });

        bConector.setText("Conector");

        bMisil.setText("Misil");

        bMultiShot.setText("MultiShot");

        bBomba.setText("Bomba");

        bComboShot.setText("ComboShot");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(bMisil, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                        .addComponent(bMultiShot, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(bBomba, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bComboShot, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(bMisil, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(bMultiShot, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(bBomba, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(bComboShot, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pTableroJuegoLayout = new javax.swing.GroupLayout(pTableroJuego);
        pTableroJuego.setLayout(pTableroJuegoLayout);
        pTableroJuegoLayout.setHorizontalGroup(
            pTableroJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 775, Short.MAX_VALUE)
        );
        pTableroJuegoLayout.setVerticalGroup(
            pTableroJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 762, Short.MAX_VALUE)
        );

        bMercado.setText("Mercado");

        bMina.setText("Mina");

        bArmeria.setText("Armería");

        bTemplo.setText("Templo");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bMercado, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(bArmeria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bTemplo, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                    .addComponent(bMina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bMina, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                    .addComponent(bMercado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bArmeria, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bTemplo, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7))
        );

        jLabel2.setText("Fábricas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pTableroJuego, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                                            .addComponent(txfMensaje))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(enviarMensajeJ1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(enviarMensajeJ2)
                                            .addComponent(enviarMensajeJ3, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblEnemigo, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(122, 122, 122))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(bMundo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(bConector, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEnemigo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txfMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(enviarMensajeJ1)
                                    .addComponent(enviarMensajeJ2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnEnviar)
                                    .addComponent(enviarMensajeJ3))))
                        .addGap(43, 43, 43))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bMundo, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bConector, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pTableroJuego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bMundoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMundoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bMundoActionPerformed

    private void enviarMensajeJ3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarMensajeJ3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_enviarMensajeJ3ActionPerformed

    private void enviarMensajeJ2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarMensajeJ2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_enviarMensajeJ2ActionPerformed

    private void enviarMensajeJ1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarMensajeJ1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_enviarMensajeJ1ActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        try {
            // se toma lo escrito
            String mensaje = txfMensaje.getText();
            // se muestra en el text area
            txaMensajes.append(cliente.nomCliente+"> "+ mensaje + "\n");
            // se limpia el textfield
            txfMensaje.setText("");

            // envia al server la opcion 4 para que le pase al enemigo
            // lo escrito
            cliente.salida.writeInt(4);
            // le envia el mensaje
            cliente.salida.writeUTF(cliente.nomCliente+"> "+mensaje);

        } catch (IOException ex) {

        }
    }//GEN-LAST:event_btnEnviarActionPerformed

                                     

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameGuerraMundos().setVisible(true);
            }
        });
    }
    public void mostrar(String texto)
    {
        txaMensajes.append(texto+"\n");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bArmeria;
    private javax.swing.JButton bBomba;
    private javax.swing.JButton bComboShot;
    private javax.swing.JButton bConector;
    private javax.swing.JButton bMercado;
    private javax.swing.JButton bMina;
    private javax.swing.JButton bMisil;
    private javax.swing.JButton bMultiShot;
    private javax.swing.JButton bMundo;
    private javax.swing.JButton bTemplo;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton enviarMensajeJ1;
    private javax.swing.JButton enviarMensajeJ2;
    private javax.swing.JButton enviarMensajeJ3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEnemigo;
    private javax.swing.JPanel pTableroJuego;
    private javax.swing.JTextArea txaMensajes;
    private javax.swing.JTextField txfMensaje;
    // End of variables declaration//GEN-END:variables
}
