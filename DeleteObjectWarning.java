package fool_named;

import java.awt.*;
import javax.swing.*;

public class DeleteObjectWarning extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeleteObjectWarning(){
		setTitle("Error");
		setSize(200,100);
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		JLabel jl1 = new JLabel("删除失败，原因可能如下:");
		con.add(jl1,BorderLayout.NORTH);
		JLabel jl2 = new JLabel("1.对象不存在；");
		con.add(jl2,BorderLayout.CENTER);
		JLabel jl3 = new JLabel("2.学号不能为空");
		con.add(jl3,BorderLayout.SOUTH);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		int windowWidth = this.getWidth();
		int windowHeight = this.getHeight();
		this.setLocation((width-windowWidth)/2,(height-windowHeight)/2);
	}
}
