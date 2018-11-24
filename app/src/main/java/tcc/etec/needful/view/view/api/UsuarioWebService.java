package tcc.etec.needful.view.view.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import tcc.etec.needful.view.view.model.UsuarioModel;

public class UsuarioWebService {

    private Gson gson = new Gson();
    private Type usuarioType = new TypeToken<UsuarioModel>() {
    }.getType();
    private Type listUsuarioType = new TypeToken<List<UsuarioModel>>() {
    }.getType();

    private String checkLogin = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/checkLogin/";
    private String buscarEmailLogin = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/buscarEmailLogin/";
    private String existeEmailLogin = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/existeEmailLogin/";
    private String alterarSenha = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/alterarSenha/";
    private String alteraConta = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/alterarconta/";
    private String permissaoDeLogin = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/permissaoDeLogin/";
    private String permissaoDaAreaRestrita = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/permissaoDaAreaRestrita/";
    private String criarusuario = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/criarusuario/";
    private String pesquisaTipoUsuario = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/pesquisaTipoUsuario/";
    private String pesquisaUsuario = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/pesquisaUsuario/";
    private String readJtablea = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/pesquisaUsuarioa";
    private String readJtableAD = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/pesquisaUsuarioAD";
    private String existerContaa = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/existerContaa/";
    private String existerContaAD = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/existerContaAD/";
    private String usuarioAD = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/usuarioAD/";
    private String statusConta = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/statusConta/";
    private String permissaoAlterar = "http://" + Constantes.getIpWebservice()
            + ":8080/WSNeedful/webresources/usuarios/permissaoAlterar/";

    public List<UsuarioModel> readJtablea() throws JsonSyntaxException, SocketException, IOException, ExecutionException, InterruptedException {
        List<UsuarioModel> retorno = new ArrayList<>();
        String retornoJson = new WebService(readJtablea, Constantes.getGET()).execute().get();
        retorno = gson.fromJson(retornoJson, listUsuarioType);
        return retorno;
    }

    /*public List<UsuarioModel> readJtableAD() throws JsonSyntaxException, SocketException, IOException {
        List<UsuarioModel> retorno = new ArrayList<>();
        retorno = gson.fromJson(httpClient.sendGET(readJtableAD, Constantes.getGet()), listUsuarioType);

        return retorno;
    }

    public UsuarioModel buscarEmailORLogin(UsuarioModel UsuarioModel) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioModel);
        UsuarioModel retorno = null;

        String retornojson = httpClient.sendPOST(buscarEmailLogin, json, Constantes.getPost());
        retorno = gson.fromJson(retornojson, usuarioType);

        return retorno;
    }

    public boolean existeEmailLogin(UsuarioModel UsuarioModel) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioModel);
        System.out.println(json);
        boolean retorno = false;

        String retornojson = httpClient.sendPOST(existeEmailLogin, json, Constantes.getPost());
        retorno = Boolean.parseBoolean(retornojson);

        return retorno;
    }

    public boolean alterarSenha(UsuarioModel UsuarioModel) throws JsonSyntaxException, SocketException, IOException {
        boolean retorno = false;
        String json = gson.toJson(UsuarioModel);
        System.out.println(json);

        String retornojson = httpClient.sendPUT(alterarSenha, json, Constantes.getPut());
        retorno = Boolean.parseBoolean(retornojson);

        return retorno;

    }

    public boolean alteraConta(UsuarioModel UsuarioModel) throws JsonSyntaxException, SocketException, IOException {
        boolean retorno = false;
        String json = gson.toJson(UsuarioModel);
        System.out.println(json);

        String retornojson = httpClient.sendPUT(alteraConta, json, Constantes.getPut());
        retorno = Boolean.parseBoolean(retornojson);

        return retorno;

    }

    public UsuarioModel permissaoDeLogin(UsuarioModel UsuarioModel) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioModel);
        System.out.println(json);

        UsuarioModel retorno = new UsuarioModel();

        retorno = gson.fromJson(httpClient.sendPOST(permissaoDeLogin, json, Constantes.getPost()), usuarioType);

        return retorno;

    }

    public boolean permissaoDaAreaRestrita(UsuarioModel UsuarioModel)
            throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioModel);
        boolean retorno = false;
        System.out.println(json);

        retorno = Boolean.parseBoolean(httpClient.sendPOST(permissaoDaAreaRestrita, json, Constantes.getPost()));

        return retorno;
    }

    public boolean criarUsuario(UsuarioModel UsuarioModel) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioModel);
        boolean retorno = false;

        retorno = Boolean.parseBoolean(httpClient.sendPOST(criarusuario, json, Constantes.getPost()));

        return retorno;
    }

    public List<UsuarioModel> pesquisaTipoDeUsuario() throws JsonSyntaxException, SocketException, IOException {
        List<UsuarioModel> retorno = new ArrayList<>();

        retorno = gson.fromJson(httpClient.sendGET(pesquisaTipoUsuario, Constantes.getGet()), listUsuarioType);

        return retorno;
    }

    public UsuarioModel pesquisaUsuario(UsuarioModel UsuarioModel) throws JsonSyntaxException, SocketException, IOException {
        UsuarioModel retorno = new UsuarioModel();
        String json = gson.toJson(UsuarioModel);
        System.out.println(json);
        retorno = gson.fromJson(httpClient.sendPOST(pesquisaUsuario, json, Constantes.getGet()), usuarioType);

        return retorno;
    }

    public boolean checkLogin(UsuarioModel UsuarioModel) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioModel);
        boolean retorno = false;

        retorno = Boolean.parseBoolean(httpClient.sendPOST(checkLogin, json, Constantes.getPost()));

        return retorno;
    }

    public boolean existerContaAD(UsuarioModel UsuarioModel) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioModel);
        boolean retorno = false;
        System.out.println(json);
        retorno = Boolean.parseBoolean(httpClient.sendPOST(existerContaAD, json, Constantes.getPost()));

        return retorno;
    }

    public boolean existerContaa(UsuarioModel UsuarioModel) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioModel);
        boolean retorno = false;

        retorno = Boolean.parseBoolean(httpClient.sendPOST(existerContaa, json, Constantes.getPost()));

        return retorno;
    }

    public boolean statusConta(UsuarioModel UsuarioModel) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioModel);
        boolean retorno = false;
        System.out.println(json);
        retorno = Boolean.parseBoolean(httpClient.sendPUT(statusConta, json, Constantes.getPut()));

        return retorno;
    }

    public boolean permissaoAlterar(UsuarioModel UsuarioModel) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioModel);
        System.out.println(json);
        return Boolean.parseBoolean(httpClient.sendPOST(permissaoAlterar, json, Constantes.getPost()));
    }

    public boolean acessoAD(UsuarioModel UsuarioModel) throws SocketException, IOException {
        String json = gson.toJson(UsuarioModel);
        return Boolean.parseBoolean(httpClient.sendPOST(usuarioAD, json, Constantes.getPost()));
    }*/

}
