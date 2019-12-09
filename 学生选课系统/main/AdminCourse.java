package main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import util.Conn;

public class AdminCourse extends JFrame implements ActionListener {

	private JScrollPane panel;

	private JButton add, stu, updatepwd;

	private final JTable table = new JTable();

	private JTextField jtf1, jtf2;

	private final DefaultTableModel model = new DefaultTableModel();

	private final String name, pwd;

	public AdminCourse(String name, String pwd) {
		super("ѧ��ѡ��ϵͳ");
		this.name = name;
		this.pwd = pwd;
		initComponent();
	}

	// ������ʾ����
	public void FillTable() {
		model.setRowCount(0);
		Vector v = getCourseData("", "");
		for (int i = 0; i < v.size(); i++) {
			model.addRow((Vector) v.get(i));
		}

		table.setModel(model);
	}

	private Vector getCourseData(String no, String name) {
		Vector v = new Vector();
		try {
			Connection conn = Conn.getConnection();
			String sql = "select courseId,no,name,address from courseinfo";
			PreparedStatement s;
			if ("".equals(no) && "".equals(name)) {
				s = conn.prepareStatement(sql);
			} else if (!"".equals(no) && "".equals(name)) {
				sql += " where no=?";
				s = conn.prepareStatement(sql);
				s.setString(1, no);
			} else if ("".equals(no) && !"".equals(name)) {
				sql += " where name=?";
				s = conn.prepareStatement(sql);
				s.setString(1, name);
			} else {
				sql += " where no=? and name=?";
				s = conn.prepareStatement(sql);
				s.setString(1, no);
				s.setString(2, name);
			}
			ResultSet r = s.executeQuery();
			while (r.next()) {
				Vector vv = new Vector();
				vv.add(r.getInt(1));
				vv.add(r.getString(2));
				vv.add(r.getString(3));
				vv.add(r.getString(4));
				v.add(vv);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	private void initComponent() {
		model.addColumn("ID");
		model.addColumn("�γ̱��");
		model.addColumn("�γ�����");
		model.addColumn("�Ͽεص�");
		this.setSize(760, 660);
		panel = new JScrollPane(table);
		panel.setBounds(10, 10, 720, 318);
		add = new JButton("��ӿγ�");
		add.setBounds(170, 580, 105, 35);
		stu = new JButton("�û���Ϣ");
		stu.setBounds(320, 580, 105, 35);
		updatepwd = new JButton("����");
		updatepwd.setBounds(460, 580, 85, 35);
		add.addActionListener(this);
		stu.addActionListener(this);
		updatepwd.addActionListener(this);
		table.setRowHeight(26);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);
		table.setFont(new Font(null, Font.PLAIN, 15));
		table.getTableHeader().setFont(new Font(null, Font.BOLD, 17));
		this.getContentPane().setLayout(null);
		JPanel jp = new JPanel();
		jp.setBounds(0, 0, 750, 50);
		jp.setLayout(null);
		JLabel jl1 = new JLabel("�γ̱��:");
		jl1.setBounds(40, 10, 100, 50);
		Font font = new Font("����", Font.BOLD, 20);
		jl1.setFont(font);
		jtf1 = new JTextField(15);
		jtf1.setBounds(130, 20, 100, 30);
		JLabel jl2 = new JLabel("�γ�����:");
		jl2.setBounds(240, 10, 100, 50);
		jl2.setFont(font);
		jtf2 = new JTextField(15);
		jtf2.setBounds(330, 20, 100, 30);
		JButton jb = new JButton("��ѯ");
		jb.setBounds(510, 18, 100, 33);
		jb.setFont(font);
		JButton re = new JButton("����");
		re.setBounds(620, 18, 100, 33);
		re.setFont(font);
		jp.add(jl1);
		jp.add(jtf1);
		jp.add(jl2);
		jp.add(jtf2);
		jp.add(jb);
		jp.add(re);
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				String person = jtf1.getText();
				String address = jtf2.getText();
				Vector v = getCourseData(person, address);
				for (int i = 0; i < v.size(); i++) {
					model.addRow((Vector) v.get(i));
				}

				table.setModel(model);
			}
		});
		re.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jtf1.setText("");
				jtf2.setText("");
				model.setRowCount(0);
				String person = jtf1.getText();
				String address = jtf2.getText();
				Vector v = getCourseData(person, address);
				for (int i = 0; i < v.size(); i++) {
					model.addRow((Vector) v.get(i));
				}

