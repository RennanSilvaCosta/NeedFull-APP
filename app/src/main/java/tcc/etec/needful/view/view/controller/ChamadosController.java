package tcc.etec.needful.view.view.controller;

import android.content.ContentValues;
import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tcc.etec.needful.view.view.datamodel.ChamadosDataModel;
import tcc.etec.needful.view.view.datamodel.ClienteDataModel;
import tcc.etec.needful.view.view.datamodel.EnderecoDataModel;
import tcc.etec.needful.view.view.datamodel.StatusDataModel;
import tcc.etec.needful.view.view.datamodel.TecnicoDataModel;
import tcc.etec.needful.view.view.datamodel.UsuarioDataModel;
import tcc.etec.needful.view.view.datasource.DataSource;
import tcc.etec.needful.view.view.model.ChamadosVO;
import tcc.etec.needful.view.view.model.ClienteVO;
import tcc.etec.needful.view.view.model.EnderecoVO;
import tcc.etec.needful.view.view.model.UsuarioModel;

public class ChamadosController extends DataSource {

    public ChamadosController(Context context) {
        super(context);
    }

    ContentValues dados;
    ContentValues dadosCliente;
    ContentValues dadosEndereco;
    ContentValues dadosStatus;
    ContentValues dadosTecnico;
    static SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");

    public boolean salvarUser(UsuarioModel user) {

        dados = new ContentValues();
        dados.put(UsuarioDataModel.getId(), user.getId());
        dados.put(UsuarioDataModel.getNome(), user.getNome());
        dados.put(UsuarioDataModel.getEmail(), user.getEmail());
        dados.put(UsuarioDataModel.getCpf(), user.getCPF());
        dados.put(UsuarioDataModel.getLogin(), user.getLogin());
        dados.put(UsuarioDataModel.getSenha(), user.getSenha());
        dados.put(UsuarioDataModel.getTipo_usuario(), user.getIdTipoUsuario());
        dados.put(UsuarioDataModel.getStatusAD(), user.getStatusAD());
        return insert(UsuarioDataModel.getTABELA(), dados);

    }

    public ArrayList<ChamadosVO> todosChamados(int idTecnico) {

        return listarChamados(idTecnico);

    }

    public ArrayList<ChamadosVO> todosAgendados(int idTecnico) {

        return listarAgendados(idTecnico);

    }

    public ArrayList<ChamadosVO> listarFinalizados(int idTecnico) {

        return listarChamadosFinalizados(idTecnico);

    }

    public ChamadosVO buscarPorid(int id) {

        return buscarChamadoPorId(id);

    }

    public UsuarioModel buscarUser(String login) {

        return buscarUsuario(login);
    }

    public boolean alterar(ChamadosVO chamados) {

        boolean salvou = true;

        dados = new ContentValues();

        dados.put(ChamadosDataModel.getId(), chamados.getID());
        dados.put(ChamadosDataModel.getDataChamado(), String.valueOf(chamados.getData()));
        dados.put(ChamadosDataModel.getHoraChamado(), String.valueOf(chamados.getHoras()));
        dados.put(ChamadosDataModel.getAgendamentoHoraChamado(), String.valueOf(chamados.getAgendamento_horas()));
        dados.put(ChamadosDataModel.getAgendamentoDataChamado(), String.valueOf(chamados.getAgendamento_Data()));
        dados.put(ChamadosDataModel.getObservacaoChamado(), chamados.getDescricao());
        dados.put(ChamadosDataModel.getIdTecnico(), chamados.getIdTecnico());
        dados.put(ChamadosDataModel.getIdTipoChamado(), chamados.getTipoChamado());
        dados.put(ChamadosDataModel.getIdStatusChamado(), chamados.getIdStatusChamado());
        dados.put(ChamadosDataModel.getJustificativa(), chamados.getJustificativa());

        salvou = Update(ChamadosDataModel.getTABELA(), dados, "id_chamado=?", new String[]{String.valueOf(chamados.getID())});

        return salvou;
    }

