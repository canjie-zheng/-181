package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.Conn;

public class Login {

	public Login() {
		final JFrame frame = new JFrame();
		frame.setTitle("学生选课系统");
		frame.setBounds(760, 400, 380, 300);
		frame.setLayout(null);

		JLabel nameLabel = new JLabel("用户名:");
		nameLabel.setBounds(60, 40, 100, 40);
		frame.add(nameLabel);

		final JTextField nameText = new JTextField("admin");
		nameText.setBounds(140, 45, 140, 30);
		frame.add(nameText);

		JLabel pwdLabel = new JLabel("密码:");
		pwdLabel.setBounds(60, 100, 100, 40);
		frame.add(pwdLabel);

		final JPasswordField pwdText = new JPasswordField("1");
		pwdText.setBounds(140, 105, 140, 30);
		frame.add(pwdText);

		JButton loginButton = new JButton("登录");
		loginButton.setBounds(40, 185, 90, 30);
		frame.add(loginButton);

		JButton regesitButton = new JButton("注册");
		regesitButton.setBounds(140, 185, 90, 30);
		frame.add(regesitButton);

		JButton cancelButton = new JButton("取消");
		cancelButton.setBounds(240, 185, 90, 30);
		frame.add(cancelButton);

		ImageIcon icon = new ImageIcon("img/test.jpg");
		JLabel bgLabel = new JLabel(icon);
		bgLabel.setBounds(0, 0, 380, 300);
		frame.add(bgLabel);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		loginButton.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = nameText.getText();
					String pwd = pwdText.getText();
					Connection conn = Conn.getConnection();
					String sql = "select role from userinfo where name=? and password=?";
					PreparedStatement s = conn.prepareStatement(sql);
					s.setString(1, name);
					s.setString(2, pwd);
					ResultSet r = s.executeQuery();
					boolean flag = false;
					String role = "";
					if (r.next()) {
						flag = true;
						role = r.getString(1);
					}
					if (!flag) {
						JOptionPane
								.showMessageDialog(null, "恭喜，登录失败！", "通知", 1);
						return;
					}
					JOptionPane.showMessageDialog(null, "恭喜，登录成功！", "通知", 1);
					frame.dispose();
					if ("2".equals(role)) {

						new ChooseCourse(name, pwd);
					} else {
						new AdminStu(name, pwd);
					}
					conn.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		regesitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new UserRegesit();
				frame.dispose();
			}
		});
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				frame.dispose();
			}
		});
	}

	public static void main(String[] args) {
		new Login();
	}
}