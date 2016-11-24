package Servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/**
 * 
 * @author Esteban's
 */
public class ServidorMundos {
    JFrameServidor ventana;
    Socket cliente1;
    Socket cliente2;
    Socket cliente3;
    Socket cliente4;
    
    public ServidorMundos(JFrameServidor padre){
        this.ventana = padre;//Referencia a la ventana
    }
    
    public void runServer(int cantidadJugadores){
        try{
            ServerSocket servidor = new ServerSocket(8081);
            ventana.desplegarMensaje("El servidor se encuentra activo");
            ventana.desplegarMensaje("Van a jugar " + cantidadJugadores + " jugadores");
            
            //Siempre van a ser minimo dos jugadores
            cliente1 = servidor.accept();
            ventana.desplegarMensaje("Se ha conectado el jugador 1");
            HiloServidor jugador1 = new HiloServidor (cliente1, this, 1);
            jugador1.start();
            
            //Segundo jugador
            cliente2 = servidor.accept();
            ventana.desplegarMensaje("Se ha conectado el jugador 2");
            HiloServidor jugador2 = new HiloServidor (cliente2, this, 2);
            jugador2.start();
            
            if (cantidadJugadores != 2){//Si entro acá minimo va a haber un jugador 3
                //Tercer jugador
                cliente3 = servidor.accept();
                ventana.desplegarMensaje("Se ha conectado el jugador 3");
                HiloServidor jugador3 = new HiloServidor (cliente3, this, 3);
                jugador3.start();
                jugador1.enemigo2 = jugador3;
                jugador2.enemigo2 = jugador3;
                jugador3.enemigo1 = jugador1;
                jugador3.enemigo2 = jugador2;
                if (cantidadJugadores == 4){//Si entro acá debo agregar un ultimo jugador
                    //Cuarto jugador
                    cliente4 = servidor.accept();
                    ventana.desplegarMensaje("Se ha conectado el jugador 4");
                    HiloServidor jugador4 = new HiloServidor (cliente4, this, 4);
                    jugador4.start();
                    jugador1.enemigo3 = jugador4;
                    jugador2.enemigo3 = jugador4;
                    jugador3.enemigo3 = jugador4;
                    
                    jugador4.enemigo1 = jugador1;
                    jugador4.enemigo2 = jugador2;
                    jugador4.enemigo3 = jugador3;
                }
            }
            //Se han terminado de conectar los jugadores necesarios
            
            jugador1.enemigo1 = jugador2;
            jugador2.enemigo1 = jugador1;
            ventana.desplegarMensaje("Todos los jugadores se han conectado");
            /*
            Los enemigos estan distribuidos de la siguiente manera
            JUGADOR 1
                Enemigo 1 = Jugador2
                Enemigo 2 = Jugador3
                Enemigo 3 = Jugador4
            JUGADOR 2
                Enemigo 1 = Jugador1
                Enemigo 2 = Jugador3
                Enemigo 3 = Jugador4
            JUGADOR 3
                Enemigo 1 = Jugador1
                Enemigo 2 = Jugador2
                Enemigo 3 = Jugador4
            JUGADOR 4
                Enemigo 1 = Jugador1
                Enemigo 2 = Jugador2
                Enemigo 3 = Jugador3
            */
            
            //Ciclo necesario para que el servidor no se cierre
            while (true){
                
            }
        } catch (IOException e){
            ventana.desplegarMensaje("Ha ocurrido un error en el servidor...");
        }
    }
}
