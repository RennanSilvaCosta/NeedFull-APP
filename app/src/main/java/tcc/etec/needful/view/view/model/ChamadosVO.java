package tcc.etec.needful.view.view.model;


import java.io.Serializable;
import java.sql.Time;
import java.util.Date;


public class ChamadosVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private int ID;
    private Date data;
    private Time horas;
    private Date agendamento_Data;
    private Time agendamento_horas;
    private Date confirmacao_Data;
    private Time confirmacao_Horas;
    private Date finalizacao_Data;
    private Time finalizacao_horas;
    private String descricao;
    private String tipo;
    private int tipoChamado; //retirar depois
    private int idTecnico;  //retirar depois
    private int idStatusChamado;  //retirar depois
    private String justificativa;

    private ClienteVO clientVO;
    private StatusVO statusVO;
    private TecnicoVO tecnicoVO;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHoras() {
        return horas;
    }

    public void setHoras(Time horas) {
        this.horas = horas;
    }

    public Date getAgendamento_Data() {
        return agendamento_Data;
    }

    public void setAgendamento_Data(Date agendamento_Data) {
        this.agendamento_Data = agendamento_Data;
    }

    public Time getAgendamento_horas() {
        return agendamento_horas;
    }

    public void setAgendamento_horas(Time agendamento_horas) {
        this.agendamento_horas = agendamento_horas;
    }

    public Date getConfirmacao_Data() {
        return confirmacao_Data;
    }

    public void setConfirmacao_Data(Date confirmacao_Data) {
        this.confirmacao_Data = confirmacao_Data;
    }

    public Time getConfirmacao_Horas() {
        return confirmacao_Horas;
    }

    public void setConfirmacao_Horas(Time confirmacao_Horas) {
        this.confirmacao_Horas = confirmacao_Horas;
    }

    public Date getFinalizacao_Data() {
        return finalizacao_Data;
    }

    public void setFinalizacao_Data(Date finalizacao_Data) {
        this.finalizacao_Data = finalizacao_Data;
    }

    public Time getFinalizacao_horas() {
        return finalizacao_horas;
    }

    public void setFinalizacao_horas(Time finalizacao_horas) {
        this.finalizacao_horas = finalizacao_horas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTipoChamado() {
        return tipoChamado;
    }

    public void setTipoChamado(int tipoChamado) {
        this.tipoChamado = tipoChamado;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public int getIdStatusChamado() {
        return idStatusChamado;
    }

    public void setIdStatusChamado(int idStatusChamado) {
        this.idStatusChamado = idStatusChamado;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public ClienteVO getClientVO() {
        return clientVO;
    }

    public void setClientVO(ClienteVO clientVO) {
        this.clientVO = clientVO;
    }

    public StatusVO getStatusVO() {
        return statusVO;
    }

    public void setStatusVO(StatusVO statusVO) {
        this.statusVO = statusVO;
    }

    public TecnicoVO getTecnicoVO() {
        return tecnicoVO;
    }

    public void setTecnicoVO(TecnicoVO tecnicoVO) {
        this.tecnicoVO = tecnicoVO;
    }
}
