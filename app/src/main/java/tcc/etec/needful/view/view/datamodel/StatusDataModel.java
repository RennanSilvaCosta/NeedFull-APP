package tcc.etec.needful.view.view.datamodel;

public class StatusDataModel {

    private final static String tabela = "status";

    private final static String idStatus = "id_status";
    private final static String status = "status";
    private final static String idTipoStatus = "id_tipo_status";

    private static String comandoSQL = "";


    public static String criarTabela(){

        setComandoSQL("CREATE TABLE " + getTabela());
        setComandoSQL(getComandoSQL() + " (");
        setComandoSQL(getComandoSQL() + idStatus + " INTEGER PRIMARY KEY, ");
        setComandoSQL(getComandoSQL() + status + " TEXT, ");
        setComandoSQL(getComandoSQL() + idTipoStatus + " INTEGER");
        setComandoSQL(getComandoSQL() + " )");

        return getComandoSQL();
    }



    public static String getTabela() {
        return tabela;
    }

    public static String getIdStatus() {
        return idStatus;
    }

    public static String getStatus() {
        return status;
    }

    public static String getIdTipoStatus() {
        return idTipoStatus;
    }

    public static String getComandoSQL() {
        return comandoSQL;
    }

    public static void setComandoSQL(String comandoSQL) {
        StatusDataModel.comandoSQL = comandoSQL;
    }
}
