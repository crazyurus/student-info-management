package com.crazyurus.sim;

import javax.swing.JOptionPane;

/**
 * 信息提示对话框
 *
 * @version 1.0.0
 * @author Crazy Urus
 */
public final class MessageBox {

    private final static String title = "学生信息管理系统";

    /**
     * 显示普通对话框
     *
     * @param msg 提示信息
     * @param title
     */
    public static void msg(String msg, String title) {
        JOptionPane.showMessageDialog(null, msg, title,
                JOptionPane.PLAIN_MESSAGE);
    }

    public static void msg(String msg) {
        msg(msg, MessageBox.title);
    }

    /**
     * 显示提示对话框
     *
     * @param msg 提示信息
     * @param title
     */
    public static void show(String msg, String title) {
        JOptionPane.showMessageDialog(null, msg, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void show(String msg) {
        show(msg, MessageBox.title);
    }

    /**
     * 显示确认对话框
     *
     * @param msg 提示信息
     * @param title
     * @return 状态
     */
    public static boolean confirm(String msg, String title) {
        return JOptionPane.showConfirmDialog(null, msg, title,
                JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION;
    }

    public static boolean confirm(String msg) {
        return confirm(msg, MessageBox.title);
    }

}
