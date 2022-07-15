package view.title;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import view.states.GameStateManager;
import view.states.PlayState;
import view.main.Camera;
import view.main.GamePanel;
import view.utils.ImageSplitter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Quản lý việc load map và render các ô trong bản đồ.
 *
 * Bản đồ gồm nhiều layer (TileMapNorm hoặc TileMapObject) chồng lên nhau.
 * Mỗi layer gồm 50x50 block
 * Mỗi block kích thước 48x48, là hình 16x16 scale x3
 *
 * TileMapNorm: layer các ô bình thường.
 * TileMapObject: các ô render không cho người chơi, con vật đi qua.
 *
 */
public class TileManager {

    GamePanel gp;
    PlayState ps;
    private final Camera camera;
    public ArrayList<TileMap> tm;
    private final ArrayList<BufferedImage> imgList;

    public TileManager(GamePanel gp, PlayState ps, view.main.Camera camera) {
        this.gp = gp;
        this.ps = ps;
        this.camera = camera;
        tm = new ArrayList<>(50);
        this.imgList = new ArrayList<>();
        loadMapNew ("/map3.xml");
    }

    private void loadMapNew (String xmlPath) {
        String imagePath;
        int width = 0;
        int height = 0;
        int tileWidth;
        int tileHeight;
        int tileColumns;
        int layers = 0;
        ImageSplitter ci;

        String[] data = new String[10];

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse((Objects.requireNonNull(getClass().getResourceAsStream(xmlPath))));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            Element eElement = (Element) node;

            imagePath = eElement.getAttribute("name");
            tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));
            tileColumns =  Integer.parseInt(eElement.getAttribute("columns"));

            int columns = tileColumns;
            String file = imagePath;

            for (int i = 0; i < list.getLength(); i++) {
                Element e = (Element) list.item(i);
                imagePath = e.getAttribute("name");
                ci = new ImageSplitter(GameStateManager.gp, "/" + imagePath, tileWidth, tileHeight, 0);
                this.imgList.addAll(ci.getArrayList());
                System.out.println("Arrlist: add " + this.imgList.size());
            }

            list = doc.getElementsByTagName("layer");
            layers = list.getLength();

            for(int i = 0; i < layers; i++) {
                node = list.item(i);
                eElement = (Element) node;
                if(i <= 0) {
                    width = Integer.parseInt(eElement.getAttribute("width"));
                    height = Integer.parseInt(eElement.getAttribute("height"));
                }

                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();

                if(i >= 1) {
                    tm.add(new TileMapNorm(data[i], this.imgList, width, height, gp.titleSize, gp.titleSize, tileColumns));
                } else {
                    tm.add(new TileMapObj(data[i], this.imgList, width, height, gp.titleSize, gp.titleSize, tileColumns));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw (Graphics2D g2) {
        for (TileMap t : tm) {
            t.render(g2, camera.getBounds());
        }
    }
}

