package ai;
public class Node {
    Node parent;//Node cha(Track The Path)
    //Tọa độ của node
    public int col;
    public int row;
    int gCost;//Chi phí đi từ điểm bắt đầu đến Node
    int hCost;//Chi phí đi từ điểm kết thúc đến Node
    int fCost;//Tổng cho chí = gCost + hCost
    boolean solid;//Trạng thái của rắn(không thể đi vào,không thể đi xuyên qua) của Node
    boolean open;// Trạng thái kiểm tra xem Node này đã nằm trong openList hay chưa
    boolean checked;//Trạng thái đã kiểm tra hay chưa

    public Node(int col, int row){
        this.col = col;
        this.row = row;
    }
}
