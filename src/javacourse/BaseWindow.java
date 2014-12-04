package javacourse;

import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Crazy Urus
 */
abstract public class BaseWindow extends WindowAdapter implements ActionListener {

    protected Window window;
    
    public void init() {
        window.setLocationRelativeTo(null);
        window.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
    }
    
    /**
     * 显示主窗体
     */
    public void show() {
        window.setVisible(true);
    }

    /**
     * 隐藏主窗体
     */
    public void hide() {
        window.setVisible(false);
    }

    /**
     * 关闭
     */
    abstract public void close();

    /**
     * 事件监听
     *
     * @param e 事件
     */
    @Override
    abstract public void actionPerformed(ActionEvent e);

    /**
     * 系统关闭事件
     *
     * @param e 事件
     */
    @Override
    public void windowClosing(WindowEvent e) {
        this.close();
    }
}