    public boolean salvar(ChamadosVO chamados) {

        boolean retornoChamado, retornoCliente, retornoEndereco, retornoStatus, retornoTecnico;

        dados = new ContentValues();
        dadosCliente = new ContentValues();
        dadosEndereco = new ContentValues();
        dadosStatus = new ContentValues();
        dadosTecnico = new ContentValues();

        dados.put(ChamadosDataModel.getId(), chamados.getID());
        dados.put(ChamadosDataModel.getDataChamado(), String.valueOf(chamados.getData()));
        dados.put(ChamadosDataModel.getHoraChamado(), String.valueOf(chamados.getHoras()));
        dados.put(ChamadosDataModel.getDataChamado(), String.valueOf(chamados.getData()));
        dados.put(ChamadosDataModel.getAgendamentoHoraChamado(), String.valueOf(chamados.getAgendamento_horas()));
        dados.put(ChamadosDataModel.getObservacaoChamado(), chamados.getDescricao());
        dados.put(ChamadosDataModel.getIdTipoChamado(), chamados.getTipoChamado());
        dados.put(ChamadosDataModel.getIdStatusChamado(), chamados.getIdStatusChamado());
        dados.put(ChamadosDataModel.getConfirmacaoData_chamado(), String.valueOf(chamados.getConfirmacao_Data()));
        dados.put(ChamadosDataModel.getConfirmacaoHora_chamado(), String.valueOf(chamados.getConfirmacao_Horas()));
        dados.put(ChamadosDataModel.getFinalizacaoData_chamado(), String.valueOf(chamados.getFinalizacao_Data()));
        dados.put(ChamadosDataModel.getFinalizacaoData_chamado(), String.valueOf(chamados.getFinalizacao_Data()));
        dados.put(ChamadosDataModel.getFinalizacaoHora_chamado(), String.valueOf(chamados.getFinalizacao_horas()));
        dados.put(ChamadosDataModel.getJustificativa(), chamados.getJustificativa());
        dados.put(ChamadosDataModel.getIdCliente(), chamados.getClientVO().getId());
        dados.put(ChamadosDataModel.getIdTecnico(), chamados.getTecnicoVO().getId());
        dados.put(ChamadosDataModel.getIdTipoChamado(), 1);
        dados.put(ChamadosDataModel.getIdStatusChamado(), chamados.getStatusVO().getId());

        dadosCliente.put(ClienteDataModel.getIdCliente(), chamados.getClientVO().getId());
        dadosCliente.put(ClienteDataModel.getNomeCliente(), chamados.getClientVO().getNome());
        dadosCliente.put(ClienteDataModel.getTelefoneCliente(), chamados.getClientVO().getTelefone());
        dadosCliente.put(ClienteDataModel.getCelularCliente(), chamados.getClientVO().getCelular());
        dadosCliente.put(ClienteDataModel.getEmailCliente(), chamados.getClientVO().geteMail());
        dadosCliente.put(ClienteDataModel.getLoginCliente(), chamados.getClientVO().getLogin());
        dadosCliente.put(ClienteDataModel.getSenhaCliente(), chamados.getClientVO().getSenha());
        dadosCliente.put(ClienteDataModel.getEquipamentoCliente(), chamados.getClientVO().getRoteador());
        dadosCliente.put(ClienteDataModel.getCaboCliente(), chamados.getClientVO().getCabeamento());
        dadosCliente.put(ClienteDataModel.getIdEndereco(), chamados.getClientVO().getEnderecoVO().getId());

        dadosEndereco.put(EnderecoDataModel.getIdEndereco(), chamados.getClientVO().getEnderecoVO().getId());
        dadosEndereco.put(EnderecoDataModel.getRuaEndereco(), chamados.getClientVO().getEnderecoVO().getRua());
        dadosEndereco.put(EnderecoDataModel.getBairroEndereco(), chamados.getClientVO().getEnderecoVO().getBairro());
        dadosEndereco.put(EnderecoDataModel.getNumeroEdenreco(), chamados.getClientVO().getEnderecoVO().getNumero());
        dadosEndereco.put(EnderecoDataModel.getCepEndereco(), chamados.getClientVO().getEnderecoVO().getCep());
        dadosEndereco.put(EnderecoDataModel.getComplementoEndereco(), chamados.getClientVO().getEnderecoVO().getComplemento());
        dadosEndereco.put(EnderecoDataModel.getReferenciaEndereco(), chamados.getClientVO().getEnderecoVO().getReferencia());

        dadosStatus.put(StatusDataModel.getIdStatus(), chamados.getStatusVO().getId());
        dadosStatus.put(StatusDataModel.getStatus(), chamados.getStatusVO().getTipo());

        dadosTecnico.put(TecnicoDataModel.getIdTecnico(), chamados.getTecnicoVO().getId());
        dadosTecnico.put(TecnicoDataModel.getNomeTecnico(), chamados.getTecnicoVO().getTecnico());
        dadosTecnico.put(TecnicoDataModel.getIdUsuario(), chamados.getTecnicoVO().getId_usuario());

        retornoChamado = insert(ChamadosDataModel.getTABELA(), dados);
        retornoCliente = insert(ClienteDataModel.getTabela(), dadosCliente);
        retornoEndereco = insert(EnderecoDataModel.getTabela(), dadosEndereco);
        retornoStatus = insert(StatusDataModel.getTabela(), dadosStatus);
        retornoTecnico = insert(TecnicoDataModel.getTabela(), dadosTecnico);

        if (retornoChamado == true && retornoCliente == true && retornoEndereco == true && retornoStatus == true && retornoTecnico == true) {
            return true;
        } else {
            return false;
        }

    }

}
