package tcc.etec.needful.view.view.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import tcc.etec.needful.R;
import tcc.etec.needful.view.view.adapter.AgendadosListAdapter;
import tcc.etec.needful.view.view.controller.ChamadosController;
import tcc.etec.needful.view.view.model.ChamadosVO;
import tcc.etec.needful.view.view.view.DetalheChamadoActivity;
import tcc.etec.needful.view.view.view.JustificarActivity;


public class AgendadosFragment extends Fragment {

    ArrayList<ChamadosVO> dataSet;
    View view;
    ListView listView;
    ChamadosController controller;
    TextView textoFundo;
    ImageView imgFundo;
    Context context;
    private int idTecnico;

    public AgendadosFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_agendados, container, false);

        Bundle id = getArguments();
        this.idTecnico = id.getInt("id_tecnico");

        controller = new ChamadosController(getContext());
        listView = view.findViewById(R.id.listviewAgendados);
        dataSet = controller.todosAgendados(this.idTecnico);
        habilitarFundo();
        AgendadosListAdapter adapter = new AgendadosListAdapter(dataSet, getContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChamadosVO chamadosVO = dataSet.get(position);

                    int idChamado = chamadosVO.getID();
                    Intent intent = new Intent(getActivity(), DetalheChamadoActivity.class);
                    intent.putExtra("id_chamado", idChamado);
                    startActivity(intent);

            }
        });
        registerForContextMenu(listView);
        return view;
    }

    private void habilitarFundo() {
        imgFundo = view.findViewById(R.id.imgFundo);
        textoFundo = view.findViewById(R.id.txtFundo);
        if(dataSet.size() == 0){
            imgFundo.setVisibility(View.VISIBLE);
            textoFundo.setVisibility(View.VISIBLE);
        }else{
            imgFundo.setVisibility(View.INVISIBLE);
            textoFundo.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_flutuante, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.opcao_1:
                final int posicao = info.position;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Bloquear Chamado");
                alertDialog.setMessage("Você tem certeza que deseja bloquear este chamado? Sua ação tera que ser justificada.");
                alertDialog.setCancelable(false);

                alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ChamadosVO chamadosVO = dataSet.get(posicao);
                        int idChamado = chamadosVO.getID();
                        chamadosVO.setFinalizacao_Data(new Date());
                        Intent intent = new Intent(getActivity(), JustificarActivity.class);
                        intent.putExtra("tipo_status", "Bloqueio");
                        intent.putExtra("id_chamado", idChamado);
                        startActivity(intent);
                    }
                });

                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show();

                    }
                });

                AlertDialog alert = alertDialog.create();
                alert.show();

                return true;

            case R.id.opcao_2:
                final int posicao2 = info.position;
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);
                alertDialog2.setTitle("Cancelar Chamado");
                alertDialog2.setMessage("Você tem certeza que deseja cancelar este chamado? Sua ação tera que ser justificada.");
                alertDialog2.setCancelable(false);

                alertDialog2.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ChamadosVO chamadosVO = dataSet.get(posicao2);
                        int idChamado = chamadosVO.getID();
                        chamadosVO.setFinalizacao_Data(new Date());
                        Intent intent = new Intent(getActivity(), JustificarActivity.class);
                        intent.putExtra("tipo_status", "Cancelamento");
                        intent.putExtra("id_chamado", idChamado);
                        startActivity(intent);
                    }
                });

                alertDialog2.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show();

                    }
                });

                AlertDialog alert2 = alertDialog2.create();
                alert2.show();
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista(){

        context = getContext();
        controller = new ChamadosController(getContext());
        listView = view.findViewById(R.id.listviewAgendados);
        dataSet = controller.todosChamados(this.idTecnico);
        AgendadosListAdapter adapter = new AgendadosListAdapter(dataSet, getContext());
        listView.setAdapter(adapter);
        listView.setLongClickable(true);
        habilitarFundo();
        adapter.notifyDataSetChanged();

    }
}
