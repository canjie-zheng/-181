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

public class ChooseCourse extends JFrame implements ActionListener {

	private JScrollPane panel;

	private JButton choose, courseList, updatepwd;

	private final JTable table = new JTable();

	private JTextField jtf1, jtf2;

	private final DefaultTableModel model = new DefaultTableModel();

	private final String name, pwd;

	private static StringBuffer sb = new StringBuffer();

	public ChooseCourse(String name, String pwd) {
		super("学生选课系统");
		this.name = name;
		this.pwd = pwd;
		initComponent();
	}

	// 数据显示界面
	public void FillTable() {
		model.setRowCount(0);
		Vector v = getCourseData("", "");
		for (int i = 0; i < v.size(); i++) {
			model.addRow((Vector) v.get(i));
		}

		table.setModel(model);
	}

	public static void main(String[] args) {
		new ChooseCourse("user", "1");
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
		model.addColumn("课程编号");
		model.addColumn("课程名称");
		model.addColumn("课程学分");
		this.setSize(760, 660);
		panel = new JScrollPane(table);
		panel.setBounds(10, 10, 720, 318);
		choose = new JButton("选择课程");
		choose.setBounds(170, 580, 105, 35);
		courseList = new JButton("所选列表");
		courseList.setBounds(300, 580, 125, 35);
		updatepwd = new JButton("改密");
		updatepwd.setBounds(460, 580, 85, 35);
		choose.addActionListener(this);
		courseList.addActionListener(this);
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
		JLabel jl1 = new JLabel("课程编号:");
		jl1.setBounds(40, 10, 100, 50);
		Font font = new Font("宋体", Font.BOLD, 20);
		jl1.setFont(font);
		jtf1 = new JTextField(15);
		jtf1.setBounds(130, 20, 100, 30);
		JLabel jl2 = new JLabel("课程名称:");
		jl2.setBounds(240, 10, 100, 50);
		jl2.setFont(font);
		jtf2 = new JTextField(15);
		jtf2.setBounds(330, 20, 100, 30);
		JButton jb = new JButton("查询");
		jb.setBounds(510, 18, 100, 33);
		jb.setFont(font);
		JButton re = new JButton("重置");
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
		this.getContentPane().add(choose);
		this.getContentPane().add(courseList);
		this.getContentPane().add(updatepwd);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		FillTable();
	}

