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
    
    private String nombreJugador;
    private OutputStream conexionSalida;
    private ObjectOutputStream flujoDeSalida;
    private InputStream conexionEntrada;
    private ObjectInputStream flujoDeEntrada;
    private GrafoObjetos grafoPropio;
    private int dineroJugador;
    private int aceroJugador;
    private ArrayList<Arma> armasJugador;

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
