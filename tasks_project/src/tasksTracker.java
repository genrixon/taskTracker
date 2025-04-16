import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
class Tasks {
    private ArrayList<String> taskList = new ArrayList<>();
    private ArrayList<String> taskListInProgress = new ArrayList<>();
    private ArrayList<String> taskListDone = new ArrayList<>();

    public ArrayList<String> getTaskListInProgress() {
        return taskListInProgress;
    }
    public ArrayList<String> getTaskListDone() {
        return taskListDone;
    }
    public ArrayList<String> getTaskList() {
        return taskList;
    }
    public void addTask(String input) {
        taskList.add(input);
    }
    public void taskUpdater(int index, String input) {
        taskList.set(index, input);
    }

}

class Choice {
    int idCounter;
    public void switcher(Tasks tasksFolder) {

        System.out.println("Это список задач.");
        Scanner sc = new Scanner(System.in, "ibm866");
        while (true) {
            System.out.println("Add - Добавить задачу");
            System.out.println("Update - Обновить задачу");
            System.out.println("Progress - пометить как выполняемую задачу");
            System.out.println("Done - пометить как выполненную задачу");
            System.out.println("Delete - Удалить задачу");
            System.out.println("List - Посмотреть список задач");
            System.out.println("Exit - Выйти");
            String commandInput = sc.nextLine();
            if (commandInput.equalsIgnoreCase("exit")) {
                break;
            } else {
                switch (commandInput.toLowerCase().trim()) {
                    case "add" :
                        idCounter ++;
                        System.out.println("Введите задачу: ");
                        tasksFolder.addTask("id" + idCounter + " " + sc.nextLine());
                        break;
                    case "update" :
                        System.out.println("Введите номер задачи: ");
                        int indexToUpdate = sc.nextInt() - 1;
                        System.out.println("Введите новую задачу: ");
                        tasksFolder.taskUpdater(indexToUpdate, sc.nextLine());
                        break;
                    case "progress":
                        System.out.println("Введите номер задачи: ");
                        String taskToMove = tasksFolder.getTaskList().remove(sc.nextInt() - 1);
                        tasksFolder.getTaskListInProgress().add(taskToMove);
                        break;
                    case "done":
                        System.out.println("Введите номер задачи: ");
                        String taskToMove2 = tasksFolder.getTaskListInProgress().remove(sc.nextInt() - 1);
                        tasksFolder.getTaskListDone().add(taskToMove2);
                        break;
                    case "delete" :
                        System.out.println("Введите номер задачи: ");
                        tasksFolder.getTaskList().remove(sc.nextInt() - 1);
                        if (tasksFolder.getTaskList().isEmpty()) {
                            System.out.println("Список пуст!");
                        }
                        break;
                    case "list":
                        System.out.println("Какой список вам показать? ");
                        String listType = sc.nextLine();
                        switch (listType.toLowerCase().trim()) {
                            case "todo":
                                for (String task : tasksFolder.getTaskList()) {
                                    System.out.println(task);
                                }
                                break;
                            case "done":
                                for (String task : tasksFolder.getTaskListDone()) {
                                    System.out.println(task);
                                }
                                break;
                            case "in progress":
                                for (String task : tasksFolder.getTaskListInProgress()) {
                                    System.out.println(task);
                                }
                                break;
                            case "all":
                                System.out.println("Список задач: ");
                                for (String task : tasksFolder.getTaskList()) {
                                    System.out.println(task);
                                }
                                System.out.println("Список задач в процессе: ");
                                for (String task : tasksFolder.getTaskListInProgress()) {
                                    System.out.println(task);
                                }
                                System.out.println("Список выполненных задач: ");
                                for (String task : tasksFolder.getTaskListDone()) {
                                    System.out.println(task);
                                }
                                break;
                            default:
                                System.out.println("Неверное значение!");
                                break;
                        }
                }
            }
        }
        sc.close();
    }
}

class FileSaver {
    public void saveTasks(ArrayList<String> list, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String task : list) {
                bw.write(task);
                bw.newLine();
            }
            System.out.println("Задачи были сохранены в " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}

class taskTracker {
    public static void main(String[] args) {
        FileSaver fileSaver = new FileSaver();
        Tasks tasksFolder = new Tasks();
        Choice choice = new Choice();
        choice.switcher(tasksFolder);
        String fileName = "tasks.txt";
        fileSaver.saveTasks(tasksFolder.getTaskList(), fileName);
    }
}