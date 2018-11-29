package tcc.etec.needful.view.view.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tcc.etec.needful.view.view.datamodel.ChamadosDataModel;
import tcc.etec.needful.view.view.datamodel.ClienteDataModel;
import tcc.etec.needful.view.view.datamodel.EnderecoDataModel;
import tcc.etec.needful.view.view.datamodel.UsuarioDataModel;
import tcc.etec.needful.view.view.model.ChamadosVO;
import tcc.etec.needful.view.view.model.ClienteVO;
import tcc.etec.needful.view.view.model.EnderecoVO;
import tcc.etec.needful.view.view.model.UsuarioVO;
import tcc.etec.needful.view.view.util.UtilChamados;

public class DataSource extends SQLiteOpenHelper {

    private static final String DB_NAME = "needful.sqlite";
    private static final int DB_VERSION = 1;
    DateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);


    SQLiteDatabase db;
    Cursor cursor;

    public DataSource(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insert(String tabela, ContentValues dados) {

        boolean retorno = true;
        try {
            retorno = db.insert(tabela, null,
                    dados) > 0;
        } catch (Exception e) {
            retorno = false;
        }
        return retorno;
    }


    public ArrayList<ChamadosVO> listarChamados(int idTecnico) {

        ArrayList<ChamadosVO> lista = new ArrayList<>();
        String comandoSQL = "SELECT chamado.*, cliente.*, endereco.* FROM chamado" +
                " INNER JOIN cliente ON(cliente.id_cliente = chamado.id_cliente)" +
                " INNER JOIN endereco ON(endereco.id_endereco = cliente.id_endereco) WHERE chamado.id_tecnico=" + idTecnico +
                " AND chamado.id_status_chamado=1 OR chamado.id_status_chamado=2";

        cursor = db.rawQuery(comandoSQL, null);
        return getChamadosVOS(lista);
    }

    public ArrayList<ChamadosVO> listarAgendados(int idTecnico) {

        UtilChamados util = new UtilChamados();
        ArrayList<ChamadosVO> lista = new ArrayList<>();

        String comandoSQL = "SELECT chamado.*, cliente.*, endereco.* FROM chamado \n" +
                "INNER JOIN cliente on(chamado.id_cliente = cliente.id_cliente) INNER JOIN endereco" +
                " ON(cliente.id_endereco = endereco.id_endereco)\n" +
                "WHERE chamado.id_tecnico=" + idTecnico + " AND (chamado.id_status_chamado=2 OR chamado.id_status_chamado=1)" +
                " AND chamado.agendamentoData_chamado > " + "'" + util.pegaDataHora() + "'";

        cursor = db.rawQuery(comandoSQL, null);
        return getChamadosVOS(lista);

    }

    public ArrayList<ChamadosVO> listarChamadosFinalizados(int idTecnico) {

        ArrayList<ChamadosVO> lista = new ArrayList<>();
        String comandoSQL = "SELECT chamado.*, cliente.*,  endereco.* FROM chamado\n" +
                " INNER JOIN cliente ON(chamado.id_cliente = cliente.id_cliente)\n" +
                "INNER JOIN endereco ON(cliente.id_endereco = endereco.id_endereco)\n" +
                "WHERE chamado.id_status_chamado=4 AND chamado.id_tecnico=" + idTecnico;

        cursor = db.rawQuery(comandoSQL, null);

        return getChamadosVOS(lista);

    }

    public ChamadosVO buscarChamadoPorId(int id) {

        ChamadosVO chamados;
        ClienteVO cliente;
        EnderecoVO endereco;

        String comandoSQL = "SELECT chamado.*, cliente.*, endereco.* FROM chamado " +
                "INNER JOIN cliente ON(cliente.id_cliente = chamado.id_cliente) INNER JOIN endereco" +
                " on(endereco.id_endereco=cliente.id_endereco) WHERE chamado.id_chamado=" + id + ";";

        cursor = db.rawQuery(comandoSQL, null);

        if (cursor.moveToFirst()) {

            chamados = new ChamadosVO();
            cliente = new ClienteVO();
            endereco = new EnderecoVO();

            try {

                chamados.setID(cursor.getInt(cursor.getColumnIndex(ChamadosDataModel.getId())));

                String auxData = cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getDataChamado()));
                Date data = inputFormat.parse(auxData);
                chamados.setData(data);

                chamados.setHoras(Time.valueOf(cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getHoraChamado()))));

                String auxDataAgend = cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getAgendamentoDataChamado()));
                Date dataAgend = inputFormat.parse(auxDataAgend);
                chamados.setAgendamento_Data(dataAgend);

                chamados.setAgendamento_horas(Time.valueOf(cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getAgendamentoHoraChamado()))));
                chamados.setDescricao(cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getObservacaoChamado())));
                chamados.setIdTecnico(cursor.getInt(cursor.getColumnIndex(ChamadosDataModel.getIdTecnico())));
                chamados.setTipoChamado(cursor.getInt(cursor.getColumnIndex(ChamadosDataModel.getIdTipoChamado())));
                chamados.setIdStatusChamado(cursor.getInt(cursor.getColumnIndex(ChamadosDataModel.getIdStatusChamado())));

                String auxDataConf = cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getConfirmacaoData_chamado()));
                Date dataConf = inputFormat.parse(auxDataConf);
                chamados.setConfirmacao_Data(dataConf);

                chamados.setConfirmacao_Horas(Time.valueOf(cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getConfirmacaoHora_chamado()))));

                String auxDataFin = cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getFinalizacaoData_chamado()));
                Date dataFin = inputFormat.parse(auxDataFin);
                chamados.setFinalizacao_Data(dataFin);

                chamados.setFinalizacao_horas(Time.valueOf(cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getFinalizacaoHora_chamado()))));
                chamados.setJustificativa(cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getJustificativa())));

                cliente.setId(cursor.getInt(cursor.getColumnIndex(ClienteDataModel.getIdCliente())));
                cliente.setNome(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getNomeCliente())));
                cliente.seteMail(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getEmailCliente())));
                cliente.setLogin(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getLoginCliente())));
                cliente.setSenha(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getSenhaCliente())));
                cliente.setTelefone(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getTelefoneCliente())));
                cliente.setCelular(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getCelularCliente())));
                cliente.setRoteador(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getEquipamentoCliente())));
                cliente.setCabeamento(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getCaboCliente())));

                endereco.setId(cursor.getInt(cursor.getColumnIndex(EnderecoDataModel.getIdEndereco())));
                endereco.setRua(cursor.getString(cursor.getColumnIndex(EnderecoDataModel.getRuaEndereco())));
                endereco.setBairro(cursor.getString(cursor.getColumnIndex(EnderecoDataModel.getBairroEndereco())));
                endereco.setNumero(cursor.getString(cursor.getColumnIndex(EnderecoDataModel.getNumeroEdenreco())));
                endereco.setCep(cursor.getString(cursor.getColumnIndex(EnderecoDataModel.getCepEndereco())));
                endereco.setComplemento(cursor.getString(cursor.getColumnIndex(EnderecoDataModel.getComplementoEndereco())));
                endereco.setReferencia(cursor.getString(cursor.getColumnIndex(EnderecoDataModel.getReferenciaEndereco())));

                chamados.setClientVO(cliente);
                cliente.setEnderecoVO(endereco);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            cursor.close();
            return chamados;
        }

        cursor.close();
        return null;

    }

    public ArrayList<ChamadosVO> buscarAgendadoPorData(String dataCompleta, int idTecnico) {

        ArrayList<ChamadosVO> lista = new ArrayList<>();

        String comandoSQL = "SELECT chamado.*, cliente.*, endereco.* FROM chamado \n" +
                "INNER JOIN cliente on(chamado.id_cliente = cliente.id_cliente) \n" +
                "INNER JOIN endereco ON(cliente.id_endereco = endereco.id_endereco) \n" +
                "WHERE chamado.id_tecnico=" + idTecnico + " AND (chamado.id_status_chamado=2 OR chamado.id_status_chamado=1)\n" +
                " AND chamado.agendamentoData_chamado=" + "'" + dataCompleta + "'";

        cursor = db.rawQuery(comandoSQL, null);
        return getChamadosVOS(lista);

    }

    public UsuarioVO buscarUsuario(String login) {

        UsuarioVO user = new UsuarioVO();

        cursor = db.rawQuery("SELECT * FROM usuarios WHERE email_usuario = " + "'" + login + "'", null);

        if (cursor.moveToFirst()) {

            user.setId(cursor.getInt(cursor.getColumnIndex(UsuarioDataModel.getId())));
            user.setNome(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.getNome())));
            user.setEmail(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.getEmail())));
            user.setCPF(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.getCpf())));
            user.setLogin(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.getLogin())));
            user.setSenha(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.getSenha())));
            user.setIdTipoUsuario(cursor.getInt(cursor.getColumnIndex(UsuarioDataModel.getTipo_usuario())));

            cursor.close();
            return user;
        }

        cursor.close();
        return user;

    }

    public UsuarioVO buscarTecnicoPorId(int id) {

        UsuarioVO user = new UsuarioVO();

        cursor = db.rawQuery("SELECT * FROM usuarios WHERE id_usuario=" + id, null);

        if (cursor.moveToFirst()) {

            user.setId(cursor.getInt(cursor.getColumnIndex(UsuarioDataModel.getId())));
            user.setNome(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.getNome())));
            user.setEmail(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.getEmail())));
            user.setCPF(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.getCpf())));
            user.setLogin(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.getLogin())));
            user.setSenha(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.getSenha())));
            user.setIdTipoUsuario(cursor.getInt(cursor.getColumnIndex(UsuarioDataModel.getTipo_usuario())));

            return user;
        }
        cursor.close();
        return user;

    }

    public int buscarTotalManutencao(int idTecnico) {

        int total = 0;
        cursor = db.rawQuery("SELECT COUNT(id_chamado) AS 'total_de_manutencoes' FROM chamado WHERE id_tecnico =" + idTecnico + " AND id_tipo_chamado = 2 AND id_status_chamado=4", null);

        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("total_de_manutencoes"));
            cursor.close();
            return total;
        }
        cursor.close();
        return total;
    }

    public int buscarTotalInstalacao(int idTecnico) {

        int total = 0;
        cursor = db.rawQuery("SELECT COUNT(id_chamado) AS 'total_de_instalacoes' FROM chamado WHERE id_tecnico = " + idTecnico + " AND id_tipo_chamado = 1 AND id_status_chamado=4", null);

        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("total_de_instalacoes"));
            cursor.close();
            return total;

        }
        cursor.close();
        return total;
    }

    public boolean Update(String tabela, ContentValues values, String where, String[] whereArgs) {
        try {
            db.update(tabela, values, where, whereArgs);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public void deletarTabela(String tabela) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + tabela);
        } catch (Exception e) {
        }
    }

    public void criarTabela(String queryCriarTabela) {
        try {
            db.execSQL(queryCriarTabela);
        } catch (SQLiteCantOpenDatabaseException e) {
        }
    }

    public ArrayList<String> buscarDatas(int idTecnico) {

        ArrayList<String> lista = new ArrayList<>();

        String comandoSQL = "SELECT chamado.agendamentoData_chamado FROM chamado\n" +
                "WHERE (chamado.id_status_chamado=1 OR chamado.id_status_chamado=2)" +
                " AND id_tecnico=" + idTecnico;

        cursor = db.rawQuery(comandoSQL, null);
        if (cursor.moveToFirst()) {
            do {
                String data = cursor.getString(cursor.getColumnIndex("agendamentoData_chamado"));
                lista.add(data);
            } while (cursor.moveToNext());
            cursor.close();
            return lista;
        }
        return lista;
    }

    public List<String> buscarAgendadosHoje(int idTecnico, String hoje) {

        ArrayList<String> lista = new ArrayList<>();
        String comandoSQL = "SELECT chamado.agendamentoData_chamado FROM chamado\n" +
                "WHERE (chamado.id_status_chamado=1 OR chamado.id_status_chamado=2) AND " +
                "chamado.agendamentoData_chamado= " + "'" + hoje + "'" + " AND id_tecnico=" + idTecnico;

        cursor = db.rawQuery(comandoSQL, null);
        if (cursor.moveToFirst()) {
            do {
                String data = cursor.getString(cursor.getColumnIndex("agendamentoData_chamado"));
                lista.add(data);
            } while (cursor.moveToNext());
            cursor.close();
            return lista;
        }
        return lista;

    }


    public ArrayList<ChamadosVO> listarBloqueadoCancelado(int idTecnico) {

        ChamadosVO chamados;
        ClienteVO cliente;
        EnderecoVO endereco;

        ArrayList<ChamadosVO> lista = new ArrayList<>();
        String comandoSQL = "SELECT chamado.*, cliente.*,  endereco.* FROM chamado\n" +
                " INNER JOIN cliente ON(chamado.id_cliente = cliente.id_cliente)\n" +
                "INNER JOIN endereco ON(cliente.id_endereco = endereco.id_endereco)\n" +
                "WHERE (chamado.id_status_chamado=5 OR chamado.id_status_chamado=6) AND chamado.id_tecnico=" + idTecnico;

        cursor = db.rawQuery(comandoSQL, null);
        return getChamadosVOS(lista);
    }

    private ArrayList<ChamadosVO> getChamadosVOS(ArrayList<ChamadosVO> lista) {

        ChamadosVO chamados;
        ClienteVO cliente;
        EnderecoVO endereco;

        if (cursor.moveToFirst()) {

            do {
                try {
                    chamados = new ChamadosVO();
                    cliente = new ClienteVO();
                    endereco = new EnderecoVO();

                    chamados.setID(cursor.getInt(cursor.getColumnIndex(ChamadosDataModel.getId())));

                    String auxData = cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getDataChamado()));
                    Date data = inputFormat.parse(auxData);
                    chamados.setData(data);

                    chamados.setHoras(Time.valueOf(cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getHoraChamado()))));

                    String auxDataAgend = cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getAgendamentoDataChamado()));
                    Date dataAgend = inputFormat.parse(auxDataAgend);
                    chamados.setAgendamento_Data(dataAgend);

                    chamados.setAgendamento_horas(Time.valueOf(cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getAgendamentoHoraChamado()))));
                    chamados.setDescricao(cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getObservacaoChamado())));
                    chamados.setIdTecnico(cursor.getInt(cursor.getColumnIndex(ChamadosDataModel.getIdTecnico())));
                    chamados.setTipoChamado(cursor.getInt(cursor.getColumnIndex(ChamadosDataModel.getIdTipoChamado())));
                    chamados.setIdStatusChamado(cursor.getInt(cursor.getColumnIndex(ChamadosDataModel.getIdStatusChamado())));

                    String auxDataConf = cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getConfirmacaoData_chamado()));
                    Date dataConf = inputFormat.parse(auxDataConf);
                    chamados.setConfirmacao_Data(dataConf);

                    chamados.setConfirmacao_Horas(Time.valueOf(cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getConfirmacaoHora_chamado()))));

                    String auxDataFin = cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getFinalizacaoData_chamado()));
                    Date dataFin = inputFormat.parse(auxDataFin);
                    chamados.setFinalizacao_Data(dataFin);

                    chamados.setFinalizacao_horas(Time.valueOf(cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getFinalizacaoHora_chamado()))));
                    chamados.setJustificativa(cursor.getString(cursor.getColumnIndex(ChamadosDataModel.getJustificativa())));

                    cliente.setId(cursor.getInt(cursor.getColumnIndex(ClienteDataModel.getIdCliente())));
                    cliente.setNome(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getNomeCliente())));
                    cliente.seteMail(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getEmailCliente())));
                    cliente.setLogin(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getLoginCliente())));
                    cliente.setSenha(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getSenhaCliente())));
                    cliente.setTelefone(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getTelefoneCliente())));
                    cliente.setCelular(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getCelularCliente())));
                    cliente.setRoteador(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getEquipamentoCliente())));
                    cliente.setCabeamento(cursor.getString(cursor.getColumnIndex(ClienteDataModel.getCaboCliente())));

                    endereco.setId(cursor.getInt(cursor.getColumnIndex(EnderecoDataModel.getIdEndereco())));
                    endereco.setRua(cursor.getString(cursor.getColumnIndex(EnderecoDataModel.getRuaEndereco())));
                    endereco.setBairro(cursor.getString(cursor.getColumnIndex(EnderecoDataModel.getBairroEndereco())));
                    endereco.setNumero(cursor.getString(cursor.getColumnIndex(EnderecoDataModel.getNumeroEdenreco())));
                    endereco.setCep(cursor.getString(cursor.getColumnIndex(EnderecoDataModel.getCepEndereco())));
                    endereco.setComplemento(cursor.getString(cursor.getColumnIndex(EnderecoDataModel.getComplementoEndereco())));
                    endereco.setReferencia(cursor.getString(cursor.getColumnIndex(EnderecoDataModel.getReferenciaEndereco())));

                    chamados.setClientVO(cliente);
                    cliente.setEnderecoVO(endereco);
                    lista.add(chamados);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());

            return lista;
        }
        cursor.close();
        return lista;
    }

}
