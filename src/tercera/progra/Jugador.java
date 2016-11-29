/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import java.io.*;
import java.util.*;

/**
 * Esta es la estructura que controlará los grafos, así como todas las acciones del juego
 * @author esteban
 */
public class Jugador {
    
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

    public Jugador(String nombreJugador, GrafoObjetos grafoPropio) {
        this.nombreJugador = nombreJugador;
        this.grafoPropio = grafoPropio;
        this.aceroJugador = 0;
        this.armasJugador = new ArrayList<>();
        this.dineroJugador = 4000;
        this.numeroJugador = -1;
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
    
    
}
