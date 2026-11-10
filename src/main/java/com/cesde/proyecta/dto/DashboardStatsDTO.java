package com.cesde.proyecta.dto;

public class DashboardStatsDTO {

    private int totalRetosActivos;
    private int equiposPostulados;
    private int proyectosEnDesarrollo;
    private int retosCompletados;
    private Double tasaExito;

    public DashboardStatsDTO() {
    }

    public DashboardStatsDTO(int totalRetosActivos,
                             int equiposPostulados,
                             int proyectosEnDesarrollo,
                             int retosCompletados,
                             Double tasaExito) {
        this.totalRetosActivos = totalRetosActivos;
        this.equiposPostulados = equiposPostulados;
        this.proyectosEnDesarrollo = proyectosEnDesarrollo;
        this.retosCompletados = retosCompletados;
        this.tasaExito = tasaExito;
    }

    public int getTotalRetosActivos() {
        return totalRetosActivos;
    }

    public void setTotalRetosActivos(int totalRetosActivos) {
        this.totalRetosActivos = totalRetosActivos;
    }

    public int getEquiposPostulados() {
        return equiposPostulados;
    }

    public void setEquiposPostulados(int equiposPostulados) {
        this.equiposPostulados = equiposPostulados;
    }

    public int getProyectosEnDesarrollo() {
        return proyectosEnDesarrollo;
    }

    public void setProyectosEnDesarrollo(int proyectosEnDesarrollo) {
        this.proyectosEnDesarrollo = proyectosEnDesarrollo;
    }

    public int getRetosCompletados() {
        return retosCompletados;
    }

    public void setRetosCompletados(int retosCompletados) {
        this.retosCompletados = retosCompletados;
    }

    public Double getTasaExito() {
        return tasaExito;
    }

    public void setTasaExito(Double tasaExito) {
        this.tasaExito = tasaExito;
    }
}

