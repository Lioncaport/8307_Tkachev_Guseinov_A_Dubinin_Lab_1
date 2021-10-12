import java.util.ArrayList;
import java.util.Stack;

public class Tree1 {
    private Stack<Peak> nodes; // список ожидающих вершин
    private ArrayList<Peak> PeakList; // список пройденных вершин
    private int countRS; // количество повторных вершин
    private char[][] startState = {{'6', ' ', '8'}, {'5', '2', '1'}, {'4', '3', '7'}};
    private char[][] endState = {{'1', '2', '3'}, {'8', ' ', '4'}, {'7', '6', '5'}};

    public Peak getFirstNode()
    {
        return nodes.peek();
    }

    public int getCountRS() {
        return countRS;
    }

    // дерево решений
    public Tree1() {
        Peak peak = new Peak(startState, null, ' ', 0);
        nodes = new Stack<>();
        PeakList = new ArrayList<>();
        countRS = 0;
        nodes.add(peak);
        PeakList.add(peak);
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

    // проверка на целевое состояние
    public boolean GoalTest(char[][] checkState) {
        boolean key = false;
        for (int i = 0; i < checkState.length; i++) {
            for (int j = 0; j < checkState[0].length; j++) {
                if (checkState[i][j] == endState[i][j]) key = true;
                else {
                    key = false;
                    i = checkState.length;
                    j = checkState[0].length;
                }
            }
        }
        return key;
    }

    // проверка на повторную вершину
    public boolean CheckState(char[][] checkState) {
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
    public void Expand(Peak peak) {
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
            if (CheckState(newState)) {
                Peak newPeak = new Peak(newState,peak,'U',peak.getDepth()+1);
                nodes.push(newPeak);
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
            if (CheckState(newState)) {
                Peak newPeak = new Peak(newState,peak,'D',peak.getDepth()+1);
                nodes.push(newPeak);
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
            if (CheckState(newState)) {
                Peak newPeak = new Peak(newState,peak,'L',peak.getDepth()+1);
                nodes.push(newPeak);
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
            if (CheckState(newState)) {
                Peak newPeak = new Peak(newState,peak,'R',peak.getDepth()+1);
                nodes.push(newPeak);
                PeakList.add(newPeak);
            }
            else {
                countRS++;
            }
        }
    }

    // поиск решения
    public Peak GeneralSearch() {
        Peak peak = new Peak();
        boolean key = true;
        while (key) {
            if (nodes.isEmpty()) {
                // решений нет
                key = false;
                peak = null;
                continue;
            }
            peak = nodes.pop();
            boolean ok = true;
            while (ok == true) {
                // ограничение в глубину
                if (peak.getDepth() > 23)
                {
                    if (!nodes.isEmpty()) peak = nodes.pop();
                    else
                    {
                        ok = false;
                        key = false;
                        peak = null;
                        return peak;
                    }
                }
                else
                {
                    ok = false;
                }
            }
            // выравниваем список пройденных вершин под глубину текущей
            for (int i = 0; i < PeakList.size(); i++) {
                if (PeakList.get(i).getDepth() > peak.getDepth())
                {
                    PeakList.remove(i);
                }
            }
            // проверка на целевое состояние
            if (GoalTest(peak.getState())) {
                key = false;
            } else {
                // раскрытие вершины
                Expand(peak);
            }
        }
        // возвращаем конечную вершину, которая является решением
        return peak;
    }
}

// 1. берём вершину из стека вершин
// 2. проверка на целевое состояние:
//    * если успешно - возвращаем/сохраняем вершину
//    * если неуспешно:
//      3. проверка на состояние:
//      * если true - добавляем состояние в список проверенных и выполняем п.4
//      * есди false - п.1
// 4. раскрываем вершину - генерируем потомков (вершины) и добавляем их в стек