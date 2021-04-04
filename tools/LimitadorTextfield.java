package tools;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.input.KeyEvent;

public class LimitadorTextfield{
    public static void limitarCaracteres(JFXTextField textField, int tamanio){
        textField.setOnKeyTyped(event ->{
            int maxCharacters = tamanio-1;
            if(textField.getText().length() > maxCharacters) event.consume();
        });
    }

    public static void soloNumeros(JFXTextField textField){
        textField.addEventHandler(KeyEvent.KEY_TYPED, event -> soloNumerosEnterosEvent(event));
    }

    public static void soloCaracteres(JFXTextField textField){
        textField.addEventHandler(KeyEvent.KEY_TYPED, event -> soloCaracteresEvent(event));
    }

    public static void soloTexto(JFXTextField textField){
        textField.addEventHandler(KeyEvent.KEY_TYPED, event -> soloTextoEvent(event));
    }

    public static void soloNumerosEnterosEvent(KeyEvent keyEvent) {
        try{
            char key = keyEvent.getCharacter().charAt(0);

            if (!Character.isDigit(key))
                keyEvent.consume();

        } catch (Exception ex){ }
    }

    public static void soloCaracteresEvent(KeyEvent keyEvent) {
        try{
            char key = keyEvent.getCharacter().charAt(0);

            if (Character.isDigit(key))
                keyEvent.consume();

        } catch (Exception ex){ }
    }

    public static void soloTextoEvent(KeyEvent keyEvent) {
        try{
            char key = keyEvent.getCharacter().charAt(0);

            if (!Character.isLetterOrDigit(key))
                keyEvent.consume();

        } catch (Exception ex){ }
    }
}
