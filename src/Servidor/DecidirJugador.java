/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Esteban
 */
public class DecidirJugador {

        private int cantidadJ = 0;

        public int decidirCantidadJugadores() {

            JLabel label = new JLabel("          ¿Cuántos jugadores van a participar?");

            JButton Jugadores2 = new JButton("2 Jugadores");
            Jugadores2.addActionListener((ActionEvent e) -> {
                cantidadJ = 2;
                JButton button = (JButton)e.getSource();
                SwingUtilities.getWindowAncestor(button).dispose();
            });

            JButton Jugadores3 = new JButton("3 Jugadores");
            Jugadores3.addActionListener((ActionEvent e) -> {
                cantidadJ = 3;
                JButton button = (JButton)e.getSource();
                SwingUtilities.getWindowAncestor(button).dispose();
            });
            
            JButton Jugadores4 = new JButton("4 Jugadores");
            Jugadores4.addActionListener((ActionEvent e) -> {
                cantidadJ = 4;
                JButton button = (JButton)e.getSource();
                SwingUtilities.getWindowAncestor(button).dispose();
            });

            JPanel buttons = new JPanel();
            buttons.add(Jugadores2);
            buttons.add(Jugadores3);
            buttons.add(Jugadores4);

            JPanel content = new JPanel(new BorderLayout(8, 8));
            content.add(label, BorderLayout.CENTER);
            content.add(buttons, BorderLayout.SOUTH);

            JDialog dialog = new JDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setModal(true);
            dialog.setTitle("Jugadores");
            dialog.getContentPane().add(content);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

            return cantidadJ;
        }
}