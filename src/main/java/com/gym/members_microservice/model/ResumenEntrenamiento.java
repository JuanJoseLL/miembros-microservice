// ResumenEntrenamiento.java
package com.gym.members_microservice.model;

public class ResumenEntrenamiento {
    private int totalRepeticiones;
    private int totalPeso;

    public ResumenEntrenamiento actualizar(DatosEntrenamiento dato) {
        this.totalRepeticiones += dato.getRepeticiones();
        this.totalPeso += dato.getPeso();
        return this;
    }

    public int getTotalRepeticiones() {
        return totalRepeticiones;
    }

    public void setTotalRepeticiones(int totalRepeticiones) {
        this.totalRepeticiones = totalRepeticiones;
    }

    public int getTotalPeso() {
        return totalPeso;
    }

    public void setTotalPeso(int totalPeso) {
        this.totalPeso = totalPeso;
    }
}