package xyz.xingfeng.www.textures;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Data implements Runnable{

    int code;

    public Data(int i){
        this.code = i;
    }

    /**
     * 升级程序
     */
    public void data_up(){
        Window.jl4.setText("开始执行");
        Window.jl4.setBounds(32,170,200,50);
        //先改block的
        String path = Window.text.getText() + "\\assets\\minecraft\\textures\\";
        File file = new File(path + "blocks");
        if (file.exists()){
            //定向到配置文件
            File file1 = new File("block.txt");
            FileDo fileDo = new FileDo(file1);
            //读取配置文件中有多少行
            int hangshu = 0;
            try {
                hangshu = fileDo.hangshu();
            } catch (IOException ioException) {
                //出现错误退出
                ioException.printStackTrace();
                JOptionPane.showOptionDialog(null,"发生未知错误",
                        "错误",JOptionPane.OK_CANCEL_OPTION,JOptionPane.ERROR_MESSAGE,
                        null,new String[]{"确定"},"确定");
                return;
            }
            String s = path + "blocks\\";
            //开始重命名
            for (int i = 1; i <= hangshu; i+=2){
                if (new File(s + fileDo.tiqu(i)).exists()) {
                    new File(s + fileDo.tiqu(i)).renameTo(new File(s + fileDo.tiqu(i + 1)));
                }
            }
            file.renameTo(new File(path+"block"));
        }
        //然后是方块部分
        file = new File(path + "items");
        if (file.exists()){
            File file1 = new File("item.txt");
            FileDo fileDo = new FileDo(file1);
            int hangshu = 0;
            try {
                hangshu = fileDo.hangshu();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                JOptionPane.showOptionDialog(null,"发生未知错误",
                        "错误",JOptionPane.OK_CANCEL_OPTION,JOptionPane.ERROR_MESSAGE,
                        null,new String[]{"确定"},"确定");
                return;
            }
            String s = path + "items\\";
            for (int i = 1; i <= hangshu; i+=2){
                if (new File(s + fileDo.tiqu(i)).exists()) {
                    new File(s + fileDo.tiqu(i)).renameTo(new File(s + fileDo.tiqu(i + 1)));
                }
            }
            file.renameTo(new File(path+"item"));
        }
        //最后把版本号改掉
        File mcmeta = new File(Window.text.getText() + "\\pack.mcmeta");
        if (mcmeta.exists()){
            FileDo fileDo1 = new FileDo(mcmeta);
            try {
                String s1 = fileDo1.copy();
                String s2 = s1.substring(0,s1.indexOf("\"pack_format\""));
                s1 = s1.substring(s1.indexOf("\"pack_format\""));
                String s3 = s1.substring(s1.indexOf(","));
                String s4 = "\"pack_format\": 4";
                String 最终信息 = s2 + s4 + s3;
                FileWriter fileWriter = new FileWriter(mcmeta);
                fileWriter.write(最终信息);
                fileWriter.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        Window.jl4.setText("完成!");
    }



    /**
     * 降级程序
     */
    public void data_down(){
        Window.jl4.setText("开始执行");
        Window.jl4.setBounds(32,170,200,50);
        //先改block的
        String path = Window.text.getText() + "\\assets\\minecraft\\textures\\";
        File file = new File(path + "block");
        System.out.println(path + "block");
        if (file.exists()){
            //定向到配置文件
            File file1 = new File("block.txt");
            FileDo fileDo = new FileDo(file1);
            //读取配置文件中有多少行
            int hangshu = 0;
            try {
                hangshu = fileDo.hangshu();
            } catch (IOException ioException) {
                //出现错误退出
                ioException.printStackTrace();
                JOptionPane.showOptionDialog(null,"发生未知错误",
                        "错误",JOptionPane.OK_CANCEL_OPTION,JOptionPane.ERROR_MESSAGE,
                        null,new String[]{"确定"},"确定");
                return;
            }
            String s = path + "block\\";
            //开始重命名
            for (int i = 1; i <= hangshu; i+=2){
                if (new File(s + fileDo.tiqu(i+1)).exists()) {
                    new File(s + fileDo.tiqu(i+1)).renameTo(new File(s + fileDo.tiqu(i)));
                }
            }
            file.renameTo(new File(path+"blocks"));
        }
        //然后是方块部分
        file = new File(path + "item");
        if (file.exists()){
            //读取配置文件
            File file1 = new File("item.txt");
            FileDo fileDo = new FileDo(file1);
            int hangshu = 0;
            try {
                hangshu = fileDo.hangshu();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                JOptionPane.showOptionDialog(null,"发生未知错误",
                        "错误",JOptionPane.OK_CANCEL_OPTION,JOptionPane.ERROR_MESSAGE,
                        null,new String[]{"确定"},"确定");
                return;
            }
            String s = path + "item\\";
            //开始重命名
            for (int i = 1; i <= hangshu; i+=2){
                if (new File(s + fileDo.tiqu(i+1)).exists()) {
                    new File(s + fileDo.tiqu(i+1)).renameTo(new File(s + fileDo.tiqu(i + 1)));
                }
            }
            file.renameTo(new File(path+"items"));
            //最后把版本号改掉
            File mcmeta = new File(Window.text.getText() + "\\pack.mcmeta");
            if (mcmeta.exists()){
                FileDo fileDo1 = new FileDo(mcmeta);
                try {
                    //构建最后生成的信息
                    String s1 = fileDo1.copy();
                    String s2 = s1.substring(0,s1.indexOf("\"pack_format\""));
                    s1 = s1.substring(s1.indexOf("\"pack_format\""));
                    String s3 = s1.substring(s1.indexOf(","));
                    String s4 = "\"pack_format\": 3";
                    String 最终信息 = s2 + s4 + s3;
                    //新建流
                    FileWriter fileWriter = new FileWriter(mcmeta);
                    //写入
                    fileWriter.write(最终信息);
                    //关闭流
                    fileWriter.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        Window.jl4.setText("完成!");
    }

    @Override
    public void run() {
        if (code == 1){
            data_up();
        }else if (code == 2){
            data_down();
        }
    }
}
