import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int value = 0;
        while (value != -1) {
            System.out.println("Поиск: 1 - ограниченный в глубину, 2 - двунаправленный, 3 - выход");
            System.out.print("Ввод: ");
            value = new Scanner(System.in).nextInt();
            switch (value) {
                case 1: {
                    int hDepth = 0;
                    System.out.print("Ограничение: ");
                    hDepth = new Scanner(System.in).nextInt();

                    Tree1 tree1 = new Tree1();
                    Peak initState = tree1.getFirstNode();
                    System.out.println("\nНачальное состояние:");
                    initState.printPeak();

                    System.out.println("Список действий:");
                    System.out.println("Вправо Влево Вниз Вверх\n");

                    int step = 0;
                    System.out.println("Режим: 1 - автоматический, 2 - ручной");
                    System.out.print("Ввод: ");
                    step = new Scanner(System.in).nextInt();
                    System.out.println("");
                    if (step != 1 && step != 2) step = 1;

                    long sTime = System.currentTimeMillis();
                    Peak solution = tree1.GeneralSearch(step, hDepth);
                    if (solution != null)
                    {
                        System.out.println("Целевое состояние:");
                        solution.printPeak();
                        System.out.println("Количество шагов:");
                        System.out.println(solution.getDepth());
                        System.out.println("Повторные вершины:");
                        System.out.println(tree1.getCountRS() + "\n");
                    }
                    else
                    {
                        System.out.println("Решения нет\n");
                    }
                    long eTime = System.currentTimeMillis();
                    System.out.println("Ёмкостная сложность: " + tree1.getCountSum());
                    System.out.println("Временная сложность: " + String.format("%.3f", (float)(eTime - sTime) / 1000)  + " c\n");

                    break;
                }
                case 2: {
                    Tree2 tree2 = new Tree2();
                    Peak initState = tree2.getFirstNode();
                    System.out.println("\nНачальное состояние:");
                    initState.printPeak();

                    System.out.println("Список действий:");
                    System.out.println("Вверх Вниз Влево Вправо\n");

                    int step = 0;
                    System.out.println("Режим: 1 - автоматический, 2 - ручной");
                    System.out.print("Ввод: ");
                    step = new Scanner(System.in).nextInt();
                    System.out.println("");
                    if (step != 1 && step != 2) step = 1;

                    long sTime = System.currentTimeMillis();
                    Peak solution = tree2.GeneralSearch(step);
                    if (solution != null)
                    {
                        System.out.println("Целевое состояние:");
                        solution.printPeak();
                        System.out.println("Количество шагов:");
                        System.out.println(solution.getDepth());
                        System.out.println("Повторные вершины:");
                        System.out.println(tree2.getCountRS() + "\n");
                    }
                    else
                    {
                        System.out.println("Решения нет\n");
                    }
                    long eTime = System.currentTimeMillis();
                    System.out.println("Ёмкостная сложность: " + tree2.getCountSum());
                    System.out.println("Временная сложность: " + String.format("%.3f", (float)(eTime - sTime) / 1000)  + " c\n");
                    break;
                }
                default : value = -1;
            }
        }
    }
}
