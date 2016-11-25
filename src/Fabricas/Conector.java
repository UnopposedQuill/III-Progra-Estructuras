/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fabricas;

import java.util.*;

/**
 *
 * @author esteban
 */
public class Conector extends Elemento{
    
    private ArrayList <Fabrica> conexiones;

    public Conector(int posicionX, int posicionY) {
        super(posicionX, posicionY);
        conexiones = new ArrayList<>();
    }

    public ArrayList<Fabrica> getConexiones() {
        return conexiones;
    }
    
    
}
