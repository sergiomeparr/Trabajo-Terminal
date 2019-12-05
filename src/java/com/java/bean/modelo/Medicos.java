package com.java.bean.modelo;

public class Medicos {
    private int id_Medico;
    private String nombreMedico;
    private String aPaternoMedico;
    private String aMaternoMedico;
    private String Especialidad;
    private String Telefonos;       
    private String cedulaprofesional;
    private String Historial;
    private String Email;
    private String foto;
    private int rankeo;
    private int idhospital;
    
    public Medicos(){}
    
    public int getIdMedicos(){
        return id_Medico;
    }
    
    public void setIdMedicos(int id_Medico){
        this.id_Medico = id_Medico;
    }
    
    public String getNombreMedico(){
        return nombreMedico;
    }
    
    public void setNombreMedico(String nombreMedico){
        this.nombreMedico = nombreMedico;
    }
    
    public String getAPaternoMedico(){
        return aPaternoMedico;
    }
    
    public void setAPaternoMedico(String aPaternoMedico){
        this.aPaternoMedico = aPaternoMedico;
    }
    
    public String getAMaternoMedico(){
        return aMaternoMedico;
    }
    
    public void setAMaternoMedico(String aMaternoMedico){
        this.aMaternoMedico = aMaternoMedico;
    }
    
    public String getEspecialidades(){
        return Especialidad;
    }
    
    public void setEspecialidades(String Especialidad){
        this.Especialidad = Especialidad;
    }
    
    public String getTelefonos(){
        return Telefonos;
    }
    
    public void setTelefonos(String Telefonos){
        this.Telefonos = Telefonos;
    }
    
    public String getCedulap(){
        return cedulaprofesional;
    }
    
    public void setCedulap(String cedulaprofesional){
        this.cedulaprofesional = cedulaprofesional;
    }
    
    public String getHistorial(){
        return Historial;
    }
    
    public void setHistorial(String Historial){
        this.Historial = Historial;
    }
    
    public String getEmail(){
        return Email;
    }
    
    public void setEmail(String Email){
        this.Email = Email;
    }
    
    public String getFoto(){
        return foto;
    }
    
    public void setFoto(String foto){
        this.foto = foto;
    }
    
    public int getRankeo(){
        return rankeo;
    }
    
    public void setRankeo(int rankeo){
        this.rankeo = rankeo;
    }
    
    public int getIdHospital(){
        return idhospital;
    }
    
    public void setIdHospital(int idhospital){
        this.idhospital = idhospital;
    }
}
