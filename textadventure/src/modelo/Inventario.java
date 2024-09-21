package modelo;

public class Inventario {
    private int usuarioId;
    private int itemId;

    public Inventario(int usuarioId, int itemId) {
        this.usuarioId = usuarioId;
        this.itemId = itemId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
