package tcc.etec.needful.view.view.datamodel;

public class ChamadosDataModel {

    private final static String tabela = "chamado";

    private final static String id = "id_chamado";
    private final static String idCliente = "id_cliente";
    private final static String dataChamado = "data_do_chamado";
    private final static String horaChamado = "hora_do_chamado";
    private final static String agendamentoHoraChamado = "agendamentoHora_chamado";
    private final static String agendamentoDataChamado = "agendamentoData_chamado";
    private final static String observacaoChamado = "observacao_chamado";
    private final static String idTecnico = "id_tecnico";
    private final static String idTipoChamado = "id_tipo_chamado";
    private final static String idStatusChamado = "id_status_chamado";
    private final static String confirmacaoData_chamado = "confirmacaoData_chamado";
    private final static String confirmacaoHora_chamado = "confirmacaoHora_chamado";
    private final static String finalizacaoData_chamado = "finalizacaoData_chamado";
    private final static String finalizacaoHora_chamado = "finalizacaoHora_chamado";
    private final static String justificativa = "justificativa_chamado";

    private static String comandoSQL = "";

    public static String criarTabela(){

        setComandoSQL("CREATE TABLE "+ getTABELA());
        setComandoSQL(getComandoSQL() + "(");
       setComandoSQL(getComandoSQL() + id + " INTEGER, ");
       setComandoSQL(getComandoSQL() + idCliente + " INTEGER, ");
       setComandoSQL(getComandoSQL() + dataChamado + " DATE, ");
       setComandoSQL(getComandoSQL() + horaChamado + " TIME, ");
       setComandoSQL(getComandoSQL() + agendamentoHoraChamado + " DATE, ");
       setComandoSQL(getComandoSQL() + agendamentoDataChamado + " TIME, ");
       setComandoSQL(getComandoSQL() + observacaoChamado +  " TEXT, ");
       setComandoSQL(getComandoSQL() + idTecnico + " INTEGER, ");
       setComandoSQL(getComandoSQL() + idTipoChamado + " INTEGER, ");
       setComandoSQL(getComandoSQL() + idStatusChamado + " INTEGER, ");
       setComandoSQL(getComandoSQL() + confirmacaoData_chamado + " DATE, ");
       setComandoSQL(getComandoSQL() + confirmacaoHora_chamado + " TIME, ");
       setComandoSQL(getComandoSQL() + finalizacaoData_chamado + " DATE, ");
       setComandoSQL(getComandoSQL() + finalizacaoHora_chamado + " TIME, ");
       setComandoSQL(getComandoSQL() + justificativa + " TEXT, ");
       setComandoSQL(getComandoSQL() + " FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente), ");
       setComandoSQL(getComandoSQL() + " FOREIGN KEY (id_tecnico) REFERENCES tecnico (id_tecnico), ");
       setComandoSQL(getComandoSQL() + " FOREIGN KEY (id_status_chamado) REFERENCES status (id_status)");
       setComandoSQL(getComandoSQL() + " );");

        return getComandoSQL();
    }

    public static String getAgendamentoDataChamado() {
        return agendamentoDataChamado;
    }

    public static String getConfirmacaoData_chamado() {
        return confirmacaoData_chamado;
    }

    public static String getConfirmacaoHora_chamado() {
        return confirmacaoHora_chamado;
    }

    public static String getFinalizacaoData_chamado() {
        return finalizacaoData_chamado;
    }

    public static String getFinalizacaoHora_chamado() {
        return finalizacaoHora_chamado;
    }

    public static String getJustificativa() {
        return justificativa;
    }

    public static String getTabela() {
        return tabela;
    }

    public static String getTABELA() {
        return tabela;
    }

    public static String getComandoSQL() {
        return comandoSQL;
    }

    public static void setComandoSQL(String comandoSQL) {
        ChamadosDataModel.comandoSQL = comandoSQL;
    }

    public static String getId() {
        return id;
    }

    public static String getIdCliente() {
        return idCliente;
    }

    public static String getDataChamado() {
        return dataChamado;
    }

    public static String getHoraChamado() {
        return horaChamado;
    }

    public static String getAgendamentoHoraChamado() {
        return agendamentoHoraChamado;
    }

    public static String getObservacaoChamado() {
        return observacaoChamado;
    }

    public static String getIdTecnico() {
        return idTecnico;
    }

    public static String getIdTipoChamado() {
        return idTipoChamado;
    }

    public static String getIdStatusChamado() {
        return idStatusChamado;
    }
}
