package tcc.etec.needful.view.view.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import tcc.etec.needful.R;
import tcc.etec.needful.view.view.api.UsuarioWebService;
import tcc.etec.needful.view.view.api.WebServiceChamado;
import tcc.etec.needful.view.view.controller.ChamadosController;
import tcc.etec.needful.view.view.datamodel.ChamadosDataModel;
import tcc.etec.needful.view.view.datamodel.ClienteDataModel;
import tcc.etec.needful.view.view.datamodel.EnderecoDataModel;
import tcc.etec.needful.view.view.datamodel.StatusDataModel;
import tcc.etec.needful.view.view.datamodel.TecnicoDataModel;
import tcc.etec.needful.view.view.datamodel.UsuarioDataModel;
import tcc.etec.needful.view.view.model.ChamadosVO;
import tcc.etec.needful.view.view.model.ClienteVO;
import tcc.etec.needful.view.view.model.UsuarioModel;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000;
    ChamadosController controller;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = getBaseContext();
        controller = new ChamadosController(context);
        apresentarTelaSplash();

    }

    private void apresentarTelaSplash() {

        sincronizarUsers();
        sincronizarChamados();

        SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
        boolean jaLogou = prefs.getBoolean("estaLogado", false);
        final int idTecnico = prefs.getInt("id_tecnico", 0);

        if (jaLogou) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent telaPrincipal = new Intent(SplashActivity.this, MainActivity.class);
                    telaPrincipal.putExtra("id_tecnico", idTecnico);
                    startActivity(telaPrincipal);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent telaPrincipal = new Intent(SplashActivity.this, UserLoginActivity.class);
                    startActivity(telaPrincipal);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }

    private void sincronizarUsers() {

        ChamadosVO chamadosVO = new ChamadosVO();
        ClienteVO clienteVO = new ClienteVO();
        clienteVO.setNome(null);
        chamadosVO.setClientVO(clienteVO);
        try {
            List<UsuarioModel> listaUSer = new UsuarioWebService().readJtablea();
            controller.deletarTabela(UsuarioDataModel.getTABELA());
            controller.criarTabela(UsuarioDataModel.criarTabela());
            for (UsuarioModel u : listaUSer) {
                controller.salvarUser(u);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void sincronizarChamados() {

        Gson gson = new Gson();
        WebServiceChamado webServiceChamado = new WebServiceChamado();
        ChamadosVO chamadosModel = new ChamadosVO();
        ClienteVO clienteModel = new ClienteVO();
        clienteModel.setNome("");
        chamadosModel.setClientVO(clienteModel);
        try {
            List<ChamadosVO> t = webServiceChamado.listagempost(chamadosModel);
            controller.deletarTabela(ChamadosDataModel.getTABELA());
            controller.deletarTabela(ClienteDataModel.getTabela());
            controller.deletarTabela(EnderecoDataModel.getTabela());
            controller.deletarTabela(StatusDataModel.getTabela());
            controller.deletarTabela(TecnicoDataModel.getTabela());

            controller.criarTabela(ChamadosDataModel.criarTabela());
            controller.criarTabela(ClienteDataModel.criarTabela());
            controller.criarTabela(EnderecoDataModel.criarTabela());
            controller.criarTabela(StatusDataModel.criarTabela());
            controller.criarTabela(TecnicoDataModel.criarTabela());
            for (ChamadosVO c : t) {
                controller.salvar(c);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


   /* private class SincronizarChamados extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog = new ProgressDialog(SplashActivity.this);
        HttpURLConnection connection;
        URL url = null;
        Uri.Builder builder;

        public SincronizarChamados() {

            this.builder = new Uri.Builder();
            builder.appendQueryParameter("app", "NeedFul");

        }

        @Override
        protected String doInBackground(String... strings) {

            //montar a URL com o endereço do script PHP

            try {

                url = new URL(UtilChamados.URL_WEB_SERVICE + "APISincronizarSistema.php");

            } catch (MalformedURLException e) {

                Log.e("Web Service ", "MalformedURLException - " + e.getMessage());

            } catch (Exception erro) {

                Log.e("Web Service ", "Exception - " + erro.getMessage());

            }

            try {

                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(UtilChamados.READ_TIMEOUT);
                connection.setConnectTimeout(UtilChamados.CONNECTION_TIMEOUT);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("charset", "UTF-8");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.connect();

                String query = builder.build().getEncodedQuery();

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                connection.connect();

            } catch (IOException e) {

                Log.e("Web Service ", "IOException - " + e.getMessage());
            }

            try {

                int response_code = connection.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {

                    InputStream input = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder resultado = new StringBuilder();

                    String linha;

                    while ((linha = reader.readLine()) != null) {
                        resultado.append(linha);
                    }
                    return resultado.toString();
                } else {
                    return "Erro de conexão";
                }

            } catch (IOException e) {

                Log.e("WebService", "IOException - " + e.getMessage());

                return e.toString();

            } finally {

                connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            try {

                JSONArray jArray = new JSONArray(result);

                if (jArray.length() != 0) {

                    controller.deletarTabela(ChamadosDataModel.getTABELA());
                    controller.deletarTabela(ClienteDataModel.getTabela());
                    controller.deletarTabela(EnderecoDataModel.getTabela());

                    controller.criarTabela(ChamadosDataModel.criarTabela());
                    controller.criarTabela(ClienteDataModel.criarTabela());
                    controller.criarTabela(EnderecoDataModel.criarTabela());

                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject jsonObject = jArray.getJSONObject(i);

                        ChamadosVO chamados = new ChamadosVO();
                        ClienteVO cliente = new ClienteVO();
                        EnderecoVO endereco = new EnderecoVO();

                        //chamado
                        chamados.setId(jsonObject.getInt(ChamadosDataModel.getId()));
                        chamados.setData(Date.valueOf(jsonObject.getString(ChamadosDataModel.getDataChamado())));
                        chamados.setHoras(Time.valueOf(jsonObject.getString(ChamadosDataModel.getHoraChamado())));
                        chamados.setAgendamento_Data(Date.valueOf(jsonObject.getString(ChamadosDataModel.getAgendamentoDataChamado())));
                        chamados.setAgendamento_horas(Time.valueOf(jsonObject.getString(ChamadosDataModel.getAgendamentoHoraChamado())));
                        chamados.setDescricao(jsonObject.getString(ChamadosDataModel.getObservacaoChamado()));
                        chamados.setIdTecnico(jsonObject.getInt(ChamadosDataModel.getIdTecnico()));
                        chamados.setTipoChamado(jsonObject.getInt(ChamadosDataModel.getIdTipoChamado()));
                        chamados.setIdStatusChamado(1);
                        chamados.setConfirmacao_Data(Date.valueOf(jsonObject.getString(ChamadosDataModel.getConfirmacaoData_chamado())));
                        chamados.setConfirmacao_Horas(Time.valueOf(jsonObject.getString(ChamadosDataModel.getConfirmacaoHora_chamado())));
                        chamados.setFinalizacao_Data(Date.valueOf(jsonObject.getString(ChamadosDataModel.getFinalizacaoData_chamado())));
                        chamados.setFinalizacao_horas(Time.valueOf(jsonObject.getString(ChamadosDataModel.getFinalizacaoHora_chamado())));
                        chamados.setJustificativa(jsonObject.getString(ChamadosDataModel.getJustificativa()));

                        //cliente
                        cliente.setId(jsonObject.getInt(ClienteDataModel.getIdCliente()));
                        cliente.setNome(jsonObject.getString(ClienteDataModel.getNomeCliente()));
                        cliente.seteMail(jsonObject.getString(ClienteDataModel.getEmailCliente()));
                        cliente.setLogin(jsonObject.getString(ClienteDataModel.getLoginCliente()));
                        cliente.setSenha(jsonObject.getString(ClienteDataModel.getSenhaCliente()));
                        cliente.setTelefone(jsonObject.getString(ClienteDataModel.getTelefoneCliente()));
                        cliente.setCelular(jsonObject.getString(ClienteDataModel.getCelularCliente()));
                        cliente.setRoteador(jsonObject.getString(ClienteDataModel.getEquipamentoCliente()));
                        cliente.setCabeamento(jsonObject.getString(ClienteDataModel.getCaboCliente()));

                        //endereco
                        endereco.setId(jsonObject.getInt(EnderecoDataModel.getIdEndereco()));
                        endereco.setRua(jsonObject.getString(EnderecoDataModel.getRuaEndereco()));
                        endereco.setBairro(jsonObject.getString(EnderecoDataModel.getBairroEndereco()));
                        endereco.setNumero(jsonObject.getString(EnderecoDataModel.getNumeroEdenreco()));
                        endereco.setCep(jsonObject.getString(EnderecoDataModel.getCepEndereco()));
                        endereco.setComplemento(jsonObject.getString(EnderecoDataModel.getComplementoEndereco()));
                        endereco.setReferencia(jsonObject.getString(EnderecoDataModel.getReferenciaEndereco()));

                        cliente.setEnderecoVO(endereco);
                        chamados.setClientVO(cliente);
                        controller.salvar(chamados, cliente, endereco);

                    }

                } else {

                    Toast.makeText(context, "Nenhum registro encontrado", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {

                Log.e("WebService", "erro JSONException - " + e.getMessage());

            } finally {

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();

                }
            }
        }
    }*/
}
