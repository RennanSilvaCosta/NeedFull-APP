package tcc.etec.needful.view.view.datamodel;

public class TecnicoDataModel {

    private final static String tabela = "tecnico";

    private final static String idTecnico = "id_tecnico";
    private final static String nomeTecnico = "nome_tecnico";
    private final static String idUsuario = "id_usuario";

    private static String comandoSQL = "";

    public static String criarTabela(){

        setComandoSQL("CREATE TABLE " + getTabela());
        setComandoSQL(getComandoSQL() + " (");
        setComandoSQL(getComandoSQL() + idTecnico + " INTEGER PRIMARY KEY, ");
        setComandoSQL(getComandoSQL() +  nomeTecnico + " TEXT, ");
        setComandoSQL(getComandoSQL() + idUsuario + " INTEGER ");
        setComandoSQL(getComandoSQL() + " )");

        return getComandoSQL();
    }

    public static String getTabela() {
        return tabela;
    }

    public static String getIdTecnico() {
        return idTecnico;
    }

    public static String getNomeTecnico() {
        return nomeTecnico;
    }

    public static String getIdUsuario() {
        return idUsuario;
    }

    public static String getComandoSQL() {
        return comandoSQL;
    }

    public static void setComandoSQL(String comandoSQL) {
        TecnicoDataModel.comandoSQL = comandoSQL;
    }
}
