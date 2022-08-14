package xyz.xingfeng.www.textures;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {
    /**
     * 窗口大小
     */
    int fcHeight = 500;
    int fcWidth = 700;

    /**
     * 升级按钮
     */
    JButton but1 = new JButton("开始升级");
    JButton but2 = new JButton("确定");
    JButton but3 = new JButton("开始降级");

    /**
     * 提示语
     */
    JLabel jl1 = new JLabel("请将材质包路径粘贴到此处");
    JLabel jl2 = new JLabel("<html><body>提示:建议将材质包复制一份再进行升级，毕竟旧的材质包还是有用的</body></html>",SwingConstants.CENTER);
    JLabel jl3 = new JLabel("识别错误?");
    static JLabel jl4 = new JLabel("");

    /**
     * 输入框
     */
    public static JTextField text = new JTextField();

    public Window(){
        // 获取屏幕宽高
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        // 设置窗口名称
        this.setTitle("DT_刑风_资源包升级程序");
        // 设置窗口位置大小
        setBounds(scrSize.width / 2 - fcWidth / 2, scrSize.height / 2 - fcHeight / 2, fcWidth, fcHeight);
        // 是否显示窗口体
        setVisible(true);
        // 窗体不能修改大小
        setResizable(false);
        // 窗体关闭后自动结束后台
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 获取布局
        Container cn = this.getContentPane();
        // 取消布局
        cn.setLayout(null);

        //文本区域
        jl1.setBounds(32,16,300,50);
        jl1.setFont(new Font("",Font.BOLD,18));
        cn.add(jl1);
        jl2.setBounds(32, 300,500,200);
        jl2.setFont(new Font("",Font.BOLD,20));
        cn.add(jl2);
        jl3.setFont(new Font("",Font.BOLD,15));
        cn.add(jl3);
        jl4.setFont(new Font("",Font.BOLD,15));
        cn.add(jl4);

        //文本框区域
        text.setBounds(32,58,550,30);
        cn.add(text);

        //按键区域
        but2.setBounds(600,58,60,30);
        cn.add(but2);
        but2.addActionListener(new but2());
        but1.setBounds(32,96,0,0);
        but3.setBounds(150,96,0,0);
        cn.add(but1);
        cn.add(but3);
        //升级材质包
        but1.addActionListener(new but1());
        //降级材质包
        but3.addActionListener(new but3());

    }

    class but1 implements ActionListener{

        /**
         * 升级
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Thread thread = new Thread(new Data(1));
            thread.start();
        }
    }

    class but3 implements ActionListener{

        /**
         * 降级
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Thread thread = new Thread(new Data(2));
            thread.start();
        }
    }

    class but2 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //检查文件路径是否有效
            File file = new File(text.getText()+"\\assets\\minecraft\\textures\\");
            File mcmeta = new File(text.getText() + "\\pack.mcmeta");
            if (file.exists()){
                if (mcmeta.exists()){
                    FileDo fileDo = new FileDo(mcmeta);
                    try {
                        String s = fileDo.copy();
                        JSONObject jsonObject = new JSONObject(s);
                        int i = jsonObject.getJSONObject("pack").getInt("pack_format");
                        if (i < 4){
                            but1.setBounds(32,96,100,50);
                            jl3.setBounds(140,90,100,50);
                            jl3.addMouseListener(new jl3());
                            text.setEnabled(false);
                            but2.setEnabled(false);
                        }else {
                            but3.setBounds(32,96,100,50);
                            jl3.setBounds(140,90,100,50);
                            jl3.addMouseListener(new jl3());
                            text.setEnabled(false);
                            but2.setEnabled(false);
                        }
                    } catch (IOException ioException) {
                        JOptionPane.showOptionDialog(null,"发生未知错误",
                            "错误",JOptionPane.OK_CANCEL_OPTION,JOptionPane.ERROR_MESSAGE,
                            null,new String[]{"确定"},"确定");
                        ioException.printStackTrace();

                    }
                }else {
                    jl3.setBounds(32,160,400,50);
                    jl3.setText("未能识别版本，请手动选择升级或降级");
                    but1.setBounds(32,96,100,50);
                    but3.setBounds(170,96,100,50);
                    System.out.println(file.getName());
                }
            }else {
                JOptionPane.showOptionDialog(null,"请输入正确的地址",
                        "错误",JOptionPane.OK_CANCEL_OPTION,JOptionPane.ERROR_MESSAGE,
                        null,new String[]{"确定"},"确定");
            }
        }
    }

    class jl3 implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            Dimension d1 = but1.getSize();
            Dimension d2 = but3.getSize();
            if (d1.width == 0){
                but1.setBounds(32,96,100,50);
                but3.setBounds(32,96,0,0);
                jl3.setText("已切换");
            }else {
                but3.setBounds(32,96,100,50);
                but1.setBounds(32,96,0,0);
                jl3.setText("已切换");
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            jl3.setFont(new Font("",Font.BOLD,18));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            jl3.setFont(new Font("",Font.BOLD,15));
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jl3.setFont(new Font("",Font.BOLD,18));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            jl3.setFont(new Font("",Font.BOLD,15));
        }
    }
}