	/**
	 * 按钮事件
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button.equals(choose)) {
			final int row = table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "您未选择课程信息!!", "通知", 1);
				return;
			}
			int a = JOptionPane.showConfirmDialog(null, "您确定要选择该选课信息吗!!", "通知",
					2);
			if (a == 1 || a == 2) {

				return;
			}
			final String no = (String) table.getValueAt(row, 1);
			final String courseName = (String) table.getValueAt(row, 2);
			try {
				PreparedStatement ps = null;
				String sql = "insert into chooseinfo(name,courseName,no) values(?,?,?)";
				try {
					Connection conn = Conn.getConnection();
					ps = conn.prepareStatement(sql);
					ps.setString(1, name);
					ps.setString(2, courseName);
					ps.setString(3, no);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(this, "选择课程信息成功!!", "通知", 1);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this, "选择课程信息失败!!", "通知", 1);
					e1.printStackTrace();
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "信息有误， 请核实!!", "通知", 1);
			}
		}
		if (button.equals(updatepwd)) {
			final JFrame frame = new JFrame();
			frame.setTitle("学生选课系统");
			frame.setBounds(800, 400, 380, 300);
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

			final JPasswordField pwdText = new JPasswordField();
			pwdText.setBounds(140, 105, 140, 30);
			frame.add(pwdText);

			JButton updateButton = new JButton("修改");
			updateButton.setBounds(70, 185, 90, 30);
			frame.add(updateButton);

			JButton cancelButton = new JButton("取消");
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
						JOptionPane.showMessageDialog(null, "密码修改成功，请从新登陆!!",
								"通知", 1);
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

		if (button.equals(courseList)) {
			final JFrame jf = new JFrame("所选课程列表");
			final DefaultTableModel courseModel = new DefaultTableModel();
			final JTable courseTable = new JTable();
			courseModel.addColumn("ID");
			courseModel.addColumn("学生姓名");
			courseModel.addColumn("课程名称");
			courseModel.addColumn("课程编号");
			jf.setSize(760, 460);
			JScrollPane coursePanel = new JScrollPane(courseTable);
			coursePanel.setBounds(10, 10, 720, 318);
			JButton courseDelete = new JButton("退选");
			JButton coursePrint = new JButton("打印");
			JButton courseLogOut = new JButton("退出");
			courseDelete.setBounds(160, 380, 125, 35);
			coursePrint.setBounds(300, 380, 125, 35);
			courseLogOut.setBounds(440, 380, 85, 35);
			courseTable.setRowHeight(26);
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();
			r.setHorizontalAlignment(JLabel.CENTER);
			courseTable.setDefaultRenderer(Object.class, r);
			courseTable.setFont(new Font(null, Font.PLAIN, 15));
			courseTable.getTableHeader().setFont(new Font(null, Font.BOLD, 17));
			jf.getContentPane().setLayout(null);
			jf.getContentPane().add(coursePanel);

			jf.getContentPane().add(courseDelete);
			jf.getContentPane().add(coursePrint);
			jf.getContentPane().add(courseLogOut);
			jf.setLocationRelativeTo(null);
			jf.setVisible(true);
			courseModel.setRowCount(0);
			Vector v = new Vector();
			Connection conn = Conn.getConnection();
			String sql = "select chooseId,name,courseName,no from chooseinfo where name=?";
			PreparedStatement s;
			sb.append("ID");
			sb.append(",");
			sb.append("学生姓名");
			sb.append(",");
			sb.append("课程名称");
			sb.append(",");
			sb.append("课程编号");
			sb.append("\r\n");
			try {
				s = conn.prepareStatement(sql);
				s.setString(1, name);
				ResultSet r1 = s.executeQuery();
				while (r1.next()) {
					Vector vv = new Vector();
					vv.add(r1.getInt(1));
					vv.add(r1.getString(2));
					vv.add(r1.getString(3));
					vv.add(r1.getString(4));
					v.add(vv);
					sb.append(r1.getInt(1));
					sb.append(",");
					sb.append(r1.getString(2));
					sb.append(",");
					sb.append(r1.getString(3));
					sb.append(",");
					sb.append(r1.getString(4));
					sb.append(",");
					sb.append("\r\n");
				}
				conn.close();
				for (int i = 0; i < v.size(); i++) {
					courseModel.addRow((Vector) v.get(i));
				}

				courseTable.setModel(courseModel);
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
			courseLogOut.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					jf.dispose();
				}
			});
			coursePrint.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(sb.toString());
				}
			});
			courseDelete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int row = courseTable.getSelectedRow();
					if (row == -1) {
						JOptionPane.showMessageDialog(null, "您未选中课程退选!!", "通知",
								1);
						return;
					} else {
						int a = JOptionPane.showConfirmDialog(null,
								"您确定要退选选择的选课信息吗!!", "通知", 2);
						if (a == 1 || a == 2) {

							return;
						}
					}
					Integer id = (Integer) courseTable.getValueAt(row, 0);
					try {
						PreparedStatement ps = null;
						String sql = "delete from chooseinfo where chooseId=?";
						int x = 0;
						Connection conn = Conn.getConnection();
						try {
							ps = conn.prepareStatement(sql);
							ps.setInt(1, id);
							x = ps.executeUpdate();
						} catch (Exception e1) {
							e1.printStackTrace();
						} finally {
							try {
								if (ps != null) {
									ps.close();
								}
								if (conn != null) {
									conn.close();
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
						if (x >= 1) {
							JOptionPane.showMessageDialog(null, "已退选成功!!",
									"通知", 1);
							courseModel.removeRow(row);
						} else {
							JOptionPane.showMessageDialog(null,
									"你选择的退选有误，请核实!!", "通知", 0);
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});
		}

		return;
	}
}
