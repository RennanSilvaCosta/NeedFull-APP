package tcc.etec.needful.view.view.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import tcc.etec.needful.R;
import tcc.etec.needful.view.view.api.WebServiceChamado;
import tcc.etec.needful.view.view.controller.ChamadosController;
import tcc.etec.needful.view.view.model.ChamadosVO;
import tcc.etec.needful.view.view.util.AlterarAsynTask;

public class ChamadosListAdapter extends ArrayAdapter<ChamadosVO> implements View.OnClickListener {

    Context context;
    AlertDialog.Builder build;
    AlertDialog alert;
    ChamadosController controller;
    ArrayList<ChamadosVO> lista;
    ArrayList<ChamadosVO> dados;
    static SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
    static SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

    private static class ViewHolder {
        TextView txt_Tipo_Chamado;
        TextView txt_Nome_Cliente;
        TextView txt_Endereco_cliente;
        TextView data_agendado;
        TextView hora_agendado;
        TextView status;
        ImageView img_logo;
        ImageView img_finalizar;
    }

    public ChamadosListAdapter(ArrayList<ChamadosVO> dataSet, Context context) {
        super(context, R.layout.list_view_chamado_aberto, dataSet);
        this.dados = dataSet;
        this.context = context;
    }

    @Override
    public void onClick(final View view) {

        final int posicao = (Integer) view.getTag();
        Object object = getItem(posicao);
        final ChamadosVO chamadosVO = (ChamadosVO) object;

        final int tested = posicao;
        switch (view.getId()) {

            case R.id.img_logo:
                if (chamadosVO.getTipoChamado() == 2) {

                    Snackbar.make(view, "Nova Manutenção:" + "\n" + chamadosVO.getClientVO().getEnderecoVO().getRua(),
                            Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                } else {

                    Snackbar.make(view, "Nova Instalação:" + "\n" + chamadosVO.getClientVO().getEnderecoVO().getRua(),
                            Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
                break;

            case R.id.img_finalizar_chamado:

                if (chamadosVO.getIdStatusChamado() == 2) {
                    build = new AlertDialog.Builder(getContext());
                    build.setTitle("ALERTA");
                    build.setMessage("Deseja FINALIZAR este chamado?");
                    build.setCancelable(true);
                    build.setIcon(R.mipmap.ic_launcher);

                    build.setPositiveButton("SIM", new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            lista = new ArrayList<>();
                            chamadosVO.setIdStatusChamado(4);
                            chamadosVO.setFinalizacao_Data(new Date());
                            controller = new ChamadosController(getContext());
                            controller.alterar(chamadosVO);

                            lista = controller.todosChamados(chamadosVO.getIdTecnico());
                            atualizarLista(lista);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Chamado finalizado com sucesso!", Toast.LENGTH_SHORT).show();

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
                break;
        }
    }

    @NonNull
    @Override
    public View getView(int position,
                        View dataSet,
                        @NonNull ViewGroup parent) {

        ChamadosVO chamadosVO = getItem(position);
        ViewHolder linha;
        if (dataSet == null) {

            linha = new ViewHolder();
            LayoutInflater layoutChamadoslList = LayoutInflater.from(getContext());
            dataSet = layoutChamadoslList.inflate(R.layout.list_view_chamado_aberto,
                    parent,
                    false);

            linha.txt_Tipo_Chamado = dataSet.findViewById(R.id.txt_tipo_chamado);
            linha.txt_Nome_Cliente = dataSet.findViewById(R.id.txt_nome_cliente);
            linha.txt_Endereco_cliente = dataSet.findViewById(R.id.txt_end_cliente);
            linha.status = dataSet.findViewById(R.id.txt_status);
            linha.data_agendado = dataSet.findViewById(R.id.data_finalizacao);
            linha.hora_agendado = dataSet.findViewById(R.id.hora_agendado);
            linha.img_logo = dataSet.findViewById(R.id.img_logo);
            linha.img_finalizar = dataSet.findViewById(R.id.img_finalizar_chamado);
            dataSet.setTag(linha);
        } else {
            linha = (ViewHolder) dataSet.getTag();
        }

        String auxDateAgend = sdfData.format(chamadosVO.getAgendamento_Data());
        String auxHoraAgend = sdfHora.format(chamadosVO.getAgendamento_horas());

        linha.txt_Tipo_Chamado.setText(tipoChamado(chamadosVO.getTipoChamado()));
        linha.txt_Nome_Cliente.setText(chamadosVO.getClientVO().getNome());
        linha.txt_Endereco_cliente.setText(chamadosVO.getClientVO().getEnderecoVO().getRua());
        linha.data_agendado.setText(String.valueOf(auxDateAgend));
        linha.hora_agendado.setText(String.valueOf(auxHoraAgend));
        Date dataHoje = new Date();

        if (chamadosVO.getAgendamento_Data().before(dataHoje) || chamadosVO.getIdStatusChamado() == 1) {
            linha.status.setText("Atrasado");
            linha.status.setTextColor(Color.parseColor("#FFFF0000"));
            linha.img_finalizar.setVisibility(View.INVISIBLE);
        }
        if (chamadosVO.getIdStatusChamado() == 1 || chamadosVO.getIdStatusChamado() == 3) {
            linha.img_logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_img_logo_chamado_novo));
            linha.img_finalizar.setVisibility(View.INVISIBLE);
        }
        if (chamadosVO.getIdStatusChamado() == 2) {
            linha.img_finalizar.setVisibility(View.VISIBLE);
            linha.status.setText("Andamento");
            linha.status.setTextColor(Color.parseColor("#FBC433"));
            linha.img_logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_img_logo_chamado_andamento));
        }

        linha.img_logo.setOnClickListener(this);
        linha.img_logo.setTag(position);
        linha.img_finalizar.setOnClickListener(this);
        linha.img_finalizar.setTag(position);

        return dataSet;
    }

    private void atualizarLista(ArrayList<ChamadosVO> novosDados) {
        this.dados.clear();
        this.dados.addAll(novosDados);
        notifyDataSetChanged();
    }

    private String tipoChamado(int tipoChamado) {

        if (tipoChamado == 1) {
            return "Instalação";
        } else if (tipoChamado == 2) {
            return "Manutenção";
        }
        return null;
    }


}
