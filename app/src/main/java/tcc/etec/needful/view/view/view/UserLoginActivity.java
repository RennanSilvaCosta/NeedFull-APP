package tcc.etec.needful.view.view.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import tcc.etec.needful.R;
import tcc.etec.needful.view.view.controller.ChamadosController;
import tcc.etec.needful.view.view.model.UsuarioModel;

public class UserLoginActivity extends AppCompatActivity {

    ChamadosController controller;
    Context context;
    EditText email;
    EditText senha;
    Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        context = getBaseContext();
        controller = new ChamadosController(context);

        email = findViewById(R.id.editTextEmail);
        senha = findViewById(R.id.editTextSenha);
        botao = findViewById(R.id.btnEntrar);

        email.requestFocus();

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UsuarioModel user;
                boolean validacao;
                String loginDitado = email.getText().toString();
                String senhaDigitado = senha.getText().toString();
                validacao = validarLogin(loginDitado, senhaDigitado);

                if (validacao == true) {
                    user = controller.buscarUser(loginDitado);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("nome_usuario", user.getNome());
                    intent.putExtra("id_tecnico", user.getId());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean validarLogin(String loginDigitado, String senhaDigitado) {

        if (loginDigitado.trim().equals("")) {
            email.requestFocus();
            email.setError("Campo Obrgatorio");
            return false;

        } else {

            UsuarioModel user;
            user = controller.buscarUser(loginDigitado);
            String loginPesquisado = user.getLogin();

            if (loginPesquisado != null) {
                if (user.getSenha().equals(senhaDigitado)) {
                    return true;
                } else {
                    senha.requestFocus();
                    senha.setError("Senha inválida.");
                    return false;
                }
            } else {
                email.requestFocus();
                email.setError("Email inválido.");
                return false;
            }
        }
    }
}
