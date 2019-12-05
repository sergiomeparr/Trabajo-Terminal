package com.java.bean.modelo;

public class Rankeo {
    private int idComentario;
    private int Ranking;
    private String Comentario;
    private int idpaciente;
    private int idhospital;
    
    public Rankeo(){}
    
    public int getIdRanking(){
        return idComentario;
    }
    
    public void setIdRanking(int idcomentario){
        this.idComentario = idcomentario;
    }
    
    public int getRankeo(){
        return Ranking;
    }
    
    public void setRankeo(int Ranking){
        this.Ranking = Ranking;
    }
    
    public String getComentario(){
        return Comentario;
    }
    
    public void setComentario(String Comentario){
        this.Comentario = Comentario;
    }
    
    public int getIdPaciente(){
        return idpaciente;
    }
    
    public void setIdPaciente(int idpaciente){
        this.idpaciente = idpaciente;
    }
    
    public int getIdHospital(){
        return idhospital;
    }
    
    public void setIdHospital(int idhospital){
        this.idhospital = idhospital;
    }
}
