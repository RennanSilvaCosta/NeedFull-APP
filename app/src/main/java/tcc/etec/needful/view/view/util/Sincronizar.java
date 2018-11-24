package tcc.etec.needful.view.view.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import tcc.etec.needful.view.view.controller.ChamadosController;


public class Sincronizar  extends AsyncTask<String, String, String> {

    ChamadosController controller;
    ProgressDialog progressDialog;
    Context context;
    HttpURLConnection connection;
    URL url = null;
    Uri.Builder builder;


    public Sincronizar() {

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
            connection.setRequestProperty("charset", "utf-8");

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

        try{

            int response_code = connection.getResponseCode();

            if(response_code == HttpURLConnection.HTTP_OK){

                InputStream input = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder resultado = new StringBuilder();


                String linha;

                while((linha = reader.readLine()) != null){
                    resultado.append(linha);
                }
                return resultado.toString();
            }else{
                return "Erro de conexão";
            }

        }catch (IOException e){

            Log.e("WebService","IOException - "+e.getMessage());

            return e.toString();

        }finally{

            connection.disconnect();
        }

    }

    /*@Override
    protected void onPostExecute(String result) {

        try{

            JSONArray jArray = new JSONArray(result);

            if(jArray.length() != 0){

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
                    chamados.setIdStatusChamado(jsonObject.getInt(ChamadosDataModel.getIdStatusChamado()));
                    chamados.setConfirmacao_Data(Date.valueOf("2018-11-01"));
                    chamados.setConfirmacao_Horas(Time.valueOf("17:22:52"));
                    chamados.setFinalizacao_Data(Date.valueOf("2018-11-01"));
                    chamados.setFinalizacao_horas(Time.valueOf("17:26:46"));
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

                    controller.salvar(chamados,cliente,endereco);

                }


            }else{

                //Toast.makeText(context , "Nenhum registro encontrado", Toast.LENGTH_LONG).show();
            }

        }catch (JSONException e){

            Log.e("WebService", "erro JSONException - " + e.getMessage());

        }finally {

            if(progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss();


            }

        }


    }*/
}
