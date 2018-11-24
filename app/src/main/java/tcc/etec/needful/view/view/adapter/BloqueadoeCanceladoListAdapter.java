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

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import tcc.etec.needful.R;
import tcc.etec.needful.view.view.controller.ChamadosController;
import tcc.etec.needful.view.view.model.ChamadosVO;
import tcc.etec.needful.view.view.util.AlterarAsynTask;

public class BloqueadoeCanceladoListAdapter extends ArrayAdapter<ChamadosVO> implements View.OnClickListener {

    Context context;
    AlertDialog.Builder build;
    AlertDialog alert;
    ChamadosController controller;
    ArrayList<ChamadosVO> dados;
    ArrayList<ChamadosVO> lista;

    private static class ViewHolder {
        TextView txt_Tipo_Chamado;
        TextView txt_Nome_Cliente;
        TextView txt_Endereco_cliente;
        TextView data_bloq_canc;
        TextView hora_bloq_canc;
        TextView status;
        ImageView img_logo;
        ImageView img_finalizar;
    }

    public BloqueadoeCanceladoListAdapter(ArrayList<ChamadosVO> dataSet, Context context) {
        super(context, R.layout.list_view_bloqueado_cancelado_fragment, dataSet);
        this.dados = dataSet;
        this.context = context;
    }

    @Override
    public void onClick(View view) {

        final int posicao = (Integer) view.getTag();
        Object object = getItem(posicao);
        final ChamadosVO chamadosVO = (ChamadosVO) object;

        switch (view.getId()) {

            case R.id.img_logo:
                if (chamadosVO.getIdStatusChamado() == 5 && chamadosVO.getTipoChamado() == 2) {
                    Snackbar.make(view, "Manutenção bloqueada:" + "\n" + chamadosVO.getClientVO().getEnderecoVO().getRua(),
                            Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                } else if(chamadosVO.getIdStatusChamado() == 5 && chamadosVO.getTipoChamado() == 1) {
                    Snackbar.make(view, "Instalação bloqueada:" + "\n" + chamadosVO.getClientVO().getEnderecoVO().getRua(),
                            Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                } else if (chamadosVO.getIdStatusChamado() == 6 && chamadosVO.getTipoChamado() == 2){
                    Snackbar.make(view, "Manutenção cancelada:" + "\n" + chamadosVO.getClientVO().getEnderecoVO().getRua(),
                            Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                } else if (chamadosVO.getIdStatusChamado() == 6 && chamadosVO.getTipoChamado() == 1){
                    Snackbar.make(view, "Instalação cancelada:" + "\n" + chamadosVO.getClientVO().getEnderecoVO().getRua(),
                            Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
                break;

            case R.id.img_finalizar_chamado:

                if (chamadosVO.getIdStatusChamado() == 5) {
                    build = new AlertDialog.Builder(getContext());
                    build.setTitle("ALERTA");
                    build.setMessage("O chamado está bloqueado, você pode desbloquea-lo ou cancela-lo.");
                    build.setCancelable(true);
                    build.setIcon(R.mipmap.ic_launcher);

                    build.setPositiveButton("DESBLOQUEA-LO", new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            lista = new ArrayList<>();
                            chamadosVO.setIdStatusChamado(1);
                            controller = new ChamadosController(getContext());
                            controller.alterar(chamadosVO);

                            AlterarAsynTask alterar = new AlterarAsynTask(chamadosVO, context);
                            alterar.execute();
                            lista = controller.listarBloqueadoCancelado(chamadosVO.getIdTecnico());
                            atualizarLista(lista);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Chamado desbloqueado com sucesso!", Toast.LENGTH_SHORT).show();

                        }

                    });

                    build.setNeutralButton("SAIR", new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }

                    });

                    build.setNegativeButton("CANCELA-LO", new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            chamadosVO.setIdStatusChamado(6);
                            ChamadosController controller = new ChamadosController(getContext());
                            controller.alterar(chamadosVO);

                            AlterarAsynTask alterar = new AlterarAsynTask(chamadosVO, context);
                            alterar.execute();
                            lista = controller.listarBloqueadoCancelado(chamadosVO.getIdTecnico());
                            atualizarLista(lista);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Chamado cancelado com sucesso!", Toast.LENGTH_SHORT).show();


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
            dataSet = layoutChamadoslList.inflate(R.layout.list_view_bloqueado_cancelado_fragment,
                    parent,
                    false);

            linha.txt_Tipo_Chamado = dataSet.findViewById(R.id.txt_tipo_chamado);
            linha.txt_Nome_Cliente = dataSet.findViewById(R.id.txt_nome_cliente);
            linha.txt_Endereco_cliente = dataSet.findViewById(R.id.txt_end_cliente);
            linha.status = dataSet.findViewById(R.id.txt_status);
            linha.data_bloq_canc = dataSet.findViewById(R.id.data_finalizacao);
            linha.hora_bloq_canc = dataSet.findViewById(R.id.hora_bloq_canc);
            linha.img_logo = dataSet.findViewById(R.id.img_logo);
            linha.img_finalizar = dataSet.findViewById(R.id.img_finalizar_chamado);
            dataSet.setTag(linha);
        } else {
            linha = (ViewHolder) dataSet.getTag();
        }

        linha.txt_Tipo_Chamado.setText(tipoChamado(chamadosVO.getTipoChamado()));
        linha.txt_Nome_Cliente.setText(chamadosVO.getClientVO().getNome());
        linha.txt_Endereco_cliente.setText(chamadosVO.getClientVO().getEnderecoVO().getRua());
        linha.data_bloq_canc.setText(String.valueOf(chamadosVO.getFinalizacao_Data())); //POR ENQUANTO ESTOU USANDO A DATA DE FINALIZAÇÃO!
        linha.hora_bloq_canc.setText(String.valueOf(chamadosVO.getFinalizacao_horas())); //POR ENQUANTO ESTOU USANDO A HORA DE FINALIZAÇÃO!

        if (chamadosVO.getIdStatusChamado() == 5) {
            linha.status.setText("Bloqueado");
            linha.status.setTextColor(Color.parseColor("#FF0200"));
            linha.img_finalizar.setVisibility(View.VISIBLE);
            linha.img_logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_img_logo_chamado_bloqueado));
            linha.img_finalizar.setImageDrawable(ContextCompat.getDrawable(context,  R.drawable.ic_chamado_blqueado2));
        }else if (chamadosVO.getIdStatusChamado() == 6){
            linha.status.setText("Cancelado");
            linha.img_logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_chamado_cancelado));
            linha.img_finalizar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_chamado_cancelados));
            linha.status.setTextColor(Color.parseColor("#CCCCCC"));
            linha.img_finalizar.setVisibility(View.VISIBLE);
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
