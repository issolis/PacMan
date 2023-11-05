import java.awt.event.KeyEvent;

public class pacman {
    private Integer vidas;
    private Boolean enter;

    public pacman() {
        this.vidas = 3;
        this.enter = false;
    }

    public void keyPressed(KeyEvent e) {
        Integer key = e.getKeyCode();

        if (key == KeyEvent.VK_ENTER) {
            this.enter = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        Integer key = e.getKeyCode();

        if (key == KeyEvent.VK_ENTER) {
            this.enter = false;
        }
    }
    
    public Integer getVidas() {
        return vidas;
    }

    public void setVidas(Integer vidas) {
        this.vidas = vidas;
    }

    public Boolean getEnter() {
        return enter;
    }

    public void setEnter(Boolean enter) {
        this.enter = enter;
    }

    
}
