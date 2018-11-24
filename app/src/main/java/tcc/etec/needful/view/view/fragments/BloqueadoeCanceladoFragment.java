package tcc.etec.needful.view.view.fragments;

import android.app.AlertDialog;
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
import tcc.etec.needful.view.view.adapter.BloqueadoeCanceladoListAdapter;
import tcc.etec.needful.view.view.controller.ChamadosController;
import tcc.etec.needful.view.view.model.ChamadosVO;
import tcc.etec.needful.view.view.view.BloqueadoOuCanceladoActivity;


public class BloqueadoeCanceladoFragment extends Fragment {

    TextView textoFundo;
    ImageView imgFundo;
    AlertDialog.Builder build;
    AlertDialog alert;
    ArrayList<ChamadosVO> dataSet;
    ListView listView;
    Context context;
    ChamadosController controller;
    private int idTecnico;
    View view;

    public BloqueadoeCanceladoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_bloqueadoe_cancelado, container, false);

        Bundle id = getArguments();
        this.idTecnico = id.getInt("id_tecnico");

        context = getContext();
        controller = new ChamadosController(context);
        listView = view.findViewById(R.id.listVieew);
        dataSet = controller.listarBloqueadoCancelado(this.idTecnico);
        BloqueadoeCanceladoListAdapter adapter = new BloqueadoeCanceladoListAdapter(dataSet, getContext());
        listView.setAdapter(adapter);
        listView.setLongClickable(true);
        habilitarFundo();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChamadosVO chamadosVO = dataSet.get(position);
                int idChamado = chamadosVO.getID();
                Intent intent = new Intent(getActivity(), BloqueadoOuCanceladoActivity.class);
                intent.putExtra("id_chamado", idChamado);
                intent.putExtra("tipo_status", statusChamado(chamadosVO.getIdStatusChamado()));
                startActivity(intent);
            }
        });

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

    private String statusChamado(int status){
        if(status == 5){
            return "Bloqueado";
        }else if(status == 6){
            return "Cancelado";
        }
        return null;
    }
}
