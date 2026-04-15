package requests;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    private String user;
    private List<LPRequest> comanda = new ArrayList<>();

    public OrderRequest() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<LPRequest> getComanda() {
        return comanda;
    }

    public void setComanda(List<LPRequest> comanda) {
        this.comanda = comanda;
    }

    public static class LPRequest {
        private int quantitat;
        private String identificador;

        public LPRequest() {
        }

        public int getQuantitat() {
            return quantitat;
        }

        public void setQuantitat(int quantitat) {
            this.quantitat = quantitat;
        }

        public String getIdentificador() {
            return identificador;
        }

        public void setIdentificador(String identificador) {
            this.identificador = identificador;
        }
    }
}
