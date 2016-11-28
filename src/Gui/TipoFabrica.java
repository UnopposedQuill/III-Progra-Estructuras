/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

/**
 * Esta clase sólo debe ser usada para renderizar una tabla de mundo, el BLANK significa que la casilla debe estar
 * en blanco
 * @author esteban
 */
public enum TipoFabrica {
    
    AGUJERO,ARMERIA,CONECTOR,MINA,MUNDO,TEMPLO, BLANK, INCENDIADO;

    public static String fakeToString(TipoFabrica tipo) {
        switch(tipo){
            case AGUJERO:{
                return "Agujero Negro";
            }
            case ARMERIA:{
                return "Armería";
            }
            case CONECTOR:{
                return "Conector";
            }
            case MINA:{
                return "Mina";
            }
            case MUNDO:{
                return "Mundo";
            }
            case TEMPLO:{
                return "Templo";
            }
            case INCENDIADO:{
                return "Incendiado";
            }
            default:{
                return "Blank";
            }
        }
    }
}
