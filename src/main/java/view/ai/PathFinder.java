package view.ai;

import interfaces.controller.ILocatable;
import org.jetbrains.annotations.NotNull;
import view.states.PlayState;
import view.entity.Entity;
import view.entity.GameObject;
import view.main.GamePanel;
import view.math.Vector2f;
import view.title.TileMapObj;

import java.awt.*;
import java.util.ArrayList;

/**
 * PathFinder: Tìm đường đi ngắn nhất trên bản đồ.
 *
 * <p>
 * A* là giải thuật tìm kiếm trong đồ thị,
 * tìm đường đi từ một đỉnh hiện tại đến đỉnh đích có sử dụng hàm
 * để ước lượng khoảng cách hay còn gọi là hàm Heuristic.
 * </p>
 * <p>
 * Từ trạng thái hiện tại A* xây dựng tất cả các đường đi có thể đi dùng hàm ước lược khoảng cách (hàm Heuristic)
 * để đánh giá đường đi tốt nhất có thể đi.
 * Tùy theo mỗi dạng bài khác nhau mà hàm Heuristic sẽ được đánh giá khác nhau.
 * A* luôn tìm được đường đi ngắn nhất nếu tồn tại đường đi như thế.
 * </p>
 * <p>
 * A* lưu giữ một tập các đường đi qua đồ thị,
 * từ đỉnh bắt đầu đến đỉnh kết thúc, tập các đỉnh có thể đi tiếp được lưu trong tập Open.
 * </p>
 * <p>
 * Thứ tự ưu tiên cho một đường đi đươc quyết định bởi hàm Heuristic được đánh giá f(x) = g(x) + h(x)
 * </p>
 * <p>
 * g(x) là chi chi phí của đường đi từ điểm xuất phát cho đến thời điểm hiện tại.
 * h(x) là hàm ước lượng chi phí từ đỉnh hiện tại đến đỉnh đích f(x) thường có giá trị càng thấp thì độ ưu tiên càng cao.
 * </p>
 */
public class PathFinder {
    private final GamePanel gp;
    private PlayState ps;
    private Node[][] node;
    private final ArrayList<Node> openList;
    private final ArrayList<Node> pathList;
    private Node startNode, goalNode, currentNode;

    boolean goalReached = false;
    int step = 0;
    int startX, startY, goalX, goalY;
    int goalRow = 0, goalCol = 0;
    int prevGoalRow = -1, prevGoalCol = -1;
    boolean isInit;

    public PathFinder(GamePanel gp, PlayState ps) {
        this.gp = gp;
        this.ps = ps;
        initialNodes();

        openList = new ArrayList<>();
        pathList = new ArrayList<>();
    }

