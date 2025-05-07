package mikron.classeconectada.System;

import javax.swing.*;

public class Util {


    public static void tela(JFrame frameNew, JFrame jframe, boolean dispose) {
        frameNew.setVisible(true);
        if(dispose) {
            jframe.dispose();
        }
    }
    public static void tela(JFrame frameNew, JFrame dispose) {
        tela(frameNew,dispose,true);
    }

}
