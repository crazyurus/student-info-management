package com.crazyurus.sim;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 查找学生信息对话框类
 *
 * @author Crazy Urus
 * @version 1.0.5
 */
public class FindDialog extends BaseWindow implements ActionListener {

    private final String[] btnText = {"查找", "关闭"};

    private final JDialog dialog;
    private final MainFrame parent;

    private final JLabel label = new JLabel("请输入学生姓名：");
    private final JTextField text = new JTextField();
    private final JButton[] btn = new JButton[2];
    private final JProgressBar progress = new JProgressBar();

    /**
     * 构造函数
     *
     * @param main 主窗体对象
     */
    public FindDialog(MainFrame main) {

        this.parent = main;

        /* 创建对话框 */
        window = new JDialog(parent.frame, "查找学生信息", false);
        dialog = (JDialog) window;

        /* 初始化对话框 */
        dialog.setSize(260, 135);
        dialog.setLayout(new GridLayout(3, 1));
        this.init();

        /* 添加控件 */
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(label);
        panel.add(text);
        dialog.add(panel);
        this.initProgress();
        this.addButton();
    }

    /**
     * 添加按钮
     */
    private void addButton() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        for (int i = 0; i < btn.length; ++i) {
            btn[i] = new JButton(btnText[i]);
            panel.add(btn[i]);
            btn[i].addActionListener(this);
        }
        dialog.add(panel);
    }

    /**
     * 初始化进度条
     */
    private void initProgress() {
        progress.setOrientation(JProgressBar.HORIZONTAL);
        progress.setStringPainted(true);
        dialog.add(progress);
    }

    /**
     * 关闭对话框
     */
    @Override
    public void close() {
        dialog.dispose();
    }

    /**
     * 事件监听
     *
     * @param e 事件
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btn[0]) {
            this.findBtnClick();
        } else if (o == btn[1]) {
            this.close();
        }
    }

    /**
     * 查找按钮点击事件
     */
    private void findBtnClick() {
        if (isComplete()) {
            if (StudentInfoManagment.findStudentInfo(parent.table, progress, text.getText())) {
                //this.close();
            } else {
                MessageBox.show("未找到该学生的信息！");
            }
        } else {
            MessageBox.show("你还没有输入学生姓名！");
        }

    }

    /**
     * 判断有效性
     *
     * @return 是否填写完整
     */
    private boolean isComplete() {
        return !text.getText().equals("");
    }

}
