package fool_named;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class Main_Window extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Thread t1;//ע�⣡��������д������t1�в���t2������
	Thread t2;

	public Main_Window(String Title,int Width,int Height){
		Container con = getContentPane();
		JTextArea jta = new JTextArea(12,10);
		//ע��˴�����������ò��ֹ���������
		JScrollPane jsp = new JScrollPane(jta);
		JPanel jp = new JPanel();
		con.setLayout(new BorderLayout());
		jp.setLayout(new FlowLayout());
		JButton jb1 = new JButton("��ʼ");
		jb1.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0){
				try{
					//����mysql_jdbc�������򣬱��������JDBCjar��������
					Class.forName("com.mysql.jdbc.Driver");
					jta.setText("�������ݿ������ɹ�\n\n��ʼҡ��\n\n");
				}catch(Exception e){
					jta.setText("�������ݿ�����ʧ��\n");
					e.printStackTrace();
				}
				try{
					Connection connect = DriverManager.getConnection
							("jdbc:mysql://localhost:3306/test_db?useSSL=true","root","123456789");
					t1 = new Thread(new Runnable(){
						Statement sql = connect.createStatement();
						ResultSet res = sql.executeQuery("select * from iot order by id");
						//ʹ�ö�̬����洢�����ݿ���õ�Ԫ�أ���ʵ�ֶ�̬����������ʹ�ü�����������Vector�ڶ��߳��������ٶȸ���Щ��
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
								jta.setCaretPosition(jta.getText().length());//�����������ʾ�����ı���
								if(j==(id.size()-1)){
									j=-1;//�˴���������������֪Ϊ����jΪ-1
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
		JButton jb2 = new JButton("��ȡ");
		jb2.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0){
				try{
					t2 = new Thread(new Runnable(){
						@SuppressWarnings("deprecation")//������õ�stop()����������������ͬ�����Կ����У��˹���ʹ�ò������ͽ��п��Ƹ���
						public void run(){
							try{
								t1.stop();
								//���˴��ſ�νɷ�ѿ��ģ�JAVA��API��û�д�jta�л�ȡָ�����ı��ķ�������������֮���Ȼ���ڻ��з�"\n"��
								//��ʹ��split()������jta�ı�һ��һ�еش������飬��һ��һ�еش�������ü��ɡ�
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
		
		JButton jb3 = new JButton("�����༭");
		jb3.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0){
				new Edit_list().init();;
			}
		});
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);
		con.add(BorderLayout.NORTH,jsp);
		con.add(BorderLayout.SOUTH,jp);
		//ʹ�����ھ�����ʾ
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
		new Main_Window("fool_named",260,300);
	}
}
