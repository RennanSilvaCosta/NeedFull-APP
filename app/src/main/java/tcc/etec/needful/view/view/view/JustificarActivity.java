package tcc.etec.needful.view.view.view;

import androidx.appcompat.app.AppCompatActivity;
import tcc.etec.needful.R;
import tcc.etec.needful.view.view.controller.ChamadosController;
import tcc.etec.needful.view.view.model.ChamadosVO;
import tcc.etec.needful.view.view.util.AlterarAsynTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class JustificarActivity extends AppCompatActivity {

    Context context;
    ChamadosController controller;
    ChamadosVO chamado;

    private Button btnConfirmar;
    private TextView txtNome;
    private TextView txtEndereco;
    private TextView txtBairro;
    private TextView txtData;
    private TextView txtHora;
    private TextView obs;
    private EditText txtComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justificar);

        context = getBaseContext();
        controller = new ChamadosController(context);
        chamado = new ChamadosVO();
        context = getBaseContext();

        Intent intent = getIntent();
        int id = intent.getIntExtra("id_chamado", 0);
        final String titulo = intent.getStringExtra("tipo_status");
        getSupportActionBar().setTitle(titulo);
        chamado = controller.buscarPorid(id);
        inicializarComponentes();
        adaptarTexto(titulo);
        popularComponentes();

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = txtComentario.getText().toString();
                if(texto == null || texto.equals("")){
                    txtComentario.setError("Campo obrigatorio");
                    txtComentario.setFocusable(true);
                }else if (titulo.trim().equals("Bloqueio")){
                    chamado.setJustificativa(txtComentario.getText().toString());
                    chamado.setIdStatusChamado(5);
                    chamado.setFinalizacao_Data(new Date());
                    controller.alterar(chamado);
                    AlterarAsynTask alterar = new AlterarAsynTask(chamado, context);
                    alterar.execute();
                    Toast.makeText(context, "Chamado bloqueado e justificado com sucesso.", Toast.LENGTH_LONG).show();
                    finish();
                }else if (titulo.trim().equals("Cancelamento")){

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(JustificarActivity.this);
                    alertDialog.setTitle("Cancelar chamado");
                    alertDialog.setMessage("Você tem certeza que deseja cancelar este chamado? Esta ação não poderá ser desfeita.");
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chamado.setJustificativa(txtComentario.getText().toString());
                            chamado.setIdStatusChamado(6);
                            chamado.setFinalizacao_Data(new Date());
                            controller.alterar(chamado);
                            AlterarAsynTask alterar = new AlterarAsynTask(chamado, context);
                            alterar.execute();
                            Toast.makeText(context, "Chamado cancelado e justificado com sucesso.", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                    alertDialog.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "Ação cancelada.", Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog alert = alertDialog.create();
                    alert.show();
                }
            }
        });

    }

    private void adaptarTexto(String status){
        if(status.trim().equals("Cancelamento")){
            obs.setText("O chamado será cancelado, esta ação não podera ser desfeita.");
            txtComentario.setHint("DESCREVA O MOTIVO DO CANCELAMENTO");
        }else if(status.trim().equals("Bloqueio")){
            obs.setText("O chamado será bloqueado, você pode desbloquea-lo a qualquer momento na aba de 'Cancelados e Bloqueados'. Ao desbloquear o chamado ele voltará ao status de aberto.");
            txtComentario.setHint("DESCREVA O MOTIVO DO BLOQUEIO");
        }
    }

    private void popularComponentes() {
        txtNome.setText(chamado.getClientVO().getNome());
        txtEndereco.setText(chamado.getClientVO().getEnderecoVO().getRua() + ", " + chamado.getClientVO().getEnderecoVO().getNumero());
        txtBairro.setText(chamado.getClientVO().getEnderecoVO().getBairro());
        txtData.setText(String.valueOf(chamado.getData()));
        txtHora.setText(String.valueOf(chamado.getHoras()));
    }

    private int recuperaIdChamado() {
        Intent intent = getIntent();
        return intent.getIntExtra("id_chamado", 0);
    }

    private void inicializarComponentes() {

        obs = findViewById(R.id.textView4);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        txtNome = findViewById(R.id.txtNome);
        txtEndereco = findViewById(R.id.txtEndereco);
        txtBairro = findViewById(R.id.txtBairro);
        txtData = findViewById(R.id.txtData);
        txtHora = findViewById(R.id.txtHora);
        txtComentario = findViewById(R.id.txtComentario);
    }
}