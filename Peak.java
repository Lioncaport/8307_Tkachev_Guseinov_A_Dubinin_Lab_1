public class Peak {
    private char[][] State;
    private Peak Parent_Node;
    private String Action;
    private int Depth;

    public Peak(){
        State = new char[3][3];
        Parent_Node = null;
        Depth = 0;
    }

    public Peak(char[][] state, Peak parent_Node, String action, int depth) {
        this();
        State = state;
        Parent_Node = parent_Node;
        Action = action;
        Depth = depth;
    }

    public char[][] getState() {
        return State;
    }

    public Peak getParent_Node() {
        return Parent_Node;
    }

    public String getAction() {
        return Action;
    }

    public int getDepth() {
        return Depth;
    }

    public void setParent_Node(Peak parent_Node) {
        Parent_Node = parent_Node;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setActionT2(String action) {
        if (action == "Вправо") {
            Action = "Влево";
        }
        else if (action == "Влево") {
            Action = "Вправо";
        }
        else if (action == "Вниз") {
            Action = "Вверх";
        }
        else {
            Action = "Вниз";
        }
    }

    public void setDepth(int depth) {
        Depth = depth;
    }

    public void printPeak(){
        for (int i = 0; i < State.length; i++) {
            for (int j = 0; j < State[0].length; j++) {
                System.out.print(State[i][j] + " ");
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }
}
