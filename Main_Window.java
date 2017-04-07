package fool_named;
//导入GUI相关包
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
//使本类继承JFrame类
public class Main_Window extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Main_Window(String Title,int Width,int Height){
		//创建容器对象con、文本区域对象jta、可滚动面板对象jta、面板对象jp
		Container con = getContentPane();
		JTextArea jta = new JTextArea(12,10);
		//注意此处滚动面板设置布局管理器存疑
		JScrollPane jsp = new JScrollPane(jta);
		JPanel jp = new JPanel();
		//设置布局管理器
		con.setLayout(new BorderLayout());
		jp.setLayout(new FlowLayout());
		//创建按钮jb1对象，并在匿名内部类中定义动作事件监听器
		JButton jb1 = new JButton("开始");
		jb1.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0){
				//连接数据库，若失败抛出异常信息
				try{
					//加载mysql_jdbc驱动程序，别忘了添加JDBCjar包
					Class.forName("com.mysql.jdbc.Driver");
					jta.setText("成功连接数据库\n\n开始摇奖\n\n");
				}catch(Exception e){
					jta.setText("连接数据库失败\n");
					e.printStackTrace();
				}
				try{
					//登录数据库
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db?useSSL=true","root","123456789");
					Thread t1 = new Thread(new Runnable(){
						String id[] = new String[3];
						String name[] = new String[3];
						String sex[] = new String[3];
						Statement sql = connect.createStatement();
						ResultSet res = sql.executeQuery("select * from iot");
						
						public void run(){
							boolean isContinue = true;
							try{
								int i=0;
								while(res.next()){
									id[i] = res.getString("id");
									name[i] = res.getString("name");
									sex[i] = res.getString("sex");
									i++;
								}
							}catch(Exception e){
								e.printStackTrace();
							}
							int j;
							for(j=0;j<id.length;j++){
								jta.append(id[j]+"  "+name[j]+"  "+sex[j]+"\n");
								if(j==(id.length-1)){
									j=0;
								}
								try{
									Thread.sleep(1000);
								}catch(Exception e){
									e.printStackTrace();
								}
								if(isContinue==false){
									break;
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
					Thread t2 = new Thread(new Runnable(){
						public void run(){
							try{
								//此处使用stop()中断线程t1
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
				new Edit_list();
			}
		});
		//添加组件
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
		//设置窗体相关属性
		setTitle(Title);
		setSize(Width,Height);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args){
		new Main_Window("fool_named",260,300);
	}
}
