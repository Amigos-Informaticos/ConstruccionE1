package tools;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
			Logger.staticLog(ex);
		}
	}
	
	public static void soloCaracteresEvent(KeyEvent keyEvent) {
		try {
			if (Character.isDigit(keyEvent.getCharacter().charAt(0))) {
				keyEvent.consume();
			}
		} catch (Exception ex) {
			Logger.staticLog(ex);
		}
	}
	
	public static void soloTextoEvent(KeyEvent keyEvent) {
		try {
			char key = keyEvent.getCharacter().charAt(0);
			if (!Character.isLetterOrDigit(key) && !Character.isSpaceChar(key)) {
				keyEvent.consume();
			}
		} catch (Exception ex) {
			Logger.staticLog(ex);
		}
	}
}
