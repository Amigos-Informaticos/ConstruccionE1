package tools;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LimitadorTextfield {

    public static void limitarTamanio(JFXTextField textField, int tamanio) {
        textField.setOnKeyTyped(event -> {
            if (textField.getText().length() > (tamanio-1)) event.consume();
        });
    }

    public static void limitarTamanioArea(JFXTextArea textField, int tamanio) {
        textField.setOnKeyTyped(event -> {
            if (textField.getText().length() > (tamanio-1)) event.consume();
        });
    }

    public static void soloNumeros(JFXTextField textField) {
        textField.addEventHandler(KeyEvent.KEY_TYPED, event -> soloNumerosEnterosEvent(event));
    }

    public static void soloCaracteres(JFXTextField textField) {
        textField.addEventHandler(KeyEvent.KEY_TYPED, event -> soloCaracteresEvent(event));
    }

    public static void soloTexto(JFXTextField textField) {
        textField.addEventHandler(KeyEvent.KEY_TYPED, event -> soloTextoEvent(event));
    }

    public static void soloTextoArea(JFXTextArea textArea){
        textArea.addEventHandler(KeyEvent.KEY_TYPED,event -> soloTextoEvent(event));

    }

    public static void soloNombres(JFXTextField textField){
        textField.addEventHandler(KeyEvent.KEY_TYPED, event -> soloNombresEvent(event));
    }
    public static void soloNombresEvent(KeyEvent keyEvent){
        if (!keyEvent.getCode().toString().matches("[a-zA-Z]")
                && keyEvent.getCode() != KeyCode.BACK_SPACE
                && keyEvent.getCode() != KeyCode.SPACE
                && keyEvent.getCode() != KeyCode.SHIFT) {
            keyEvent.consume();
        }
    }
    public static void soloNumerosEnterosEvent(KeyEvent keyEvent) {
        try {
            char key = keyEvent.getCharacter().charAt(0);

            if (!Character.isDigit(key))
                keyEvent.consume();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void soloCaracteresEvent(KeyEvent keyEvent) {
        try {
            char key = keyEvent.getCharacter().charAt(0);

            if (Character.isDigit(key))
                keyEvent.consume();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void soloTextoEvent(KeyEvent keyEvent) {
        try {
            char key = keyEvent.getCharacter().charAt(0);

            if (!Character.isLetterOrDigit(key) && !Character.isSpaceChar(key)){
                    keyEvent.consume();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
