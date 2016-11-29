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
    
    AGUJERO,ARMERIAH1,ARMERIAH2,ARMERIAV1,ARMERIAV2,CONECTOR,CONECTOR2,CONECTOR3,MINAH1,MINAH2,MINAV1,MINAV2,MUNDO1,MUNDO2,MUNDO3,MUNDO4,TEMPLOH1,TEMPLOH2,TEMPLOV1,TEMPLOV2,BLANK,INCENDIADO,MERCADOH1,MERCADOH2,MERCADOV1,MERCADOV2;

    public static String fakeToString(TipoFabrica tipo) {
        switch(tipo){
            case AGUJERO:{
                return "Agujero Negro";
            }
            case ARMERIAH1:{
                return "Armería Horizontal 1";
            }
            case ARMERIAH2:{
                return "Armería Horizontal 2";
            }
            case ARMERIAV1:{
                return "Armería Vertical 1";
            }
            case ARMERIAV2:{
                return "Armería Vertical 2";
            }
            case CONECTOR:{
                return "Conector";
            }
            case MINAH1:{
                return "Mina Horizontal 1";
            }
            case MINAH2:{
                return "Mina Horizontal 2";
            }
            case MINAV1:{
                return "Mina Vertical 1";
            }
            case MINAV2:{
                return "Mina Vertical 2";
            }
            case MUNDO1:{
                return "Mundo Superior Izquierda";
            }
            case MUNDO2:{
                return "Mundo Superior Derecha";
            }
            case MUNDO3:{
                return "Mundo Inferior Izquierda";
            }
            case MUNDO4:{
                return "Mundo Inferior Derecha";
            }
            case TEMPLOH1:{
                return "Templo Horizontal 1";
            }
            case TEMPLOH2:{
                return "Templo Horizontal 2";
            }
            case TEMPLOV1:{
                return "Templo Vertical 1";
            }
            case TEMPLOV2:{
                return "Templo Vertical 2";
            }
            case INCENDIADO:{
                return "Incendiado";
            }
            case MERCADOH1:{
                return "Mercado Horizontal 1";
            }
            case MERCADOH2:{
                return "Mercado Horizontal 2";
            }
            case MERCADOV1:{
                return "Mercado Vertical 1";
            }
            case MERCADOV2:{
                return "Mercado Vertical 2";
            }
            default:{
                return "Blank";
            }
        }
    }
}
