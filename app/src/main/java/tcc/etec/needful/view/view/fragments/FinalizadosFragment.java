package tcc.etec.needful.view.view.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tcc.etec.needful.R;
import tcc.etec.needful.view.view.adapter.FinalizadosListAdapter;
import tcc.etec.needful.view.view.controller.ChamadosController;
import tcc.etec.needful.view.view.model.ChamadosVO;
import tcc.etec.needful.view.view.view.DetalheChamadoActivity;


public class FinalizadosFragment extends Fragment {

    TextView textoFundo;
    ImageView imgFundo;
    ArrayList<ChamadosVO> dataSet;
    ListView listView;
    View view;
    Context context;
    ChamadosController controller;
    private int idTecnico;

    public FinalizadosFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_finalizados, container, false);

        Bundle id = getArguments();
        this.idTecnico = id.getInt("id_tecnico");

        context = getContext();
        controller = new ChamadosController(context);

        ChamadosController controller = new ChamadosController(getContext());
        listView = view.findViewById(R.id.listView);
        dataSet = controller.listarFinalizados(this.idTecnico);
        FinalizadosListAdapter adapter = new FinalizadosListAdapter(dataSet, getContext());
        listView.setAdapter(adapter);

        habilitarFundo();

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

        return view;
    }

    private void habilitarFundo() {
        imgFundo = view.findViewById(R.id.imgFundo);
        textoFundo = view.findViewById(R.id.txtFundo);
        if (dataSet.size() == 0) {
            imgFundo.setVisibility(View.VISIBLE);
            textoFundo.setVisibility(View.VISIBLE);
        } else {
            imgFundo.setVisibility(View.INVISIBLE);
            textoFundo.setVisibility(View.INVISIBLE);
        }
    }

}
