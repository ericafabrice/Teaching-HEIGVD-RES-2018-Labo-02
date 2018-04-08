/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.res.labs.roulette.net.protocol;

/**
 *
 * @Fabrice Mbassi
 */
public class LoadCommandResponse {
    private String status;
    private int nbStudents;

    public LoadCommandResponse() {}

    public LoadCommandResponse(String status, int nbStudents) {
        this.status = status;
        this.nbStudents = nbStudents;
    }
    
     public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberOfNewStudents() {
        return nbStudents;
    }

    public void setNumberOfNewStudents(int nbStudents) {
        this.nbStudents = nbStudents;
}
}
