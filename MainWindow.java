package fool_named;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class MainWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Thread t1;//注意！！！这样写才能在t1中操作t2！！！
	Thread t2;

	public MainWindow(String Title,int Width,int Height){
		Container con = getContentPane();
		JTextArea jta = new JTextArea(12,10);
		//注意此处滚动面板设置布局管理器存疑
		JScrollPane jsp = new JScrollPane(jta);
		JPanel jp = new JPanel();
		con.setLayout(new BorderLayout());
		jp.setLayout(new FlowLayout());
		JButton jb1 = new JButton("开始");
		jb1.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0){
				try{
					//加载mysql_jdbc驱动程序，别忘了添加JDBCjar包！！！
					Class.forName("com.mysql.jdbc.Driver");
					jta.setText("加载数据库驱动成功\n\n开始摇奖\n\n");
				}catch(Exception e){
					jta.setText("加载数据库驱动失败\n");
					e.printStackTrace();
				}
				try{
					Connection connect = DriverManager.getConnection
							("jdbc:mysql://localhost:3306/test_db?useSSL=true","root","123456789");
					t1 = new Thread(new Runnable(){
						Statement sql = connect.createStatement();
						ResultSet res = sql.executeQuery("select * from iot order by id");
						/*使用动态数组存储从数据库调用的元素，以实现动态增长，本待使用集合类解决，但Vector在多线程中
						运行速度更快些。*/
						Vector<String> id = new Vector<>();
						Vector<String> name = new Vector<>();
						Vector<String> sex = new Vector<>();
						
						public void run(){
							try{
								while(res.next()){
									id.add(res.getString("id"));
									name.add(res.getString("name"));
									sex.add(res.getString("sex"));
								}
							}catch(Exception e){
								e.printStackTrace();
							}
							int j;
							for(j=0;j<id.size();j++){
								jta.append(id.get(j)+"  "+name.get(j)+"  "+sex.get(j)+"\n");
								//跟随滚动条显示最新文本行
								jta.setCaretPosition(jta.getText().length());
								if(j==(id.size()-1)){
									j=-1;//此处问题虽解决，但不知为何设j为-1
								}
								try{
									Thread.sleep(10);
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						}
					});
					t1.start();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		});
		JButton jb2 = new JButton("抽取");
		jb2.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0){
				try{
					t2 = new Thread(new Runnable(){
						//下面调用的stop()方法不被编译器赞同，但仍可运行，此功能使用布尔类型进行控制更佳
						@SuppressWarnings("deprecation")
						public void run(){
							try{
								t1.stop();
								/*鄙人此着可谓煞费苦心，JAVA的API中没有从jta中获取指定行文本的方法，
								但行与行之间必然存在换行符"\n"，故使用split()方法将jta文本一行一行
								地存入数组，再一行一行地从数组调用即可。*/
								String[] line = jta.getText().split("\n");
								new The_fool(line);                       
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					});
					t2.start();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		JButton jb3 = new JButton("名单编辑");
		jb3.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0){
				new EditList().init();;
			}
		});
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);
		con.add(BorderLayout.NORTH,jsp);
		con.add(BorderLayout.SOUTH,jp);
		//使主窗口居中显示
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		int windowWidth = this.getWidth();
		int windowHeight = this.getHeight();
		this.setLocation((width-windowWidth)/2,(height-windowHeight)/2);
		setTitle(Title);
		setSize(Width,Height);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args){
		new MainWindow("fool_named",260,300);
	}
}
