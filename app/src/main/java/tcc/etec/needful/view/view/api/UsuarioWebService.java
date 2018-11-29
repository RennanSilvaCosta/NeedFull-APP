package tcc.etec.needful.view.view.api;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import tcc.etec.needful.view.view.model.UsuarioVO;

public class UsuarioWebService {

    private Gson gson = new Gson();
    private Type usuarioType = new TypeToken<UsuarioVO>() {
    }.getType();
    private Type listUsuarioType = new TypeToken<List<UsuarioVO>>() {
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

    public List<UsuarioVO> readJtablea() throws JsonSyntaxException, SocketException, IOException, ExecutionException, InterruptedException {
        List<UsuarioVO> retorno = new ArrayList<>();
        String retornoJson = new WebService(readJtablea, Constantes.getGET()).execute().get();
        retorno = gson.fromJson(retornoJson, listUsuarioType);
        return retorno;
    }

   /* public UsuarioVO buscarEmailORLogin(UsuarioVO UsuarioVO) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioVO);
        UsuarioVO retorno = null;
        String retornojson = httpClient.sendPOST(buscarEmailLogin, json, Constantes.getPost());
        retorno = gson.fromJson(retornojson, usuarioType);

        return retorno;
    }

    public boolean existeEmailLogin(UsuarioVO UsuarioVO) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioVO);
        System.out.println(json);
        boolean retorno = false;

        String retornojson = httpClient.sendPOST(existeEmailLogin, json, Constantes.getPost());
        retorno = Boolean.parseBoolean(retornojson);

        return retorno;
    }

    public UsuarioVO permissaoDeLogin(UsuarioVO UsuarioVO) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioVO);
        System.out.println(json);

        UsuarioVO retorno = new UsuarioVO();

        retorno = gson.fromJson(httpClient.sendPOST(permissaoDeLogin, json, Constantes.getPost()), usuarioType);

        return retorno;

    }

    public List<UsuarioVO> pesquisaTipoDeUsuario() throws JsonSyntaxException, SocketException, IOException {
        List<UsuarioVO> retorno = new ArrayList<>();

        retorno = gson.fromJson(httpClient.sendGET(pesquisaTipoUsuario, Constantes.getGet()), listUsuarioType);

        return retorno;
    }

    public UsuarioVO pesquisaUsuario(UsuarioVO UsuarioVO) throws JsonSyntaxException, SocketException, IOException {
        UsuarioVO retorno = new UsuarioVO();
        String json = gson.toJson(UsuarioVO);
        System.out.println(json);
        retorno = gson.fromJson(httpClient.sendPOST(pesquisaUsuario, json, Constantes.getGet()), usuarioType);

        return retorno;
    }*/

    public boolean checkLogin(UsuarioVO UsuarioVO) throws JsonSyntaxException, SocketException, IOException, ExecutionException, InterruptedException {
        String json = gson.toJson(UsuarioVO);
        return Boolean.parseBoolean(new WebService(checkLogin, json, Constantes.getPOST()).execute().get());
    }

   /* public boolean existerContaAD(UsuarioVO UsuarioVO) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioVO);
        boolean retorno = false;
        System.out.println(json);
        retorno = Boolean.parseBoolean(httpClient.sendPOST(existerContaAD, json, Constantes.getPost()));

        return retorno;
    }

    public boolean existerContaa(UsuarioVO UsuarioVO) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioVO);
        boolean retorno = false;

        retorno = Boolean.parseBoolean(httpClient.sendPOST(existerContaa, json, Constantes.getPost()));

        return retorno;
    }

    public boolean statusConta(UsuarioVO UsuarioVO) throws JsonSyntaxException, SocketException, IOException {
        String json = gson.toJson(UsuarioVO);
        boolean retorno = false;
        System.out.println(json);
        retorno = Boolean.parseBoolean(httpClient.sendPUT(statusConta, json, Constantes.getPut()));

        return retorno;
    }

    public boolean acessoAD(UsuarioVO UsuarioVO) throws SocketException, IOException {
        String json = gson.toJson(UsuarioVO);
        return Boolean.parseBoolean(httpClient.sendPOST(usuarioAD, json, Constantes.getPost()));
    }*/

}
