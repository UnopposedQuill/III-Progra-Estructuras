/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import java.io.*;
import java.net.*;
import java.util.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 * Esta es la clase del servidor.
 * @author Esteban
 */
public class Servidor extends Thread{
    
    private boolean activo;
    private boolean pausado;
    private ArrayList<Partida> partidasEnCurso;
    private ArrayList<Jugador> jugadoresEsperaDuo;
    private ArrayList<Jugador> jugadoresEsperaTrio;
    private ArrayList<Jugador> jugadoresEsperaCuarteto;
    
    //Campos de las conexiones del servidor
    private ServerSocket serverSocket;
    private OutputStream conexionSalida;
    private ObjectOutputStream flujoDeSalida;
    private InputStream conexionEntrada;
    private ObjectInputStream flujoDeEntrada;
    
    /**
     * Crea un servidor nuevo a partir del nombre de un archivo XML por defecto que está dentro de la carpeta del proyecto
     */
    public Servidor() {
        this.activo = true;
        this.pausado = false;
        this.partidasEnCurso = new ArrayList<>();
        this.jugadoresEsperaDuo = new ArrayList<>();
        this.jugadoresEsperaTrio = new ArrayList<>();
        this.jugadoresEsperaCuarteto = new ArrayList<>();
    }
    
    /**
     * Este método lo que hace es que deshace todas las conexiones y puertos que haya definido el servidor
     */
    public void asesinarServidor(){
        try {
            this.serverSocket.close();
            System.out.println("Servidor eliminado con éxito");
        } catch (IOException ex) {
            System.out.println("Error al eliminar el servidor");
        }
    }
    
    /**
     * Esto es para que el servidor se detenga completamente, esté o no en ejecución
     * @return El estado en el que quedó el servidor
     */
    public boolean pararServidor(){
        this.activo = false;
        this.pausado = false;
        return this.activo;
    }
    
    /**
     * Esto habilita el servidor, pero NO lo pone en marcha
     * @return El estado final en el que quedó el servidor
     */
    public boolean activarServidor(){
        this.activo = true;
        return this.activo;
    }
    
    /**
     * Esto es para pausar el servidor, de modo que al remover la pausa el servidor pueda reanudar todo rápidamente
     * @return El estado final en el que quedó el servidor
     */
    public boolean pausarServidor(){
        this.pausado = true;
        return this.pausado;
    }
    
    /**
     * Para remover el estado de pausa del servidor
     * @return El estado final en el que quedó el servidor
     */
    public boolean desPausarServidor(){
        this.pausado = false;
        return this.pausado;
    }
    
    /**
     * Esto es para habilitar el servidor, y hacer que esté en ejecución en un sólo paso
     */
    public void correrServidor(){
        this.activarServidor();
        this.start();
    }

    /**
     * Para saber si el servidor está o no activado
     * @return True si el servidor está activado, false en el otro caso
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Para saber si el servidor está o no pausado
     * @return True si el servidor está pausado, false en el otro caso
     */
    public boolean isPausado() {
        return pausado;
    }
    
    /**
     * Este método es el método que controla el servidor, lo que hace este método es que controla todas las nuevas conexiones
     * desde y hacia el servidor
     */
    @Override
    public void run(){
        try{
            //nuevo servidor
            this.serverSocket = new ServerSocket(5000);
            //que esté corriendo mientras el servidor esté activo
            while(this.activo){
                System.out.println("Servidor en espera por una nueva conexión");
                Socket socketNuevo = serverSocket.accept();//consigo el nuevo socket que haya deseado conectarse
                System.out.println("Detectada nueva conexión");
                //ahora las conexiones de entrada y de salida del servidor
                System.out.println("Haciendo nuevas conexiones");
                this.conexionSalida = socketNuevo.getOutputStream();
                this.flujoDeSalida = new ObjectOutputStream(conexionSalida);
                this.conexionEntrada = socketNuevo.getInputStream();
                this.flujoDeEntrada = new ObjectInputStream(conexionEntrada);
                System.out.println("Averiguando Mensaje");
                //ya tengo las conexiones hechas, ahora tengo que ver qué hago con lo que el cliente le envió al servidor
                try{
                    Mensaje mensajeRecibido = (Mensaje)flujoDeEntrada.readObject();//consigo el mensaje enviado (o intento hacerlo)
                    System.out.println("Atendiendo Petición");
                    atenderPeticion(mensajeRecibido);//hago que el servidor atienda la petición
                }catch(ClassNotFoundException | ClassCastException excep){
                    System.out.println("Ocurrió un error a la hora de averiguar el mensaje enviado");
                }
                //Esto es en caso de que el administrador desee pausar el servidor
                while(this.pausado){
                    try{
                        System.out.println("Servidor en pausa");
                        Thread.sleep(1000);
                    }catch(InterruptedException except){
                        System.out.println("Hubo un error durante la espera");
                    }
                }
            }
        }catch(IOException exception){
            System.out.println("Hubo un problema al intentar conectar, o se eliminó el servidor");
        }
    }
    