    private void initialNodes () {
        node = new Node[gp.maxWorldRow][gp.maxWorldCol];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[row][col] = new Node(row, col);

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    private void resetNodes () {
        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[row][col].open = false;
            node[row][col].checked = false;
            node[row][col].solid = false;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    private void getCost(@NotNull Node node) {
        int xDistance = Math.abs(node.column * gp.titleSize - startNode.column * gp.titleSize);
        int yDistance = Math.abs(node.row * gp.titleSize - - startNode.row * gp.titleSize);
        node.gCost = xDistance + yDistance;

        xDistance = Math.abs(node.column * gp.titleSize - goalNode.column * gp.titleSize);
        yDistance = Math.abs(node.row * gp.titleSize - goalNode.row * gp.titleSize);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;
    }

    private void trackThePath () {
        Node current = goalNode;

        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }

    private void openNode (Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    /**
     * Đặt các giá trị start và goal.
     * Gọi 1 lần trước khi dùng serach()
     *
     * @param startX, startY: tọa độ hiện tại
     * @param goalX, goalY: đích
     */
    public void setNodes (int startX, int startY, int goalX, int goalY) {
        isInit = true;
        resetNodes();

        this.startX = startX;
        this.startY = startY;
        this.goalX = goalX;
        this.goalY = goalY;

        int startRow = (startX) / gp.titleSize;
        int startCol = (startY) / gp.titleSize;

        this.goalRow = goalX / gp.titleSize;
        this.goalCol = goalY / gp.titleSize;

        startNode = node[startRow][startCol];
        currentNode = startNode;
        goalNode = node[goalRow][goalCol];
        openList.add(currentNode);

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

            if (TileMapObj.event_blocks[row * TileMapObj.height + col] != null)  {
                node[col][row].solid = true;
            }

            getCost(node[row][col]);

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }

        }
    }

    public void setNodes (Entity entity, GameObject gameObject) {
        this.setNodes(
                (int) entity.getBounds().getCenterX(),
                (int) entity.getBounds().getCenterY(),
                (int) gameObject.getBounds().getCenterX(),
                (int) gameObject.getBounds().getCenterY()
        );
    }

    public void setNodes (ILocatable obj1, ILocatable obj2) {
        this.setNodes(
                (int) obj1.getBounds().getCenterX(),
                (int) obj1.getBounds().getCenterY(),
                (int) obj2.getBounds().getCenterX(),
                (int) obj2.getBounds().getCenterY()
        );
    }

    public void setNodes (ILocatable obj1, int x, int y) {
        this.setNodes(
                (int) obj1.getBounds().getCenterX(),
                (int) obj1.getBounds().getCenterY(),
                x,
                y
        );
    }

    public void setNode (Vector2f start, Vector2f goal) {
        this.setNodes(
                (int) start.x,
                (int) start.y,
                (int) goal.x,
                (int) goal.y
        );
    }

    /**
     * Tìm đường đi ngắn nhất từ startNode đến goalNode.
     * Thuật toán: A* ( khá giống dijkstra nhưng ưu tiên tìm những nút có h(x) thấp hơn )
     *
     * Sử dụng:
     *  - Kết tập trong đối tượng cần tìm đường đi.
     *  - Cần gọi setNode() để set điểm đầu và đich của đường đi trước.
     *  - Gọi pathFindet.search() trong parent.update().
     *
     * @return
     */
    public boolean search () {
        if (isInit && (prevGoalCol != goalCol || prevGoalRow == goalRow))
            while (!goalReached && step < 1000) {
                int col = currentNode.column;
                int row = currentNode.row;

                currentNode.checked = true;
                openList.remove(currentNode);

                int deltaX = Math.abs(goalCol - col);
                int deltaY = Math.abs(goalRow - row);

                // open x
                if (col - 1 >= 0 )
                    openNode(node[col-1][row]);
                if (col + 1 < gp.maxWorldCol)
                    openNode(node[col+1][row]);
                // open y
                if (row + 1 < gp.maxWorldRow)
                    openNode(node[col][row+1]);
                if (row - 1 >= 0 )
                    openNode(node[col][row - 1]);

                int bestNodeIndex = 0;
                int bestNodeFCost = 999;
                for (int i = 0; i < openList.size(); i++ ) {
                    if(openList.get(i).fCost < bestNodeFCost) {
                        bestNodeIndex = i;
                        bestNodeFCost = openList.get(i).fCost;
                    }
                    else if (openList.get(i).fCost == bestNodeFCost) {
                        if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                            bestNodeIndex = i;
                        }
                    }
                }

                if (openList.size() == 0) break;

                currentNode = openList.get(bestNodeIndex);

                if (currentNode == goalNode) {
                    goalReached = true;
                    trackThePath();
                }
                step++;
            }
        return goalReached;
    }

    /**
     * Trả về đường đi ngắn nhất từ startNode đến goalNode.
     * Sử dụng: sau khi gọi setNode() và serach().
     *
     * @return
     */
    public ArrayList<Node> getPathList() {
        return pathList;
    }

    // debug
    public void draw (Graphics2D g2) {
        if (this.getPathList().size() > 0) {
            for (Node step : this.getPathList() ) {
                g2.setColor(Color.red);
                g2.drawRect(
                        (int) Vector2f.getStaticScreenX(step.column * gp.titleSize),
                        (int) Vector2f.getStaticScreenY(step.row * gp.titleSize),
                        gp.titleSize,
                        gp.titleSize
                );
            }
        }
    }

}
