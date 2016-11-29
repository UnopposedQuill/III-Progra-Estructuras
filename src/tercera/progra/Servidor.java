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
    private ArrayList<Jugador> jugadoresEsperaDuo,jugadoresEsperaTrio,jugadoresEsperaCuarteto;
    private String logs;
    
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
        this.logs = "";
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

    public ArrayList<Partida> getPartidasEnCurso() {
        return partidasEnCurso;
    }

    public ArrayList<Jugador> getJugadoresEsperaDuo() {
        return jugadoresEsperaDuo;
    }

    public ArrayList<Jugador> getJugadoresEsperaTrio() {
        return jugadoresEsperaTrio;
    }

    public ArrayList<Jugador> getJugadoresEsperaCuarteto() {
        return jugadoresEsperaCuarteto;
    }

    public String getLogs() {
        return logs;
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
                    atenderPeticion(mensajeRecibido, socketNuevo);//hago que el servidor atienda la petición
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
     * @param socketPeticion El socket del cual se recibió la conexión
     */
    private void atenderPeticion(Mensaje mensajeAAtender, Socket socketPeticion){
        try{
            switch(mensajeAAtender.getTipoDelMensaje()){
                case actualizarTablas:{
                    System.out.println("Se desea actualizar las tablas de cada jugador");
                    mensajeAAtender.setDatoDeRespuesta(this.partidasEnCurso);
                    this.enviarMensaje(mensajeAAtender, this.flujoDeSalida);
                    break;
                }
                case activado:{
                    System.out.println("Se desea averiguar si el servidor está activo");
                    mensajeAAtender.setDatoDeRespuesta(true);
                    this.enviarMensaje(mensajeAAtender, this.flujoDeSalida);
                    break;
                }
                case atacarJugador:{
                    System.out.println("Se desea agregar un daño a un jugador");
                    Ataque ataque = (Ataque)mensajeAAtender.getDatoDeSolicitud();
                    Partida partidaAModificar = this.encontrarPartidaDelJugador(ataque.getBlancoDelAtaque());
                    System.out.println("Se consiguieron correctamente los datos del ataque");
                    boolean resultado = partidaAModificar.getJugadores().get(partidaAModificar.getJugadores().indexOf(ataque.getBlancoDelAtaque())).getGrafoPropio().agregarDanhos(ataque.getCoordenadaDeAtaque());
                    if(resultado){
                        System.out.println("Se agregaron los daños correctamente");
                    }
                    else{
                        System.out.println("No se agregaron correctamente los daños");
                    }
                    mensajeAAtender.setDatoDeRespuesta(resultado);
                    this.enviarMensaje(mensajeAAtender, this.flujoDeSalida);
                    break;
                }
                case unirseACola:{
                    Jugador posibleNuevoJugador = (Jugador)mensajeAAtender.getDatoDeSolicitud();
                    
                    if(this.encontrarPartidaDelJugador(posibleNuevoJugador) == null){//primero verifico si no estaba en otra partida
                        switch((int)mensajeAAtender.getDatoDeSolicitud()){
                            case 2:{
                                this.jugadoresEsperaDuo.add(posibleNuevoJugador);
                                if(this.jugadoresEsperaDuo.size() >= 2){
                                    emparejar(2);
                                }
                                break;
                            }
                            case 3:{
                                this.jugadoresEsperaTrio.add(posibleNuevoJugador);
                                if(this.jugadoresEsperaTrio.size() >= 3){
                                    emparejar(3);
                                }
                                break;
                            }
                            case 4:{
                                this.jugadoresEsperaCuarteto.add(posibleNuevoJugador);
                                if(this.jugadoresEsperaCuarteto.size() >= 4){
                                    emparejar(4);
                                }
                                break;
                            }
                        }
                        mensajeAAtender.setDatoDeRespuesta(true);
                    }
                    else{
                        mensajeAAtender.setDatoDeRespuesta(false);
                    }
                    this.enviarMensaje(mensajeAAtender, this.flujoDeSalida);
                }
            }
        }catch(ClassCastException exc){
            System.out.println("Ocurrió un error a la hora de descifrar alguno de los datos en una solicitud del tipo: " + mensajeAAtender.getTipoDelMensaje().getRepString());
        }
    }
    
    /**
     * Este método se encarga de emparejar los jugadores en partidas
     * @param tipoEmparejamiento El tamaño de la partida
     * @return Si se logró agregar la partida
     */
    private boolean emparejar(int tipoEmparejamiento){
        ArrayList <Jugador> jugadoresAEmparejar = new ArrayList();
        switch(tipoEmparejamiento){
            case 2:{
                for (int i = 0; i < tipoEmparejamiento; i++) {
                    Jugador jugadorAEmparejar = this.jugadoresEsperaDuo.remove(0);
                    jugadorAEmparejar.setNumeroJugador(i);
                    jugadoresAEmparejar.add(jugadorAEmparejar);
                }
                break;
            }
            case 3:{
                for (int i = 0; i < tipoEmparejamiento; i++) {
                    Jugador jugadorAEmparejar = this.jugadoresEsperaTrio.remove(0);
                    jugadorAEmparejar.setNumeroJugador(i);
                    jugadoresAEmparejar.add(jugadorAEmparejar);
                }
                break;
            }
            case 4:{
                for (int i = 0; i < tipoEmparejamiento; i++) {
                    Jugador jugadorAEmparejar = this.jugadoresEsperaCuarteto.remove(0);
                    jugadorAEmparejar.setNumeroJugador(i);
                    jugadoresAEmparejar.add(jugadorAEmparejar);
                }
                break;
            }
        }
        return this.partidasEnCurso.add(new Partida(jugadoresAEmparejar));
    }
    
    /**
     * Este método busca entre todas las partidas en curso por un determinado jugador, y retorna la partida en la está
     * este método es usado en servidor para que sepa cuál partida debe modificar
     * @param jugadorABuscar El jugador a buscar
     * @return La partida en la que encontró el jugador, o null, si no encontró el jugador
     */
    private Partida encontrarPartidaDelJugador(Jugador jugadorABuscar){
        for (int i = 0; i < partidasEnCurso.size(); i++) {
            Partida getPartida = partidasEnCurso.get(i);
            for (int j = 0; j < getPartida.getJugadores().size(); j++) {
                Jugador getJugador = getPartida.getJugadores().get(j);
                if(jugadorABuscar.equals(getJugador)){
                    return getPartida;
                }
            }
        }
        return null;
    }
    
    /**
     * Este método lo que hace es notificar a los jugadores de una partida que hubo algún cambio
     * @param partidaANotificar La partida a ser notificada
     */
    public void notificarUsuariosPartida(Partida partidaANotificar){
        for (int i = 0; i < partidaANotificar.getJugadores().size(); i++) {
            Jugador get = partidaANotificar.getJugadores().get(i);
            this.enviarMensaje(new Mensaje(TipoMensaje.notificarJugadores, null), get.getFlujoDeSalida());
        }
    }
    
    /**
     * Este es el método encargado de enviar un mensaje a alguno de los jugadores
     * @param mensajeAEnviar El mensaje que se desea enviar
     */
    private void enviarMensaje(Mensaje mensajeAEnviar, ObjectOutputStream canalEscritura){
        try {
            canalEscritura.writeObject(mensajeAEnviar);
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