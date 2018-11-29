package tcc.etec.needful.view.view.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

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
import tcc.etec.needful.view.view.model.UsuarioVO;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000;
    ChamadosController controller;
    Context context;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = getBaseContext();
        progressBar = findViewById(R.id.progressBar);
        controller = new ChamadosController(context);
        apresentarTelaSplash();

    }

    private void apresentarTelaSplash() {

        if (sincronizarUsers() && sincronizarChamados()) {

            SharedPreferences prefs = getSharedPreferences("preferenciasUsuario", 0);
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

        } if(sincronizarUsers() == true && sincronizarChamados() == false) {

            SharedPreferences prefs = getSharedPreferences("preferenciasUsuario", 0);
            boolean jaLogou = prefs.getBoolean("estaLogado", false);
            final int idTecnico = prefs.getInt("id_tecnico", 0);

            if (jaLogou) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent telaPrincipal = new Intent(SplashActivity.this, MainActivity.class);
                        telaPrincipal.putExtra("id_tecnico", idTecnico);
                        telaPrincipal.putExtra("sincronizouChamados", false);
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
        if(sincronizarUsers() == false && sincronizarChamados() == false){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent telaPrincipal = new Intent(SplashActivity.this, UserLoginActivity.class);
                    telaPrincipal.putExtra("conectado",false);
                    startActivity(telaPrincipal);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }

    }

    private boolean sincronizarUsers() {

        UsuarioDataModel usm = new UsuarioDataModel();
        ChamadosVO chamadosVO = new ChamadosVO();
        ClienteVO clienteVO = new ClienteVO();
        clienteVO.setNome(null);
        chamadosVO.setClientVO(clienteVO);
        try {
            List<UsuarioVO> listaUSer = new UsuarioWebService().readJtablea();
            if (listaUSer != null) {
                controller.deletarTabela(usm.getTABELA());
                controller.criarTabela(usm.criarTabela());
                for (UsuarioVO u : listaUSer) {
                    controller.salvarUser(u);
                }
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean sincronizarChamados() {

        Gson gson = new Gson();
        WebServiceChamado webServiceChamado = new WebServiceChamado();
        ChamadosVO chamadosModel = new ChamadosVO();
        ClienteVO clienteModel = new ClienteVO();
        clienteModel.setNome("");
        chamadosModel.setClientVO(clienteModel);
        try {
            List<ChamadosVO> t = webServiceChamado.listagempost(chamadosModel);
            if (t != null) {
                controller.deletarTabela(ChamadosDataModel.getTABELA());
                controller.deletarTabela(ClienteDataModel.getTabela());
                controller.deletarTabela(EnderecoDataModel.getTabela());
                controller.deletarTabela(StatusDataModel.getTabela());
                controller.deletarTabela(TecnicoDataModel.getTabela());

                controller.criarTabela(StatusDataModel.criarTabela());
                controller.criarTabela(ClienteDataModel.criarTabela());
                controller.criarTabela(EnderecoDataModel.criarTabela());
                controller.criarTabela(TecnicoDataModel.criarTabela());
                controller.criarTabela(ChamadosDataModel.criarTabela());
                for (ChamadosVO c : t) {
                    controller.salvar(c);
                }
                return true;
            } else {
                return false;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
