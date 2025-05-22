package mikron.classeconectada.System;

import mikron.classeconectada.Telas.TelaInicial;
import mikron.classeconectada.db.DBUtil;

import javax.swing.*;
import java.sql.Date;
import java.util.Objects;

public class Util {

    public static String userPermission;

    public static void tela(JFrame frameNew, JFrame jframe, boolean dispose) {
        frameNew.setVisible(true);
        if(dispose) {
            jframe.dispose();
        }
    }
    public static void tela(JFrame frameNew, JFrame dispose) {
        tela(frameNew,dispose,true);
    }

    public static Date getSQLDate() {
        java.util.Date data = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(data.getTime());
        return sqlDate;
    }

}
