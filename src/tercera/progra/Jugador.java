/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import java.io.*;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta es la estructura que controlará los grafos, así como todas las acciones del juego
 * @author esteban
 */
public class Jugador extends Thread{
    
    private final String nombreJugador;
    private OutputStream conexionSalida;
    private ObjectOutputStream flujoDeSalida;
    private InputStream conexionEntrada;
    private ObjectInputStream flujoDeEntrada;
    private GrafoObjetos grafoPropio;
    private int dineroJugador;
    private int aceroJugador;
    private ArrayList<Arma> armasJugador;
    private int numeroJugador;
    private String huesped = "localhost";
    private int puerto = 5000;

    public Jugador(String nombreJugador, GrafoObjetos grafoPropio, String huesped) {
        this.nombreJugador = nombreJugador;
        this.grafoPropio = grafoPropio;
        this.aceroJugador = 0;
        this.armasJugador = new ArrayList<>();
        this.dineroJugador = 4000;
        this.numeroJugador = -1;
        this.huesped = huesped;
    }

    public GrafoObjetos getGrafoPropio() {
        return grafoPropio;
    }

    public void setGrafoPropio(GrafoObjetos grafoPropio) {
        this.grafoPropio = grafoPropio;
    }

    public int getDineroJugador() {
        return dineroJugador;
    }

    public void setDineroJugador(int dineroJugador) {
        this.dineroJugador = dineroJugador;
    }

    public int getAceroJugador() {
        return aceroJugador;
    }

    public void setAceroJugador(int aceroJugador) {
        this.aceroJugador = aceroJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public OutputStream getConexionSalida() {
        return conexionSalida;
    }

    public ObjectOutputStream getFlujoDeSalida() {
        return flujoDeSalida;
    }

    public InputStream getConexionEntrada() {
        return conexionEntrada;
    }

    public ObjectInputStream getFlujoDeEntrada() {
        return flujoDeEntrada;
    }

    public ArrayList<Arma> getArmasJugador() {
        return armasJugador;
    }

    public boolean agregarArma(Arma armaAAgregar){
        return this.armasJugador.add(armaAAgregar);
    }
    
    public int getNumeroJugador() {
        return numeroJugador;
    }

    public void setNumeroJugador(int numeroJugador) {
        this.numeroJugador = numeroJugador;
    }

    public String getHuesped() {
        return huesped;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.nombreJugador);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Jugador other = (Jugador) obj;
        if (!Objects.equals(this.nombreJugador, other.nombreJugador)) {
            return false;
        }
        return true;
    }

    /**
     * El run del hilo va a ser el que actualiza qué ha recibido el Jugador
     */
    @Override
    public void run() {
        while(true){
            try {
                Mensaje mensajeRecibido = (Mensaje)this.flujoDeEntrada.readObject();
                switch(mensajeRecibido.getTipoDelMensaje()){
                    case notificarJugadores:{//el servidor acaba de notificar cambios
                        //tiene que actualizarse todo
                        
                    }
                    case emparejado:{
                        //se acaba de avisar que se emparejó con otros jugadores
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("No se captó nada");
            }
        }
    }
    
    public Mensaje realizarPeticion(Mensaje aEnviar){
        for (int i = 0; i < 3; i++) {
            try{
                System.out.println("Conectándose al servidor especificado");
                Socket socketConexion = new Socket(this.huesped, this.puerto);
                System.out.println("Estableciendo conexiones con el servidor");
                this.conexionEntrada = socketConexion.getInputStream();
                this.flujoDeEntrada = new ObjectInputStream(this.conexionEntrada);
                this.conexionSalida = socketConexion.getOutputStream();
                this.flujoDeSalida = new ObjectOutputStream(this.conexionSalida);
                System.out.println("Enviando mensaje");
                this.flujoDeSalida.writeObject(aEnviar);
                try{
                    System.out.println("Mensaje recibido con éxito");
                    return (Mensaje) this.flujoDeEntrada.readObject();
                }catch(ClassNotFoundException | ClassCastException exc){
                    System.out.println("Ocurrió un error a la hora de averiguar el mensaje retornado");
                    return null;
                }
            }catch(IOException exc){
                System.out.println("Algo salió mal al intentar conectarse al servidor: " + this.huesped + " " + this.puerto);
            }
        }
        System.out.println("Se rindió al no poder conectarse al servidor");
        return null;
    }
}
