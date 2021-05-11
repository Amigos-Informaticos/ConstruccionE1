package tools;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LimitadorTextfield {
	
	public static void limitarTamanio(JFXTextField textField, int tamanio) {
		textField.setOnKeyTyped(event -> {
			if (textField.getText().length() > (tamanio - 1)) {
				event.consume();
			}
		});
	}
	
	public static void limitarTamanioArea(JFXTextArea textField, int tamanio) {
		textField.setOnKeyTyped(event -> {
			if (textField.getText().length() > (tamanio - 1)) {
				event.consume();
			}
		});
	}
	
	public static void soloNumeros(JFXTextField textField) {
		textField.addEventHandler(KeyEvent.KEY_TYPED, LimitadorTextfield::soloNumerosEnterosEvent);
	}
	
	public static void soloCaracteres(JFXTextField textField) {
		textField.addEventHandler(KeyEvent.KEY_TYPED, LimitadorTextfield::soloCaracteresEvent);
	}
	
	public static void soloTexto(JFXTextField textField) {
		textField.addEventHandler(KeyEvent.KEY_TYPED, LimitadorTextfield::soloTextoEvent);
	}
	
	public static void soloTextoArea(JFXTextArea textArea) {
		textArea.addEventHandler(KeyEvent.KEY_TYPED, LimitadorTextfield::soloTextoEvent);
	}
	
	public static void soloNumerosEnterosEvent(KeyEvent keyEvent) {
		try {
			if (!Character.isDigit(keyEvent.getCharacter().charAt(0))) {
				keyEvent.consume();
			}
		} catch (Exception ex) {
			Logger.staticLog(ex, true);
		}
	}
	
	public static void soloCaracteresEvent(KeyEvent keyEvent) {
		try {
			if (Character.isDigit(keyEvent.getCharacter().charAt(0))) {
				keyEvent.consume();
			}
		} catch (Exception ex) {
			Logger.staticLog(ex, true);
		}
	}
	
	public static void soloTextoEvent(KeyEvent keyEvent) {
		try {
			char key = keyEvent.getCharacter().charAt(0);
			if (!Character.isLetterOrDigit(key) && !Character.isSpaceChar(key)) {
				keyEvent.consume();
			}
		} catch (Exception ex) {
			Logger.staticLog(ex, true);
		}
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
}
