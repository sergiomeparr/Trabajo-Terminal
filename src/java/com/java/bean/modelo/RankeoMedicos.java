package com.java.bean.modelo;

public class RankeoMedicos {
    private int idrankingmedicos;
    private int Ranking;
    private String Comentario;
    private int idmedico;
    private int idusuario;
    private int idhospital;
    
    public RankeoMedicos(){}
    
    public int getIdRankingMedicos(){
        return idrankingmedicos;
    }
    
    public void setIdRankingMedicos(int idrankingmedicos){
        this.idrankingmedicos = idrankingmedicos;
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
    
    public int getIdMedico(){
        return idmedico;
    }
    
    public void setIdMedico(int idmedico){
        this.idmedico = idmedico;
    }
    
    public int getIdUsuario(){
        return idusuario;
    }
    
    public void setIdUsuario(int idusuario){
        this.idusuario = idusuario;
    }
    
    public int getIdHospital(){
        return idhospital;
    }
    
    public void setIdHospital(int idhospital){
        this.idhospital = idhospital;
    }
}
