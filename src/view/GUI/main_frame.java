package view.GUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class main_frame {
    private JTextArea TextArea;
    private JTextArea TextArea1;
    private JButton Button1;
    private JButton Button2;

    public main_frame() {
        Button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        Button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }

    public static void main(String[] args){
        main_frame mf = new main_frame();

    }
}


