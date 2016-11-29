/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import Gui.TipoFabrica;
import java.util.*;

/**
 *
 * @author Esteban
 */
public class GrafoObjetos {
    int cantidadVertices;
    private Object[] vertices;
    private int[][] matrizAdyacencia;
    private boolean[] visitados;
    private ArrayList<Coordenada> danhos;
    
    /**
     * 
     * @param cantidadVertices
     */
    public GrafoObjetos(int cantidadVertices) {
        this.cantidadVertices = cantidadVertices;
        matrizAdyacencia = new int[cantidadVertices][cantidadVertices];
        vertices = new Object[cantidadVertices];
        visitados = new boolean[cantidadVertices];
        
        for (int i = 0; i < cantidadVertices; i++){
            vertices[i] = null;
            visitados[i] = false;
            for (int j = 0; j < cantidadVertices; j++){
                matrizAdyacencia[i][j] = 0;
            }
        }
        this.danhos = new ArrayList<>();
    }

    public ArrayList<Coordenada> getDanhos() {
        return danhos;
    }
    
    /**
     * Este método agrega nuevos daños a la lista de daños
     * @param coordenadaDelDanho La coordenada donde está el daño
     * @return True si se insertó correctamente, False en el otro csao
     */
    public boolean agregarDanhos(Coordenada coordenadaDelDanho){
        return this.danhos.add(coordenadaDelDanho);
    }
    
    public boolean agregarNuevoVertice (Elemento nuevoVertice){
        for (int i = 0; i < cantidadVertices; i++){
            if(vertices[i] == null){
                nuevoVertice.setPosicionGrafo(i);
                vertices[i] = nuevoVertice;
                
                return true;//Lo agregó de manera exitosa
            }
        }
        return false;//No encontró espacio disponible
    }
    
    public boolean agregarNuevaConexion (Elemento desde, Elemento hasta, int distancia){
        //Me aseguro de que existen los vertices
        int desdeLogico = desde.getPosicionGrafo();
        int hastaLogico = hasta.getPosicionGrafo();
        if (vertices[desdeLogico] != null && vertices[hastaLogico] != null){
            if (matrizAdyacencia [desdeLogico][hastaLogico] == 0){
                matrizAdyacencia [desdeLogico][hastaLogico] = distancia;//Agrego la conexión
                return true;
            }
        }
        return false;
    }
    
    @SuppressWarnings("empty-statement")
    public int RevisarConexiones (Elemento desde){
        int cantidadConexiones = 0;
        int desdeLogico = desde.getPosicionGrafo();
        int indiceVisual;
        int indiceVisual2 = desdeLogico+1;
        //Me aseguro de que existen los vertices
        if (vertices[desdeLogico] != null){
            for (int i = 0; i < cantidadVertices; i++){
                if(matrizAdyacencia[desdeLogico][i] != 0){
                    cantidadConexiones++;
                    indiceVisual = i+1;
                    System.out.println("Conexión desde " + indiceVisual2 + " hasta " + indiceVisual);
                }
            }
            
        }
        return cantidadConexiones;
    }
    
    public int visitarAdyacentes (Elemento desde){
        int cantidadConexiones = 0;
        int desdeLogico = desde.getPosicionGrafo();
        //Me aseguro de que existen los vertices
        if (vertices[desdeLogico] != null){
            for (int i = 0; i < cantidadVertices; i++){
                if(matrizAdyacencia[desdeLogico][i] != 0){
                    cantidadConexiones++;
                    visitados[i] = true;
                }
            }
            
        }
        return cantidadConexiones;
    }
    
    public int visitarAdyacentesMismoTipo (Object tipo){
        int cantidadConexiones = 0;
        //Me aseguro de que existen los vertices
        for (int i = 0; i < cantidadVertices; i++){
            if (vertices[i] instanceof Conector){
                System.out.println("ASDASDASD");
                RevisarConexiones((Elemento) vertices[i]);
                for (int j = 0; j < cantidadVertices; j++){
                    if(matrizAdyacencia[i][j] != 0){
                        cantidadConexiones++;
                        visitados[i] = true;
                    }
                }
            }
        }
        
        return cantidadConexiones;
    }
    
    public boolean eliminarConexion (Elemento desde, Elemento hasta){
        //Me aseguro de que existen los vertices
        int desdeLogico = desde.getPosicionGrafo();
        int hastaLogico = hasta.getPosicionGrafo();
        if (vertices[desdeLogico] != null && vertices[hastaLogico] != null){
            if (matrizAdyacencia [desdeLogico][hastaLogico] != 0){
                matrizAdyacencia [desdeLogico][hastaLogico] = 0;//Elimino la conexión
                return true;
            }
        }
        return false;
    }
    
    public void limpiarVisitados (){
        for (int i = 0; i < cantidadVertices; i++){
            visitados[i] = false;
        }
    }
    
    public Elemento obtenerElementoIndice (int indice){
        return (Elemento)vertices[indice];
    }
    
    
    public Object[][] generarMatriz (){
        Object[][] datosGuia = new Object[15][15];
        int posicionX;
        int posicionY;
        for (int i = 0; i < cantidadVertices; i++){
            if(vertices[i] != null){
                if (vertices[i] instanceof AgujeroNegro){
                    AgujeroNegro elementoPintar = (AgujeroNegro)vertices[i];
                    posicionX = elementoPintar.getPosicionX();
                    posicionY = elementoPintar.getPosicionY();
                    datosGuia[posicionX][posicionY] = TipoFabrica.AGUJERO;
                }
                else if (vertices[i] instanceof Armeria){
                    Armeria elementoPintar = (Armeria)vertices[i];
                    posicionX = elementoPintar.getPosicionX();
                    posicionY = elementoPintar.getPosicionY();
                    datosGuia[posicionX][posicionY] = TipoFabrica.ARMERIA;
                }
                else if (vertices[i] instanceof Conector){
                    Conector elementoPintar = (Conector)vertices[i];
                    posicionX = elementoPintar.getPosicionX();
                    posicionY = elementoPintar.getPosicionY();
                    datosGuia[posicionX][posicionY] = TipoFabrica.CONECTOR;
                }
                else if (vertices[i] instanceof Mina){
                    Mina elementoPintar = (Mina)vertices[i];
                    posicionX = elementoPintar.getPosicionX();
                    posicionY = elementoPintar.getPosicionY();
                    datosGuia[posicionX][posicionY] = TipoFabrica.MINA;
                }
                else if (vertices[i] instanceof Mundo){
                    Mundo elementoPintar = (Mundo)vertices[i];
                    posicionX = elementoPintar.getPosicionX();
                    posicionY = elementoPintar.getPosicionY();
                    datosGuia[posicionX][posicionY] = TipoFabrica.MUNDO;
                }
                else if (vertices[i] instanceof Templo){
                    Templo elementoPintar = (Templo)vertices[i];
                    posicionX = elementoPintar.getPosicionX();
                    posicionY = elementoPintar.getPosicionY();
                    datosGuia[posicionX][posicionY] = TipoFabrica.TEMPLO;
                }
                else if (vertices[i] instanceof Mercado){
                    Mercado elementoPintar = (Mercado)vertices[i];
                    posicionX = elementoPintar.getPosicionX();
                    posicionY = elementoPintar.getPosicionY();
                    datosGuia[posicionX][posicionY] = TipoFabrica.MERCADO;
                }
            }
        }
        
        return datosGuia;
    }
    
}
