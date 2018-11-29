package tcc.etec.needful.view.view.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilChamados {

    public String pegaDataHora(){

        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        String dataFormatada = formataData.format(data);
        return dataFormatada;
    }

    public String mesPt(String mes) {
        if (mes.trim().equals("Jan")) {
            return "Janeiro";
        } else if (mes.trim().equals("Feb")) {
            return "Fevereiro";
        } else if (mes.trim().equals("Mar")) {
            return "Março";
        } else if (mes.trim().equals("Apr")) {
            return "Abril";
        } else if (mes.trim().equals("May")) {
            return "Maio";
        } else if (mes.trim().equals("Jun")) {
            return "Junho";
        } else if (mes.trim().equals("Jul")) {
            return "Julho";
        } else if (mes.trim().equals("Aug")) {
            return "Agosto";
        } else if (mes.trim().equals("Sep")) {
            return "Setembro";
        } else if (mes.trim().equals("Oct")) {
            return "Outubro";
        } else if (mes.trim().equals("Nov")) {
            return "Novembro";
        } else if (mes.trim().equals("Dec")) {
            return "Dezembro";
        }
        return null;
    }

    public String mesNumerico(String mes) {
        if (mes.trim().equals("Jan")) {
            return "01";
        } else if (mes.trim().equals("Feb")) {
            return "02";
        } else if (mes.trim().equals("Mar")) {
            return "03";
        } else if (mes.trim().equals("Apr")) {
            return "04";
        } else if (mes.trim().equals("May")) {
            return "05";
        } else if (mes.trim().equals("Jun")) {
            return "06";
        } else if (mes.trim().equals("Jul")) {
            return "07";
        } else if (mes.trim().equals("Aug")) {
            return "08";
        } else if (mes.trim().equals("Sep")) {
            return "09";
        } else if (mes.trim().equals("Oct")) {
            return "10";
        } else if (mes.trim().equals("Nov")) {
            return "11";
        } else if (mes.trim().equals("Dec")) {
            return "12";
        }
        return null;
    }

    public String tipoChamado(int tipoChamado) {

        if (tipoChamado == 1) {
            return "Instalação";
        } else if (tipoChamado == 2) {
            return "Manutenção";
        }
        return null;
    }

    public String statusChamado(int status) {
        if (status == 1) {
            return "Aberto";
        } else if (status == 2) {
            return "Andamento";
        }
        return null;
    }

}
