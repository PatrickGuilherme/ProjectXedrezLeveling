
package model;

public class Tabuleiro {
    private static Tabuleiro tabuleiro = new Tabuleiro();
    
    private boolean pequeno = false;
    private boolean medio = false;
    private boolean grande = false;
    String vencedor;
    
    public static Tabuleiro getTabuleiro(){
        return tabuleiro;
    }

    public boolean isPequeno() {
        return pequeno;
    }

    public void setPequeno(boolean pequeno) {
        this.pequeno = pequeno;
    }

    public boolean isMedio() {
        return medio;
    }

    public void setMedio(boolean medio) {
        this.medio = medio;
    }

    public boolean isGrande() {
        return grande;
    }

    public void setGrande(boolean grande) {
        this.grande = grande;
    }

    /**
     * @return the vencedor
     */
    public String getVencedor() {
        return vencedor;
    }

    /**
     * @param vencedor the vencedor to set
     */
    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }
    
}
