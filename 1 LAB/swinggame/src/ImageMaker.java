import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
public class ImageMaker {
    BufferedImage[] parts;
    int row, col;
    int imageType;
    public int[][] grid;
    long circlex,circley;
    public ImageMaker(BufferedImage image, int row, int col){
        this.row = row;
        this.col = col;
        int cW = image.getWidth()/col, cH = image.getHeight()/row;
        int amount = 0;
        imageType = image.getType();
        parts = new BufferedImage[row * col];
        for(int x = 0; x < row; x++){
            for(int y = 0; y < col; y++){
                parts[x*7 + y] = new BufferedImage(cW, cH, image.getType());
                Graphics2D gr = parts[x * 7 + y].createGraphics();
                gr.drawImage(image, 0, 0, cW, cH , cW * y, cH * x, cW * y + cW, cH * x + cW, null);
                gr.dispose();
            }
        }
    }
    public BufferedImage buildImage(InputStream json) throws IOException, ParseException {
        JSONObject data = (JSONObject) new JSONParser().parse(new InputStreamReader(json));
        Map dimensions = (Map)data.get("size");
        long WinValue = 0;
        WinValue = (long) data.get("win");
        long width = (long) dimensions.get("x");
        Map Circle = (Map)data.get("circle");
        circlex = (long)Circle.get("x");
        circley = (long)Circle.get("y");
        long height = 7;
        grid = new int[(int) width][(int) height];
        BufferedImage map = new BufferedImage((int)width * 16, (int)height * 16, imageType);
        Graphics2D gr = map.createGraphics();
        JSONArray mapData = (JSONArray) data.get("data");
        Iterator coord = mapData.iterator();
        while(coord.hasNext()){
            Map itr = (Map) coord.next();
            long posx = (long)itr.get("x"), posy = (long) itr.get("y"), value = (long) itr.get("value");
            grid[(int)posx][(int)posy] = 1;
            if((int)value==(int)WinValue){
                grid[(int)posx][(int)posy] = 2;
            }
            gr.drawImage(parts[(int)value], (int)posx * 16, (int)posy * 16, null);
        }
        gr.drawOval((int)circlex*16,(int) circley*16, 16,16);
        gr.dispose();
        return map;
    }
    public void createMap(int n, int[][] data, int W, int num){
        JSONObject json = new JSONObject();
        json.put("win", W);
        Map<String, Integer> size = new HashMap<String, Integer>();
        size.put("x", n);
        json.put("size", size);
        JSONArray ja = new JSONArray();
        for(int i = 0; i<n; ++i){
            for(int j = 0; j<7; ++j) {
                Map<String, Integer> tile = new HashMap<String, Integer>();
                if(data[i][j]!=-1){
                        tile.put("x", i);
                        tile.put("y", j);
                        tile.put("value", data[i][j]);
                        ja.add(tile);
                }
            }
        }
        json.put("data", ja);
        String raw = json.toString();
        try {
            FileWriter fw = new FileWriter(num + ".json");
            fw.write(raw);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
