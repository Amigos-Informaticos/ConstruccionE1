package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends Frame {
	private JTextField email;
	private JTextField password;
	private JButton login;

	public Login() {
		super("LOGIN");
		this.setLayout(new BorderLayout());
		this.add(this.buildCenter(), BorderLayout.CENTER);
		this.add(this.buildSouth(), BorderLayout.SOUTH);
		this.setVisible(true);
	}

	private JPanel buildCenter() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.email = new JTextField();
		this.password = new JTextField();
		panel.add(this.email);
		panel.add(this.password);
		return panel;
	}

	private JPanel buildSouth() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.login = new JButton("LOGIN");
		this.login.addActionListener(new LoginController());
		panel.add(this.login);
		return panel;
	}

	private class LoginController implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			/*if (!this.areEmpty()) {
			}*/
		}

		private boolean areEmpty() {
			return email.getText().equals("") && password.getText().equals("");
		}
	}
}