    /**
     * Este es el método que se encarga de averiguar qué exactamente traía el mensaje, así como de retornar una respuesta
     * @param mensajeAAtender El mensaje recibido que se desea atender
     */
    public void atenderPeticion(Mensaje mensajeAAtender){
        switch(mensajeAAtender.getTipoDelMensaje()){
            case conseguirLista:{//se desea conseguir la lista completa de los productos
                try{
                    System.out.println("Era una petición de la lista de productos");
                    mensajeAAtender.setDatoDeRespuesta(this.productos);
                    this.flujoDeSalida.writeObject(mensajeAAtender);
                    System.out.println("Mensaje Retornado con Éxito");
                }catch(IOException excep){
                    System.out.println("Hubo un error a la hora de enviar la respuesta");
                }
                break;
            }
            case nuevoPedido:{//se desea solicitar un nuevo pedido
                try{
                    try{
                        System.out.println("Era una petición para hacer un nuevo pedido");
                        this.pedidos.add((Pedido) mensajeAAtender.getDatoDeSolicitud());
                        this.actualizarIDPedidos();
                        mensajeAAtender.setDatoDeRespuesta(true);
                        System.out.println("Mensaje correctamente atendido");
                    }catch(ClassCastException exc){
                        System.out.println("Hubo un error a la hora de introducir el nuevo pedido en el servidor");
                        mensajeAAtender.setDatoDeRespuesta(false);
                        System.out.println("Mensaje incorrectamente atendido");
                    }
                    this.flujoDeSalida.writeObject(mensajeAAtender);
                    System.out.println("Mensaje Retornado con Éxito");
                } catch (IOException ex) {
                    System.out.println("Hubo un error a la hora de enviar la respuesta");
            }
                break;
            }
            case conseguirTransporte:{//se desea conseguir el extra por transporte
                try{
                    System.out.println("Era una petición del extra por transporte");
                    mensajeAAtender.setDatoDeRespuesta(this.aumentoEmpaque);
                    this.flujoDeSalida.writeObject(mensajeAAtender);
                    System.out.println("Mensaje Retornado con Éxito");
                }catch(IOException excep){
                    System.out.println("Hubo un error a la hora de enviar la respuesta");
                }
                break;
            }
            default:{
                try{
                    this.flujoDeSalida.writeObject(mensajeAAtender);
                }catch(IOException ex){
                    System.out.println("Hubo un error a la hora de devolver una solicitud no soportada");
                }
            }
        }
    }
    
    /**
     * Este método es para cargar los datos de un XML como los productos del servidor
     * @return Un ArrayList con los productos del servidor
     */
    private ArrayList<Producto> conseguirProductosXML(String XML){
        SAXBuilder saxBuilder = new SAXBuilder();
        File archivoXML = new File(XML);
        ArrayList <Producto> arrayLProductos = new ArrayList();
        try{
            Document documentoXML = (Document) saxBuilder.build(archivoXML);
            Element nodoRaiz = documentoXML.getRootElement();

            List <Element> listaProductos = nodoRaiz.getChildren();
            
            for (Element producto : listaProductos) {
                //System.out.println(Servidor.eliminaUnderScores(producto.getName()));
                //System.out.println("Elementos Producto: ");
                List <Element> listaElementos = producto.getChildren();
                Producto productoAAgregar = new Producto(listaElementos.get(0).getTextTrim(), Servidor.eliminaUnderScores(producto.getName()), listaElementos.get(1).getTextTrim(), Integer.parseInt(listaElementos.get(2).getTextTrim()), Integer.parseInt(listaElementos.get(3).getTextTrim()), Integer.parseInt(listaElementos.get(4).getTextTrim()), Integer.parseInt(listaElementos.get(5).getTextTrim()), Integer.parseInt(listaElementos.get(6).getTextTrim()));
                //System.out.println("Producto Nuevo: ");
                //System.out.println(productoAAgregar.toString());
                arrayLProductos.add(productoAAgregar);
                //for (Element elementosProducto : listaElementos) {
                //    System.out.println(elementosProducto.getTextTrim());
                //}
            }
        }catch(JDOMException | IOException exc){
            System.out.println("Error a la hora de agarrar el archivo XML especificado");
        }
        return arrayLProductos;
    }
    