				table.setModel(model);
			}
		});
		this.getContentPane().add(jp);
		panel.setBounds(0, 60, 750, 500);
		this.getContentPane().add(panel);
		this.getContentPane().add(add);
		this.getContentPane().add(stu);
		this.getContentPane().add(updatepwd);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		FillTable();
	}

	/**
	 * ��ť�¼�
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button.equals(stu)) {
			dispose();
			new AdminStu(name, pwd);
		}
		if (button.equals(add)) {
			final JFrame addFrame = new JFrame();
			final JTextField noText = new JTextField(12);
			final JTextField nameText = new JTextField(12);
			final JTextField addressText = new JTextField(12);
			JLabel noLabel = new JLabel("�γ̱��:");
			JLabel nameLabel = new JLabel("�γ�����");
			JLabel addressLabel = new JLabel("�Ͽεص�:");
			JButton submit = new JButton("���");
			JButton cancel = new JButton("�ر�");
			JPanel jp1 = new JPanel();
			JPanel jp2 = new JPanel();
			JPanel jp3 = new JPanel();
			JPanel jp4 = new JPanel();
			addFrame.setLayout(null);

			jp1.setLayout(null);
			noLabel.setBounds(60, 10, 80, 30);
			noText.setBounds(130, 10, 180, 30);
			jp1.setBounds(0, 10, 550, 40);
			jp1.add(noLabel);
			jp1.add(noText);

			jp2.setLayout(null);
			nameLabel.setBounds(60, 10, 80, 30);
			nameText.setBounds(130, 10, 180, 30);
			jp2.setBounds(0, 50, 550, 40);
			jp2.add(nameLabel);
			jp2.add(nameText);

			jp3.setLayout(null);
			addressLabel.setBounds(60, 10, 80, 30);
			addressText.setBounds(130, 10, 180, 30);
			jp3.setBounds(0, 90, 550, 40);
			jp3.add(addressLabel);
			jp3.add(addressText);

			jp4.setLayout(null);
			submit.setBounds(100, 25, 80, 30);
			cancel.setBounds(220, 25, 80, 30);
			jp4.setBounds(0, 130, 550, 60);
			jp4.add(submit);
			jp4.add(cancel);

			addFrame.add(jp1);
			addFrame.add(jp2);
			addFrame.add(jp3);
			addFrame.add(jp4);
			addFrame.setBounds(700, 300, 400, 260);
			addFrame.setVisible(true);
			addFrame.setTitle("�����Ϣ");
			submit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String no = noText.getText();
					String name = nameText.getText();
					String address = addressText.getText();
					Connection conn;
					PreparedStatement st;
					try {
						conn = Conn.getConnection();
						st = conn
								.prepareStatement("insert into courseinfo(no,name,address) values(?,?,?)");
						st.setString(1, no);
						st.setString(2, name);
						st.setString(3, address);
						st.executeUpdate();
						JOptionPane.showMessageDialog(null, "��ӳɹ�!", "ȷ��", 1);
						Vector<String> cNew = new Vector<String>();
						cNew.add(String.valueOf(getMaxId()));
						cNew.add(no);
						cNew.add(name);
						cNew.add(address);
						model.addRow(cNew);
						addFrame.dispose();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});
			cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					addFrame.dispose();
				}
			});
		}
		if (button.equals(updatepwd)) {
			final JFrame frame = new JFrame();
			frame.setTitle("ѧ��ѡ��ϵͳ");
			frame.setBounds(800, 400, 380, 300);
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

			final JPasswordField pwdText = new JPasswordField();
			pwdText.setBounds(140, 105, 140, 30);
			frame.add(pwdText);

			JButton updateButton = new JButton("�޸�");
			updateButton.setBounds(70, 185, 90, 30);
			frame.add(updateButton);

			JButton cancelButton = new JButton("ȡ��");
			cancelButton.setBounds(190, 185, 90, 30);
			frame.add(cancelButton);

			ImageIcon icon = new ImageIcon("img/1.jpg");
			JLabel bgLabel = new JLabel(icon);
			bgLabel.setBounds(0, 0, 380, 300);
			frame.add(bgLabel);
			frame.setVisible(true);
			nameText.setText(name);
			pwdText.setText(pwd);
			updateButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String name = nameText.getText();
					String pwd = pwdText.getText();
					Connection conn = Conn.getConnection();
					PreparedStatement s;
					try {
						s = conn.prepareStatement("update userinfo set name=?,password=? where name=?");
						s.setString(1, name);
						s.setString(2, pwd);
						s.setString(3, name);
						s.executeUpdate();
						JOptionPane.showMessageDialog(null, "�����޸ĳɹ�������µ�½!!",
								"֪ͨ", 1);
						frame.dispose();
						dispose();
						new Login();
					} catch (SQLException e1) {
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
		}
	}

	// �γ���Ϣ�б����ID
	protected static int getMaxId() {
		int a = 0;
		try {
			Connection conn = Conn.getConnection();
			String sql = "select max(courseId) from courseinfo";
			PreparedStatement s = conn.prepareStatement(sql);
			ResultSet r = s.executeQuery();
			while (r.next()) {
				a = r.getInt(1);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}
}
