package markdown;

import java.util.Scanner;

public class MarkdownTransform {
	String out = ""; // ת�����
    boolean section = false; // �����ʶ 
    boolean ul = false; // �����б��ʶ
    public String run(String str){
    	
		String HTML = "<!DOCTYPE html>\r\n" + 
				"<html lang=\"en\">\r\n" + 
				"<head>\r\n" + 
				"    <meta charset=\"UTF-8\">\r\n" + 
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
				"    <title>Document</title>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"    <markdown>\r\n" + 
				"</body>\r\n" + 
				"</html>";//html�ļ��̶�ģ��
		int i=0;
        Scanner sc = new Scanner(System.in);
        String [] line = str.split("\n");
        while (!line[i].isEmpty()) { 
            //String line = sc.nextLine(); // ��ȡһ��

            // ���ж�ȡ����������ĸ�ʽ����
            if(!line[i].equals("")){ // ɾ������
                switch(line[i].substring(0,1)){
                    case "*": // �����б�
                        String s1 = unorderList(line[i]);
                        out += s1;
                        break;
                    case "#": // ����
                        out += title(line[i]);
                        break;
                    default: // ����
                        String s2 = sections(line[i]);
                        out += s2;
                        break;
                }
            }else{ // �п��У��������
                if(section){ // �������飬�޸ı�ʶ
                    section = false;
                }

                if(ul){ // �����б����飬�޸ı�ʶ
                    ul = false;
                }
            }
            i++;
        }
            //�滻markdown
            String newHTML = HTML.replace("<markdown>", out);
            // ������
            return newHTML;
            //System.out.print(out);
        

    }

    // ǿ������
    public String emphasis(String str){
        String res = "";

        // ��'_'תΪ<em>��ǩ
        boolean tail = false; // ���ֱ�ǩ�Ŀ�ʼ�ͽ���
        for (int i = 0; i < str.length(); i++) {
            String t = str.substring(i, i+1);

            if(t.equals("_")){
                res += tail ? "</em>" : "<em>";
                tail = !tail;
            }else{
                res += t;
            }
        }

        return res;
    }

    // ������ת��
    public String transform(String str){
        // �������Ӻ�����
        String s[] = new String[2];
        int index = str.indexOf("]");
        s[0] = str.substring(1, index);
        s[1] = str.substring(index+2, str.length()-1);

        return "<a href=\"" + s[1] + "\">" + s[0] + "</a>";
    }

    // �����Ӵ���
    public String link(String str){
        String res = "";

        int i = 0;
        while(i < str.length()){
            String t = str.substring(i, i+1);

            if(t.equals("[")){
                int index = str.substring(i+1, str.length()).indexOf(")"); // ')'������
                res += transform(str.substring(i, i+index+2));
                i += index+1; // ��������
            }else{
                res += t;
            }

            i++;
        }

        return res;
    }

    // ���������б�
    public String unorderList(String str){
        String res = "";

        // �Ƿ����<ul>��ǩ
        res += ul ? "" : "<ul>\n";

        // �޸ı�ʶ�������
        if(!ul) ul = true;
        else out = out.substring(0, out.length()-7) + "\n";

        // ȥ��'*'�Ϳո�
        str = str.substring(1, str.length());
        str = removeBlank(str);

        // ���ڴ���
        str = emphasis(str);
        str = link(str);

        // �������
        res += "<li>" + str + "</li>\n</ul>";

        return res + "\n";
    }

    // �������
    public String sections(String str){
        String res = "";

        // �Ƿ����<p>��ǩ
        res += section ? "" : "<p>";

        // �޸ı�ʶ�������
        if(!section) section = true;
        else out = out.substring(0, out.length()-5) + "\n";

        // ���ڴ���
        str = emphasis(str);
        str = link(str);

        // ����֮ǰ���н���׷��
        res += str + "</p>";

        return res + "\n";
    }

    // �����������
    public String title(String str){

        // ͳ��'#'�ĸ���
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if(!str.substring(i, i+1).equals("#")){
                count = i;
                break;
            }
        }

        // ��ȡ��������ݣ�ȥ�����е�'#'�Ϳո�
        str = str.replaceAll("#", "");
        str = removeBlank(str);

        // ���ڴ���
        str = emphasis(str);
        str = link(str);

        return "<h"+count+">" + str + "</h"+count+">\n";
    }

    // ȥ���ո񣬱���������еĿո��ȥ�����Բ���string.replaceAll(" ","");����
    public String removeBlank(String str){
        for (int i = 0; i < str.length(); i++) {
            if(!str.substring(i, i+1).equals(" ")){
                return str.substring(i, str.length());
            }
        }

        return "";
    }
}
