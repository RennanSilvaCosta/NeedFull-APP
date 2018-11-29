package tcc.etec.needful.view.view.view;

import androidx.appcompat.app.AppCompatActivity;
import tcc.etec.needful.R;
import tcc.etec.needful.view.view.controller.ChamadosController;
import tcc.etec.needful.view.view.model.ChamadosVO;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class BloqueadoOuCanceladoActivity extends AppCompatActivity {

    private TextView textViewData, textViewHora, nomeCliente, observacoes, equipamento, referenciaCliente, cepCliente, complementoCliente,
            bairroCliente, enderecoCliente, telefoneCliente, celularCliente, dataBloque, horaBloque, justifcativa;

    private ImageView imgTelefone, imgCelular;
    private int idChamado;
    ChamadosController controller;
    Context context;
    ChamadosVO chamado;
    private String titulo;
    DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloqueado_ou_cancelado);

        context = getBaseContext();
        controller = new ChamadosController(context);
        chamado = new ChamadosVO();

        Intent intent = getIntent();
        titulo = intent.getStringExtra("tipo_status");
        idChamado = intent.getIntExtra("id_chamado", 0);
        getSupportActionBar().setTitle(titulo);
        inicializarComponentes();
        if (titulo.trim().equals("Cancelado")) {
            textViewData.setText("Data Cancelamento:");
            textViewHora.setText("Hora Cancelamento:");
        }
        chamado = controller.buscarChamadoPorId(idChamado);
        popularComponentes();

        imgTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerLigacaoTelefone();
            }

        });

        imgCelular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerLigacaoCelular();
            }
        });

    }

    private void popularComponentes() {

        String dataFina = formatDate.format(chamado.getFinalizacao_Data());
        String horaFina = formatDate.format(chamado.getFinalizacao_horas());

        nomeCliente.setText(chamado.getClientVO().getNome());
        observacoes.setText(chamado.getDescricao());
        equipamento.setText(chamado.getClientVO().getRoteador());
        cepCliente.setText(chamado.getClientVO().getEnderecoVO().getCep());
        bairroCliente.setText(chamado.getClientVO().getEnderecoVO().getBairro());
        enderecoCliente.setText(chamado.getClientVO().getEnderecoVO().getRua() + " " +
                chamado.getClientVO().getEnderecoVO().getBairro() + ", " + chamado.getClientVO().getEnderecoVO().getNumero());
        complementoCliente.setText(chamado.getClientVO().getEnderecoVO().getComplemento());
        referenciaCliente.setText(chamado.getClientVO().getEnderecoVO().getReferencia());
        telefoneCliente.setText(chamado.getClientVO().getTelefone());
        celularCliente.setText(chamado.getClientVO().getCelular());
        dataBloque.setText(String.valueOf(dataFina));
        horaBloque.setText(String.valueOf(horaFina));
        justifcativa.setText(chamado.getJustificativa());

    }

    private void inicializarComponentes() {

        imgCelular = findViewById(R.id.imgLigarCelular);
        imgTelefone = findViewById(R.id.imgLigarTelefone);
        textViewData = findViewById(R.id.textViewData);
        textViewHora = findViewById(R.id.textViewHora);
        nomeCliente = findViewById(R.id.txtNome);
        observacoes = findViewById(R.id.txtObs);
        equipamento = findViewById(R.id.txtEquipamento);
        cepCliente = findViewById(R.id.txtCep);
        bairroCliente = findViewById(R.id.txtBairro);
        enderecoCliente = findViewById(R.id.txtEndereco);
        complementoCliente = findViewById(R.id.txtComplemento);
        referenciaCliente = findViewById(R.id.txtReferencia);
        telefoneCliente = findViewById(R.id.txtTelefone);
        celularCliente = findViewById(R.id.txtCelular);
        dataBloque = findViewById(R.id.txtdataBloqueio);
        horaBloque = findViewById(R.id.txtHoraBloqueio);
        justifcativa = findViewById(R.id.txtJustificativa);

    }

    private void fazerLigacaoTelefone() {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", chamado.getClientVO().getTelefone(), null));
        startActivity(i);
    }

    private void fazerLigacaoCelular() {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", chamado.getClientVO().getCelular(), null));
        startActivity(i);
    }
}
