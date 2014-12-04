package javacourse;

import java.util.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;

/**
 * 学生信息管理类
 *
 * @author Crazy Urus
 * @version 1.0
 */
public final class StudentInfoManagment {

    private final static HashMap<String, Student> s = new HashMap<>();
    private static File file = new File("C:\\myfile.data");

    /**
     * 从文件加载学生信息
     *
     * @param file 文件
     * @param l 表格控件
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public static void loadStudentInfo(File file, DefaultTableModel l) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream ois;
        try (FileInputStream fis = new FileInputStream(file)) {
            ois = new ObjectInputStream(fis);
            HashMap<String, Student> temp = (HashMap<String, Student>) ois.readObject();
            for (String key : temp.keySet()) {
                Student t = temp.get(key);
                s.put(key, t);
                showSingle(l, t);
            }
        }
        StudentInfoManagment.file = file;
        ois.close();
    }

    /**
     * 保存学生信息到文件
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void saveStudentInfo() throws FileNotFoundException, IOException {
        ObjectOutputStream oos;
        try (FileOutputStream fos = new FileOutputStream(file)) {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(s);
        }
        oos.close();
    }

    /**
     * 清除列表中显示的信息
     *
     * @param l 列表组
     */
    public static void clearStudentInfo(DefaultTableModel l) {
        for (int i = 0; i < l.getRowCount(); ++i) {
            l.removeRow(i);
        }
    }

    /**
     * 在列表中显示学生信息
     *
     * @param l 表格控件
     */
    public static void showStudentInfo(DefaultTableModel l) {
        clearStudentInfo(l);
        for (String key : s.keySet()) {
            Student e = s.get(key);
            showSingle(l, e);
        }
    }

    /**
     * 添加学生信息
     *
     * @param e 学生
     */
    public static void addStudentInfo(Student e) {
        s.put(e.getName(), e);
    }

    /**
     * 查找学生信息
     *
     * @param l 表格控件
     * @param name 姓名
     * @return 是否找到
     */
    public static boolean findStudentInfo(DefaultTableModel l, String name) {
        boolean isFind = false;
        clearStudentInfo(l);
        for (String key : s.keySet()) {
            Student e = s.get(key);
            if (name.equals(e.getName())) {
                showSingle(l, e);
                isFind = true;
            }
        }
        return isFind;
    }

    /**
     * 向列表添加单个学生信息
     *
     * @param l 表格控件
     * @param e 学生
     */
    private static void showSingle(DefaultTableModel l, Student e) {
        int index = l.getRowCount();
        l.addRow(e.toStringArray(index + 1));
    }

}
