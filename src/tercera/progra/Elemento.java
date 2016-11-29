/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tercera.progra;

/**
 * Los elementos serán los tipos de elementos que habrán en el grafo del mundo, estos son abstractos para
 * rendir código, los que importan son los que heredan de este
 * @author esteban
 */
public abstract class Elemento {
    
    /**
     * La posición X será la fila donde se ubica el cuadro más alto
     */
    protected int posicionX;
    
    /**
     * La posicón Y será la columna donde se ubica el cuadro más a la izquierda
     */
    protected int posicionY;

    /**
     * La identificación del objeto
     */
    protected int posicionGrafo;
    
    public Elemento(int posicionX, int posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        posicionGrafo = 0;
    }
    
    public Elemento(int posicionX, int posicionY, int identificador) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.posicionGrafo = identificador;
    }
    
    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public int getPosicionGrafo() {
        return posicionGrafo;
    }

    public void setPosicionGrafo(int posicionGrafo) {
        this.posicionGrafo = posicionGrafo;
    }
    
    
}