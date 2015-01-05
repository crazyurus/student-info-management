package com.crazyurus.sim;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.filechooser.*;

/**
 * 主窗体类
 * 
 * @author Crazy Urus
 * @version 1.0
 */
public final class MainFrame extends BaseWindow implements ActionListener {

	private final String[] menuInfo = { "功能(F)", "关于(A)" };
	private final String[][] menuItemInfo = {
			{ "加载(L)", "保存(S)", "-", "添加学生(A)", "编辑(E)", "删除(D)", "刷新(R)",
					"查找(F)", "-", "退出(E)" }, { "帮助(H)", "关于(A)" } };
	private final String[] tableColumn = { "序号", "学号", "姓名", "性别", "年龄", "手机",
			"E-mail", "学院" };

	public final JFrame frame;
	private final JMenuBar m_MenuBar = new JMenuBar();
	private final JMenu[] m_Menu = new JMenu[menuInfo.length];
	private final JMenuItem[] m_MenuItem = new JMenuItem[12];
	public final DefaultTableModel table;
	private final JTable m_Table;

	/**
	 * 构造函数
	 */
	public MainFrame() {

		window = new JFrame("学生信息管理系统");
		frame = (JFrame) window;
		try {
			javax.swing.UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		this.table = new DefaultTableModel(null, tableColumn) {
			private static final long serialVersionUID = 1L;

			/**
			 * 控制表格是否可编辑
			 * 
			 * @param row
			 *            行索引
			 * @param column
			 *            列索引
			 * @return 是否可编辑
			 */
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		m_Table = new JTable(table);
		m_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m_Table.setRowSorter(new TableRowSorter<>(table));

		/* 控件对象初始化 */
		this.initMenu();
		this.initTable();

		/* 主窗体位置、字体设置 */
		frame.setSize(720, 540);
		frame.setLayout(new GridLayout(1, 1));
		frame.setJMenuBar(m_MenuBar);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.init();
	}

	/**
	 * 添加一级菜单项
	 */
	private void initMenu() {
		int num = menuInfo.length;
		for (int i = 0; i < num; ++i) {
			m_Menu[i] = new JMenu(menuInfo[i]);
			initMenuItem(i);
			m_MenuBar.add(m_Menu[i]);
		}
	}

	/**
	 * 添加二级菜单项
	 * 
	 * @param index
	 *            一级菜单编号
	 */
	private void initMenuItem(int index) {
		int pre = 0;
		int num = menuItemInfo[index].length;
		if (index > 0) {
			pre = menuItemInfo[index - 1].length;
		}
		for (int i = pre; i < num + pre; ++i) {
			String value = menuItemInfo[index][i - pre];
			if (value.equals("-")) {
				m_Menu[index].addSeparator();
			} else {
				m_MenuItem[i] = new JMenuItem(value);
				m_Menu[index].add(m_MenuItem[i]);
				m_MenuItem[i].addActionListener(this);
			}
		}
	}

	/**
	 * 初始化列表
	 */
	private void initTable() {
		JScrollPane jsc = new JScrollPane(m_Table);
		frame.add(jsc);
	}

	/**
	 * 判断表格是否为空
	 * 
	 * @return 是否为空
	 */
	private boolean isEmpty() {
		return table.getRowCount() == 0;
	}
	
	/**
	 * 判断表格是否有选中项
	 * 
	 * @return 选中项index
	 */
	private int checkSelect() {
		int cur = m_Table.getSelectedRow();
		if (cur == -1) {
			MessageBox.show("没有任何学生信息被选中！");
		}
		return cur;
	}

	/**
	 * 关闭对话框
	 */
	@Override
	public void close() {
		if (MessageBox.confirm("确定要退出本系统吗？")) {
			System.exit(0);
		}
	}

	/**
	 * 事件监听
	 * 
	 * @param e
	 *            事件
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == m_MenuItem[0]) { // 加载数据
			this.loadMenuClick();
		} else if (o == m_MenuItem[1]) { // 保存数据
			this.saveMenuClick();
		} else if (o == m_MenuItem[3]) { // 添加数据
			this.addMenuClick();
		} else if (o == m_MenuItem[4]) { // 编辑数据
			this.editMenuClick();
		} else if (o == m_MenuItem[5]) { // 删除数据
			this.deleteMenuClick();
		} else if (o == m_MenuItem[6]) { // 刷新数据
			this.showMenuClick();
		} else if (o == m_MenuItem[7]) { // 查找数据
			this.findMenuClick();
		} else if (o == m_MenuItem[9]) { // 退出
			this.exitMenuClick();
		} else if (o == m_MenuItem[10]) { // 帮助
			MessageBox.show("暂无帮助", "帮助");
		} else if (o == m_MenuItem[11]) { // 关于
			MessageBox.msg("学生信息管理系统\n软件开发工具课程设计\n作者：软件zy1201  廖星", "关于");
		}
	}

	private void loadMenuClick() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("选择数据文件");
		chooser.setApproveButtonText("加载");
		chooser.setCurrentDirectory(new File("C:\\"));
		chooser.setFileFilter(new FileNameExtensionFilter("数据文件 (*.data)",
				"data"));
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			try {
				StudentInfoManagment.loadStudentInfo(chooser.getSelectedFile(),
						table);
			} catch (IOException | ClassNotFoundException ex) {
				MessageBox.show("加载数据文件时出错！");
			}
		}
	}

	private void saveMenuClick() {
		if (this.isEmpty()) {
			MessageBox.show("暂无学生信息可保存！");
		} else {
			try {
				StudentInfoManagment.saveStudentInfo();
				MessageBox.show("数据保存成功！");
			} catch (IOException ex) {
				MessageBox.show("保存数据时出错！");
			}
		}

	}

	private void addMenuClick() {
		AddDialog d = new AddDialog(this, 1);
		d.show();
	}

	private void editMenuClick() {
		int cur = checkSelect();
		if (cur != -1) {
			AddDialog d = new AddDialog(this, 2);
			String sno=(String) table.getValueAt(cur, 1);
			d.setStudentInfo(StudentInfoManagment.getStudentInfo(sno));
			d.show();
			StudentInfoManagment.deleteStudentInfo(sno);
			table.removeRow(cur);
		}
	}

	private void deleteMenuClick() {
		int cur = checkSelect();
		if (cur != -1) {
			if (MessageBox
					.confirm("确定删除 " + table.getValueAt(cur, 2) + " 的信息？")) {
				StudentInfoManagment.deleteStudentInfo(table.getValueAt(cur, 1)
						.toString());
				table.removeRow(cur);
			}
		}
	}

	private void showMenuClick() {
		StudentInfoManagment.showStudentInfo(table);
	}

	private void findMenuClick() {
		if (this.isEmpty()) {
			MessageBox.show("暂无学生信息可供查找！");
		} else {
			FindDialog d = new FindDialog(this);
			d.show();
		}
	}

	private void exitMenuClick() {
		this.close();
	}

}
