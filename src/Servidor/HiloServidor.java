package Servidor;
import java.io.*;
import java.net.*;
import java.util.Vector;
/**
 *
 * @author Esteban
 */
public class HiloServidor extends Thread{
    Socket cliente = null;
    DataInputStream entrada = null;
    DataOutputStream salida = null;
    String nombreUsuario;
    ServidorMundos servidor;
    
    HiloServidor enemigo1 = null;
    HiloServidor enemigo2 = null;
    HiloServidor enemigo3 = null;
    
    int numeroJugador;
    
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
            entrada = new DataInputStream(cliente.getInputStream());
            salida = new DataOutputStream(cliente.getOutputStream());
            
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
                case 2:// 
                    
                    
                   break;
                case 3: //le envia el status, que es el numero de jugador y el nombre enemigo
                    salida.writeInt(3);
                    salida.writeInt(numeroJugador);
                    if (enemigo1 != null)
                        salida.writeUTF(enemigo1.nombreUsuario);
                    else
                        salida.writeUTF("");
                    System.out.println("3. Op3: envia 3 y numeroJugador y enemigo: "+ numeroJugador);
                   break;
                 case 4:
                     // lee el mensaje enviado desde el jframe
                     String mensaje = entrada.readUTF();
                     // envia un 4 al thradCliente enemigo
                     enemigo1.salida.writeInt(4);
                     // envia el emnsaje al thread cliente enemigo
                     enemigo1.salida.writeUTF(mensaje+"OtraCosa");
                     System.out.println("Op4: envia 4 y mensaje: "+ mensaje);
                 break;
                 case 5:
                     // lee la columna
                     int col = entrada.readInt();
                     // lee la fila
                     int fil = entrada.readInt();
                     // envia un 5 al thradCliente enemigo
                     enemigo1.salida.writeInt(5);
                     // envia el emnsaje al thread cliente enemigo
                     enemigo1.salida.writeInt(col);
                     enemigo1.salida.writeInt(fil);
                     System.out.println("Op5: envia columna fila para bomba ");
                 break;
                }
            }catch (IOException e) {
              System.out.println("El cliente termino la conexion");
              break;
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
