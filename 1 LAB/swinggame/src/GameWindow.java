import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
public class GameWindow extends JPanel {
    Image map;
    ImageMaker data;
    int x = 0, y = 0, px = 16*4, py = 0, dWL, jumpSpeed = -20, blocksize = 8, mapscale = 4, pgridx = 0, pgridy = 0, level = 1;
    float speed = 0, gravity = 1.5F;
    boolean isLeft = false, isRight = false, isJump = false, jumpLock = true;
    Action left, right, hleft, hright, jump;
    InputStream inputStream;
    Timer playerT;
    BufferedImage im, player1, player2, player;
    InputStream jsonFile;
    ImageMaker imake;
    BufferedImage map2;
    void update(){
        level++;
        if(level == 4){
            JOptionPane.showMessageDialog(null, "Laimeta");
            System.exit(0);
        }
        inputStream = getClass().getResourceAsStream(level + ".png");
        im = null;
        try {
            im = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        jsonFile = getClass().getResourceAsStream(level + ".json");
        if(level==1) {
            imake = new ImageMaker(im, 7, 7);
        }else{
            imake = new ImageMaker(im, 6, 7);
        }
        try {
            map2 = imake.buildImage(jsonFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        data = imake;
        this.map = map2.getScaledInstance(map2.getWidth()*mapscale, map2.getHeight()*mapscale, Image.SCALE_DEFAULT);
        x = 0;
        y = 0;
        px = 16*4;
        py = 0;
        dWL = 0;
        jumpSpeed = -20;
        blocksize = 8;
        mapscale = 4;
        pgridx = 0;
        pgridy = 0;
         speed = 0;
         gravity = 1.5F;
         isLeft = false;
         isRight = false;
         isJump = false;
         jumpLock = true;
    }
    public GameWindow(int num){
        inputStream = getClass().getResourceAsStream("player.png");
        BufferedImage pl;
        try {
            pl = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        player1 = new BufferedImage(16, 16, pl.getType());
        player2 = new BufferedImage(16, 16, pl.getType());
        Graphics g = player1.getGraphics();
        g.drawImage(pl, 0, 0, 16, 16, 0, 0, 16 ,16, null);
        g.dispose();
        g = player2.getGraphics();
        g.drawImage(pl, 0, 0, 16, 16, 17, 0, 32, 16, null);
        g.dispose();
        player = player1;
        inputStream = getClass().getResourceAsStream(num + ".png");
        im = null;
        try {
            im = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        jsonFile = getClass().getResourceAsStream(num + ".json");
        imake = new ImageMaker(im, 7, 7);
        try {
            map2 = imake.buildImage(jsonFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        data = imake;
        left = new Left();
        right = new Right();
        hleft = new HLeft();
        hright = new HRight();
        jump = new Jump();
        dWL = getWidth() / 3;
        this.map = map2.getScaledInstance(map2.getWidth()*mapscale, map2.getHeight()*mapscale, Image.SCALE_DEFAULT);
        playerT = new Timer(200, (e) -> {
            if(player == player1){
                player = player2;
            }else{
                player = player1;
            }
        });
        Timer t = new Timer(16, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while((double)px/(double)getWidth()>0.666&&x+px<data.grid.length * 16 * mapscale - (getWidth() / 3)){
                   px--;
                   x++;
                }
                while((double)px/(double)getWidth()<0.333&&(double)x>0){
                    px++;
                    x--;
                }
                if(isJump && !jumpLock){
                    speed = jumpSpeed;
                    jumpLock = true;
                    py--;
                }
                if(py>=getHeight()-blocksize*mapscale){
                    py=getHeight()-blocksize*mapscale;
                    speed = 0;
                    isJump = false;
                    jumpLock = false;
                }
                if(py<0){
                    py = 0;
                }
                speed += (int) gravity;
                pgridx = (px+x)/(16*mapscale);
                pgridy = (py+y + blocksize*mapscale)/(16*mapscale);
                int otherx = (px+x+blocksize*mapscale)/(16*mapscale);
                if(py<getHeight()-blocksize*mapscale){
                    py+=(int)speed;
                    pgridy = (py+y + blocksize*mapscale)/(16*mapscale);
                    if(pgridy>=data.grid[0].length){
                        JOptionPane.showMessageDialog(null, "L");
                        System.exit(0);
                    }
                    if(data.grid[pgridx][pgridy]==1||data.grid[otherx][pgridy]==1){
                        while(data.grid[pgridx][pgridy]==1||data.grid[otherx][pgridy]==1){
                            py--;
                            pgridy = (py+y + blocksize*mapscale)/(16*mapscale);
                        }
                        speed = 0;
                        jumpLock = false;
                        isJump = false;
                    }
                }

                pgridy = (py+y + blocksize*mapscale)/(16*mapscale);
                //if fell
                if(pgridy>=data.grid[0].length){
                    JOptionPane.showMessageDialog(null, "L");
                    System.exit(0);
                }
                //right bound
                while(otherx >= data.grid.length){
                    px--;
                    otherx = (px+x+blocksize*mapscale)/(16*mapscale);
                }
                //if on solid block
                while(data.grid[pgridx][pgridy]==1 || data.grid[otherx][pgridy]==1){
                    py--;
                    pgridy = (py+y)/(16*mapscale);
                    speed = 0;
                    jumpLock = false;
                    isJump = false;
                }
                //horizontal motion
                if(isLeft && !isRight){
                    while((px + x +blocksize*mapscale + 4) / (16*mapscale) >= data.grid.length){
                        px--;
                    }
                    if(data.grid[(px + x +blocksize*mapscale + 4) / (16*mapscale)][pgridy]!=1) {
                        px += 4;
                    }
                } else if (!isLeft && isRight) {
                    while((px + x - 4) / (16*mapscale) >= data.grid.length){
                        px++;
                    }
                    if(data.grid[(px + x - 4) / (16*mapscale)][pgridy]!=1) {
                        px -= 4;
                    }
                }
                //terminal velocity
                if(speed>15) {
                    speed = 15;
                }
                //left bound
                if(px<0) {
                    px = 0;
                }
                //win state
                if(data.grid[(px+x)/(16*mapscale)][(py+y)/(16*mapscale)]==2||data.grid[(px+x+blocksize*mapscale)/(16*mapscale)][(py+y)/(16*mapscale)]==2||
                data.grid[(px+x)/(16*mapscale)][(py+y+blocksize*mapscale)/(16*mapscale)]==2||data.grid[(px+x+blocksize*mapscale)/(16*mapscale)][(py+y+blocksize*mapscale)/(16*mapscale)]==2) {
                    JOptionPane.showMessageDialog(null, "W");
                    update();
                }
                //halt stuttering
                Toolkit.getDefaultToolkit().sync();
                //refresh screen
                repaint();
            }
        });
        getInputMap().put(KeyStroke.getKeyStroke("pressed RIGHT"), "s move left");
        getActionMap().put("s move left", left);
        getInputMap().put(KeyStroke.getKeyStroke("pressed LEFT"), "s move right");
        getActionMap().put("s move right", right);
        getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "h move left");
        getActionMap().put("h move left", hleft);
        getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "h move right");
        getActionMap().put("h move right", hright);
        getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "Jump");
        getActionMap().put("Jump", jump);
        t.start();
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(map, -x, y, null);
        g.drawImage(player.getScaledInstance(player.getWidth() * mapscale / 2, player.getHeight() * mapscale / 2, player.getType()), px, py, null);
    }
    public class Left extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            isLeft=true;
            playerT.start();
        }
    }
    public class Right extends  AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            isRight = true;
            playerT.start();
        }
    }
    public class HRight extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            isRight = false;
            playerT.stop();
            player = player1;
        }
    }
    public class HLeft extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            isLeft = false;
            playerT.stop();
            player = player1;
        }
    }
    public class Jump extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!isJump) {
                isJump = true;
            }
        }
    }
}