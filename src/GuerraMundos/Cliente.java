/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuerraMundos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Esteban
 */
public class Cliente {
   public static String IP_SERVER = "localhost"; //IP del Servidor
   JFrameGuerraMundos ventanaCliente; // Ventana del cliente
   ObjectOutputStream salida = null;//escribir comunicacion
   ObjectInputStream entrada = null;//leer comunicacion

   Socket cliente = null;//para la comunicacion
   String nomCliente;// nombre del user
   /** Creates a new instance of Cliente */
   public Cliente(JFrameGuerraMundos vent) throws IOException
   {      
      this.ventanaCliente=vent;
   }
   
   public void conexion() throws IOException 
   {
      try {
          // se conecta con dos sockets al server, uno comunicacion otro msjes
         cliente = new Socket(Cliente.IP_SERVER, 8081);
         // inicializa las entradas-lectura y salidas-escritura
         salida = new ObjectOutputStream(cliente.getOutputStream());
         entrada = new ObjectInputStream(cliente.getInputStream());
         // solicita el nombre del user
         nomCliente = JOptionPane.showInputDialog("Introducir Nick :");
         //Lo coloca en la ventana
         ventanaCliente.setTitle(nomCliente);
         // es lo primero que envia al server
         // el thread servidor esta pendiente de leer el nombre antes de entrar
         // al while para leer opciones
         salida.writeUTF(nomCliente);
         System.out.println("1. Envia el nombre del cliente: "+nomCliente);
      } catch (IOException e) {
         System.out.println("\tEl servidor no esta levantado");
         System.out.println("\t=============================");
      }
      // solo se le pasa entrada pues es solo para leer mensajes
      // el hiloCliente lee lo que el servidor le envia, opciones y como tiene referencia
      // a la ventana gato puede colocar en la pantalla cualquier cosa, como las
      //imagenes de X o O, llamar a metodo marcar, colocar el nombre de enemigo
      // o el suyo propio
      new HiloCliente(entrada, ventanaCliente).start();
   }
   
   //GETTET AND SETTER
   public String getNombre()
   {
      return nomCliente;
   }
}
