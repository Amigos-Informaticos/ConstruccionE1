package tools;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.input.KeyEvent;

public class LimitadorTextfield {

    public static void limitarTamanio(JFXTextField textField, int tamanio) {
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

            if (!Character.isLetterOrDigit(key)){
                if (!Character.isSpaceChar(key)){
                    keyEvent.consume();
                }

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}