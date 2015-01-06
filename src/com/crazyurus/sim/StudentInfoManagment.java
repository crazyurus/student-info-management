package com.crazyurus.sim;

import java.util.*;
import java.io.*;

import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

/**
 * 学生信息管理类
 *
 * @author Crazy Urus
 * @version 1.1.0
 */
public final class StudentInfoManagment {

    private final static HashMap<String, Student> s = new HashMap<>();
    private static File file = new File("D:\\myfile.data");

    /**
     * 从文件加载学生信息
     *
     * @param file 文件
     * @param l 表格控件
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static void loadStudentInfo(File file, DefaultTableModel l)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream ois;
        try (FileInputStream fis = new FileInputStream(file)) {
            ois = new ObjectInputStream(fis);
            HashMap<String, Student> temp = (HashMap<String, Student>) ois
                    .readObject();
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
    public static void saveStudentInfo() throws FileNotFoundException,
            IOException {
        ObjectOutputStream oos;
        try (FileOutputStream fos = new FileOutputStream(file)) {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(s);
        }
        oos.close();
    }

    /**
     * 删除学生信息
     *
     * @param sno 学号
     */
    public static void deleteStudentInfo(String sno) {
        s.remove(sno);
    }

    public static void deleteStudentInfo(DefaultTableModel l, String sno, int index) {
        l.removeRow(index);
        deleteStudentInfo(sno);
    }

    /**
     * 清除列表中显示的信息
     *
     * @param l 列表组
     */
    public static void clearStudentInfo(DefaultTableModel l) {
        int num = l.getRowCount();
        for (int i = num - 1; i >= 0; --i) {
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
            Student e = getStudentInfo(key);
            showSingle(l, e);
        }
    }

    /**
     * 添加学生信息
     *
     * @param l 表格控件
     * @param e 学生
     */
    public static void addStudentInfo(DefaultTableModel l, Student e) {
        s.put(e.getSNO(), e);
        showSingle(l, e);
    }

    /**
     * 获取学生信息
     *
     * @param e 学生学号
     * @return 学生
     */
    public static Student getStudentInfo(String sno) {
        return s.get(sno);
    }

    /**
     * 查找学生信息
     *
     * @param l 表格控件
     * @param p 进度条控件
     * @param name 姓名
     * @return 是否找到
     */
    public static boolean findStudentInfo(DefaultTableModel l, JProgressBar p,
            String name) {
        boolean isFind = false;
        int i = 1;
        p.setMinimum(0);
        p.setMaximum(s.keySet().size() + 5);
        clearStudentInfo(l);
        p.setValue(5);
        for (String key : s.keySet()) {
            Student e = getStudentInfo(key);
            p.setValue(5 + i++);
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
