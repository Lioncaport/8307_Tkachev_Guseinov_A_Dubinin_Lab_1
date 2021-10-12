import java.util.ArrayList;
import java.util.LinkedList;

public class Tree2 {
    private LinkedList<Peak> nodes1; // список ожидающих вершин 1-ого дерева
    private LinkedList<Peak> nodes2; // список ожидающих вершин 2-ого дерева
    private ArrayList<Peak> PeakList1; // список пройденных вершин 1-ого дерева
    private ArrayList<Peak> PeakList2; // список пройденных вершин 2-ого дерева
    private int countRS; // количество повторных вершин
    private char[][] startState = {{'6', ' ', '8'}, {'5', '2', '1'}, {'4', '3', '7'}};
    private char[][] endState = {{'1', '2', '3'}, {'8', ' ', '4'}, {'7', '6', '5'}};

    // дерево решени
    public Tree2() {
        Peak peak1 = new Peak(startState, null, ' ', 0);
        Peak peak2 = new Peak(endState, null, ' ', 0);
        nodes1 = new LinkedList<>();
        nodes2 = new LinkedList<>();
        PeakList1 = new ArrayList<>();
        PeakList2 = new ArrayList<>();
        countRS = 0;
        nodes1.add(peak1);
        nodes2.add(peak2);
        PeakList1.add(peak1);
        PeakList2.add(peak2);
    }

    public Peak getFirstNode()
    {
        return nodes1.peek();
    }

    public int getCountRS() {
        return countRS;
    }

