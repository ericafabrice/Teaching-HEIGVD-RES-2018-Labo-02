/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.res.labs.roulette.net.protocol;

/**
 *
 * @author Fabrice Mbassi
 */
public class ByeCommandResponse {
   

    private String status;
    private int nbCmd;

    public ByeCommandResponse() {
    }

    public ByeCommandResponse(String status, int nbCmd) {
        this.status = status;
        this.nbCmd = nbCmd;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
     public int getNumberOfCommands() {
        return nbCmd;
    }
     
     public void setNumberOfCommands(int numberOfCommands) {
        this.nbCmd = nbCmd;
    }

}
