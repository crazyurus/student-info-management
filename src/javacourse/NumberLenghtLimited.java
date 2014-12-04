package javacourse;

import javax.swing.text.*;

/**
 * ����������
 *
 * @author Crazy Urus
 */
public class NumberLenghtLimited extends PlainDocument {

    private int limit;

    public NumberLenghtLimited(int limit) {
        super();
        this.limit = limit;
    }

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
