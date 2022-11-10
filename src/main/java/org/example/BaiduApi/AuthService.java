package org.example.BaiduApi;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.example.BaiduApi.*;

public class AuthService {
    // static baiduApi baiduapi;
    static Sample sample = new Sample();

    public static void main(String[] args) {

        JFrame frame = new ImageVieweFrame();
        frame.setTitle("文字识别");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static class ImageVieweFrame extends JFrame {
        private JButton warning1;
        private JButton warning2;
        private JButton upload1;
        private JButton upload2;
        private JButton toView_hidden1;
        private JButton toView_hidden2;
        private JFileChooser chooser;
        private File file = null;
        private String name;

        public ImageVieweFrame() {

            this.setLocationRelativeTo(null);
            this.setLocation(300, 100);
            this.setSize(1024, 600);

            this.addMouseListener(new MouseAdapter() {
                @SuppressWarnings("unchecked")
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (file == null) {//
                        warning1.doClick();
                    } else {
                        String dirctory = file.getParent();
                        File file = new File(dirctory);
                        // 测试此抽象路径名表示的文件是否为目录。
                        if (file.isDirectory()) { // 是目录
                            File[] files = file.listFiles();
                            @SuppressWarnings("rawtypes")
                            final List list = new ArrayList<>();
                            if (files[0].getName().endsWith(".jpg")) {
                                list.add(files[0].getPath());
                            }
                            // 显示文件地址的点击事件
                            toView_hidden1.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JOptionPane.showMessageDialog(warning1, "文件地址为 :::=> " + list, "提示",
                                            JOptionPane.PLAIN_MESSAGE);
                                }
                            });

                        }
                    }
                }
            });

            // 使用给定的文件作为路径构造JFileChooser实例
            chooser = new JFileChooser("E:\\");
            // 切换当前进程的当前工作目录
            chooser.setCurrentDirectory(new File("D:\\Study\\Code\\Java\\VsCode\\rjgz\\test2\\src\\main\\images"));
            //
            Container container = this.getContentPane();
            container.setLayout(null);

            // 按钮1
            upload1 = new JButton("上传图片");
            toView_hidden1 = new JButton();
            warning1 = new JButton();
            // 按钮2
            upload2 = new JButton("上传网络验证");
            toView_hidden2 = new JButton();
            warning2 = new JButton();

            final JLabel label = new JLabel("");

            // 警告弹窗
            warning1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // new MyDialog("请上传文件！！！");
                    JOptionPane.showMessageDialog(warning1, "请上传文件！！！ ", "提示 ", 1);
                }
            });

            final Dimension d = this.getSize();

            // 按钮相关设置
            // 设置隐藏查看区大小和位置

            toView_hidden1.setSize(d.width - 300, d.height - 200);
            toView_hidden1.setLocation(0, 0);
            toView_hidden1.setBackground(Color.white);
            container.add(toView_hidden1);

            // 设置上传按钮的大小和位置，并添加到容器中
            upload1.setSize(150, 40);
            upload1.setLocation(d.width - 900, d.height - 142);
            upload1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
            container.add(upload1);

            upload2.setSize(200, 40);
            upload2.setLocation(d.width - 400, d.height - 142);
            upload2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
            container.add(upload2);

            label.setBounds(725, 0, d.width - 100, d.height - 100);
            container.add(label);

            // 上传按钮点击事件
            upload1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            "JPG & PNG Images", "jpg", "png");
                    chooser.setFileFilter(filter);

                    int result = chooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        file = chooser.getSelectedFile();

                        name = file.getPath();
                        System.out.println(name);
                        Image image = getToolkit().getImage(name);
                        image = image.getScaledInstance(d.width - 300, d.height - 200, image.SCALE_DEFAULT);
                        toView_hidden1.setIcon(new ImageIcon(image));
                    }
                }
            });

            upload2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // jt.setText("经过网络查询姓名是：" + sample.idcard(name) + "\n");
                    // label.setText("经过网络查询性别是：" + sample.idcard1(name) + "\n");
                    // label.setText("经过网络查询民族是：" + sample.idcard2(name) + "\n");
                    // label.setText("经过网络查询住址是：" + sample.idcard3(name) + "\n");
                    // label.setText("经过网络查询身份证号是：" + sample.idcard4(name));

                    label.setText("<html><body>上传身份证信息为：<br>" +
                            "姓名为：" + sample.idcard(name) + "<br>性别为：" + sample.idcard1(name) + "<br>民族是："
                            + sample.idcard2(name) + "<br>住址为：" + sample.idcard3(name) + "<br>身份证号是:"
                            + sample.idcard4(name) + "<body></html>");

                    label.setFont(new Font(Font.DIALOG, 1, 16));

                }
            });

        }
    }
}