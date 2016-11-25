/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fabricas;

/**
 * Esta es la subclase de elemento donde la orientación importa, como las minas, armerías, templo y mercado
 * @author esteban
 */
public abstract class Fabrica extends Elemento{
    
    protected Orientacion orientacionFabrica;

    public Fabrica(Orientacion orientacionFabrica, int posicionX, int posicionY) {
        super(posicionX, posicionY);
        this.orientacionFabrica = orientacionFabrica;
    }
}