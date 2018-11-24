package tcc.etec.needful.view.view.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilChamados {

    public static final String URL_WEB_SERVICE = "https://rennancosta.000webhostapp.com/";

    public static final int CONNECTION_TIMEOUT = 10000;

    public static final int READ_TIMEOUT = 15000;

    public String pegaDataHora(){

        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();

        String dataFormatada = formataData.format(data);
        return dataFormatada;
    }

}
