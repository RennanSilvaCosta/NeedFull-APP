package tcc.etec.needful.view.view.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import tcc.etec.needful.R;
import tcc.etec.needful.view.view.api.WebServiceChamado;
import tcc.etec.needful.view.view.controller.ChamadosController;
import tcc.etec.needful.view.view.helper.Permissoes;
import tcc.etec.needful.view.view.model.ChamadosVO;
import tcc.etec.needful.view.view.util.UtilChamados;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class DetalheChamadoActivity extends AppCompatActivity {

    AlertDialog.Builder build;
    AlertDialog alert;
    ChamadosController controller;
    ChamadosVO chamados;
    Context context;
    private int idChamado;
    DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    UtilChamados util = new UtilChamados();

    TextView nomeCliente, dataAgendado, horaAgendado, cepCliente, complementoCliente, bairroCliente, enderecoCliente, telefoneCliente, celularCliente, roteador, referenciaCliente, observacoes, loginCliente, senhaCliente;
    ImageView imgConfirmar, imgLocalizar, ligarTelefone, ligarCelular;

    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_chamado);

        context = getBaseContext();
        Intent intent = getIntent();
        this.idChamado = intent.getIntExtra("id_chamado", 0);

        Permissoes.validarPermissoes(permissoes, this, 1);

        chamados = new ChamadosVO();
        controller = new ChamadosController(context);
        chamados = controller.buscarPorid(this.idChamado);

        inicializarComponentes();
        popularComponentes();
        getSupportActionBar().setTitle(util.tipoChamado(chamados.getTipoChamado()));
        exibirTelefone(chamados.getClientVO().getTelefone(), telefoneCliente, ligarTelefone);
        exibirCelular(chamados.getClientVO().getCelular(), celularCliente, ligarCelular);

        ligarTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerLigacaoTelefone();
            }
        });

        ligarCelular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerLigacaoCelular();
            }
        });

        ativarDesativarBotaoConfirmar();

        imgConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                build = new AlertDialog.Builder(DetalheChamadoActivity.this);
                build.setTitle("ALERTA");
                build.setMessage("Deseja CONFIRMAR este chamado?");
                build.setCancelable(true);
                build.setIcon(R.mipmap.ic_launcher);

                build.setPositiveButton("SIM", new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WebServiceChamado web = new WebServiceChamado();

                        chamados.setIdStatusChamado(2);
                        chamados.setConfirmacao_Data(new Date());
                        ChamadosController controller = new ChamadosController(context);
                        controller.alterar(chamados);

                        ChamadosVO chamado = new ChamadosVO();
                        chamado.setIdStatusChamado(2);
                        chamado.setID(chamados.getID());
                        try {
                            boolean teste = web.atualizarInstalacao(chamado);
                            if(teste){
                                Toast.makeText(context, "teste", Toast.LENGTH_SHORT).show();
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        ativarDesativarBotaoConfirmar();
                        Toast.makeText(context, "Chamado confirmado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                });
                build.setNegativeButton("CANCELAR", new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alert = build.create();
                alert.show();

            }
        });


        imgLocalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("google.navigation:q=" + chamados.getClientVO().getEnderecoVO().getRua() + " " +
                        chamados.getClientVO().getEnderecoVO().getBairro() + ", " + chamados.getClientVO().getEnderecoVO().getNumero() + "&mode=d");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

    }

    private void popularComponentes() {

        String dataAgen = formatData.format(chamados.getAgendamento_Data());

        nomeCliente.setText(chamados.getClientVO().getNome());
        dataAgendado.setText(String.valueOf(dataAgen));
        horaAgendado.setText(String.valueOf(chamados.getAgendamento_horas()));
        loginCliente.setText(chamados.getClientVO().getLogin());
        senhaCliente.setText(chamados.getClientVO().getSenha());
        roteador.setText(chamados.getClientVO().getRoteador());
        observacoes.setText(chamados.getDescricao());
        cepCliente.setText(chamados.getClientVO().getEnderecoVO().getCep());
        bairroCliente.setText(chamados.getClientVO().getEnderecoVO().getBairro());
        enderecoCliente.setText(chamados.getClientVO().getEnderecoVO().getRua() + ", " + chamados.getClientVO().getEnderecoVO().getNumero());
        complementoCliente.setText(chamados.getClientVO().getEnderecoVO().getComplemento());
        referenciaCliente.setText(chamados.getClientVO().getEnderecoVO().getReferencia());
        telefoneCliente.setText(chamados.getClientVO().getTelefone());
        celularCliente.setText(chamados.getClientVO().getCelular());
    }

    private void inicializarComponentes() {
        imgConfirmar = findViewById(R.id.imgConfirmar);
        imgLocalizar = findViewById(R.id.imgLocalizar);
        ligarTelefone = findViewById(R.id.imgLigarTelefone);
        ligarCelular = findViewById(R.id.imgLigarCelular);
        nomeCliente = findViewById(R.id.txtNome);
        dataAgendado = findViewById(R.id.txtDataAgendado);
        horaAgendado = findViewById(R.id.txtHoraAgendada);
        loginCliente = findViewById(R.id.txtLogin);
        senhaCliente = findViewById(R.id.txtSenha);
        cepCliente = findViewById(R.id.txtCep);
        bairroCliente = findViewById(R.id.txtBairro);
        enderecoCliente = findViewById(R.id.txtEndereco);
        complementoCliente = findViewById(R.id.txtComplemento);
        referenciaCliente = findViewById(R.id.txtReferencia);
        roteador = findViewById(R.id.txtRoteador);
        telefoneCliente = findViewById(R.id.txtTelefone);
        celularCliente = findViewById(R.id.txtCelular);
        observacoes = findViewById(R.id.txtObservacoes);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int permissoesResultado : grantResults) {
            if (permissoesResultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar a rota é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void exibirCelular(String celular, TextView celularCliente, ImageView ligarCelular) {
        if (celular == null || celular.equals("null")) {
            celularCliente.setText("");
            ligarCelular.setVisibility(View.INVISIBLE);
        } else {
            celularCliente.setText(celular);
        }
    }

    private void exibirTelefone(String telefone, TextView telefoneCliente, ImageView ligarTelefone) {
        if (telefone == null || telefone.equals("null")) {
            telefoneCliente.setText("");
            ligarTelefone.setVisibility(View.INVISIBLE);
        } else {
            telefoneCliente.setText(telefone);
        }
    }

    private void fazerLigacaoTelefone() {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", chamados.getClientVO().getTelefone(), null));
        startActivity(i);
    }

    private void fazerLigacaoCelular() {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", chamados.getClientVO().getCelular(), null));
        startActivity(i);
    }

    private void ativarDesativarBotaoConfirmar() {
        if (chamados.getIdStatusChamado() == 2) {
            imgConfirmar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_confirmar_desabilitado));
            imgConfirmar.setEnabled(false);
        } else if (chamados.getIdStatusChamado() == 6) {
            imgConfirmar.setEnabled(false);
            imgLocalizar.setEnabled(false);
            imgLocalizar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_location_desabilitado));
            imgConfirmar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_confirmar_desabilitado));
        } else if (chamados.getIdStatusChamado() == 5) {
            imgConfirmar.setEnabled(false);
            imgLocalizar.setEnabled(false);
            imgLocalizar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_location_desabilitado));
            imgConfirmar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_confirmar_desabilitado));
        } else if (chamados.getIdStatusChamado() == 4) {
            imgConfirmar.setEnabled(false);
            imgLocalizar.setEnabled(false);
            imgLocalizar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_location_desabilitado));
            imgConfirmar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_confirmar_desabilitado));
        } else {
            imgConfirmar.setEnabled(true);
            imgLocalizar.setEnabled(true);
        }
    }
}
