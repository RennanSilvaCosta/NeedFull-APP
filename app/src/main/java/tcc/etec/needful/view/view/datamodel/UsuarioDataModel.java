package tcc.etec.needful.view.view.datamodel;

public class UsuarioDataModel {

    private final static String TABELA = "usuarios";

    private final static String id = "id_usuario";
    private final static String nome = "nome_usuario";
    private final static String email = "email_usuario";
    private final static String cpf = "cpf_usuario";
    private final static String login = "login_usuario";
    private final static String senha = "senha_usuario";
    private final static String tipo_usuario = "id_tipo_usuario";
    private final static String statusAD = "statusAD";

    private static String comandoSQL = "";


    public static String criarTabela() {

        comandoSQL = "CREATE TABLE " + getTABELA();
        comandoSQL += " (";
        comandoSQL += id + " INTEGER PRIMARY KEY, ";
        comandoSQL += nome + " TEXT, ";
        comandoSQL += email + " TEXT, ";
        comandoSQL += cpf + " TEXT, ";
        comandoSQL += login + " TEXT, ";
        comandoSQL += senha + " TEXT, ";
        comandoSQL += tipo_usuario + " INTEGER, ";
        comandoSQL += statusAD + " INTEGER ";
        comandoSQL += ")";

        return comandoSQL;
    }


    public static String getTABELA() {
        return TABELA;
    }

    public static String getId() {
        return id;
    }

    public static String getNome() {
        return nome;
    }

    public static String getEmail() {
        return email;
    }

    public static String getCpf() {
        return cpf;
    }

    public static String getLogin() {
        return login;
    }

    public static String getSenha() {
        return senha;
    }

    public static String getTipo_usuario() {
        return tipo_usuario;
    }

    public static String getComandoSQL() {
        return comandoSQL;
    }

    public static void setComandoSQL(String comandoSQL) {
        UsuarioDataModel.comandoSQL = comandoSQL;
    }

    public static String getStatusAD() {
        return statusAD;
    }
}
