package tools;

import com.jfoenix.controls.JFXTextField;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class LimitadorTextfield extends PlainDocument {
    private TextField editor;
    private int num;

    public LimitadorTextfield(TextField editor, int num) {
        this.editor = editor;
        this.num = num;
    }

    public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException {
        if ((editor.getText().length()+arg1.length()) > this.num){
            return;
        }super.insertString(arg0,arg1,arg2);
    }

}
