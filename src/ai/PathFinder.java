package ai;
import java.util.ArrayList;
import main.GamePanel;
public class PathFinder {
    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();// List lưu những node có trạng thái open để kiểm tra
    public ArrayList<Node> pathList = new ArrayList<>();//List lưu đường đi
    Node startNode,goalNode, currentNode;//startNode:Node bắt đầu; goalNode: Node đích đến; currentNode:Node đang xét
    boolean goalReached = false;//Biến kiểm tra xem đến đích chưa 
    int step = 0;//Đếm số lần thực hiện tìm kiếm để đặt giới hạn vòng lặp
    public PathFinder(GamePanel gp){
        this.gp = gp;
        instantiateNodes();
    }
    //KHOI TAO NODE
    public void instantiateNodes(){
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row <gp.maxWorldRow){
            node[col][row] = new Node(col, row);
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row ++;
            }
        }
    }
    //Reset các trạng thái của Node(solid,open,checked,...)
    public void resetNodes(){
        int row =0;
        int col =0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            //THIẾT LẬP LẠI CÁC TRẠNG THÁI
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;      
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row ++;
            }            
        }
        // RESET OTHER SETTING
        openList.clear();
        pathList.clear();
        goalReached = false;
        step =0;       
    }
    //Thiết lập các trạng thái của Node(solid,open,checked,...)
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();
        //THIẾT LẬP NÚT BẮT ĐẦU VÀ MỤC TIÊU
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);
        int col =0;
        int row =0;
        while(col < gp.maxWorldCol && row <gp.maxWorldRow){
            //Cài đặt node không thể đi vào 
            //KIỂM TRA GẠCH
            int tileNum = gp.tileM.mapTileNum[col][row];
            if(gp.tileM.tile[tileNum].collision == true){
                node[col][row].solid = true;
            }
            //KIỂM TRA TƯƠNG TÁC
            // for(int i =0; i<gp.iTile[1].length;i++){
            //     if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible == true){
            //         int itCol = gp.iTile[gp.currentMap][i].worldX/gp.tileSize;
            //         int itRow = gp.iTile[gp.currentMap][i].worldY/gp.tileSize;
            //         node[itCol][itRow].solid = true;
            //     }
            // }
            //ĐẶT CHI PHÍ
            getCost(node[col][row]);
            col++;
            if(col == gp.maxWorldCol){
                col =0;
                row ++;

            }
        }
    }
    //Tính chi phí cho từng Node
    public void getCost(Node node){
        // G COST
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        // H COST
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        // F COST
        node.fCost = node.gCost +node.hCost;
    }
    //Tìm đường đi
    public boolean search(){
        while(goalReached == false && step <500){
            //Game có thể bị treo khi không tìm được đường đi đến đích
            //Nên cần đặt điều kiện đề dừng thuật toán
            //Step > 500=>dừng thuật toán tìm kiếm,nếu không game sẽ bị treo         
            int col = currentNode.col;
            int row = currentNode.row;
            //ĐÁNH DẤU NÚT HIỆN TẠI
            currentNode.checked = true;
            openList.remove(currentNode);
            
            //Đặt điều kiện để tránh lỗi khi trỏ ra ngoài phạm vi của mảng
            //Mở trạng thái open cho các nude trái, phải, trên, dưới để kiểm tra
            //MỞ NÚT LÊN
            if(row -1 >= 0){
                openNode(node[col][row-1]);
            }
            //MỞ NÚT TRÁI
            if(col -1 >= 0){
                openNode(node[col-1][row]);
            }
            //MỞ NÚT XUỐNG
            if(row + 1 < gp.maxWorldRow){
                openNode(node[col][row+1]);
            }
             //MỞ NÚT PHẢI
            if(col + 1 < gp.maxWorldCol){
                openNode(node[col+1][row]);
            }
            //TÌM NÚT TỐT NHẤT
            int bestNodeIndex =0;
            int bestNodefCost = 999;           
            for(int i=0;i<openList.size();i++){
                //KIỂM TRA NẾU CHI PHÍ F ,LẤY NODE CÓ CHI PHÍ ÍT NHẤT
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                //NẾU CHI PHÍ F BẰNG NHAU,THÌ KIỂM KIỂM TRA CHI PHÍ G
                else if(openList.get(i).fCost == bestNodefCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            //NẾU KHÔNG CÓ NÚT TRONG OPENLIST, KẾT THÚC VÒNG ĐẶT
            if(openList.size()==0){
                break;
            }
            //SAU VONG LAP,OPENLIST[bestNodeIndex] LÀ BƯỚC TIẾP THEO(=curentNode)
            currentNode = openList.get(bestNodeIndex);
            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }
    //Thay đổi trạng thái 'open' của NODE
    public void openNode(Node node){
        if(node.open == false && node.checked == false && node.solid == false){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    //Lưu đường đi vào pathList
    public void trackThePath(){
        Node current = goalNode;
        while(current != startNode){
            pathList.add(0,current);
            current = current.parent;
        }
    }
}
