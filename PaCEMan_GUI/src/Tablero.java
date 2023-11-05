import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Tablero extends JPanel implements ActionListener {
    private Timer timer;
    private String estado = "inicio"; // inicio, jugando, pausa, gameover, ganaste
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);
    private final Font tittleFont = new Font("Verdana", Font.BOLD, 50);

    private pacman pacMan;
    
    private Short[][] map = new Short[15][15];
    private final Integer BLOCK_SIZE = 24;
    private final Integer N_BLOCKS = 15;
    private final Integer SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE; // 360 * 360

    // Imagenes
    private Image heart, ghost;
    private Image up, down, left, right;

    /*
     * 1 : wall
     * 2 : regular pill
     */
    private final Short[][] levelData = {
        
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 1
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 2
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 3
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 4
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 5
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 6
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 7
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 8
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 9
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 10
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 11
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 12
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 13
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 14
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 15

    };

    public Tablero() {
        addKeyListener(new TAdapter());
        loadImages();
        setBackground(Color.black);
        pacMan = new pacman();
        //addKeyListener(new TAdapter());
        //setFocusable(true);
        //initGame();
    }

    public void loadImages() {
        down = new ImageIcon("src/resources/down.gif").getImage();
        up = new ImageIcon("src/resources/up.gif").getImage();
        left = new ImageIcon("src/resources/left.gif").getImage();
        right = new ImageIcon("src/resources/right.gif").getImage();
        ghost = new ImageIcon("src/resources/ghost.gif").getImage();
        heart = new ImageIcon("src/resources/heart.png").getImage();
    }

    private class TAdapter extends KeyAdapter {
        public void keyRelased(KeyEvent e) {
            pacMan.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            pacMan.keyPressed(e);
        }
    }

    public void loadMap() {
        try {
            
        } catch (Exception e) {
            System.out.println("Error loading map");
        }
    }   

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g; // Convetimos el grafico en tipo 2D
        Toolkit.getDefaultToolkit().sync(); // Sincroniza el grafico


        switch (estado) {
            case "inicio":
            {
                g2d.setColor(Color.white);

                g2d.setFont(tittleFont);
                FontMetrics metrics = g2d.getFontMetrics(tittleFont);
                Integer posX = (SCREEN_SIZE - metrics.stringWidth("PaCEMan")) / 2;
                Integer posY = SCREEN_SIZE / 3;
                g2d.drawString("PaCEMan", posX, posY);

                g2d.setFont(smallFont);
                metrics = g2d.getFontMetrics(smallFont);
                posX = (SCREEN_SIZE - metrics.stringWidth("Presione[Enter] para jugar")) / 2;
                posY = SCREEN_SIZE / 2;
                g2d.drawString("Presione[Enter] para jugar", posX, posY);

                posX = (SCREEN_SIZE - metrics.stringWidth("Presione[V] para visualizar una partida")) / 2;
                posY = SCREEN_SIZE / 2 + 30;
                g2d.drawString("Presione[V] para visualizar una partida", posX, posY);

                //Carga y mostrar GIF 
                try {
                    ImageIcon img = new ImageIcon(this.getClass().getResource("resources/inicioRedimensionadoSinFondo.gif"));
                    Image image = img.getImage();
                    g2d.drawImage(image, 25, -50, this);
                    System.out.println("Gif loaded");
                    break;
                } catch (Exception e) {
                    System.out.println("Error loading gif");
                }

                break;
            }

            case "jugando":
            {

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        requestFocus();

        if (estado.equals("inicio") && pacMan.getEnter()) {
            estado = "jugando";
        }
    }
}
