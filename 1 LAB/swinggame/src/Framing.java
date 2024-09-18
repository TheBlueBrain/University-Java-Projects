import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
public class Framing extends JFrame {
    JButton dumpData;
    JLabel victory;
    BufferedImage map;
    BufferedImage Tile, im;
    JLabel gamemap;
    ImageMaker imk;
    int[][] data;
    int tileNum, Wnum;
    int xpos, ypos;
    Framing(String FileNumber, int length){
        this.setSize(1000, 777);
        this.setVisible(true);
        data = new int[length][7];
        for(int x = 0; x<length;x++){
            for(int i = 0; i < 7; i++){
                data[x][i]=-1;
            }
        }
        InputStream inputStream = getClass().getResourceAsStream(FileNumber + ".png");
        try {
            im = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        map = new BufferedImage(16*length, 16 * 7, im.getType());
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel l = new JLabel();
        int W = im.getWidth()/16;
        int H = im.getHeight()/16;
        l.setIcon(new ImageIcon(im.getScaledInstance(im.getWidth()*2, im.getHeight()*2, Image.SCALE_DEFAULT)));
        l.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                xpos = e.getX()/(2*16);
                ypos = (e.getY())/(2*16);
                l.setText("Position: " + e.getX() + ", " + e.getY() + "  Chunk number: " + (xpos + ypos * 7));
            }
        });
        l.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tile = imk.parts[ypos * im.getWidth()/16 + xpos];
                tileNum = ypos * im.getWidth()/16 + xpos;
            }
        });
        imk=new ImageMaker(im, im.getHeight()/16, im.getWidth()/16);
        l.setHorizontalTextPosition(JLabel.CENTER);
        l.setVerticalTextPosition(JLabel.CENTER);
        l.setForeground(Color.RED);
        JPanel p = new JPanel();
        p.setLayout(null);
        l.setBounds(713, 63, 224,224 );
        gamemap = new JLabel();
        gamemap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Graphics g = map.getGraphics();
                g.drawImage(Tile, 16*(e.getX()/16), 16*(e.getY()/16), null);
                g.dispose();
                data[e.getX()/16][e.getY()/16] = tileNum;
                repaint();
            }
        });
        gamemap.setPreferredSize(new Dimension(length*16, 16*7));
        gamemap.setIcon(new ImageIcon(map));
        gamemap.setBounds(0, 0, length * 16, 7*16);
        gamemap.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane editor = new JScrollPane();
        editor.setLayout(new ScrollPaneLayout());
        editor.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        editor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editor.setViewportView(gamemap);
        editor.setPreferredSize(gamemap.getPreferredSize());
        editor.setBounds(0, 0, 630, 7*16);
        victory = new JLabel("Wining tile");
        victory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                victory.setIcon(new ImageIcon(Tile));
                Wnum = tileNum;
            }
        });
        dumpData = new JButton("Create map");
        dumpData.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imk.createMap(length, data, Wnum, Integer.parseInt(FileNumber));
                JOptionPane.showMessageDialog(null, "File created");
                System.exit(0);
            }
        });
        dumpData.setBounds(60, 550, 200, 25);
        victory.setBounds(650, 350, 350, 350);
        p.add(dumpData);
        p.add(victory);
        p.add(l);
        editor.setViewportView(gamemap);
        p.add(editor);
        p.setSize(1000, 700);
        add(p);
    }
}
