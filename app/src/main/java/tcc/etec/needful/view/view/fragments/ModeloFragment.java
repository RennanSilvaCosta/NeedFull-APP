package tcc.etec.needful.view.view.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import tcc.etec.needful.R;
import tcc.etec.needful.view.view.adapter.AdapterAgendados;
import tcc.etec.needful.view.view.adapter.RecyclerItemClickListener;
import tcc.etec.needful.view.view.controller.ChamadosController;
import tcc.etec.needful.view.view.model.ChamadosVO;
import tcc.etec.needful.view.view.util.UtilChamados;
import tcc.etec.needful.view.view.view.JustificarActivity;
import tcc.etec.needful.view.view.view.DetalheChamadoActivity;

public class ModeloFragment extends Fragment {

    View view;
    Context context;
    TextView txtMes, txtFundo;
    ImageView imgFundo;
    private AdapterAgendados adapterAgendados;
    private RecyclerView recyclerView;
    private List<ChamadosVO> listaChamados;
    private ChamadosVO chamado;
    private UtilChamados util;
    private int idTecnico;
    private int posicao;
    CompactCalendarView compactCalendarView;
    private long epoch;
    private String mes = "", dia = "", ano = "", dataCompleta = "";

    public ModeloFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_modelo, container, false);

        Bundle id = getArguments();
        this.idTecnico = id.getInt("id_tecnico");

        listaChamados = new ArrayList<>();
        util = new UtilChamados();
        context = getContext();
        recyclerView = view.findViewById(R.id.recyclerAgendados);
        compactCalendarView = view.findViewById(R.id.compactcalendar_view);
        txtMes = view.findViewById(R.id.txt_mes);
        txtFundo = view.findViewById(R.id.txtFundo);
        imgFundo = view.findViewById(R.id.imgFundo);
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        setarMesView(compactCalendarView.getFirstDayOfCurrentMonth());

        Date data = new Date();
        adiconarEventosHoje(data);
        adicionarEventos(data);
        swipe();

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                zerarData();
                String dataSelecionada = String.valueOf(dateClicked);
                for (int i = 4; i < 7; i++) {
                    char c = dataSelecionada.charAt(i);
                    mes = mes + String.valueOf(c);
                }
                for (int i = 29; i <= 33; i++) {
                    char c = dataSelecionada.charAt(i);
                    ano = ano + String.valueOf(c);
                }

                ChamadosController controller = new ChamadosController(context);
                listaChamados = controller.buscarAgendadoPorData(dataSelecionada, idTecnico);
                if (listaChamados.size() == 0) {
                    txtFundo.setVisibility(View.VISIBLE);
                    imgFundo.setVisibility(View.VISIBLE);
                } else {
                    txtFundo.setVisibility(View.INVISIBLE);
                    imgFundo.setVisibility(View.INVISIBLE);
                }
                adapterAgendados = new AdapterAgendados(listaChamados, context);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapterAgendados);

                String viewMes = util.mesPt(mes);
                txtMes.setText(viewMes + " -" + ano);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setarMesView(firstDayOfNewMonth);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                ChamadosVO chamadosVO = listaChamados.get(position);
                int idChamado = chamadosVO.getID();
                Intent intent = new Intent(getActivity(), DetalheChamadoActivity.class);
                intent.putExtra("id_chamado", idChamado);
                startActivity(intent);

            }

            @Override
            public void onLongItemClick(View view, int position) {
                registerForContextMenu(recyclerView);
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        return view;
    }

    private void adicionarEventos(Date data) {
        try {
            List<String> listaDatas;
            ChamadosController controller = new ChamadosController(context);
            listaDatas = controller.buscarDatas(idTecnico);
            String dataAux = "";

            for (String datas : listaDatas) {
                dataAux = datas;
                for (int i = 4; i < 7; i++) {
                    char c = dataAux.charAt(i);
                    mes = mes + String.valueOf(c);
                }
                for (int i = 8; i < 10; i++) {
                    char c = dataAux.charAt(i);
                    dia = dia + String.valueOf(c);
                }
                for (int i = 29; i <= 33; i++) {
                    char c = dataAux.charAt(i);
                    ano = ano + String.valueOf(c);
                }

                mes = util.mesNumerico(mes);
                dataCompleta = mes + "/" + dia + "/" + ano;
                SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
                Date dataConvertida = formato.parse(dataCompleta);

                if (dataConvertida.before(data)) {

                    this.epoch = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(dataCompleta + " 14:30:00").getTime();
                    Event ev = new Event(Color.RED, epoch);
                    compactCalendarView.addEvent(ev);
                    ev = null;
                    this.epoch = 0;
                    zerarData();

                } else if (dataConvertida.after(data)) {

                    this.epoch = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(dataCompleta + " 14:30:00").getTime();
                    Event ev = new Event(Color.BLUE, epoch);
                    compactCalendarView.addEvent(ev);
                    ev = null;
                    this.epoch = 0;
                    zerarData();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void zerarData() {
        ano = "";
        mes = "";
        dia = "";
        dataCompleta = "";
    }

    private void setarMesView(Date dataCalender) {
        String data = String.valueOf(dataCalender);

        zerarData();
        for (int i = 4; i < 7; i++) {
            char c = data.charAt(i);
            mes = mes + String.valueOf(c);
        }
        for (int i = 29; i <= 33; i++) {
            char c = data.charAt(i);
            ano = ano + String.valueOf(c);
        }
        String mesPortugues = util.mesPt(mes);
        txtMes.setText(mesPortugues + " -" + ano);
        zerarData();
    }

    public void swipe() {

        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                bloquearChamado(viewHolder);
                adapterAgendados.notifyItemRemoved(viewHolder.getAdapterPosition());
                adapterAgendados.notifyDataSetChanged();
            }
        };

        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView);

    }

    public void bloquearChamado(final RecyclerView.ViewHolder viewHolder) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Bloquear Chamado");
        alertDialog.setMessage("Você tem certeza que deseja bloquear este chamado? Sua ação tera que ser justificada.");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chamado = listaChamados.get(viewHolder.getAdapterPosition());
                int idChamado = chamado.getID();
                Intent intent = new Intent(getActivity(), JustificarActivity.class);
                intent.putExtra("id_chamado", idChamado);
                intent.putExtra("tipo_status", "Bloqueio");
                startActivity(intent);
                adapterAgendados.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show();
                adapterAgendados.notifyDataSetChanged();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();

    }

    private void adiconarEventosHoje(Date hoje) {

        long epoca;
        zerarData();
        String dataSelecionada = String.valueOf(hoje);
        for (int i = 4; i < 7; i++) {
            char c = dataSelecionada.charAt(i);
            mes = mes + String.valueOf(c);
        }
        for (int i = 8; i < 10; i++) {
            char c = dataSelecionada.charAt(i);
            dia = dia + String.valueOf(c);
        }
        for (int i = 29; i <= 33; i++) {
            char c = dataSelecionada.charAt(i);
            ano = ano + String.valueOf(c);
        }

        dataCompleta = ano.trim() + "-" + util.mesNumerico(mes) + "-" + dia;

        popularRecyclerView(dataSelecionada);
        ChamadosController controller = new ChamadosController(context);
        try {
            List<String> listaDatas;
            listaDatas = controller.buscarAgendadosHoje(idTecnico, dataSelecionada);
            zerarData();
            String teste = "";

            for (String teste1 : listaDatas) {
                teste = teste1;

                for (int i = 0; i < 4; i++) {
                    char c = teste.charAt(i);
                    ano = ano + String.valueOf(c);
                }
                for (int i = 5; i <= 6; i++) {
                    char c = teste.charAt(i);
                    mes = mes + String.valueOf(c);
                }
                for (int i = 8; i < 10; i++) {
                    char c = teste.charAt(i);
                    dia = dia + String.valueOf(c);
                }

                dataCompleta = mes + "/" + dia + "/" + ano;

                this.epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(dataCompleta + " 14:30:00").getTime();
                Event ev = new Event(Color.BLUE, epoch);
                compactCalendarView.addEvent(ev);
                ev = null;
                this.epoch = 0;
                zerarData();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void popularRecyclerView(String data) {
        ChamadosController controller = new ChamadosController(context);
        listaChamados = controller.buscarAgendadoPorData(data, idTecnico);
        if (listaChamados.size() == 0) {
            txtFundo.setVisibility(View.VISIBLE);
            imgFundo.setVisibility(View.VISIBLE);
        } else {
            txtFundo.setVisibility(View.INVISIBLE);
            imgFundo.setVisibility(View.INVISIBLE);

            adapterAgendados = new AdapterAgendados(listaChamados, context);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapterAgendados);
        }
    }

    private Date prepararData(Date hoje) throws ParseException {

        zerarData();
        String dataSelecionada = String.valueOf(hoje);
        for (int i = 4; i < 7; i++) {
            char c = dataSelecionada.charAt(i);
            mes = mes + String.valueOf(c);
        }
        for (int i = 8; i < 10; i++) {
            char c = dataSelecionada.charAt(i);
            dia = dia + String.valueOf(c);
        }
        for (int i = 29; i <= 33; i++) {
            char c = dataSelecionada.charAt(i);
            ano = ano + String.valueOf(c);
        }

        dataCompleta = mes + "/" + dia + "/" + ano;
        SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
        Date data = formato.parse(dataCompleta);
        return data;
    }

}
