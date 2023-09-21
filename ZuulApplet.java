import java.awt. Color;
import java.awt. Font;
import java.awt. Graphics;
import java.awt. Image;
import java.awt.event. ActionListener;
import java.awt.event. ActionEvent;
import java.net. URL;
import javax.swing. ImageIcon;
import javax.swing. JApplet;
import javax.swing. JComponent;
import javax.swing. JFrame;
import javax.swing. JScrollPane;
import javax.swing. JTextArea;
import javax.swing. JTextField;
import java.util. Date;
/**
 * Class zuulApplet - a View + Controller for zuul
 * 
 * @author William H. Hooper
 * @version 2018-04-16
 */
public class ZuulApplet 
extends JApplet
implements ActionListener
{
    private JTextArea output;
    private JTextField input;
    private JScrollPane scrollPane;
    Game game;
    String history;
    Image image;
    MusicPlayer mplayer;

    /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * has been loaded into the system. It is always called before the first 
     * time that the start method is called.
     */
    public void init()
    {
        output = new JTextArea(1, 1);
        output.setEditable(false);

        scrollPane = new JScrollPane(output);
        scrollPane.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        input = new JTextField();
        input.addActionListener(this);
        add(input);
    }

    /**
     * Called by the browser or applet viewer to inform this JApplet that it 
     * should start its execution. It is called after the init method and 
     * each time the JApplet is revisited in a Web page. 
     */
    public void start()
    {
        setLayout(null);
        setBackground(new Color(50, 0, 50));
        scrollPane.setBounds(10, getHeight()/2, 
            getWidth()-20, getHeight()/2-40);
        input.setBounds(10, getHeight()-30, getWidth()-20, 20);

        game = new Game();
        getAudio();
        getImage();
        history = game.readMessages();
        output.setText(history);
        input.requestFocusInWindow();
        repaint();
    }

    /** 
     * Called by the browser or applet viewer to inform this JApplet that
     * it should stop its execution. It is called when the Web page that
     * contains this JApplet has been replaced by another page, and also
     * just before the JApplet is to be destroyed. 
     */
    public void stop()
    {
        // provide any code that needs to be run when page
        // is replaced by another page or before JApplet is destroyed 
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == input)
        {
            String inputLine = input.getText();
            input.setText("");

            history += "> " + inputLine + "\n";
            game.processInput(inputLine);
            getAudio();
            getImage();

            history += game.readMessages();
            int window = Math.max(history.length() - 5000, 0);
            int length = Math.min(history.length(), 5000);
            output.setText(history.substring(window, length));
            output.setCaretPosition(length);

            repaint();
        }
    }

    private void getImage()
    {
        String filename = game.getImage();
        if(filename == null) {
            return;
        }
        
        Class<? extends JApplet> appClass = null;
        URL url = null;
        ImageIcon icon = null;
        try {
            appClass = getClass();
            url = appClass.getResource(filename);
            icon = new ImageIcon(url);
            image = icon.getImage();  
        } catch (Exception e) { 
            System.err.println("GetImage(): " + e);
            System.err.println("filename = " + filename);
            System.err.println("appClass = " + appClass);
            System.err.println("     url = " + url);
            System.err.println("    icon = " + icon);
            image = null;
        }
    }
    
    private void getAudio()
    {
        if(mplayer == null) {
            mplayer = new MusicPlayer();
        }
        String filename = game.getAudio();
        if(filename == null) {
            return;
        }
        
        mplayer.startPlaying(filename);
    }

    /**
     * Paint method for applet.
     * 
     * @param  g   the Graphics object for this applet
     */
    public void paint(Graphics g)
    {
        g.fillRect(0, 0, getWidth(), getHeight());
        output.repaint();
        input.repaint();
        paintImage(g);
    }

    private void paintImage(Graphics g)
    {
        getImage();
        if(image == null) {
            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.BOLD, 24));
            g.drawString("???", getWidth() / 2 - 12, getHeight() / 3 - 12);
            return;
        }

        // image original height and width
        int ow = image.getWidth(null);
        int oh = image.getHeight(null);

        // fit the image to the window
        int fw = Math.min(ow, getWidth() - 50);
        int fh = Math.min(oh, getHeight()/2 - 50);

        // scale the image to correct proportion
        int w =  Math.min(fw, ow * fh / oh);
        int h =  Math.min(fh, oh * fw / ow);

        // center the image in at the top of the screen
        int x = (getWidth() - w) / 2;
        int y = (getHeight()/2 - h) / 2;

        g.drawImage(image, x, y, w, h, this);
    }

    public static void play() {
        int width = 500;
        int height = 600;
        ZuulApplet applet = new ZuulApplet();

        //         String windowTitle = applet.getClass().getName();
        //         System.out.println(windowTitle + " created " + new Date());
        JFrame frame = new JFrame("zuul");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height + 20);
        applet.setSize(width, height);
        frame.add(applet);
        applet.init();      // simulate browser call(1)
        applet.start();      // simulate browser call(2)
        frame.setVisible(true);
    } 

    public static void main(String[] argv) 
    { 
        play(); 
    }
}
