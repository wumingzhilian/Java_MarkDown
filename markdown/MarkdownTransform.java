package markdown;

import java.util.Scanner;

public class MarkdownTransform {
	String out = ""; // 转换结果
    boolean section = false; // 段落标识 
    boolean ul = false; // 无序列表标识
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
				"</html>";//html文件固定模板
		int i=0;
        Scanner sc = new Scanner(System.in);
        String [] line = str.split("\n");
        while (!line[i].isEmpty()) { 
            //String line = sc.nextLine(); // 读取一行

            // 逐行读取，按各区块的格式处理
            if(!line[i].equals("")){ // 删除空行
                switch(line[i].substring(0,1)){
                    case "*": // 无序列表
                        String s1 = unorderList(line[i]);
                        out += s1;
                        break;
                    case "#": // 标题
                        out += title(line[i]);
                        break;
                    default: // 段落
                        String s2 = sections(line[i]);
                        out += s2;
                        break;
                }
            }else{ // 有空行，区块结束
                if(section){ // 段落区块，修改标识
                    section = false;
                }

                if(ul){ // 无序列表区块，修改标识
                    ul = false;
                }
            }
            i++;
        }
            //替换markdown
            String newHTML = HTML.replace("<markdown>", out);
            // 输出结果
            return newHTML;
            //System.out.print(out);
        

    }

    // 强调处理
    public String emphasis(String str){
        String res = "";

        // 将'_'转为<em>标签
        boolean tail = false; // 区分标签的开始和结束
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

    // 超链接转换
    public String transform(String str){
        // 划分链接和内容
        String s[] = new String[2];
        int index = str.indexOf("]");
        s[0] = str.substring(1, index);
        s[1] = str.substring(index+2, str.length()-1);

        return "<a href=\"" + s[1] + "\">" + s[0] + "</a>";
    }

    // 超链接处理
    public String link(String str){
        String res = "";

        int i = 0;
        while(i < str.length()){
            String t = str.substring(i, i+1);

            if(t.equals("[")){
                int index = str.substring(i+1, str.length()).indexOf(")"); // ')'的索引
                res += transform(str.substring(i, i+index+2));
                i += index+1; // 继续搜索
            }else{
                res += t;
            }

            i++;
        }

        return res;
    }

    // 处理无序列表
    public String unorderList(String str){
        String res = "";

        // 是否添加<ul>标签
        res += ul ? "" : "<ul>\n";

        // 修改标识或结束符
        if(!ul) ul = true;
        else out = out.substring(0, out.length()-7) + "\n";

        // 去除'*'和空格
        str = str.substring(1, str.length());
        str = removeBlank(str);

        // 行内处理
        str = emphasis(str);
        str = link(str);

        // 添加内容
        res += "<li>" + str + "</li>\n</ul>";

        return res + "\n";
    }

    // 处理段落
    public String sections(String str){
        String res = "";

        // 是否添加<p>标签
        res += section ? "" : "<p>";

        // 修改标识或结束符
        if(!section) section = true;
        else out = out.substring(0, out.length()-5) + "\n";

        // 行内处理
        str = emphasis(str);
        str = link(str);

        // 紧接之前的行进行追加
        res += str + "</p>";

        return res + "\n";
    }

    // 处理标题区块
    public String title(String str){

        // 统计'#'的个数
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if(!str.substring(i, i+1).equals("#")){
                count = i;
                break;
            }
        }

        // 截取标题的内容（去除所有的'#'和空格）
        str = str.replaceAll("#", "");
        str = removeBlank(str);

        // 行内处理
        str = emphasis(str);
        str = link(str);

        return "<h"+count+">" + str + "</h"+count+">\n";
    }

    // 去除空格，避免把内容中的空格出去，所以不用string.replaceAll(" ","");方法
    public String removeBlank(String str){
        for (int i = 0; i < str.length(); i++) {
            if(!str.substring(i, i+1).equals(" ")){
                return str.substring(i, str.length());
            }
        }

        return "";
    }
}
