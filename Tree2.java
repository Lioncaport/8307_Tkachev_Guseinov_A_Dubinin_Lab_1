import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Tree2 {
    private LinkedList<Peak> nodes1; // список ожидающих вершин 1-ого дерева
    private LinkedList<Peak> nodes2; // список ожидающих вершин 2-ого дерева
    private ArrayList<Peak> PeakList1; // список пройденных вершин 1-ого дерева
    private ArrayList<Peak> PeakList2; // список пройденных вершин 2-ого дерева
    private int countRS; // количество повторных вершин
    private int countSum; // количество раскрытых вершин
    private char[][] startState = {{'6', ' ', '8'}, {'5', '2', '1'}, {'4', '3', '7'}};
    private char[][] endState = {{'1', '2', '3'}, {'8', ' ', '4'}, {'7', '6', '5'}}; // 23 - глубина решения по варианту
//    private char[][] endState = {{'8', '1', '2'}, {'4', '6', '3'}, {'7', ' ', '5'}}; // 16

    // дерево решени
    public Tree2() {
        Peak peak1 = new Peak(startState, null, " ", 0);
        Peak peak2 = new Peak(endState, null, " ", 0);
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

    public int getCountSum() {
        return countSum;
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
                Peak newPeak = new Peak(newState,peak,"Вверх",peak.getDepth()+1);
                nodes.add(newPeak);
                PeakList.add(newPeak);
                countSum++;
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
                Peak newPeak = new Peak(newState,peak,"Вниз",peak.getDepth()+1);
                nodes.add(newPeak);
                PeakList.add(newPeak);
                countSum++;
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
                Peak newPeak = new Peak(newState,peak,"Влево",peak.getDepth()+1);
                nodes.add(newPeak);
                PeakList.add(newPeak);
                countSum++;
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
                Peak newPeak = new Peak(newState,peak,"Вправо",peak.getDepth()+1);
                nodes.add(newPeak);
                PeakList.add(newPeak);
                countSum++;
            }
            else {
                countRS++;
            }
        }
    }

    // поиск решения
    public Peak GeneralSearch(int step) {
        if (step == 2) {
            System.out.println("Дальше? 1 - да, 2 - выход\n");
        }
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

                    // пошаговый вывод вершин
                    if (step == 2)
                    {
                        System.out.print("Ввод: ");
                        int value = new Scanner(System.in).nextInt();
                        System.out.println("");
                        switch (value)
                        {
                            case 1: {
                                System.out.println("Дерево 1");
                                System.out.println("Глубина: " + peak1.getDepth());
                                System.out.println("Движение: " + peak1.getAction());
                                peak1.printPeak();
                                break;
                            }
                            default: {
                                key = false;
                                peakS = null;
                                return peakS;
                            }
                        }
                    }

                    for (int j = 0; j < size2; j++) {
                        Peak peak2;
                        // проверка вершины на совпадение с листьями 2-ого дерева
                        if ((peak2 = GoalTest(peak1.getState(), nodes2)) != null) {
                            // объединение путей в решение
                            String act1 = " ";
                            Peak peakNow = peak2;
                            Peak peakNext = peakNow.getParent_Node();
                            Peak peakPrev = peak1.getParent_Node();
                            if (peakPrev != null) {
                                peakNow.setDepth(peak1.getDepth());
                                peakNow.setAction(peak1.getAction());
                                peakNow.setParent_Node(peakPrev);
                                act1 = peakNow.getAction();
                                peakNow.setAction(peak1.getAction());
                            }
                            String act2 = " ";
                            while (peakNext != null) {
                                peakPrev = peakNow;
                                peakNow = peakNext;
                                peakNext = peakNow.getParent_Node();
                                peakNow.setParent_Node(peakPrev);
                                peakNow.setDepth(peakPrev.getDepth() + 1);
                                act2 = peakNow.getAction();
                                peakNow.setActionT2(act1);
                                act1 = act2;
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

                    // пошаговый вывод вершин
                    if (step == 2)
                    {
                        System.out.print("Ввод: ");
                        int value = new Scanner(System.in).nextInt();
                        System.out.println("");
                        switch (value)
                        {
                            case 1: {
                                System.out.println("Дерево 2");
                                System.out.println("Глубина: " + peak2.getDepth());
                                System.out.println("Движение: " + peak2.getAction());
                                peak2.printPeak();
                                break;
                            }
                            default: {
                                key = false;
                                peakS = null;
                                return peakS;
                            }
                        }
                    }

                    for (int j = 0; j < size1; j++) {
                        Peak peak1;
                        // проверка вершины на совпадение с листьями 1-ого дерева
                        if ((peak1 = GoalTest(peak2.getState(), nodes1)) != null) {
                            // объединение путей в решение
                            String act1 = " ";
                            Peak peakNow = peak2;
                            Peak peakNext = peakNow.getParent_Node();
                            Peak peakPrev = peak1.getParent_Node();
                            if (peakPrev != null) {
                                peakNow.setDepth(peak1.getDepth());
                                peakNow.setAction(peak1.getAction());
                                peakNow.setParent_Node(peakPrev);
                                act1 = peakNow.getAction();
                                peakNow.setAction(peak1.getAction());
                            }
                            String act2 = " ";
                            while (peakNext != null) {
                                peakPrev = peakNow;
                                peakNow = peakNext;
                                peakNext = peakNow.getParent_Node();
                                peakNow.setParent_Node(peakPrev);
                                peakNow.setDepth(peakPrev.getDepth() + 1);
                                act2 = peakNow.getAction();
                                peakNow.setActionT2(act1);
                                act1 = act2;
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

// 1. берём вершину из очереди вершин первого дерева
// 2. проверка вершины на совпадение с листьями второго дерева
//    * если успешно - сохраняем вершину, выводим решение
//    * если неуспешно - п.3
// 3. раскрываем вершину - генерируем потомков (вершины) и проверяем каждый на повторное состояние:
//    * если успешно - добавляем его в список проверенных (первого дерева) и в очередь
//    * если неуспешно - потомок игнорируется
// 4. п.1 - п.3 выполняются пока не найдено решение и очередь первого дерева не пуста
// 5. Выполняем переходы между деревьями для анализа, пока не найдено решение и обе очереди не пусты