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
		frame.setTitle("ѧ��ѡ��ϵͳ-ע��");
		frame.setBounds(760, 400, 380, 300);
		frame.setLayout(null);

		JLabel nameLabel = new JLabel("�û���:");
		nameLabel.setBounds(60, 40, 100, 40);
		frame.add(nameLabel);

		final JTextField nameText = new JTextField();
		nameText.setBounds(140, 45, 140, 30);
		frame.add(nameText);

		JLabel pwdLabel = new JLabel("����:");
		pwdLabel.setBounds(60, 100, 100, 40);
		frame.add(pwdLabel);

		final JPasswordField pwdText = new JPasswordField("1");
		pwdText.setBounds(140, 105, 140, 30);
		frame.add(pwdText);

		JButton loginButton = new JButton("��¼");
		loginButton.setBounds(40, 185, 90, 30);
		frame.add(loginButton);

		JButton regesitButton = new JButton("ע��");
		regesitButton.setBounds(140, 185, 90, 30);
		frame.add(regesitButton);

		JButton cancelButton = new JButton("ȡ��");
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
						JOptionPane.showMessageDialog(null, "���û�������Ϊ�գ�����ע�ᣡ",
								"֪ͨ", 1);
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
						JOptionPane.showMessageDialog(null, "���û����Ѿ���ռ�ã�����ע�ᣡ",
								"֪ͨ", 1);
						return;
					}
					if (!flag) {
						JOptionPane.showMessageDialog(null, "ע��ʧ�ܣ�", "֪ͨ", 1);
						return;
					}
					sql = "insert into userinfo(name,password,role) values(?,?,?)";
					try {
						s = conn.prepareStatement(sql);
						s.setString(1, name);
						s.setString(2, pwd);
						s.setString(3, "2");
						s.executeUpdate();
						JOptionPane.showMessageDialog(null, "��ϲ��ע��ɹ������¼��",
								"֪ͨ", 1);
						frame.dispose();
						new Login();
						conn.close();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "ע��ʧ��!!", "֪ͨ", 1);
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