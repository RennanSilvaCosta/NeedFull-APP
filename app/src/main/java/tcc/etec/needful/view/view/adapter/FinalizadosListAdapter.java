package tcc.etec.needful.view.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import tcc.etec.needful.R;
import tcc.etec.needful.view.view.model.ChamadosVO;
import tcc.etec.needful.view.view.util.UtilChamados;

public class FinalizadosListAdapter extends ArrayAdapter<ChamadosVO> implements View.OnClickListener {

    Context context;
    ArrayList<ChamadosVO> dados;
    UtilChamados util = new UtilChamados();
    DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

    private static class ViewHolder {

        TextView txt_tipo_chamado;
        TextView txt_nome_cliente;
        TextView txt_endereco_cliente;
        TextView data_finalizado;
        TextView hora_finalizado;
        TextView status;
        ImageView img_tipo_chamado;
        ImageView img_finalizar_chamado;
    }


    public FinalizadosListAdapter(ArrayList<ChamadosVO> dataSet, Context context) {
        super(context, R.layout.list_view_finalizados_fragment, dataSet);

        this.dados = dataSet;
        this.context = context;

    }

    @Override
    public void onClick(View view) {

        int posicao = (Integer) view.getTag();
        Object object = getItem(posicao);
        final ChamadosVO chamadosVO = (ChamadosVO) object;

        switch (view.getId()) {

            case R.id.img_logo:
                if (chamadosVO.getIdStatusChamado() == 4) {

                    if (chamadosVO.getTipoChamado() == 2) {

                        Snackbar.make(view, "Manutenção finalizada: " + formatDate.format(chamadosVO.getFinalizacao_Data()) + " Ás " + sdfHora.format(chamadosVO.getFinalizacao_horas()),
                                Snackbar.LENGTH_LONG)
                                .setAction("No action", null).show();

                    } else {

                        Snackbar.make(view, "Instalação finalizada: " + formatDate.format(chamadosVO.getFinalizacao_Data()) + " Ás " + sdfHora.format(chamadosVO.getFinalizacao_horas()),
                                Snackbar.LENGTH_LONG)
                                .setAction("No action", null).show();
                    }
                }
                break;

            case R.id.img_finalizar_chamado:

                if (chamadosVO.getIdStatusChamado() == 4) {

                    Snackbar.make(view, "Chamado Finalizado",
                            Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
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
        FinalizadosListAdapter.ViewHolder linha;
        if (dataSet == null) {

            linha = new FinalizadosListAdapter.ViewHolder();

            LayoutInflater layoutTodosChamadoslList = LayoutInflater.from(getContext());

            dataSet = layoutTodosChamadoslList.inflate(R.layout.list_view_finalizados_fragment,
                    parent,
                    false);

            linha.txt_tipo_chamado = dataSet.findViewById(R.id.txt_tipo_chamado);
            linha.txt_nome_cliente = dataSet.findViewById(R.id.txt_nome_cliente);
            linha.txt_endereco_cliente = dataSet.findViewById(R.id.txt_end_cliente);
            linha.status = dataSet.findViewById(R.id.txt_status);
            linha.data_finalizado = dataSet.findViewById(R.id.data_finalizacao);
            linha.hora_finalizado = dataSet.findViewById(R.id.hora_finalizado);
            linha.img_tipo_chamado = dataSet.findViewById(R.id.img_logo);
            linha.img_finalizar_chamado = dataSet.findViewById(R.id.img_finalizar_chamado);

            dataSet.setTag(linha);

        } else {
            linha = (FinalizadosListAdapter.ViewHolder) dataSet.getTag();
        }

        linha.txt_tipo_chamado.setText(util.tipoChamado(chamadosVO.getTipoChamado()));

        if (chamadosVO.getIdStatusChamado() == 4) {
            linha.status.setText("Finalizado");
            linha.status.setTextColor(Color.parseColor("#12BC00"));
            linha.img_finalizar_chamado.setVisibility(View.VISIBLE);
            linha.img_tipo_chamado.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_img_logo_chamado_finalizado));
            linha.img_finalizar_chamado.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_chamado_finalizado));
        }

        String dataFina = formatDate.format(chamadosVO.getFinalizacao_Data());
        String horaFin = sdfHora.format(chamadosVO.getFinalizacao_horas());

        linha.txt_tipo_chamado.setText(util.tipoChamado(chamadosVO.getTipoChamado()));
        linha.txt_nome_cliente.setText(chamadosVO.getClientVO().getNome());
        linha.txt_endereco_cliente.setText(chamadosVO.getClientVO().getEnderecoVO().getRua());
        linha.data_finalizado.setText(String.valueOf(dataFina));
        linha.hora_finalizado.setText(String.valueOf(horaFin));

        linha.img_tipo_chamado.setOnClickListener(this);
        linha.img_tipo_chamado.setTag(position);
        linha.img_finalizar_chamado.setOnClickListener(this);
        linha.img_finalizar_chamado.setTag(position);

        return dataSet;
    }

}
