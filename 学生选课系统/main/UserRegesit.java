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

public class UserRegesit {

	public UserRegesit() {
		final JFrame frame = new JFrame();
		frame.setTitle("学生选课系统-注册");
		frame.setBounds(760, 400, 380, 300);
		frame.setLayout(null);

		JLabel nameLabel = new JLabel("用户名:");
		nameLabel.setBounds(60, 40, 100, 40);
		frame.add(nameLabel);

		final JTextField nameText = new JTextField();
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

		regesitButton.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = nameText.getText();
					if (name == null || "".equals(name)) {
						JOptionPane.showMessageDialog(null, "该用户名不能为空，不能注册！",
								"通知", 1);
						return;
					}
					String pwd = pwdText.getText();
					Connection conn = Conn.getConnection();
					String sql = "select role from userinfo where name=?";
					PreparedStatement s = conn.prepareStatement(sql);
					s.setString(1, name);
					ResultSet r = s.executeQuery();
					boolean flag = false;
					if (r.next()) {
						JOptionPane.showMessageDialog(null, "该用户名已经被占用，不能注册！",
								"通知", 1);
						return;
					}
					if (!flag) {
						JOptionPane.showMessageDialog(null, "注册失败！", "通知", 1);
						return;
					}
					sql = "insert into userinfo(name,password,role) values(?,?,?)";
					try {
						s = conn.prepareStatement(sql);
						s.setString(1, name);
						s.setString(2, pwd);
						s.setString(3, "2");
						s.executeUpdate();
						JOptionPane.showMessageDialog(null, "恭喜，注册成功，请登录！",
								"通知", 1);
						frame.dispose();
						new Login();
						conn.close();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "注册失败!!", "通知", 1);
						e1.printStackTrace();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				frame.dispose();
			}
		});
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Login();
				frame.dispose();
			}
		});
	}

	public static void main(String[] args) {
		new UserRegesit();
	}
}