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
    private void atenderPeticion(Mensaje mensajeAAtender){
        switch(mensajeAAtender.getTipoDelMensaje()){
            case actualizarTablas:{
                System.out.println("Se desea actualizar las tablas");
            }
        }
    }
    
    private void enviarMensaje(Mensaje mensajeAEnviar){
        try {
            this.flujoDeSalida.writeObject(mensajeAEnviar);
            System.out.println("Mensaje enviado de vuelta correctamente");
        } catch (IOException ex) {
            System.out.println("Error al enviar el mensaje de vuelta");
        }
    }
    
    @Override
    public String toString() {
        return "Servidor: \n" + "Activo: " + activo + ", pausado: " + pausado;
    }
}