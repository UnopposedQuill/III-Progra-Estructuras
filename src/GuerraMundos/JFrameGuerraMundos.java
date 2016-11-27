/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuerraMundos;

import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

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
            cliente.salida.flush();
            
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
    
    int[][] tableroEnemigo1 = new int[DIMENSIONES][DIMENSIONES];
    int[][] tableroEnemigo2 = new int[DIMENSIONES][DIMENSIONES];
    int[][] tableroEnemigo3 = new int[DIMENSIONES][DIMENSIONES];
    // crea imagen blanco
    ImageIcon iconoVacio = new ImageIcon(getClass().getResource("cvacio.GIF"));
    // crea imagen X
    ImageIcon iconoEquiz = new ImageIcon(getClass().getResource("cequiz.GIF"));
    // crea la imagen circulo
    ImageIcon iconoCirculo = new ImageIcon(getClass().getResource("ccirculo.GIF"));
    
    int componenteColocar = 0;
    int orientacionColocarFabrica = 0;
    int dineroJugador = 4000;
    int turnoJugador = 1;
    //numero de jugador 1 a 4
    int numeroJugador = 0;
    
    /*
    Misil (1x1) = 0 Se ocupan 500 KG de acero
    MultiShot (Acierta y crea 4 disparos mas al azar 1x1) = 1 Se ocupan 1000 KG de acero
    Bomba (3 bombas con un rango 1x2 o 2x1 al azar) = 2 Se ocupan 2000 KG de acero
    ComboShot (10 disparos de misil en un turno) = 3 Se ocupan 5000 KG de acero
    */
    int tipoDisparo = 0;
    int cantidadDisparosRestantes = 0;
    
    int mundoInicial = 1;
    int mercadoInicial = 1;;

    
    boolean atacandoEnemigo1 = false;
    boolean atacandoEnemigo2 = false;
    boolean atacandoEnemigo3 = false;
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
                @Override
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
                tableroLogico[i][j] = 0;
            }
        }
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
        

        // Si es equivalente a 0 es porque no hay nada y se puede colocar un componente
        if(tableroLogico[columna][fila]!=0)
            return;

        /*
        Misil (1x1) = 0 Se ocupan 500 KG de acero
        MultiShot (Acierta y crea 4 disparos mas al azar 1x1) = 1 Se ocupan 1000 KG de acero
        Bomba (3 bombas con un rango 1x2 o 2x1 al azar) = 2 Se ocupan 2000 KG de acero
        ComboShot (10 disparos de misil en un turno) = 3 Se ocupan 5000 KG de acero
        */
        if(atacandoEnemigo1 == true){
            //Se está atacando al enemigo 1
            
            
        }
        else if (atacandoEnemigo2 == true){
            //Se está atacando al enemigo 1
            
            
        }
        else if (atacandoEnemigo3 == true){
            //Se está atacando al enemigo 1
            
            
        }
        else{
            switch (componenteColocar) {
                case 1://El mundo es 4x4
                    if (dineroJugador >= 12000 || mundoInicial == 1){
                        //Debo revisar si hay espacio
                        if (columna != DIMENSIONES-1 && fila != DIMENSIONES-1 && tableroLogico[columna+1][fila] == 0 && tableroLogico[columna][fila+1] == 0 && tableroLogico[columna+1][fila+1] == 0){

                            tableroLogico[columna][fila]= 21;//Mundo Izquierda Superior
                            tableroLogico[columna+1][fila]= 22;//Mundo Derecha Superior
                            tableroLogico[columna][fila+1]= 23;//Mundo Izquierda Inferior
                            tableroLogico[columna+1][fila+1]= 24;//Mundo Derecha Inferior

                            tableroLabels[columna][fila].setIcon(iconoCirculo);
                            tableroLabels[columna+1][fila].setIcon(iconoCirculo);//Mundo Derecha Superior
                            tableroLabels[columna][fila+1].setIcon(iconoCirculo);//Mundo Izquierda Inferior
                            tableroLabels[columna+1][fila+1].setIcon(iconoCirculo);//Mundo Derecha Inferior
                            actualizarTablerosEnemigos();
                            if (mundoInicial != 1){
                                dineroJugador -= 12000; 
                            }
                            else{
                                mundoInicial = 0;
                            }
                        }
                    }
                    break;
                case 2:
                    if (dineroJugador >= 100){
                        tableroLogico[columna][fila]= componenteColocar;//Conector
                        tableroLabels[columna][fila].setIcon(iconoCirculo);
                        actualizarTablerosEnemigos();
                        dineroJugador -= 100;
                    }

                    break;
                case 3://Las fabricas son 2x1 o 1x2
                    if (dineroJugador >= 2000 || mercadoInicial == 1){
                        if (orientacionColocarFabrica == 0 && fila != DIMENSIONES-1 && tableroLogico[columna][fila+1] == 0){//Vertical
                            tableroLogico[columna][fila]= 34;//Mercado
                            tableroLogico[columna][fila+1] = 33;//Mercado

                            tableroLabels[columna][fila].setIcon(iconoCirculo);
                            tableroLabels[columna][fila+1].setIcon(iconoCirculo);
                            actualizarTablerosEnemigos();

                            if (mercadoInicial != 1){
                                dineroJugador -= 2000; 
                            }
                            else{
                                mercadoInicial = 0;
                            }
                        }
                        else if (orientacionColocarFabrica == 1 && columna != DIMENSIONES-1 && tableroLogico[columna+1][fila] == 0){//Horizontal
                            tableroLogico[columna][fila]= 31;//Mercado
                            tableroLogico[columna+1][fila]= 32;//Mercado

                            tableroLabels[columna][fila].setIcon(iconoCirculo);
                            tableroLabels[columna+1][fila].setIcon(iconoCirculo);
                            actualizarTablerosEnemigos();
                            
                            if (mercadoInicial != 1){
                                dineroJugador -= 2000; 
                            }
                            else{
                                mercadoInicial = 0;
                            }
                        }
                    }
                    break;
                case 4://Mina
                    if (dineroJugador >= 1000){
                        if (orientacionColocarFabrica == 0 && fila != DIMENSIONES-1 && tableroLogico[columna][fila+1] == 0){//Vertical
                            tableroLogico[columna][fila]= 44;
                            tableroLogico[columna][fila+1] = 43;

                            tableroLabels[columna][fila].setIcon(iconoCirculo);
                            tableroLabels[columna][fila+1].setIcon(iconoCirculo);
                            actualizarTablerosEnemigos();
                            dineroJugador -= 1000;
                        }
                        else if (orientacionColocarFabrica == 1 && columna != DIMENSIONES-1 && tableroLogico[columna+1][fila] == 0){//Horizontal
                            tableroLogico[columna][fila]= 41;
                            tableroLogico[columna+1][fila]= 42;

                            tableroLabels[columna][fila].setIcon(iconoCirculo);
                            tableroLabels[columna+1][fila].setIcon(iconoCirculo);
                            actualizarTablerosEnemigos();
                            dineroJugador -= 1000;
                        }
                    }
                    break;
                case 5://Armería
                    if (dineroJugador >= 1500){
                        if (orientacionColocarFabrica == 0 && fila != DIMENSIONES-1 && tableroLogico[columna][fila+1] == 0){//Vertical
                            tableroLogico[columna][fila]= 54;
                            tableroLogico[columna][fila+1] = 53;

                            tableroLabels[columna][fila].setIcon(iconoCirculo);
                            tableroLabels[columna][fila+1].setIcon(iconoCirculo);
                            actualizarTablerosEnemigos();
                            dineroJugador -= 1500;
                        }
                        else if (orientacionColocarFabrica == 1 && columna != DIMENSIONES-1 && tableroLogico[columna+1][fila] == 0){//Horizontal
                            tableroLogico[columna][fila]= 51;
                            tableroLogico[columna+1][fila]= 52;

                            tableroLabels[columna][fila].setIcon(iconoCirculo);
                            tableroLabels[columna+1][fila].setIcon(iconoCirculo);
                            actualizarTablerosEnemigos();
                            dineroJugador -= 1500;
                        }

                    }
                    break;
                case 6://Templo
                    if (dineroJugador >= 2500){
                        if (orientacionColocarFabrica == 0 && fila != DIMENSIONES-1 && tableroLogico[columna][fila+1] == 0){//Vertical
                            tableroLogico[columna][fila]= 64;
                            tableroLogico[columna][fila+1] = 63;

                            tableroLabels[columna][fila].setIcon(iconoCirculo);
                            tableroLabels[columna][fila+1].setIcon(iconoCirculo);
                            actualizarTablerosEnemigos();
                            dineroJugador -= 2500;
                        }
                        else if (orientacionColocarFabrica == 1 && columna != DIMENSIONES-1 && tableroLogico[columna+1][fila] == 0){//Horizontal
                            tableroLogico[columna][fila]= 61;
                            tableroLogico[columna+1][fila]= 62;

                            tableroLabels[columna][fila].setIcon(iconoCirculo);
                            tableroLabels[columna+1][fila].setIcon(iconoCirculo);
                            actualizarTablerosEnemigos();
                            dineroJugador -= 2500;
                        }

                    }
                    break;
                default:
                    break;
            }
        }
        
        
        lDineroActual.setText("Dinero Actual: "+ dineroJugador +"$");
        // muestra el turno del jugador
        jLabel1.setText("Turno del Jugador "+turnoJugador);
 
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
        
    }
    public void actualizarTablerosEnemigos(){
        try {
            System.out.println("TABLERO Logico");
                    for (int i = 0; i < 15 ; i++){
                        for (int j = 0; j < 15 ; j++){
                            System.out.print(tableroLogico[j][i]);
                        }
                        System.out.println();
                    }
            cliente.salida.writeInt(6);
            cliente.salida.writeInt(numeroJugador);
            cliente.salida.reset();
            cliente.salida.writeUnshared(tableroLogico);
 
            
        } catch (IOException ex) {
            Logger.getLogger(JFrameGuerraMundos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarTablero(int indicador){
        if (indicador == 0){//Es mi popio mapa
            for(int i=0;i<DIMENSIONES;i++){
                for(int j=0;j<DIMENSIONES;j++){

                    if (tableroLogico[j][i] == 0)
                        tableroLabels[j][i].setIcon(iconoVacio);
                    else if (tableroLogico[j][i] != 0){
                        tableroLabels[j][i].setIcon(iconoCirculo);
                    }
                }
            }
        }
        
        else if (indicador == 1){//Enemigo 1
            for(int i=0;i<DIMENSIONES;i++){
                for(int j=0;j<DIMENSIONES;j++){

                    if (tableroEnemigo1[j][i] == 0)
                        tableroLabels[j][i].setIcon(iconoVacio);
                    else if (tableroEnemigo1[j][i] != 0){
                        tableroLabels[j][i].setIcon(iconoEquiz);
                    }
                }
            }
        }
        
        else if (indicador == 2){//Enemigo 2
            for(int i=0;i<DIMENSIONES;i++){
                for(int j=0;j<DIMENSIONES;j++){

                    if (tableroEnemigo2[j][i] == 0)
                        tableroLabels[j][i].setIcon(iconoVacio);
                    else if (tableroEnemigo2[j][i] != 0){
                        tableroLabels[i][j].setIcon(iconoEquiz);
                    }
                }
            }
        }
        
        else if (indicador == 3){//Enemigo 3
            for(int i=0;i<DIMENSIONES;i++){
                for(int j=0;j<DIMENSIONES;j++){

                    if (tableroEnemigo3[j][i] == 0)
                        tableroLabels[j][i].setIcon(iconoVacio);
                    else if (tableroEnemigo3[j][i] != 0){
                        tableroLabels[j][i].setIcon(iconoEquiz);
                    }
                }
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
        jLabel1 = new javax.swing.JLabel();
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        bOrientacionFabrica = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lComponenteColocar = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lDineroActual = new javax.swing.JLabel();
        lOrientacionFabrica = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        bAtacarEnemigo1 = new javax.swing.JButton();
        bAtacarEnemigo2 = new javax.swing.JButton();
        bAtacarEnemigo3 = new javax.swing.JButton();
        lComponenteColocar1 = new javax.swing.JLabel();
        lNumJugador = new javax.swing.JLabel();
        bIniciarPartida = new javax.swing.JButton();
        bVerMapaPropio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Turno del Jugador: Jugador 1");

        txaMensajes.setColumns(20);
        txaMensajes.setRows(5);
        jScrollPane1.setViewportView(txaMensajes);

        btnEnviar.setText("Enviar Todos");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        enviarMensajeJ1.setText("Enviar Enemigo 1");
        enviarMensajeJ1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarMensajeJ1ActionPerformed(evt);
            }
        });

        enviarMensajeJ2.setText("Enviar Enemigo 2");
        enviarMensajeJ2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarMensajeJ2ActionPerformed(evt);
            }
        });

        enviarMensajeJ3.setText("Enviar Enemigo 3");
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
        bConector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConectorActionPerformed(evt);
            }
        });

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
            .addGap(0, 755, Short.MAX_VALUE)
        );
        pTableroJuegoLayout.setVerticalGroup(
            pTableroJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        bMercado.setText("Mercado");
        bMercado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMercadoActionPerformed(evt);
            }
        });

        bMina.setText("Mina");
        bMina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMinaActionPerformed(evt);
            }
        });

        bArmeria.setText("Armería");
        bArmeria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bArmeriaActionPerformed(evt);
            }
        });

        bTemplo.setText("Templo");
        bTemplo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTemploActionPerformed(evt);
            }
        });

        jLabel5.setText("2000$");

        jLabel6.setText("1000$");

        jLabel7.setText("1500$");

        jLabel8.setText("2500$");

        jLabel11.setText("Armería");

        jLabel12.setText("Templo");

        jLabel13.setText("Mercado");

        jLabel14.setText("Mina");

        bOrientacionFabrica.setText("Orientación Fábrica");
        bOrientacionFabrica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOrientacionFabricaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(39, 39, 39))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bMercado, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(bArmeria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bTemplo, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                    .addComponent(bMina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(40, 40, 40))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel14))
                .addGap(41, 41, 41))
            .addComponent(bOrientacionFabrica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bMina, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                    .addComponent(bMercado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bArmeria, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bTemplo, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(19, 19, 19)
                .addComponent(bOrientacionFabrica))
        );

        jLabel2.setText("Fábricas");

        jLabel3.setText("12 000$");

        jLabel4.setText("100$");

        lComponenteColocar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lComponenteColocar.setText("Colocando:");

        jLabel9.setText("Mundo");

        jLabel10.setText("Conector");

        lDineroActual.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lDineroActual.setText("Dinero Actual: 4000$");

        lOrientacionFabrica.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        bAtacarEnemigo1.setText("Atacar Enemigo1");
        bAtacarEnemigo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAtacarEnemigo1ActionPerformed(evt);
            }
        });

        bAtacarEnemigo2.setText("Atacar Enemigo2");
        bAtacarEnemigo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAtacarEnemigo2ActionPerformed(evt);
            }
        });

        bAtacarEnemigo3.setText("Atacar Enemigo3");
        bAtacarEnemigo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAtacarEnemigo3ActionPerformed(evt);
            }
        });

        lComponenteColocar1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lComponenteColocar1.setText("Enemigos");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bAtacarEnemigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(bAtacarEnemigo2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(bAtacarEnemigo3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(238, 238, 238)
                        .addComponent(lComponenteColocar1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(lComponenteColocar1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bAtacarEnemigo3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(bAtacarEnemigo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bAtacarEnemigo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        lNumJugador.setText(" ");

        bIniciarPartida.setText("Iniciar");
        bIniciarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIniciarPartidaActionPerformed(evt);
            }
        });

        bVerMapaPropio.setText("Ver Mapa Propio");
        bVerMapaPropio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVerMapaPropioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lNumJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bIniciarPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30)
                .addComponent(pTableroJuego, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel10)
                                        .addGap(58, 58, 58))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addComponent(jLabel3)
                                                .addGap(111, 111, 111)
                                                .addComponent(jLabel4)
                                                .addGap(40, 40, 40))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(bMundo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(bConector, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lComponenteColocar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lOrientacionFabrica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(bVerMapaPropio, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(87, 87, 87)))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(122, 122, 122))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(enviarMensajeJ1))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(enviarMensajeJ2)
                                        .addComponent(enviarMensajeJ3, javax.swing.GroupLayout.Alignment.TRAILING)))
                                .addComponent(txfMensaje)))
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(919, Short.MAX_VALUE)
                    .addComponent(lDineroActual, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(304, 304, 304)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lComponenteColocar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(lOrientacionFabrica, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(bMundo, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(bConector, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel10))
                                        .addGap(18, 18, 18)
                                        .addComponent(bVerMapaPropio, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)))
                                .addGap(44, 44, 44)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(enviarMensajeJ1)
                                            .addComponent(enviarMensajeJ2))
                                        .addGap(40, 40, 40)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(btnEnviar)
                                            .addComponent(enviarMensajeJ3)))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addComponent(txfMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pTableroJuego, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(171, 171, 171)
                        .addComponent(bIniciarPartida)
                        .addGap(18, 18, 18)
                        .addComponent(lNumJugador)))
                .addGap(0, 0, 0))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(lDineroActual, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(702, Short.MAX_VALUE)))
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
        componenteColocar = 1;//Vamos a colocar un mundo
        lComponenteColocar.setText("Colocando: Mundo");
        lOrientacionFabrica.setText("");
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

            cliente.salida.writeInt(2);
            // le envia el mensaje
            cliente.salida.writeUTF(cliente.nomCliente+"> "+mensaje);
            cliente.salida.flush();
        } catch (IOException ex) {

        }
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void bConectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConectorActionPerformed
        // TODO add your handling code here:
        componenteColocar = 2;//Vamos a colocar un conector
        lComponenteColocar.setText("Colocando: Conector");
        lOrientacionFabrica.setText("");
    }//GEN-LAST:event_bConectorActionPerformed

    private void bMercadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMercadoActionPerformed
        // TODO add your handling code here:
        componenteColocar = 3;//Vamos a colocar un mercado
        lComponenteColocar.setText("Colocando: Mercado");
        if (orientacionColocarFabrica == 0){
            lOrientacionFabrica.setText("Orientación: Vertical");
        }
        else{
            lOrientacionFabrica.setText("Orientación: Horizontal");
        }
    }//GEN-LAST:event_bMercadoActionPerformed

    private void bMinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMinaActionPerformed
        // TODO add your handling code here:
        componenteColocar = 4;//Vamos a colocar una mina
        lComponenteColocar.setText("Colocando: Mina");
        if (orientacionColocarFabrica == 0){
            lOrientacionFabrica.setText("Orientación: Vertical");
        }
        else{
            lOrientacionFabrica.setText("Orientación: Horizontal");
        }
    }//GEN-LAST:event_bMinaActionPerformed

    private void bArmeriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bArmeriaActionPerformed
        // TODO add your handling code here:
        componenteColocar = 5;//Vamos a colocar una armería
        lComponenteColocar.setText("Colocando: Armería");
        if (orientacionColocarFabrica == 0){
            lOrientacionFabrica.setText("Orientación: Vertical");
        }
        else{
            lOrientacionFabrica.setText("Orientación: Horizontal");
        }
    }//GEN-LAST:event_bArmeriaActionPerformed

    private void bTemploActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTemploActionPerformed
        // TODO add your handling code here:
        componenteColocar = 6;//Vamos a colocar un templo
        lComponenteColocar.setText("Colocando: Templo");
        if (orientacionColocarFabrica == 0){
            lOrientacionFabrica.setText("Orientación: Vertical");
        }
        else{
            lOrientacionFabrica.setText("Orientación: Horizontal");
        }
    }//GEN-LAST:event_bTemploActionPerformed

    private void bOrientacionFabricaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOrientacionFabricaActionPerformed
        // TODO add your handling code here:
        orientacionColocarFabrica = (orientacionColocarFabrica == 1) ? 0:1;
        if (orientacionColocarFabrica == 0){
            lOrientacionFabrica.setText("Orientación: Vertical");
        }
        else{
            lOrientacionFabrica.setText("Orientación: Horizontal");
        }
    }//GEN-LAST:event_bOrientacionFabricaActionPerformed

    private void bAtacarEnemigo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAtacarEnemigo1ActionPerformed
        // TODO add your handling code here:
        atacandoEnemigo1 = true;
        atacandoEnemigo2 = false;
        atacandoEnemigo3 = false;
        mostrarTablero(1);
    }//GEN-LAST:event_bAtacarEnemigo1ActionPerformed

    private void bAtacarEnemigo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAtacarEnemigo2ActionPerformed
        // TODO add your handling code here:
        atacandoEnemigo1 = false;
        atacandoEnemigo2 = true;
        atacandoEnemigo3 = false;
        mostrarTablero(2);
    }//GEN-LAST:event_bAtacarEnemigo2ActionPerformed

    private void bAtacarEnemigo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAtacarEnemigo3ActionPerformed
        // TODO add your handling code here:
        atacandoEnemigo1 = false;
        atacandoEnemigo2 = false;
        atacandoEnemigo3 = true;
        mostrarTablero(3);
    }//GEN-LAST:event_bAtacarEnemigo3ActionPerformed

    private void bIniciarPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bIniciarPartidaActionPerformed
        // TODO add your handling code here:
        lNumJugador.setText(Integer.toString(numeroJugador));
    }//GEN-LAST:event_bIniciarPartidaActionPerformed

    private void bVerMapaPropioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bVerMapaPropioActionPerformed
        // TODO add your handling code here:
        atacandoEnemigo1 = false;
        atacandoEnemigo2 = false;
        atacandoEnemigo3 = false;
        mostrarTablero(0);
    }//GEN-LAST:event_bVerMapaPropioActionPerformed

                                     

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
    private javax.swing.JButton bAtacarEnemigo1;
    private javax.swing.JButton bAtacarEnemigo2;
    private javax.swing.JButton bAtacarEnemigo3;
    private javax.swing.JButton bBomba;
    private javax.swing.JButton bComboShot;
    private javax.swing.JButton bConector;
    private javax.swing.JButton bIniciarPartida;
    private javax.swing.JButton bMercado;
    private javax.swing.JButton bMina;
    private javax.swing.JButton bMisil;
    private javax.swing.JButton bMultiShot;
    private javax.swing.JButton bMundo;
    private javax.swing.JButton bOrientacionFabrica;
    private javax.swing.JButton bTemplo;
    private javax.swing.JButton bVerMapaPropio;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton enviarMensajeJ1;
    private javax.swing.JButton enviarMensajeJ2;
    private javax.swing.JButton enviarMensajeJ3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lComponenteColocar;
    private javax.swing.JLabel lComponenteColocar1;
    private javax.swing.JLabel lDineroActual;
    private javax.swing.JLabel lNumJugador;
    private javax.swing.JLabel lOrientacionFabrica;
    private javax.swing.JPanel pTableroJuego;
    private javax.swing.JTextArea txaMensajes;
    private javax.swing.JTextField txfMensaje;
    // End of variables declaration//GEN-END:variables
}