    // копирует состояние
    public char[][] copy(char [][] b){
        char[][] a = new char[b.length][b[0].length];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                a[i][j] = b[i][j];
            }
        }
        return a;
    }

    // проверка вершины на совпадение с листьями дерева
    public Peak GoalTest(char[][] checkState, LinkedList <Peak> nodes) {
        Peak peak = new Peak();
        boolean key = false;
        for (int i = 0; i < nodes.size(); i++) {
            key = true;
            for (int j = 0; j < checkState.length; j++) {
                for (int k = 0; k < checkState[0].length; k++) {
                    key = key && (checkState[j][k] == nodes.get(i).getState()[j][k]);
                }
            }
            if (key) {
                peak = nodes.get(i);
                i = nodes.size();
            }
            else {
                peak = null;
            }
        }
        return peak;
    }

    // проверка на повторную вершину заданного дерева
    public boolean CheckState(char[][] checkState, ArrayList<Peak> PeakList) {
        boolean key = false;
        for (int i = 0; i < PeakList.size(); i++) {
            key = true;
            for (int j = 0; j < checkState.length; j++) {
                for (int k = 0; k < checkState[0].length; k++) {
                    key = key && (checkState[j][k] == PeakList.get(i).getState()[j][k]);
                }
            }
            if (key) {
                i = PeakList.size();
            }
        }
        return !key;
    }

    // функция развертки вершины
    public void Expand(Peak peak, LinkedList <Peak> nodes, ArrayList <Peak> PeakList) {
        int[] pos = new int[]{0, 0};
        for (int i = 0; i < peak.getState().length; i++)
            for (int j = 0; j < peak.getState()[0].length; j++) {
                if (peak.getState()[i][j] == ' ') {
                    pos[0] = i;
                    pos[1] = j;
                    i = peak.getState().length;
                    j = peak.getState()[0].length;
                }
            }
        if (pos[0] + 1 < 3) {
            char[][] newState = copy(peak.getState());
            newState[pos[0]][pos[1]] = newState[pos[0]+1][pos[1]];
            newState[pos[0]+1][pos[1]] = ' ';
            if (CheckState(newState, PeakList)) {
                Peak newPeak = new Peak(newState,peak,'U',peak.getDepth()+1);
                nodes.add(newPeak);
                PeakList.add(newPeak);
            }
            else {
                countRS++;
            }
        }
        if (pos[0] - 1 >= 0) {
            char[][] newState = copy(peak.getState());
            newState[pos[0]][pos[1]] = newState[pos[0]-1][pos[1]];
            newState[pos[0]-1][pos[1]] = ' ';
            if (CheckState(newState, PeakList)) {
                Peak newPeak = new Peak(newState,peak,'D',peak.getDepth()+1);
                nodes.add(newPeak);
                PeakList.add(newPeak);
            }
            else {
                countRS++;
            }
        }
        if (pos[1] + 1 < 3) {
            char[][] newState = copy(peak.getState());
            newState[pos[0]][pos[1]] = newState[pos[0]][pos[1]+1];
            newState[pos[0]][pos[1]+1] = ' ';
            if (CheckState(newState, PeakList)) {
                Peak newPeak = new Peak(newState,peak,'L',peak.getDepth()+1);
                nodes.add(newPeak);
                PeakList.add(newPeak);
            }
            else {
                countRS++;
            }
        }
        if (pos[1] - 1 >= 0) {
            char[][] newState = copy(peak.getState());
            newState[pos[0]][pos[1]] = newState[pos[0]][pos[1]-1];
            newState[pos[0]][pos[1]-1] = ' ';
            if (CheckState(newState, PeakList)) {
                Peak newPeak = new Peak(newState,peak,'R',peak.getDepth()+1);
                nodes.add(newPeak);
                PeakList.add(newPeak);
            }
            else {
                countRS++;
            }
        }
    }

    // поиск решения
    public Peak GeneralSearch() {
        Peak peakS = new Peak();
        boolean key = true;
        int cnt = 0; // задаёт порядок просмотра деревьев
        while (key) {
            if (nodes1.isEmpty() || nodes2.isEmpty()) {
                // решений нет
                key = false;
                peakS = null;
            }
            int size1 = nodes1.size();
            int size2 = nodes2.size();
            if (cnt % 2 == 0) {
                // развертка и проверка вершин 1-ого дерева
                for (int i = 0; i < size1; i++) {
                    Peak peak1 = nodes1.poll();
                    for (int j = 0; j < size2; j++) {
                        Peak peak2;
                        // проверка вершины на совпадение с листьями 2-ого дерева
                        if ((peak2 = GoalTest(peak1.getState(), nodes2)) != null) {
                            // объединение путей в решение
                            Peak peakNow = peak2;
                            Peak peakNext = peakNow.getParent_Node();
                            Peak peakPrev = peak1.getParent_Node();
                            if (peakPrev != null) {
                                peakNow.setDepth(peak1.getDepth());
                                peakNow.setAction(peak1.getAction());
                                peakNow.setParent_Node(peakPrev);
                            }
                            while (peakNext != null) {
                                peakPrev = peakNow;
                                peakNow = peakNext;
                                peakNext = peakNow.getParent_Node();
                                peakNow.setParent_Node(peakPrev);
                                peakNow.setDepth(peakPrev.getDepth() + 1);
                            }
                            peakS = peakNow;
                            j = size2;
                            i = size1;
                            key = false;
                        }
                    }
                    if (key != false) {
                        // раскрытие вершины 1-ого дерева
                        Expand(peak1, nodes1, PeakList1);
                    }
                }
            } else {
                // развертка и проверка вершин 2-ого дерева
                for (int i = 0; i < size2; i++) {
                    Peak peak2 = nodes2.poll();
                    for (int j = 0; j < size1; j++) {
                        Peak peak1;
                        // проверка вершины на совпадение с листьями 1-ого дерева
                        if ((peak1 = GoalTest(peak2.getState(), nodes1)) != null) {
                            // объединение путей в решение
                            Peak peakNow = peak2;
                            Peak peakNext = peakNow.getParent_Node();
                            Peak peakPrev = peak1.getParent_Node();
                            if (peakPrev != null) {
                                peakNow.setDepth(peak1.getDepth());
                                peakNow.setAction(peak1.getAction());
                                peakNow.setParent_Node(peakPrev);
                            }
                            while (peakNext != null) {
                                peakPrev = peakNow;
                                peakNow = peakNext;
                                peakNext = peakNow.getParent_Node();
                                peakNow.setParent_Node(peakPrev);
                                peakNow.setDepth(peakPrev.getDepth() + 1);
                            }
                            peakS = peakNow;
                            j = size1;
                            i = size2;
                            key = false;
                        }
                    }
                    if (key != false) {
                        // раскрытие вершины 2-ого дерева
                        Expand(peak2, nodes2, PeakList2);
                    }
                }
            }
            cnt++;
        }
        return peakS;
    }
}
