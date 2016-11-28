/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Esteban
 */
public class JFrameGuerraMundos extends JFrame {

    
    /**
     * Creates new form JFrameGuerraMundos
     */
    public JFrameGuerraMundos() {

        // esto es parte del gato
        initComponents();
        /*
        generarTablero();
        */
        this.Mundo.setRowHeight(42);
        this.dibujarTablero();
        // Cra una cliente que es su coenxion al server
        //cliente = new Cliente(this);
        //cliente.conexion();

        // pide el status al server, el server le enviara
        // al cliente el numero jugador que es y el nombre
        // enemigo

        //cliente.salida.writeInt(3);
        //cliente.salida.flush();


    }
    
    //----------------------------------
    //----------------------------------
    
    int componenteColocar = 0;
    int dineroJugador = 4000;
    int turnoJugador=1;
    //numero de jugador 1 a 4
    int numeroJugador = 0;
    int mundoInicial = 1;
    int mercadoInicial = 1;
    int orientacionColocarFabrica = 0;
    boolean atacandoEnemigo1 = false;
    boolean atacandoEnemigo2 = false;
    boolean atacandoEnemigo3 = false;
    /*
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
    
    */
    private void dibujarTablero(){
        
        //Aquí irán los datos de guía, estos pasarán de estar aqui, a ir como parámetro o algo por el estilo
        Object[][] datosGuia = new Object[15][15];
        boolean dibujarMinaAgujero = false;
        //dentro de estos fors se asignan los datos, por ahora sólo hago que los dibuje a modo de tablero
        //de ajedrez dado que no tengo la matriz de datos
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if(dibujarMinaAgujero){
                    datosGuia[i][j] = TipoFabrica.AGUJERO;
                }
                else{
                    datosGuia[i][j] = TipoFabrica.MINA;
                }
                dibujarMinaAgujero = !dibujarMinaAgujero;
            }
        }
        
        //aquí irán los datos que serán dibujados en la tabla
        Object[][] datos = new Object[15][15];
        //por ahora sólo tengo estas burdas imágenes
        // crea imagen blanco
        ImageIcon iconoVacio = new ImageIcon(getClass().getResource("cvacio.GIF"));
        // crea imagen X
        ImageIcon iconoEquiz = new ImageIcon(getClass().getResource("cequiz.GIF"));
        // crea la imagen circulo
        ImageIcon iconoCirculo = new ImageIcon(getClass().getResource("ccirculo.GIF"));
        //ahora toca llenar la tabla de datos
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Object objetoGuia = datosGuia[i][j];
                if(objetoGuia instanceof TipoFabrica){//este es un if de validación, sino, revienta todo
                    //ahora creo el botón a renderizar
                    JButton botonARenderizar = new JButton("");
                    //esto es un switch de todos los tipos de cosas que debemos dibujar
                    switch((TipoFabrica)objetoGuia){
                        case AGUJERO:{//agujero negro...
                            //le seteo la imagen al botón
                            botonARenderizar.setIcon(iconoEquiz);
                            //lo ingreso en la tabla
                            datos[i][j] = botonARenderizar;
                        }
                        case BLANK:{
                            //le seteo la imagen al botón
                            botonARenderizar.setIcon(iconoVacio);
                            //lo ingreso en la tabla
                            datos[i][j] = botonARenderizar;
                        }
                        case MINA:{
                            //le seteo la imagen al botón
                            botonARenderizar.setIcon(iconoCirculo);
                            //lo ingreso en la tabla
                            datos[i][j] = botonARenderizar;
                        }
                        default:{//como no tengo más imágenes, entonces pongo el default
                            //a estos en lugar de imagen les pongo de texto lo que eran
                            botonARenderizar.setText(TipoFabrica.fakeToString((TipoFabrica)objetoGuia));
                            //lo ingreso en la tabla
                            datos[i][j] = botonARenderizar;
                        }
                    }
                }//si no calza en tipo fabrica, entonces se retorna y se lava las manos
                datos[i][j] = new JButton("Desconocido");
            }
        }
                
        
        //aquí irán los datos de las columnas
        String [] columnas = new String[15];
        //las columnas irán mostradas con la posición, ¿no?
        for (int i = 0; i < 15; i++) {
            columnas[i] = String.valueOf(i);
        }
        
        
        DefaultTableModel dtm = new DefaultTableModel(datos, columnas){ //a partir de aquí me pongo a modificar algunos aspectos del modelo
            
            //con esto defino los tipos por fuerza que va a tener el modelo
            Class[] tipos = new Class[]{
            //estos son los tipos de datos que van a ir en cada columna, NO MODIFICAR,
            //esta es el alma del renderizador
            JButton.class,
            JButton.class,
            JButton.class,
            JButton.class,
            JButton.class, //son 15 columnas, 15 clases
            
            JButton.class,
            JButton.class,
            JButton.class,
            JButton.class,
            JButton.class,
            
            JButton.class,
            JButton.class,
            JButton.class,
            JButton.class,
            JButton.class,};
            
            //este override es para modificar lo que consigue al intentar la tabla
            @Override
            public Class getColumnClass(int columnIndex){
                //Este método es invocado por el CellRenderer para saber que dibujar en la celda,
                //observen que estamos retornando la clase que definimos de antemano.
                return tipos[columnIndex];
            }
            
            @Override
            public boolean isCellEditable(int row, int column){
                //Sobrescribo este método para evitar que la columna que contiene los botones sea editada
                return !(this.getColumnClass(column).equals(JButton.class));
            }
            
        };
        this.Mundo.setModel(dtm);//aquí le ingreso a la tabla todos los cambios
        
        //esta es la parte que decide QUÉ DEBE DIBUJAR
        this.Mundo.setDefaultRenderer(JButton.class, new TableCellRenderer(){
            /**
             * Este método sólo se encarga de una sóla y simple idiotez:
             * retornar lo que entra xD
             * El asunto es que a la hora de pintar la tabla dibuje el objeto tal
             * y como entra, en lugar de hacerlo asquerosamente de manera default 
             * Resumen: hace que aparezca un botón cuando aparece un botón en la matriz de datos
             */
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                return (Component) objeto;
            }
        });
        //aquí sólo resta hacer el panel que contendrá la tabla junto con el mouse listener
        
        //para definir las accionas al dar click en la tabla
        this.Mundo.addMouseListener(new MouseAdapter() {
            /*
            el mouse listener se coloca en la tabla y no en la ventana, así logro saber en qué celda se dio
            clic, en lugar de buscar por el frame al que pertenece
            el asunto es que tengo que capturar cuando el mouse clickea en el botón de mostrar el mapa
            */
            @Override
            public void mouseClicked(MouseEvent e){   
                
                //esto consigue la fila y columna del evento
                int fila = Mundo.rowAtPoint(e.getPoint());
                int columna = Mundo.columnAtPoint(e.getPoint());
                
                /**
                 * Pregunto si hizo clic sobre la celda que contiene un botón, 
                 * este if puede quitarlo si quiere, pero a mi parecer mejor dejarlo
                 */
                if (Mundo.getModel().getColumnClass(columna).equals(JButton.class)) {
                    //significa que sí dio en el botón, por lo que hago que dibuje el mapa
                    //ingresar aquí lo que se desea que suceda al presionar uno de los botones
                    System.out.println("Se presionó el botón en la posición: " + fila + ", " + columna);
                }
            }
        });
    }
    /*
            
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
                tableroLogico[i][j] = 0;
            }
        }
    }
    
    // este metodo es la respuesta del cliente al click del enemigo
    public void marcar(int columna, int fila)
    {
        // marca el tablero con num de jugador
        tableroLogico[columna][fila] = turnoJugador;
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
        

        // Si es equivalente a 0 es porque no hay nada y se puede colocar un componente
        if(tableroLogico[columna][fila]!=0)
            return;

        if(atacandoEnemigo1 == true){
            
        }
        else if (atacandoEnemigo2 == true){
            
        }
        else if (atacandoEnemigo3 == true){
            
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
    
    public void clickSobreTableroDisparo(java.awt.event.MouseEvent evt)
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
        
        // si era el jugador 1 marca con x y cambia el turno a jugador 2
        if (numeroJugador == 1){
            turnoJugador = 2;
        }
        else if (numeroJugador == 2){
            turnoJugador = 3;
        }
        else if (numeroJugador == 3){
            turnoJugador = 4;
        }
        else{
            turnoJugador = 1;
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
    */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bMisil = new javax.swing.JButton();
        bMultiShot = new javax.swing.JButton();
        bBomba = new javax.swing.JButton();
        bComboShot = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Mundo = new javax.swing.JTable();
        lDineroActual = new javax.swing.JLabel();
        LabelTurno = new javax.swing.JLabel();
        TipoFabricaComprar = new javax.swing.JComboBox<>();
        FabricaComprar = new javax.swing.JButton();
        NumeroMundo = new javax.swing.JLabel();
        MundoAnterior = new javax.swing.JButton();
        MundoSiguiente = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        TextFieldChat = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        EnviarMensaje = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bMisil.setText("Misil");

        bMultiShot.setText("MultiShot");
        bMultiShot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMultiShotActionPerformed(evt);
            }
        });

        bBomba.setText("Bomba");

        bComboShot.setText("ComboShot");

        Mundo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(Mundo);
        if (Mundo.getColumnModel().getColumnCount() > 0) {
            Mundo.getColumnModel().getColumn(0).setResizable(false);
        }

        lDineroActual.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lDineroActual.setText("Dinero Actual: 4000$");

        LabelTurno.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LabelTurno.setText("Turno del Jugador: Jugador 1");

        TipoFabricaComprar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        FabricaComprar.setText("Comprar");

        NumeroMundo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        NumeroMundo.setText("Número de Mundo: 0");

        MundoAnterior.setText("Mundo Anterior");

        MundoSiguiente.setText("Mundo Siguiente");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        TextFieldChat.setText("Mensaje");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        EnviarMensaje.setText("Enviar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(bComboShot, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                .addComponent(bBomba, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bMultiShot, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bMisil, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(TipoFabricaComprar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FabricaComprar))
                            .addComponent(MundoAnterior, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addComponent(jScrollPane1)
                        .addComponent(TextFieldChat))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EnviarMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lDineroActual, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LabelTurno, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NumeroMundo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(MundoSiguiente))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(NumeroMundo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lDineroActual, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(142, 142, 142)
                        .addComponent(MundoSiguiente)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bMisil, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bMultiShot, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bBomba, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bComboShot, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TipoFabricaComprar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FabricaComprar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MundoAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextFieldChat, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1)
                            .addComponent(EnviarMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bMultiShotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMultiShotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bMultiShotActionPerformed

                                     

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
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EnviarMensaje;
    private javax.swing.JButton FabricaComprar;
    private javax.swing.JLabel LabelTurno;
    private javax.swing.JTable Mundo;
    private javax.swing.JButton MundoAnterior;
    private javax.swing.JButton MundoSiguiente;
    private javax.swing.JLabel NumeroMundo;
    private javax.swing.JTextField TextFieldChat;
    private javax.swing.JComboBox<String> TipoFabricaComprar;
    private javax.swing.JButton bBomba;
    private javax.swing.JButton bComboShot;
    private javax.swing.JButton bMisil;
    private javax.swing.JButton bMultiShot;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lDineroActual;
    // End of variables declaration//GEN-END:variables
}
