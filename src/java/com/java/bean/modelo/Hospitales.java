package com.java.bean.modelo;

public class Hospitales {
    private int idHospital;
    private String nombre;
    private String direccion;
    private String foto;
    private String telefono;
    private String pagina;       
    private int rankeo;
    private int verificar;

    public Hospitales(){}
    
    public int getIdHospital(){
        return idHospital;
    }
    
    public void setIdHospital(int idHospital){
        this.idHospital = idHospital;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getDireccion(){
        return direccion;
    }
    
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }
    
    public String getFoto(){
        return foto;
    }
    
    public void setFoto(String foto){
        this.foto = foto;
    }
    
    public String getTelefono(){
        return telefono;
    }
    
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    
    public String getPagina(){
        return pagina;
    }
    
    public void setPagina(String pagina){
        this.pagina = pagina;
    }
    
    public int getRankeo(){
        return rankeo;
    }
    
    public void setRankeo(int rankeo){
        this.rankeo = rankeo;
    }
    
    public int getVerificar() {
        return verificar;
    }

    public void setVerificar(int verificar) {
        this.verificar = verificar;
    }
}
