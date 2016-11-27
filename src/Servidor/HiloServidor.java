package Servidor;
import java.io.*;
import java.net.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Esteban
 */
public class HiloServidor extends Thread{
    Socket cliente = null;
    
    ObjectOutputStream salida = null;
    ObjectInputStream entrada = null;
    
    String nombreUsuario;
    ServidorMundos servidor;
    
    HiloServidor enemigo1 = null;
    HiloServidor enemigo2 = null;
    HiloServidor enemigo3 = null;
    
    int numeroJugador;
    String mensaje;
    
    

    
    public HiloServidor (Socket jugador, ServidorMundos servidor, int numJugador){
        this.cliente = jugador;
        this.servidor = servidor;
        this.numeroJugador = numJugador;
        nombreUsuario = "";
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    @Override
    public void run(){
        try{
            //Primero va el output
            salida = new ObjectOutputStream(cliente.getOutputStream());
            entrada = new ObjectInputStream(cliente.getInputStream());
            
            this.setNombreUsuario(entrada.readUTF());
            
            enviarNombre1();//Enemigo 1
            enviarNombre2();//Enemigo 2
            enviarNombre3();//Enemigo 3
        }catch (IOException e){
            
        }
        
        //Variables a utilizar en el hilo
        int opcion;
        
        while (true){
            try{
                
                opcion = entrada.readInt();
                switch (opcion){
                    case 1://envio de coordenada
                    // LEYO OPCION 1
                    // LEE LAS COORDENADAS QUE ENVIO ESTE CLIENTE
                    // Luego las pasa al enemigo para que marque su tablero
                   int columna = entrada.readInt();//Lee coordenada
                   int fila = entrada.readInt();//Lee coordenada fila
                   servidor.ventana.desplegarMensaje("Recibido " + columna +","+fila);
                   // ENVIA LA COORDENADA AL ENEMIGO
                   enemigo1.salida.writeInt(1);// Opcion 1 al hilo cliente del enemigo
                   enemigo1.salida.writeInt(columna);// envia columna
                   enemigo1.salida.writeInt(fila);// envia fila
                   
                   
                   
                   System.out.println("Op1: lee col,fil, envia al enemigo, 1, col, fila: "+columna+" , "+fila );
                   break;
                case 2:
                    //Enviar mensajes a todos los jugadores
                     mensaje = entrada.readUTF();
                     System.out.println("Caso 2");
                     // envia un 4 al threadCliente enemigo
                     if (enemigo1 != null){
                        enemigo1.salida.writeInt(4);
                        // envia el mensaje al thread cliente enemigo
                        enemigo1.salida.writeUTF(mensaje);
                        enemigo1.salida.flush();
                     }
                     
                     if (enemigo2 != null){
                        // envia un 4 al thradCliente enemigo
                        enemigo2.salida.writeInt(4);
                        // envia el mensaje al thread cliente enemigo
                        enemigo2.salida.writeUTF(mensaje);
                        enemigo2.salida.flush();
                     }
                     if (enemigo3 != null){
                        // envia un 4 al thradCliente enemigo
                        enemigo3.salida.writeInt(4);
                        // envia el mensaje al thread cliente enemigo
                        enemigo3.salida.writeUTF(mensaje);
                        enemigo3.salida.flush();
                     }
                   break;
                case 3: //le envia el status, que es el numero de jugador y el nombre enemigo
                    salida.writeInt(3);
                    salida.writeInt(numeroJugador);
                    
                    if (enemigo1 != null)
                        salida.writeUTF(enemigo1.nombreUsuario);
                    else
                        salida.writeUTF("");
                    System.out.println("3. Op3: envia 3 y numeroJugador y enemigo: "+ numeroJugador);
                    salida.flush();
                   break;
                 case 4:
                    // lee el mensaje enviado desde el jframe
                    mensaje = entrada.readUTF();
                    // envia un 4 al thradCliente enemigo
                    enemigo1.salida.writeInt(4);
                    // envia el emnsaje al thread cliente enemigo
                    enemigo1.salida.writeUTF(mensaje);
                    enemigo1.salida.flush();
                    System.out.println("Op4: envia 4 y mensaje: "+ mensaje);
                    break;
                 case 5:
                     
                    break;
                 case 6:
                    //Envio mi array a todos los enemigos y tambien indico que numero de jugador soy
                    int numJugador = entrada.readInt(); 
                    int[][] miTablero = (int[][])entrada.readUnshared();
                    System.out.println("TABLERO");
                    for (int i = 0; i < 15 ; i++){
                        for (int j = 0; j < 15 ; j++){
                            System.out.print(miTablero[j][i]);
                        }
                        System.out.println();
                    }
                    if (enemigo1 != null){
                        enemigo1.salida.writeInt(6); //Actualizar tablero enemigo
                        enemigo1.salida.writeInt(numJugador);
                        enemigo1.salida.writeUnshared(miTablero);
                        System.out.println("Se envia la informacion a enemigo1");
                    }
                    
                    if (enemigo2 != null){
                        enemigo2.salida.writeInt(6); //Actualizar tablero enemigo
                        enemigo2.salida.writeInt(numJugador);
                        enemigo2.salida.writeUnshared(miTablero);
                    }
                    
                    if (enemigo3 != null){
                        enemigo3.salida.writeInt(6); //Actualizar tablero enemigo
                        enemigo3.salida.writeInt(numJugador);
                        enemigo3.salida.writeUnshared(miTablero);
                    }
                    
                    servidor.ventana.desplegarMensaje("Se ha actualizado el array en todos los usuarios");
                    
                break;
                }
            }catch (IOException e) {
              System.out.println("El cliente termino la conexion");
              break;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        servidor.ventana.desplegarMensaje("Se removio un usuario");
    	
    	try
    	{
          servidor.ventana.desplegarMensaje("Se desconecto un usuario: "+nombreUsuario);
          cliente.close();
    	}	
        catch(Exception et)
        {servidor.ventana.desplegarMensaje("no se puede cerrar el socket");}
    }
    
    public void enviarNombre1(){
        if (enemigo1 != null){
            try{
                enemigo1.salida.writeInt(2);
                enemigo1.salida.writeUTF(this.getNombreUsuario());
            }catch (IOException e){
                
            }
        }
    }
    
    public void enviarNombre2(){
        if (enemigo2 != null){
            try{
                enemigo2.salida.writeInt(2);
                enemigo2.salida.writeUTF(this.getNombreUsuario());
            }catch (IOException e){
                
            }
        }
    }
    
    public void enviarNombre3(){
        if (enemigo3 != null){
            try{
                enemigo3.salida.writeInt(2);
                enemigo3.salida.writeUTF(this.getNombreUsuario());
            }catch (IOException e){
                
            }
        }
    }
    
}
