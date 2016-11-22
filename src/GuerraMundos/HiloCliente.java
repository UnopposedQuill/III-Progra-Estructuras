/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuerraMundos;
import java.io.*;
/**
 *
 * @author Esteban
 */
public class HiloCliente extends Thread{
    //solo de lectura
   DataInputStream entrada;
   JFrameGuerraMundos vcli; //referencia acliente
   public HiloCliente (DataInputStream entrada,JFrameGuerraMundos vcli) throws IOException {
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
                  int col = entrada.readInt();//lee columna
                  int fila = entrada.readInt();//lee fila
                  // llama a marcar, que es lo que hace el cliente cuando
                  // el enemigo marco la sailla
                  vcli.marcar(col,fila);
                  System.out.println("Op1: lee col y fila: "+col+" , "+fila);
                  break;
               case 2://se lee el nombre del user  
                  menser=entrada.readUTF();
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
                     // lee la columna
                     int col1 = entrada.readInt();
                     // lee la fila
                     int fil1 = entrada.readInt();
                     // hace el emtodo cliente para generar bomba
                     vcli.bomba(col1,fil1);
                     
                     System.out.println("Op5: recibe columna fila para bomba ");
                 break;
                    
            }
         }
         catch (IOException e){
            System.out.println("Error en la comunicación "+"Información para el usuario");
            break;
         }
      }
      System.out.println("se desconecto el servidor");
   }
}
