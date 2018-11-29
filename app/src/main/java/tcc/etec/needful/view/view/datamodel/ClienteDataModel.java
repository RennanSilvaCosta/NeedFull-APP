package tcc.etec.needful.view.view.datamodel;

import com.google.android.gms.common.stats.StatisticalEventTrackerProvider;

public class ClienteDataModel {

    private final static String tabela = "cliente";

    private final static String idCliente = "id_cliente";
    private final static String nomeCliente = "nome_cliente";
    private final static String emailCliente = "email_cliente";
    private final static String loginCliente = "login_cliente";
    private final static String senhaCliente = "senha_cliente";
    private final static String telefoneCliente = "telefone_cliente";
    private final static String celularCliente = "celular_cliente";
    private final static String equipamentoCliente = "equipamento_cliente";
    private final static String caboCliente = "cabo_cliente";
    private final static String idEndereco = "id_endereco";

    private static String comandoSQL = "";

    public static String criarTabela(){

        setComandoSQL("CREATE TABLE " + getTabela());
        setComandoSQL(getComandoSQL() + "( ");
        setComandoSQL(getComandoSQL() + idCliente + " INTEGER PRIMARY KEY, ");
        setComandoSQL(getComandoSQL() + nomeCliente + " TEXT, ");
        setComandoSQL(getComandoSQL() + emailCliente + " TEXT, ");
        setComandoSQL(getComandoSQL() + loginCliente + " TEXT, ");
        setComandoSQL(getComandoSQL() + senhaCliente + " TEXT, ");
        setComandoSQL(getComandoSQL() + telefoneCliente + " TEXT, ");
        setComandoSQL(getComandoSQL() + celularCliente + " TEXT, ");
        setComandoSQL(getComandoSQL() + equipamentoCliente + " TEXT, ");
        setComandoSQL(getComandoSQL() + caboCliente + " TEXT, ");
        setComandoSQL(getComandoSQL() + idEndereco + " INTEGER,");
        setComandoSQL(getComandoSQL() + " FOREIGN KEY (id_endereco) REFERENCES endereco (id_endereco)");
        setComandoSQL(getComandoSQL() + ")");

        return getComandoSQL();

    }



    public static String getComandoSQL() {
        return comandoSQL;
    }

    public static void setComandoSQL(String comandoSQL) {
        ClienteDataModel.comandoSQL = comandoSQL;
    }

    public static String getTabela() {
        return tabela;
    }

    public static String getIdCliente() {
        return idCliente;
    }

    public static String getNomeCliente() {
        return nomeCliente;
    }

    public static String getEmailCliente() {
        return emailCliente;
    }

    public static String getLoginCliente() {
        return loginCliente;
    }

    public static String getSenhaCliente() {
        return senhaCliente;
    }

    public static String getTelefoneCliente() {
        return telefoneCliente;
    }

    public static String getCelularCliente() {
        return celularCliente;
    }

    public static String getEquipamentoCliente() {
        return equipamentoCliente;
    }

    public static String getCaboCliente() {
        return caboCliente;
    }

    public static String getIdEndereco() {
        return idEndereco;
    }
}
