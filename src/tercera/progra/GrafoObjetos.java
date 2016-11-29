/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

import Gui.TipoFabrica;

/**
 *
 * @author Esteban
 */
public class GrafoObjetos {
    int cantidadVertices;
    private Object[] vertices;
    private int[][] matrizAdyacencia;
    private boolean[] visitados;
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
   
    /**
     * Visita todos los que están conectados a un componente en específico
     * En este caso el componenente será un mundo
     * @param desde
     * @return 
     */
    public int visitarConectados (Elemento desde){
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
            if (vertices[desdeLogico] instanceof Conector){
                //Encontré un conector, llamada recursiva
                visitarConectados ((Elemento) vertices[desdeLogico]);
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
    /**
     * Obtiene el elemento según el índice deen la matriz de vectores
     * @param indice
     * @return 
     */
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
                    if (elementoPintar.getOrientacionFabrica() == Orientacion.Horizontal){
                        datosGuia[posicionX][posicionY] = TipoFabrica.ARMERIAH1;
                        datosGuia[posicionX+1][posicionY] = TipoFabrica.ARMERIAH2;
                    }
                    else if (elementoPintar.getOrientacionFabrica() == Orientacion.Vertical){
                        datosGuia[posicionX][posicionY] = TipoFabrica.ARMERIAV1;
                        datosGuia[posicionX][posicionY+1] = TipoFabrica.ARMERIAV2;
                    }
                    
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
                    if (elementoPintar.getOrientacionFabrica() == Orientacion.Horizontal){
                        datosGuia[posicionX][posicionY] = TipoFabrica.MINAH1;
                        datosGuia[posicionX+1][posicionY] = TipoFabrica.MINAH2;
                    }
                    else if (elementoPintar.getOrientacionFabrica() == Orientacion.Vertical){
                        datosGuia[posicionX][posicionY] = TipoFabrica.MINAV1;
                        datosGuia[posicionX][posicionY+1] = TipoFabrica.MINAV2;
                    }
                }
                else if (vertices[i] instanceof Mundo){
                    Mundo elementoPintar = (Mundo)vertices[i];
                    posicionX = elementoPintar.getPosicionX();
                    posicionY = elementoPintar.getPosicionY();
                    datosGuia[posicionX][posicionY] = TipoFabrica.MUNDO1;
                    datosGuia[posicionX+1][posicionY] = TipoFabrica.MUNDO2;
                    datosGuia[posicionX][posicionY+1] = TipoFabrica.MUNDO3;
                    datosGuia[posicionX+1][posicionY+1] = TipoFabrica.MUNDO4;
                }
                else if (vertices[i] instanceof Templo){
                    Templo elementoPintar = (Templo)vertices[i];
                    posicionX = elementoPintar.getPosicionX();
                    posicionY = elementoPintar.getPosicionY();
                    if (elementoPintar.getOrientacionFabrica() == Orientacion.Horizontal){
                        datosGuia[posicionX][posicionY] = TipoFabrica.TEMPLOH1;
                        datosGuia[posicionX+1][posicionY] = TipoFabrica.TEMPLOH2;
                    }
                    else if (elementoPintar.getOrientacionFabrica() == Orientacion.Vertical){
                        datosGuia[posicionX][posicionY] = TipoFabrica.TEMPLOV1;
                        datosGuia[posicionX][posicionY+1] = TipoFabrica.TEMPLOV2;
                    }
                }
                else if (vertices[i] instanceof Mercado){
                    Mercado elementoPintar = (Mercado)vertices[i];
                    posicionX = elementoPintar.getPosicionX();
                    posicionY = elementoPintar.getPosicionY();
                    if (elementoPintar.getOrientacionFabrica() == Orientacion.Horizontal){
                        datosGuia[posicionX][posicionY] = TipoFabrica.MERCADOH1;
                        datosGuia[posicionX+1][posicionY] = TipoFabrica.MERCADOH2;
                    }
                    else if (elementoPintar.getOrientacionFabrica() == Orientacion.Vertical){
                        datosGuia[posicionX][posicionY] = TipoFabrica.MERCADOV1;
                        datosGuia[posicionX][posicionY+1] = TipoFabrica.MERCADOV2;
                    }
                }
            }
        }
        
        return datosGuia;
    }
    
}
