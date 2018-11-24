package tcc.etec.needful.view.view.model;

import java.io.Serializable;

public class UsuarioModel implements Serializable {

    private int id;
    private String nome;
    private String email;
    private String CPF;
    private String login;
    private String senha;
    private int idTipoUsuario;
    private int statusAD;

    public int getStatusAD() {
        return statusAD;
    }

    public void setStatusAD(int statusAD) {
        this.statusAD = statusAD;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }
}
