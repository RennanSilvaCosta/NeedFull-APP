package tcc.etec.needful.view.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import tcc.etec.needful.R;
import tcc.etec.needful.view.view.controller.ChamadosController;
import tcc.etec.needful.view.view.model.UsuarioModel;

public class StatusFragment extends Fragment {

    View view;
    ProgressBar progressoManutencao;
    ProgressBar progressInstalacao;
    ChamadosController controller;
    Context context;
    TextView exibirM;
    TextView exibirI;
    TextView nome;
    TextView email;

    private int totalInstalacao = 0;
    private int totalManutencao = 0;
    private int idTecnico;

    public StatusFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_status, container, false);

        Bundle id = getArguments();
        this.idTecnico = id.getInt("id_tecnico");

        UsuarioModel user = new UsuarioModel();
        context = getContext();
        controller = new ChamadosController(context);
        user = controller.buscarTecnicoPorId(this.idTecnico);
        this.totalManutencao = controller.buscarTotalManutencao(this.idTecnico) + this.totalManutencao;
        this.totalInstalacao = controller.buscarTotalInstalacao(this.idTecnico) + this.totalInstalacao;

        progressoManutencao = view.findViewById(R.id.progressBarManutencao);
        progressInstalacao = view.findViewById(R.id.progressBarInstalacao);
        nome = view.findViewById(R.id.txt_nome);
        email = view.findViewById(R.id.txt_email);

        exibirM = view.findViewById(R.id.totalM);
        exibirI = view.findViewById(R.id.totalI);
        exibirM.setText(String.valueOf(totalManutencao));
        exibirI.setText(String.valueOf(totalInstalacao));
        progressoManutencao.setProgress(this.totalManutencao);
        progressInstalacao.setProgress(this.totalInstalacao);

        nome.setText(user.getNome());
        email.setText(user.getEmail());

        return view;
    }

}
