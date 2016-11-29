/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import java.io.Serializable;

/**
 *
 * @author Esteban
 */
public enum TipoMensaje implements Serializable{
    atacarJugador, actualizarTablas, enviarMensaje, unirseACola, activado, notificarJugadores;
    
    public String getRepString(){
        switch(this){
            case activado:{
                return "Activado";
            }
            case actualizarTablas:{
                return "Actualizar Tablas";
            }
            case atacarJugador:{
                return "Atacar Jugador";
            }
            case enviarMensaje:{
                return "Enviar Mensaje";
            }
            case notificarJugadores:{
                return "Notificar Jugadores";
            }
            case unirseACola:{
                return "Unirse a Cola";
            }
            default:{
                return "NULL";
            }
        }
    }
}
