import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int value = 0;
        while (value != -1) {
            System.out.println("1 - в глубину, 2 - двунаправленный, иное - выход");
            System.out.print("Ввод: ");
            value = new Scanner(System.in).nextInt();
            System.out.println("");
            switch (value) {
                case 1: {
                    Tree1 tree1 = new Tree1();
                    Peak initState = tree1.getFirstNode();
                    System.out.println("Начальное состояние:");
                    initState.printPeak();

                    System.out.println("Список действий:");
                    System.out.println("Вправо Влево Вниз Вверх\n");

                    Peak solution = tree1.GeneralSearch();
                    if (solution != null)
                    {
                        System.out.println("Целевое состояние:");
                        solution.printPeak();
                        System.out.println("Количество шагов:");
                        System.out.println(solution.getDepth());
                        System.out.println("Повторные вершины:");
                        System.out.println(tree1.getCountRS() + "\n");

                        ArrayList<Peak> solway = new ArrayList<>();
                        Peak peak = solution;
                        solway.add(peak);
                        for (int i = 0; i < solution.getDepth(); i++) {
                            peak = peak.getParent_Node();
                            solway.add(peak);
                        }

                        System.out.println("Первые 5 действий:");
                        for (int i = solway.size() - 1; (i > 0) && (i > solway.size() - 6); i--) {
                            solway.get(i-1).printPeak();
                        }
                    }
                    else
                    {
                        System.out.println("Решения нет");
                    }
                    break;
                }
                case 2: {
                    Tree2 tree2 = new Tree2();
                    Peak initState = tree2.getFirstNode();
                    System.out.println("Начальное состояние:");
                    initState.printPeak();

                    System.out.println("Список действий:");
                    System.out.println("Вверх Вниз Влево Вправо\n");

                    Peak solution = tree2.GeneralSearch();
                    if (solution != null)
                    {
                        System.out.println("Целевое состояние:");
                        solution.printPeak();
                        System.out.println("Количество шагов:");
                        System.out.println(solution.getDepth());
                        System.out.println("Повторные вершины:");
                        System.out.println(tree2.getCountRS() + "\n");

                        ArrayList<Peak> solway = new ArrayList<>();
                        Peak peak = solution;
                        solway.add(peak);
                        for (int i = 0; i < solution.getDepth(); i++) {
                            peak = peak.getParent_Node();
                            solway.add(peak);
                        }

                        System.out.println("Первые 5 действий:");
                        for (int i = solway.size() - 1; (i > 0) && (i > solway.size() - 6); i--) {
                            solway.get(i-1).printPeak();
                        }
                    }
                    else
                    {
                        System.out.println("Решения нет");
                    }
                    break;
                }
                default : value = -1;
            }
        }
    }
}
