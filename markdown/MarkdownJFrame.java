package markdown;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MarkdownJFrame extends JFrame{
	JLabel l_markdown,l_html,l_title;
	JTextArea t_markdown,t_html,t_title;
	JButton b_transform;
	public MarkdownJFrame() {
		super("Markdown->HTML");
		setBounds(500,150,600,600);
		init();
	    setVisible(true);
	}
	public void init() {
		
		t_markdown = new JTextArea();
		t_html = new JTextArea();
		t_title = new JTextArea();
		
		l_markdown = new JLabel("请输入Markdown轻量级标记语言：");
		l_html = new JLabel();
		l_title = new JLabel("请输入转化后HTML标题：");
		
		b_transform = new JButton("转换");
		b_transform.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MarkdownTransform mt = new MarkdownTransform();
				HTMLInput hi = new HTMLInput();
				
				String markdown = t_markdown.getText().toString();
				String title = t_title.getText();
				String newhtml = null;
				//获取用户输入内容
				String html = mt.run(markdown);
				//markdown转换
				if(!title.isEmpty()) {
					newhtml = html.replace("Document", title);
				}
				//更改html标题
				t_html.setText(newhtml);
				hi.inputTxt(newhtml);
				//输出html文件
			}
			
		});
		
		setLayout(new GridLayout(2,2,5,5));
		add(t_markdown);
		add(t_html);
		add(t_title);
		add(b_transform);
		
		//setLayout(null);
		
	}
	
}
