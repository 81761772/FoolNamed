package fool_named;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.sql.*;

public class Edit_list {
	JTextField jtf1;
	JTextField jtf2;
	JComboBox<String> jcb;
	JTextArea jta;
	
	public void init(){
		JFBuild();
	}
	
	public void JFBuild(){
		JFrame jf = new JFrame();
		jf.setTitle("Edit_list");
		jf.setSize(480,210);
		Container con = jf.getContentPane();
		con.setLayout(new BorderLayout());
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		JPanel jp3 = new JPanel();
		JPanel jp4 = new JPanel();
		JPanel jp5 = new JPanel();
		jp1.setLayout(new BorderLayout());
		jp2.setLayout(new FlowLayout());
		jp3.setLayout(new GridLayout(3,2,5,5));
		jp4.setLayout(new FlowLayout());
		jp5.setLayout(new FlowLayout());
		con.add(jp1,BorderLayout.WEST);
		con.add(jp2,BorderLayout.EAST);
		jp1.add(jp3,BorderLayout.NORTH);
		jp1.add(jp4,BorderLayout.CENTER);
		jp1.add(jp5,BorderLayout.SOUTH);
		JLabel jl1 = new JLabel("               学号");
		JLabel jl2 = new JLabel("               姓名");
		JLabel jl3 = new JLabel("               性别");
		jtf1 = new JTextField(8);
		jtf2 = new JTextField(8);
		jcb = new JComboBox<>(new MyComboBox());
		jp3.add(jl1);
		jp3.add(jtf1);
		jp3.add(jl2);
		jp3.add(jtf2);
		jp3.add(jl3);
		jp3.add(jcb);
		JButton jb1 = new JButton("添加");
		jb1.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0){
				
				class Edit_AddObject{
					Connection con1;
					PreparedStatement sql1;
					
					public Connection getConnection1(){
						try{
							Class.forName("com.mysql.jdbc.Driver");
							jta.append("数据库驱动加载成功\n");
						}catch(ClassNotFoundException e){
							jta.append("数据库驱动加载失败\n");
						}
						try{
							con1 = DriverManager.getConnection
									("jdbc:mysql://localhost:3306/test_db?useSSL=true",
									 "root","123456789");
							jta.append("数据库连接成功\n");
						}catch(SQLException e){
							jta.append("数据库连接失败\n");
						}
						return con1;
					}
					
					public void doit1(){
						Edit_AddObject ea = new Edit_AddObject();
						con1 = ea.getConnection1();
						String id1 = jtf1.getText();
						String name1 = jtf2.getText();
						String sex1 = jcb.getSelectedItem().toString();
					    try{	
					    	sql1 = con1.prepareStatement("insert into iot values(?,?,?)");
							sql1.setString(1,id1);
							sql1.setString(2,name1);
							sql1.setString(3,sex1);
							sql1.executeUpdate();
							jta.append("插入成功");
						}catch(Exception e){
							new AddObjectWarning();
						}
					}
				}
				Edit_AddObject ea = new Edit_AddObject();
				ea.doit1();
			}
		});
		JButton jb2 = new JButton("删除");
		jb2.addActionListener(new ActionListener(){
			
            public void actionPerformed(ActionEvent arg0){
				
            	class Edit_DeleteObject{
            		Connection con2;
            		PreparedStatement sql2;
            		
            		public Connection getConnection2(){
            			try{
            				Class.forName("com.mysql.jdbc.Driver");
            				jta.append("数据库驱动加载成功\n");
            			}catch(ClassNotFoundException e){
            				jta.append("数据库驱动加载失败\n");
            			}
            			try{
            				con2 = DriverManager.getConnection
            						("jdbc:mysql://localhost:3306/test_db?useSSL=true","root","123456789");
            				jta.append("数据库连接成功\n");
            			}catch(SQLException e){
            				jta.append("数据库连接失败\n");
            			}
            			return con2;
            		}
            		
            		public void doit2(){
            			Edit_DeleteObject ed = new Edit_DeleteObject();
            			con2 = ed.getConnection2();
            			String id2 = jtf1.getText();
            			try{
            				sql2 = con2.prepareStatement("delete from iot where id=?");
            				sql2.setString(1,id2);
            				sql2.executeUpdate();
            				jta.append("删除成功\n");
            			}catch(Exception e){
            				new DeleteObjectWarning();
            			}
            		}
            	}
            	Edit_DeleteObject ed = new Edit_DeleteObject();
            	ed.doit2();
			}
		});
		
		JButton jb3 = new JButton("显示名单");
		jb3.addActionListener(new ActionListener(){
			
            public void actionPerformed(ActionEvent arg0){
				
            	class Edit_ShowList{
            		Connection con3;
            		Statement sql3;
            		ResultSet res1;
            		
            		public Connection getConnection3(){
            			try{
            				Class.forName("com.mysql.jdbc.Driver");
            				jta.append("数据库驱动加载成功\n");
            			}catch(ClassNotFoundException e){
            				jta.append("数据库驱动加载失败\n");
            			}
            			try{
            				con3 = DriverManager.getConnection
            						("jdbc:mysql://localhost:3306/test_db?useSSL=true","root","123456789");
            				jta.append("数据库连接成功\n");
            			}catch(SQLException e){
            				jta.append("数据库连接失败\n");
            			}
            			return con3;
            		}
            		
            		public void doit3(){
            			Edit_ShowList es = new Edit_ShowList();
            			con3 = es.getConnection3();
            			try{
            				sql3 = con3.createStatement();
            				res1 = sql3.executeQuery("select* from iot");
            				jta.setText("");//耍个心眼，使用res1获取名单人数受阻，故直接从jta面板上获取
            				while(res1.next()){
            					String id3 = res1.getString("id");
            					String name3 = res1.getString("name");
            					String sex3 = res1.getString("sex");
            					jta.append(id3+" "+name3+" "+sex3+"\n");
            				}
            				jta.append("调取完成\n");
            				String[] spline = jta.getText().split("\n");
            				int count = spline.length;
            				jta.append("名单人数："+(count-1));
            			}catch(SQLException e){
            				jta.append("调取失败");
            			}
            		}
            	}
            	Edit_ShowList es = new Edit_ShowList();
            	es.doit3();
			}
		});
		
		JButton jb4 = new JButton("查找");
		jb4.addActionListener(new ActionListener(){
			
            public void actionPerformed(ActionEvent arg0){
				
            	class Edit_Search{
            		Connection con4;
            		PreparedStatement sql4;
            		ResultSet res2;
            		
            		public Connection getConnection4(){
            			try{
            				Class.forName("com.mysql.jdbc.Driver");
            				jta.append("数据库驱动加载成功\n");
            			}catch(ClassNotFoundException e){
            				jta.append("数据库驱动加载失败\n");
            			}
            			try{
            				con4 = DriverManager.getConnection
            						("jdbc:mysql://localhost:3306/test_db?useSSL=true","root","123456789");
            				jta.append("数据库连接成功\n");
            			}catch(SQLException e){
            				jta.append("数据库连接失败\n");
            			}
            			return con4;
            		}
            		
            		public void doit4(){
            			Edit_Search es = new Edit_Search();
            			con4 = es.getConnection4();
            			try{
            				sql4 = con4.prepareStatement("select* from iot where id=?");
            				String jtfid = jtf1.getText();
            				sql4.setString(1,jtfid);
            				res2 = sql4.executeQuery();
            				while(res2.next()){
            					String id = res2.getString("id");
            					String name = res2.getString("name");
            					String sex = res2.getString("sex");
            					jta.append(id+" "+name+" "+sex);
            				}
            			}catch(Exception e){
            				jta.append("查找失败");
            			}
            		}
            	}
            	Edit_Search es = new Edit_Search();
            	es.doit4();
			}
		});
		
		jp4.add(jb1);
		jp4.add(jb2);
		jp4.add(jb3);
		jp5.add(jb4);
		jta = new JTextArea(8,19);
		JScrollPane jsp = new JScrollPane(jta);
		jp2.add(jsp);
		jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		jf.setVisible(true);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		int windowWidth = jf.getWidth();
		int windowHeight = jf.getHeight();
		jf.setLocation((width-windowWidth)/2,(height-windowHeight)/2);
	}	
	
	public static void main(String[] args){
		new Edit_list().init();
	}
}
