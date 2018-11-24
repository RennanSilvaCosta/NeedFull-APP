package tcc.etec.needful.view.view.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import tcc.etec.needful.view.view.model.ChamadosVO;

public class WebServiceChamado {

    private Gson gson = new Gson();
    private Type chamadoType = new TypeToken<ChamadosVO>() {
    }.getType();
    private Type listChamadoType = new TypeToken<List<ChamadosVO>>() {
    }.getType();
    private Type listStringType = new TypeToken<List<String>>() {
    }.getType();
    private WebService webService;
    // URL[
    private final static String IP = "18.228.43.192";

    private String listagemFiltro = "http://" + IP + ":8080/WSNeedful/webresources/chamados/";
    private String pesquisaTipoDeChamado = "http://" + IP
            + ":8080/WSNeedful/webresources/chamados/pesquisaTipoDeChamado/";
    private String FECHARCHAMADO = "http://" + IP + ":8080/WSNeedful/webresources/chamados/fecharChamadomanutencao/";
    private String atualizarInstalacao = "http://" + IP
            + ":8080/WSNeedful/webresources/chamados/alteraChamadoinstalacao/";
    private String atualizarmanutencao = "http://" + IP
            + ":8080/WSNeedful/webresources/chamados/alteraChamadomanutencao/";
    private String CARREGARTELADEINSTALACAO = "http://" + IP
            + ":8080/WSNeedful/webresources/chamados/carregarTelaEdicaoInstalacao/";
    private String carregarteladeManuntecao = "http://" + IP
            + ":8080/WSNeedful/webresources/chamados/carregarTelaEdicaoManutencao/";

    // Metodos do Web Services
    final String POST = "POST";
    final String PUT = "PUT";
    final String GET = "GET";
    final String DELETE = "DELETE";

    public List<ChamadosVO> listagempost(ChamadosVO chamados) throws IOException, ExecutionException, InterruptedException {
        String json = gson.toJson(chamados);
        String retornoJson = new WebService(listagemFiltro,GET).execute().get();

        return gson.fromJson(retornoJson, listChamadoType);
    }

   /* public boolean fecharChamado(ChamadosVO chamados) throws IOException {
        String json = gson.toJson(chamados);

        return Boolean.parseBoolean(httpClient.sendPUT(FECHARCHAMADO, json, PUT));
    }

    public ChamadosVO carregarTelaEdicaoInstalacao(ChamadosVO chamados) throws IOException {
        String json = gson.toJson(chamados);

        return gson.fromJson(httpClient.sendPOST(CARREGARTELADEINSTALACAO, json, POST), chamadoType);
    }

    public ChamadosVO carregarteladeManuntecao(ChamadosVO chamados) throws IOException {
        String json = gson.toJson(chamados);

        return gson.fromJson(httpClient.sendPOST(carregarteladeManuntecao, json, POST), chamadoType);
    }
*/
    public boolean atualizarInstalacao(ChamadosVO chamados) throws ExecutionException, InterruptedException {
        String json = gson.toJson(chamados);
        System.out.println(json);

  return new Boolean(new WebService(atualizarInstalacao,json,PUT).execute().get());
    }

    public boolean atualizarmanutencao(ChamadosVO chamados) throws ExecutionException, InterruptedException {
        String json = gson.toJson(chamados);
        System.out.println(json);
        return new Boolean(new WebService(atualizarmanutencao,json,PUT).execute().get());
    }

}
