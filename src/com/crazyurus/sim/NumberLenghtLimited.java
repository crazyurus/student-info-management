package com.crazyurus.sim;

import javax.swing.text.*;

/**
 * 输入限制类
 *
 * @version 1.0.0
 * @author Crazy Urus
 */
public class NumberLenghtLimited extends PlainDocument {

    private static final long serialVersionUID = 1L;
    private final int limit;

    public NumberLenghtLimited(int limit) {
        super();
        this.limit = limit;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (str == null) {
            return;
        }
        if ((getLength() + str.length()) <= limit) {

            char[] upper = str.toCharArray();
            int length = 0;
            for (int i = 0; i < upper.length; i++) {
                if (upper[i] >= '0' && upper[i] <= '9') {
                    upper[length++] = upper[i];
                }
            }
            super.insertString(offset, new String(upper, 0, length), attr);
        }
    }
}
