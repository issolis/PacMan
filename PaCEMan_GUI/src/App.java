import javax.swing.JFrame;

public class App extends JFrame {

    public App() {
        add(new Tablero());
        setTitle("PaCEMan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) throws Exception {
        new App();
        
        System.out.println("Hello, World!");
    }
}
