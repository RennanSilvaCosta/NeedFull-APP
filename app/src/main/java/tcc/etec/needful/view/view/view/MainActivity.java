package tcc.etec.needful.view.view.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import tcc.etec.needful.R;
import tcc.etec.needful.view.view.controller.ChamadosController;
import tcc.etec.needful.view.view.fragments.BloqueadoeCanceladoFragment;
import tcc.etec.needful.view.view.fragments.ChamadosFragment;
import tcc.etec.needful.view.view.fragments.ModeloFragment;
import tcc.etec.needful.view.view.fragments.AgendadosFragment;
import tcc.etec.needful.view.view.fragments.StatusFragment;
import tcc.etec.needful.view.view.fragments.FinalizadosFragment;
import tcc.etec.needful.view.view.model.UsuarioModel;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    ChamadosController controller;
    Context context;

    private int idTecnico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = getBaseContext();
        controller = new ChamadosController(context);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sincronização do sistema", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        this.idTecnico = intent.getIntExtra("id_tecnico", 0 );

        UsuarioModel tecnico;
        tecnico = controller.buscarTecnicoPorId(idTecnico);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView nomeTecnico = headerView.findViewById(R.id.txt_nome_tecnico);
        TextView emailTecnico = headerView.findViewById(R.id.txt_email_tecnico);
        nomeTecnico.setText("Rennan Silva Costa");
        emailTecnico.setText("rennan@needful.com");

        fragmentManager = getSupportFragmentManager();
        Fragment modeloFragment = new ModeloFragment();
        transferirIdTecnico(modeloFragment);

        SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("estaLogado", true);
        editor.putInt("id_tecnico",this.idTecnico);
        editor.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sair) {
            SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("estaLogado", false);
            editor.commit();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {

            setTitle("NeedFul");
            Fragment modeloFragment = new ModeloFragment();
            transferirIdTecnico(modeloFragment);

        } else if (id == R.id.nav_chamados) {

            setTitle("Chamados");
            Fragment chamadosFragment = new ChamadosFragment();
            transferirIdTecnico(chamadosFragment);


        } else if (id == R.id.nav_agendados) {

            setTitle("Agendados");
            Fragment agendadosFragment = new AgendadosFragment();
            transferirIdTecnico(agendadosFragment);

        }else if (id == R.id.nav_bloqueado_cancelado) {

            setTitle("Bloqueados e Cancelados");
            Fragment bloqueadosCancelados = new BloqueadoeCanceladoFragment();
            transferirIdTecnico(bloqueadosCancelados);

        } else if (id == R.id.nav_finalizados) {

            setTitle("Chamados Finalizados");
            Fragment finalizadosFragment = new FinalizadosFragment();
            transferirIdTecnico(finalizadosFragment);

        } else if (id == R.id.nav_status) {

            setTitle("Status");
            Fragment statusFragment = new StatusFragment();
            transferirIdTecnico(statusFragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void transferirIdTecnico(Fragment tela) {
        Bundle bundle = new Bundle();
        bundle.putInt("id_tecnico", this.idTecnico);
        tela.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.content_fragment, tela).commit();
    }


    /*private class SincronizarChamados extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        HttpURLConnection connection;
        URL url = null;
        Uri.Builder builder;

        public SincronizarChamados() {

            this.builder = new Uri.Builder();
            builder.appendQueryParameter("app", "NeedFul");

        }

        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Sincronizando...");
            progressDialog.setCancelable(false);
            progressDialog.show();

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

        @Override
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

                    for (int i = 0; i < jArray.length(); i++){

                        JSONObject jsonObject = jArray.getJSONObject(i);

                        ChamadosVO chamados = new ChamadosVO();
                        ClienteVO cliente = new ClienteVO();
                        EnderecoVO endereco = new EnderecoVO();

                        //chamado
                        chamados.setId(jsonObject.getInt(ChamadosDataModel.getId()));
                        chamados.setData(Date.valueOf(jsonObject.getString(ChamadosDataModel.getDataChamado())));
                        chamados.setHoras(Time.valueOf(jsonObject.getString(ChamadosDataModel.getHoraChamado())));
                        chamados.setAgendamento_Data(Date.valueOf(jsonObject.getString(ChamadosDataModel.getAgendamentoHoraChamado())));
                        chamados.setDescricao(jsonObject.getString(ChamadosDataModel.getObservacaoChamado()));
                        chamados.setIdTecnico(jsonObject.getInt(ChamadosDataModel.getIdTecnico()));
                        chamados.setTipoChamado(jsonObject.getInt(ChamadosDataModel.getIdTipoChamado()));
                        chamados.setIdStatusChamado(jsonObject.getInt(ChamadosDataModel.getIdStatusChamado()));

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

        }

    }*/

}
