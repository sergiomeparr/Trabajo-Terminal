package com.java.bean.modelo;

public class ServiciosMedicos {

    private int idServicio;
    private String tipo;
    private String precio;

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
    private int idHospital;
    
    public ServiciosMedicos(){}
    
    public int getIdServicio(){
        return idServicio;
    }
    
    public void setIdServicio(int idServicio){
        this.idServicio = idServicio;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    
    public int getIdHospital(){
        return idHospital;
    }
    
    public void setIdHospital(int idHospital){
        this.idHospital = idHospital;
    }
}
