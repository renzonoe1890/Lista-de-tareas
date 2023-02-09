import model.Task;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

public class TaskManager {
    private static final String FILENAME = "tasks.txt";
    private ArrayList<Task> tasks = new ArrayList<Task>();

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.loadTasks();
        manager.initUI();
    }

    private void initUI() {
        JFrame frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        final JList taskList = new JList();
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setListData(tasks.toArray());
        content.add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel controls = new JPanel();
        content.add(controls, BorderLayout.SOUTH);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String taskName = JOptionPane.showInputDialog("Task name:");
                if (taskName != null) {
                    Task task = new Task(taskName);
                    tasks.add(task);
                    taskList.setListData(tasks.toArray());
                }
            }
        });
        controls.add(addButton);

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Task task = (Task) taskList.getSelectedValue();
                if (task != null) {
                    String taskName = JOptionPane.showInputDialog("Task name:", task.getName());
                    if (taskName != null) {
                        task.setName(taskName);
                        taskList.repaint();
                    }
                }
            }
        });
        controls.add(editButton);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Task task = (Task) taskList.getSelectedValue();
                if (task != null) {
                    tasks.remove(task);
                    taskList.setListData(tasks.toArray());
                }
            }
        });
        controls.add(removeButton);

        frame.setSize(300, 400);
        frame.setVisible(true);
    }

    private void loadTasks() {
        try {
            Scanner scanner = new Scanner(new File(FILENAME));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = new Task(line);
                tasks.add(task);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + FILENAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveTasks() {
        try {
            PrintWriter writer = new PrintWriter(new File(FILENAME));
            for (Task task : tasks) {
                writer.println(task.getName());
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}