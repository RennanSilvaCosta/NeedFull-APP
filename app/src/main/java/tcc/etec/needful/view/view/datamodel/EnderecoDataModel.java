package tcc.etec.needful.view.view.datamodel;

public class EnderecoDataModel {

    private final static String tabela = "endereco";

    private final static String idEndereco = "id_endereco";
    private final static String ruaEndereco = "rua_endereco";
    private final static String bairroEndereco = "bairro_endereco";
    private final static String numeroEdenreco = "numero_endereco";
    private final static String cepEndereco = "cep_endereco";
    private final static String complementoEndereco = "complemento_endereco";
    private final static String referenciaEndereco = "referencia_endereco";

    private static String comandoSQL = "";

    public static String criarTabela(){

        setComandoSQL("CREATE TABLE " + getTabela());
        setComandoSQL(getComandoSQL() + " (");
        setComandoSQL(getComandoSQL() + idEndereco + " INTEGER PRIMARY KEY, ");
        setComandoSQL(getComandoSQL() + ruaEndereco + " TEXT, ");
        setComandoSQL(getComandoSQL() + bairroEndereco + " TEXT, ");
        setComandoSQL(getComandoSQL() + numeroEdenreco + " TEXT, ");
        setComandoSQL(getComandoSQL() + cepEndereco + " TEXT, ");
        setComandoSQL(getComandoSQL() + complementoEndereco + " TEXT, ");
        setComandoSQL(getComandoSQL() + referenciaEndereco + " TEXT ");
        setComandoSQL(getComandoSQL() + ")");

        return getComandoSQL();
    }

    public static String getTabela() {
        return tabela;
    }

    public static String getComandoSQL() {
        return comandoSQL;
    }

    public static void setComandoSQL(String comandoSQL) {
        EnderecoDataModel.comandoSQL = comandoSQL;
    }

    public static String getIdEndereco() {
        return idEndereco;
    }

    public static String getRuaEndereco() {
        return ruaEndereco;
    }

    public static String getBairroEndereco() {
        return bairroEndereco;
    }

    public static String getNumeroEdenreco() {
        return numeroEdenreco;
    }

    public static String getCepEndereco() {
        return cepEndereco;
    }

    public static String getComplementoEndereco() {
        return complementoEndereco;
    }

    public static String getReferenciaEndereco() {
        return referenciaEndereco;
    }
}