    /**
     * Este método se usa para guardar los productos del servidor en un archivo .xml a salvo
     * @param XML El nombre del XML donde se desea guardar los productos
     */
    private void guardarProductosXML(String XML){
        //Defino una nueva etiqueta con cabecera para guardar los productos
        Element root = new Element("Productos");
        
        //recorro toda la lista de productos para guardar los productos
        for (int i = 0; i < productos.size(); i++) {
            Producto get = productos.get(i);
            //El elemento que va a contener todos los datos del producto
            Element producto = new Element(colocaUnderScores(get.getNombre()));
            
            //Ahora llenaré los datos del producto dentro de su elemento
            Element codigo = new Element("Codigo");
            codigo.addContent(get.getCodigo());
            producto.addContent(codigo);
            
            Element descripcion = new Element("Descripcion");
            descripcion.addContent(get.getDescripcion());
            producto.addContent(descripcion);
            
            Element tamanhoPorcion = new Element("TamanhoPorcion");
            tamanhoPorcion.addContent(String.valueOf(get.getTamanhoPorcion()));
            producto.addContent(tamanhoPorcion);
            
            Element piezaPorPorcion = new Element("PiezasPorPorcion");
            piezaPorPorcion.addContent(String.valueOf(get.getPiezasPorcion()));
            producto.addContent(piezaPorPorcion);
            
            Element caloriasEnUnaPorcion = new Element("CaloriasEnUnaPorcion");
            caloriasEnUnaPorcion.addContent(String.valueOf(get.getCaloriasPorcion()));
            producto.addContent(caloriasEnUnaPorcion);
            
            Element caloriasPorPieza = new Element("CaloriasPorPieza");
            caloriasPorPieza.addContent(String.valueOf(get.getCaloriasPieza()));
            producto.addContent(caloriasPorPieza);
            
            Element precio = new Element("Precio");
            precio.addContent(String.valueOf(get.getPrecio()));
            producto.addContent(precio);
            
            //agrego el producto a la raíz del XML
            root.addContent(producto);
        }
        //ahora sólo falta guardarlo
        XMLOutputter outputterXML = new XMLOutputter(Format.getPrettyFormat());
        try{
          outputterXML.output(new Document(root), new FileOutputStream(XML));
        } catch (IOException e){
            System.out.println("Ocurrió un error a la hora de guardar el XML");
        }
    }
    
    /**
     * Este método busca entre la lista de pedidos cuántas veces se ordenó un producto específico
     * @param productoABuscar El producto que se va a buscar
     * @return Un entero con la cantidad de veces que se pidió
     */
    public int conseguirCantidadVecesPedido(Producto productoABuscar){
        int resultado = 0;
        
        //para cada pedido
        for (int i = 0; i < this.pedidos.size(); i++) {
            Pedido getPedido = this.pedidos.get(i);
            
            //para cada producto en cada pedido
            for (int j = 0; j < getPedido.getProductosPedidos().size(); j++) {
                Producto getProductoPedido = getPedido.getProductosPedidos().get(j);
                //si alguno de los productos coincide
                if(getProductoPedido.equals(productoABuscar)){
                    //sume la cantidad de veces que se pidió al resultado
                    resultado += getPedido.getCantidadProductos()[j];
                }
            }
        }
        return resultado;//retorne
    }
    
    /**
     * Este método se usa para cambiar los underscores ('_') de un string por un espacio en blanco
     * @param string El string del cual se van a cambiar los underscores
     * @return El string sin underscores
     */
    private static String eliminaUnderScores(String string){
        string = string.replaceAll("_", " ");
        return string;
    }

    /**
     * Este método se usa para cambiar los espacios en blanco por un underscore '_'
     * @param string El string del cual se van a cambiar los espacios en blanco
     * @return El string sin espacios en blanco
     */
    private static String colocaUnderScores(String string){
        string = string.replaceAll(" ", "_");
        return string;
    }
    
    @Override
    public String toString() {
        return "Servidor: \n" + "Activo: " + activo + ", pausado: " + pausado + ", productos: " + productos + ", pedidos: " + pedidos;
    }
}