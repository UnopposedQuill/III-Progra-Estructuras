/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuerraMundos;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Esteban
 */
public class HiloCliente extends Thread{
    //solo de lectura

   ObjectInputStream entrada;
   JFrameGuerraMundos vcli; //referencia acliente
   public HiloCliente (ObjectInputStream entrada,JFrameGuerraMundos vcli) throws IOException {
      this.entrada=entrada;
      this.vcli=vcli;
   }
   
   public void run()
   {
       //VARIABLES
      String menser="",amigo="";
      int opcion=0;
      
      // solamente lee lo que el servidor threadServidor le envia
      while(true)
      {         
         try{
             // esta leyendo siempre la instruccion, un int
             opcion=entrada.readInt();
            
            switch(opcion)
            {
               case 1://mensaje enviado
                  
                  break;
               case 2://se lee el nombre del user  
                  menser = entrada.readUTF();
                  // coloca el nombre del enemigo
                  vcli.setEnemigo(menser);                  
                  System.out.println("Op2: lee nombre enemigo: "+menser);
                  break;
               case 3://lee el numero del jugador
                  vcli.numeroJugador = entrada.readInt();
                  // lee el nomnre del enemigo vuando pide el status y lo coloca
                  // en la pantalla cliente
                  vcli.setEnemigo(entrada.readUTF());
                  System.out.println("OP3, lee numero de jugador "+vcli.numeroJugador);
                  break;
                case 4:
                    // lee el mensaje
                  menser = entrada.readUTF();
                  // muestra el mensjae en pantalla.
                  System.out.println("OP4, lee mensaje "+menser);
                  vcli.mostrar(menser);
                break;
                
                case 5:
                    System.out.println("OP5");
                    break;
                 
                case 6:
                     /* Actualizo el tablero del enemigo correspondiente
                     
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
                    int numEnemigo = entrada.readInt();
                    int [][] tableroAcualizar = (int[][])entrada.readUnshared();
                    
                    System.out.println("TABLERO Hilo Cliente");
                    for (int i = 0; i < 15 ; i++){
                        for (int j = 0; j < 15 ; j++){
                            System.out.print(tableroAcualizar[j][i]);
                        }
                        System.out.println();
                    }
                    
             switch (vcli.numeroJugador) {
                 case 1:
                     //Yo soy el jugador 1
                     if (numEnemigo == 2){
                         vcli.tableroEnemigo1 = tableroAcualizar;
                     }
                     else if (numEnemigo == 3){
                         vcli.tableroEnemigo2 = tableroAcualizar;
                     }
                     else if (numEnemigo == 4){
                         vcli.tableroEnemigo3 = tableroAcualizar;
                     }
                     break;
                 case 2:
                     //Yo soy el jugador 2
                     if (numEnemigo == 1){
                         vcli.tableroEnemigo1 = tableroAcualizar;
                     }
                     else if (numEnemigo == 3){
                         vcli.tableroEnemigo2 = tableroAcualizar;
                     }
                     else if (numEnemigo == 4){
                         vcli.tableroEnemigo3 = tableroAcualizar;
                     }
                     break;
                 case 3:
                     //Yo soy el jugador 3
                     if (numEnemigo == 1){
                         vcli.tableroEnemigo1 = tableroAcualizar;
                     }
                     else if (numEnemigo == 2){
                         vcli.tableroEnemigo2 = tableroAcualizar;
                     }
                     else if (numEnemigo == 4){
                         vcli.tableroEnemigo3 = tableroAcualizar;
                     }
                     break;
                 case 4:
                     //Yo soy el jugador 4
                     if (numEnemigo == 1){
                         vcli.tableroEnemigo1 = tableroAcualizar;
                     }
                     else if (numEnemigo == 2){
                         vcli.tableroEnemigo2 = tableroAcualizar;
                     }
                     else if (numEnemigo == 3){
                         vcli.tableroEnemigo3 = tableroAcualizar;
                     }
                     break;
                 default:
                     break;
             }
                    
                    System.out.println("Op6: Actualizar tablero enemigo");
                    break;
                    
            }
         }
         catch (IOException e){
            System.out.println("Error en la comunicación "+"Información para el usuario");
            break;
         } catch (ClassNotFoundException ex) {
              Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
      System.out.println("se desconecto el servidor");
   }
}
