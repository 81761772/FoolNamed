package fool_named;
//����GUI��ذ�
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
//ʹ����̳�JFrame��
public class Main_Window extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Main_Window(String Title,int Width,int Height){
		//������������con���ı��������jta���ɹ���������jta��������jp
		Container con = getContentPane();
		JTextArea jta = new JTextArea(12,10);
		//ע��˴�����������ò��ֹ���������
		JScrollPane jsp = new JScrollPane(jta);
		JPanel jp = new JPanel();
		//���ò��ֹ�����
		con.setLayout(new BorderLayout());
		jp.setLayout(new FlowLayout());
		//������ťjb1���󣬲��������ڲ����ж��嶯���¼�������
		JButton jb1 = new JButton("��ʼ");
		jb1.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0){
				//�������ݿ⣬��ʧ���׳��쳣��Ϣ
				try{
					//����mysql_jdbc�������򣬱��������JDBCjar��
					Class.forName("com.mysql.jdbc.Driver");
					jta.setText("�ɹ��������ݿ�\n\n��ʼҡ��\n\n");
				}catch(Exception e){
					jta.setText("�������ݿ�ʧ��\n");
					e.printStackTrace();
				}
				try{
					//��¼���ݿ�
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
		JButton jb2 = new JButton("��ȡ");
		jb2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				try{
					Thread t2 = new Thread(new Runnable(){
						public void run(){
							try{
								//�˴�ʹ��stop()�ж��߳�t1
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
				new Edit_list();
			}
		});
		//������
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
		//���ô����������
		setTitle(Title);
		setSize(Width,Height);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args){
		new Main_Window("fool_named",260,300);
	}
}
