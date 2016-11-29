/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import java.io.*;

/**
 * Esta es la estructura que controlará los grafos, así como todas las acciones del juego
 * @author esteban
 */
class Jugador {
    
    private OutputStream conexionSalida;
    private ObjectOutputStream flujoDeSalida;
    private InputStream conexionEntrada;
    private ObjectInputStream flujoDeEntrada;
    private GrafoObjetos grafoPropio;
    private int dineroJugador;
    
}
