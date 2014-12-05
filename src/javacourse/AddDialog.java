package javacourse;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 添加学生信息对话框类
 *
 * @author Crazy Urus
 * @version 1.0
 */
public class AddDialog extends BaseWindow implements ActionListener {

    private final String[] labelText = {"学号", "姓名", "电话", "E-mail", "年龄", "学院", "性别"};
    private final String[] radioText = {"男", "女"};
    private final String[] btnText = {"添加", "重置", "关闭"};
    private final String[] college = {"材料科学与工程学院", "交通学院", "管理学院", "机电工程学院", "能源与动力工程学院", "土木工程与建筑学院", "汽车工程学院", "资源与环境工程学院", "信息工程学院", "计算机科学与技术学院", "自动化学院", "航运学院", "物流工程学院", "理学院", "化学化工与生命科学学院", "经济学院", "艺术与设计学院", "外国语学院", "文法学院", "政治与行政学院", "国际教育学院", "马克思主义学院", "网络/继续教育学院", "职业技术学院", "体育部"};

    private final JDialog dialog;
    private final MainFrame parent;

    private final JTextField[] text = new JTextField[labelText.length - 3];
    private final JButton[] btn = new JButton[btnText.length];
    private final JRadioButton[] radio = new JRadioButton[radioText.length];
    private final JComboBox combo = new JComboBox(college);
    private final ButtonGroup group = new ButtonGroup();
    private final SpinnerModel model = new SpinnerNumberModel(0, 0, 100, 1);
    private final JSpinner spinner = new JSpinner(model);

    /**
     * 构造函数
     *
     * @param main 主窗体对象
     */
    @SuppressWarnings("")
    public AddDialog(MainFrame main) {

        this.parent = main;

        /* 创建对话框 */
        window = new JDialog(parent.frame, "添加学生信息", false);
        dialog = (JDialog) window;

        /* 初始化对话框 */
        dialog.setSize(400, 270);
        dialog.setLayout(new GridLayout(8, 1));
        this.init();

        /* 添加控件 */
        this.addLabel();
        this.addSpinner();
        this.addCombo();
        this.addRadio();
        this.addButton();
    }

    /**
     * 添加标签
     */
    private void addLabel() {
        JPanel labelPanel;
        for (int i = 0; i < text.length; ++i) {
            text[i] = new JTextField();
            labelPanel = new JPanel(new GridLayout(1, 2));
            labelPanel.add(new JLabel(labelText[i] + "："));
            labelPanel.add(text[i]);
            dialog.add(labelPanel);
        }
        text[0].setDocument(new NumberLenghtLimited(13));
        text[2].setDocument(new NumberLenghtLimited(11));
    }

    /**
     * 添加组合框
     */
    private void addCombo() {
        JPanel labelPanel = new JPanel(new GridLayout(1, 2));
        labelPanel.add(new JLabel(labelText[labelText.length - 2] + "："));
        labelPanel.add(combo);
        dialog.add(labelPanel);
    }

    /**
     * 添加微调框
     */
    private void addSpinner() {
        JPanel labelPanel = new JPanel(new GridLayout(1, 2));
        labelPanel.add(new JLabel(labelText[labelText.length - 3] + "："));
        labelPanel.add(spinner);
        dialog.add(labelPanel);
    }

    /**
     * 添加单选框
     */
    private void addRadio() {
        JPanel radioPanel = new JPanel(new GridLayout(1, 2));
        JPanel labelPanel = new JPanel(new GridLayout(1, 2));
        labelPanel.add(new JLabel(labelText[labelText.length - 1] + "："));
        for (int i = 0; i < radio.length; ++i) {
            radio[i] = new JRadioButton(radioText[i], false);
            group.add(radio[i]);
            radioPanel.add(radio[i]);
        }
        labelPanel.add(radioPanel);
        dialog.add(labelPanel);
    }

    /**
     * 添加按钮
     */
    private void addButton() {
        JPanel btnPanel = new JPanel(new GridLayout(1, 3));
        for (int i = 0; i < btn.length; ++i) {
            btn[i] = new JButton(btnText[i]);
            btnPanel.add(btn[i]);
            btn[i].addActionListener(this);
        }
        dialog.add(btnPanel);
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
            this.submitBtnClick();
        } else if (o == btn[1]) {
            this.resetBtnClick();
        } else if (o == btn[2]) {
            this.close();
        }
    }

    /**
     * 提交按钮点击事件
     */
    private void submitBtnClick() {
        if (isComplete()) {
            Student s = new Student(text[0].getText(), text[1].getText(), (Integer) spinner.getValue(), text[2].getText(), text[3].getText(), combo.getSelectedItem().toString(), radio[1].isSelected());
            StudentInfoManagment.addStudentInfo(parent.table, s);
            this.resetBtnClick();
        } else {
            MessageBox.show("填写的信息不完整！");
        }
    }

    /**
     * 重置按钮点击事件
     */
    private void resetBtnClick() {
        for (JTextField t : text) {
            t.setText("");
        }
        group.clearSelection();
        combo.setSelectedIndex(0);
        spinner.setValue(0);
    }

    /**
     * 判断有效性
     *
     * @return 是否填写完整
     */
    private boolean isComplete() {
        return (radio[0].isSelected() || radio[1].isSelected()) && !text[0].getText().equals("") && !text[1].getText().equals("") && !text[2].getText().equals("") && !text[3].getText().equals("") && (Integer) spinner.getValue() != 0;
    }

}
