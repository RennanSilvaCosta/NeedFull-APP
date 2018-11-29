package tcc.etec.needful.view.view.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import tcc.etec.needful.view.view.model.ChamadosVO;
import tcc.etec.needful.view.view.model.ClienteVO;
import tcc.etec.needful.view.view.model.UsuarioVO;
import tcc.etec.needful.view.view.util.CriptografaSenha;

public class UserLoginActivity extends AppCompatActivity {

    ChamadosController controller;
    UsuarioWebService userWS;
    UsuarioVO user;
    Context context;
    EditText email;
    EditText senha;
    Button botao;
    SplashActivity s;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        context = getBaseContext();
        s = new SplashActivity();
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        Intent i = getIntent();
        if(i.getBooleanExtra("conectado", true) == false){

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserLoginActivity.this);
            alertDialog.setTitle("Algo deu errado");
            alertDialog.setMessage("Não foi possivel estabelecer uma conexão com o servidor. Verifique sua conexão com a internet e tente novamente mais tarde.");
            alertDialog.setCancelable(false);

            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();

        }
        controller = new ChamadosController(context);
        userWS = new UsuarioWebService();
        user = new UsuarioVO();

        email = findViewById(R.id.editTextEmail);
        senha = findViewById(R.id.editTextSenha);
        botao = findViewById(R.id.btnEntrar);
        email.requestFocus();

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    user.setEmail(email.getText().toString());
                    user.setSenha(CriptografaSenha.criptografaSenha(senha.getText().toString()));
                    boolean retorno = userWS.checkLogin(user);
                    progressBar.setVisibility(View.VISIBLE);
                    if (retorno) {
                        user = controller.buscarUser(email.getText().toString());
                        Intent intent = new Intent(context, MainActivity.class);
                        if(sincronizarChamados()){
                            intent.putExtra("sincronizouChamados", true);
                        }else{
                            intent.putExtra("sincronizouChamados", false);
                        }
                        intent.putExtra("tecnico", user);
                        startActivity(intent);
                        finish();
                    } else {
                        senha.setFocusable(true);
                        senha.setError("Senha inválida");
                        progressBar.setVisibility(View.GONE);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
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
