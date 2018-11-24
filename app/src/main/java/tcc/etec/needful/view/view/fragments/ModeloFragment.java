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
import tcc.etec.needful.view.view.view.JustificarActivity;
import tcc.etec.needful.view.view.view.DetalheChamadoActivity;

public class ModeloFragment extends Fragment {

    View view;
    Context context;
    TextView txtMes, txtFundo;
    ImageView imgFundo;
    private AdapterAgendados adapterAgendados;
    private RecyclerView recyclerView;
    private List<ChamadosVO> listaChamados = new ArrayList<>();
    private ChamadosVO chamado;
    private int idTecnico;
    private int teste;
    CompactCalendarView compactCalendarView;
    private long epoch;
    private String mes = "";
    private String dia = "";
    private String ano = "";
    private String dataCompleta = "";
    private String gg = "";

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
        swipe();

       /* try {
            List<String> listaDatas;
            ChamadosController controller = new ChamadosController(context);
            listaDatas = controller.buscarDatas(idTecnico);
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
                SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
                Date data2 = formato.parse(dataCompleta);

                if (data2.before(data)) {

                    this.epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(dataCompleta + " 14:30:00").getTime();
                    Event ev = new Event(Color.RED, epoch);
                    compactCalendarView.addEvent(ev);
                    ev = null;
                    this.epoch = 0;
                    zerarData();

                } else if (data2.after(data)) {

                    this.epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(dataCompleta + " 14:30:00").getTime();
                    Event ev = new Event(Color.BLUE, epoch);
                    compactCalendarView.addEvent(ev);
                    ev = null;
                    this.epoch = 0;
                    zerarData();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                zerarData();
                String dataSelecionada = String.valueOf(dateClicked);
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

                dataCompleta = ano.trim() + "-" + mesNumerico(mes) + dia;

                ChamadosController controller = new ChamadosController(context);
                listaChamados = controller.buscarAgendadoPorData(dataCompleta, idTecnico);
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

                String viewMes = mesPt(mes);
                txtMes.setText(viewMes+" -"+ano);
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

    private void zerarData() {
        ano = "";
        mes = "";
        dia = "";
        gg = "";
        dataCompleta = "";
    }

    private String mesNumerico(String mes) {
        if (mes.trim().equals("Jan")) {
            return "01-";
        } else if (mes.trim().equals("Feb")) {
            return "02-";
        } else if (mes.trim().equals("Mar")) {
            return "03-";
        } else if (mes.trim().equals("Apr")) {
            return "04-";
        } else if (mes.trim().equals("May")) {
            return "05-";
        } else if (mes.trim().equals("Jun")) {
            return "06-";
        } else if (mes.trim().equals("Jul")) {
            return "07-";
        } else if (mes.trim().equals("Aug")) {
            return "08-";
        } else if (mes.trim().equals("Sep")) {
            return "09-";
        } else if (mes.trim().equals("Oct")) {
            return "10-";
        } else if (mes.trim().equals("Nov")) {
            return "11-";
        } else if (mes.trim().equals("Dec")) {
            return "12-";
        }
        return null;
    }

    private String mesPt(String mes) {
        if (mes.trim().equals("Jan")) {
            return "Janeiro";
        } else if (mes.trim().equals("Feb")) {
            return "Fevereiro";
        } else if (mes.trim().equals("Mar")) {
            return "Março";
        } else if (mes.trim().equals("Apr")) {
            return "Abril";
        } else if (mes.trim().equals("May")) {
            return "Maio";
        } else if (mes.trim().equals("Jun")) {
            return "Junho";
        } else if (mes.trim().equals("Jul")) {
            return "Julho";
        } else if (mes.trim().equals("Aug")) {
            return "Agosto";
        } else if (mes.trim().equals("Sep")) {
            return "Setembro";
        } else if (mes.trim().equals("Oct")) {
            return "Outubro";
        } else if (mes.trim().equals("Nov")) {
            return "Novembro";
        } else if (mes.trim().equals("Dec")) {
            return "Dezembro";
        }
        return null;
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
        String mesPortugues = mesPt(mes);
        txtMes.setText(mesPortugues +" -" + ano);
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
                int position = viewHolder.getAdapterPosition();
                chamado = listaChamados.get(position);
                int idChamado = chamado.getID();
                Intent intent = new Intent(getActivity(), JustificarActivity.class);
                intent.putExtra("id_chamado", idChamado);
                intent.putExtra("tipo_status", "Bloqueio");
                startActivity(intent);
                teste = position;
                adapterAgendados.notifyItemRemoved(teste);
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

        dataCompleta = ano.trim() + "-" + mesNumerico(mes) + dia;
        ChamadosController controller = popularRecyclerView();

        try {
            List<String> listaDatas;
            listaDatas = controller.buscarAgendadosHoje(idTecnico, dataCompleta);
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

    private ChamadosController popularRecyclerView() {
        ChamadosController controller = new ChamadosController(context);
        listaChamados = controller.buscarAgendadoPorData(dataCompleta, idTecnico);
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
        return controller;
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

    @Override
    public void onResume() {
        super.onResume();
        adapterAgendados.notifyItemRemoved(teste);
        popularRecyclerView();
    }
}
