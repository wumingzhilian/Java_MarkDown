package markdown;

import java.io.*;

public class HTMLInput {
	public void inputTxt(String str) {
		//MarkdownTransform mt = new MarkdownTransform();
		
		//BufferedReader br = null;
		BufferedWriter bw = null;
		BufferedReader br1 = null;
		try {
			//br = new BufferedReader(new InputStreamReader(System.in));
			bw = new BufferedWriter(new FileWriter("D:/new/123.html"));
			br1 = new BufferedReader(new FileReader("D:/new/123.html"));
			//�ļ�·��
			
			//String str = br.readLine();
			//String out = mt.run(str);
			bw.write(str);
			bw.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				//br.close();
				bw.close();
				br1.close();
				//�ر�
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
