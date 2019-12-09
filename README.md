大数据181 郑灿杰 2018310299
一、实验目的
分析学生选课系统
使用GUI窗体及其组件设计窗体界面，完成学生选课过程业务逻辑编程，基于文件保存并读取数据，处理异常。
一、系统角色分析及类设计
例如：学校有“人员”，分为“教师”和“学生”，教师教授“课程”，学生选择课程。
定义每种角色人员的属性，及其操作方法。
属性示例：​人员（编号、姓名、性别……）
教师（编号、姓名、性别、所授课程、……）
​学生（编号、姓名、性别、所选课程、……）
​课程（编号、课程名称、上课地点、时间、授课教师、……）
三、实验过程
教师登录系统 添加学生信息 对课程信息管理 修改教师的登录密码
课程信息包括课程编号 课程名称 课程学分
学生信息包括 注册学生用户名密码 学生姓名 登录密码 学生修改密码 打印学生选课列表
查询所选课程  退选课程 学生退课打印学生选课列表
四、流程图https://github.com/canjie-zheng/-181/blob/master/%E5%AD%A6%E7%94%9F.jpg
https://github.com/canjie-zheng/-181/blob/master/%E6%95%99%E5%B8%88.jpg
五、核心代码和注释
// 启动jar包路径
	private static String driver = "com.mysql.jdbc.Driver";

	// 数据库路径
	private static String url = "jdbc:mysql://localhost:3306/course";

	// 用户名
	private static String userName = "root";

	// 密码
	private static String password = "test123456";
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
            六、运行截图
            https://github.com/canjie-zheng/-181/blob/master/50f81ca0221d061480e72a95763193c.png
