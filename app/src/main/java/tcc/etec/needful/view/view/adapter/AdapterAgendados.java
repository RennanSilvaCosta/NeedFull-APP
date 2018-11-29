package tcc.etec.needful.view.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;
import tcc.etec.needful.R;
import tcc.etec.needful.view.view.datamodel.ChamadosDataModel;
import tcc.etec.needful.view.view.model.ChamadosVO;
import tcc.etec.needful.view.view.util.UtilChamados;


public class AdapterAgendados extends RecyclerView.Adapter<AdapterAgendados.MyViewHolder> {

    List<ChamadosVO> chamados;
    Context context;
    DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
    UtilChamados util = new UtilChamados();

    public AdapterAgendados(List<ChamadosVO> chamados, Context context) {
        this.chamados = chamados;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_agendados, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChamadosVO chamadosVO = chamados.get(position);

        String dataAgend = formatDate.format(chamadosVO.getAgendamento_Data());
        String horaAgend = sdfHora.format(chamadosVO.getAgendamento_horas());

        holder.tipoChamado.setText(util.tipoChamado(chamadosVO.getTipoChamado()));
        holder.data.setText(String.valueOf(dataAgend));
        holder.hora.setText(String.valueOf(horaAgend));
        holder.nome.setText(chamadosVO.getClientVO().getNome());
        holder.endereco.setText(chamadosVO.getClientVO().getEnderecoVO().getBairro() + ", " + chamadosVO.getClientVO().getEnderecoVO().getNumero());
        holder.status.setText(util.statusChamado(chamadosVO.getIdStatusChamado()));

        if (chamadosVO.getIdStatusChamado() == 2) {
            holder.status.setTextColor(Color.parseColor("#FBC433"));
            holder.data.setTextColor(Color.parseColor("#FBC433"));
            holder.hora.setTextColor(Color.parseColor("#FBC433"));
        } else if (chamadosVO.getAgendamento_Data().before(new Date())) {
            holder.status.setText("Atrasado");
            holder.status.setTextColor(Color.parseColor("#FFFF0000"));
            chamadosVO.setIdStatusChamado(3);
        }
    }

    @Override
    public int getItemCount() {
        return chamados.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tipoChamado, data, hora, endereco, nome, status;

        public MyViewHolder(View itemView) {
            super(itemView);

            tipoChamado = itemView.findViewById(R.id.textAdapterTipoChamado);
            data = itemView.findViewById(R.id.textAdapterData);
            hora = itemView.findViewById(R.id.textAdapterHora);
            endereco = itemView.findViewById(R.id.textAdapterEndereco);
            nome = itemView.findViewById(R.id.textAdapterNome);
            status = itemView.findViewById(R.id.textAdapterStatus);
        }
    }

}
