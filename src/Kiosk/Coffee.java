package Kiosk;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Coffee extends JFrame {
    String type[] = {"커피", "프라푸치노", "티", "etc"};

    String coffee[] = {"americano", "cafelatte", "macchiato", "mocha"};
    String frappuccino[] = {"esf_frap", "java_frap", "mat_frap", "wtiger_frap"};
    String tea[] = {"earlgrey", "green", "chamomile", "mint"};

    String c_name[] = {"아메리카노","카페라떼","카라멜 마키아또","카페모카"};
    String f_name[] = {"에스프레소 프라푸치노","자바칩 프라푸치노","말차 프라푸치노","화이트 타이거 프라푸치노"};
    String t_name[] = {"얼그레이 티","녹차","캐모마일 티","민트 티"};

    int c_price[] = {4500, 5000, 5900, 5500};
    int f_price[] = {5500, 6300, 6300, 6900};
    int t_price[] = {4500, 5300, 4500, 4500};

    JPanel coffeePanel;
    JPanel frappuccinoPanel;
    JPanel teaPanel;
    JPanel etcPanel;

    private DefaultTableModel tableModel;
    private JLabel totalAmountLabel;

    private static JTextField cnText;
    private static JTextField cpText;
    private static JRadioButton satis;
    private static JTextArea improveText;

    private static int surveyCount = 1;

    public Coffee() {
        setTitle("starbucks_kiosk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setBackground(Color.white);
        c.setLayout(new FlowLayout());


        Font btnfont = new Font("나눔고딕", Font.BOLD, 18);

        // starbucks 로고 삽입 패널--------------------------------------------
        JPanel northpanel = new JPanel();
        northpanel.setBackground(new Color(45, 95, 68));
        northpanel.setPreferredSize(new Dimension(900, 125));
        ImageIcon logo = new ImageIcon("C:\\images\\sb_logo2.png");
        JLabel logolabel = new JLabel(logo);
        northpanel.add(logolabel);
        c.add(northpanel);

        // 메뉴 삽입 패널------------------------------------------------------
        JPanel menupanel = new JPanel(new FlowLayout());
        menupanel.setBackground(Color.gray);
        menupanel.setPreferredSize(new Dimension(520, 470));

        coffeePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,50,10));
        coffeePanel.setBackground(Color.gray);
        coffeePanel.setPreferredSize(new Dimension(520, 470));

        frappuccinoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,50,10));
        frappuccinoPanel.setBackground(Color.gray);
        frappuccinoPanel.setPreferredSize(new Dimension(520, 470));

        teaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,50,10));
        teaPanel.setBackground(Color.gray);
        teaPanel.setPreferredSize(new Dimension(520, 470));

        etcPanel = new JPanel();
        etcPanel.setLayout(null);
        etcPanel.setBackground(Color.gray);
        etcPanel.setPreferredSize(new Dimension(520, 470));

        //etcPanel 내용-------------------------------------------------------
        cnText = new JTextField("  ");
        cpText = new JTextField("  ");
        satis = new JRadioButton("만족");
        improveText = new JTextArea();
        Font etcfont = new Font("나눔고딕", Font.BOLD, 15);
        JLabel subLabel = new JLabel("고객 설문조사");
        subLabel.setFont(new Font("나눔고딕", Font.BOLD, 18));
        subLabel.setBounds(180, 20, 200, 30);
        JLabel customerName = new JLabel("성함 : "); //고객이름
        customerName.setFont(etcfont);
        customerName.setBounds(70,70,100,30);
        JLabel customerPhone = new JLabel("전화번호 : "); //고객전화번호
        customerPhone.setFont(etcfont);
        customerPhone.setBounds(70,120,100,30);
        cnText.setPreferredSize(new Dimension(200,30));
        cnText.setBounds(180,80,200,30);
        cpText.setPreferredSize(new Dimension(200,30));
        cpText.setBounds(180,120,200,30);
        JLabel satisLabel = new JLabel("서비스에 만족하십니까? ");
        satisLabel.setFont(etcfont);
        satisLabel.setBounds(70,170,300,30);
        ButtonGroup satisGroup = new ButtonGroup();
        satis.setBounds(150,210,100,30);
        JRadioButton dissatis = new JRadioButton("불만족");
        dissatis.setBounds(250,210,100,30);
        satisGroup.add(satis);
        satisGroup.add(dissatis);
        JLabel improveLabel = new JLabel("개선되었으면 하는 점을 자유롭게 써주세요");
        improveLabel.setFont(etcfont);
        improveLabel.setBounds(70,250,400,30);
        improveText.setFont(etcfont);
        improveText.setBounds(70,290,400,60);
        improveText.setLineWrap(true);
        JScrollPane sp = new JScrollPane(improveText);
        sp.setBounds(70, 290, 350, 60);
        JButton submit = new JButton("제출하기");
        submit.setFont(etcfont);
        submit.setBounds(180,370,100,30);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSurveyToFile();
            }
        });


        etcPanel.add(subLabel);
        etcPanel.add(customerName);
        etcPanel.add(cnText);
        etcPanel.add(customerPhone);
        etcPanel.add(cpText);
        etcPanel.add(satisLabel);
        etcPanel.add(satis);
        etcPanel.add(dissatis);
        etcPanel.add(improveLabel);
        etcPanel.add(sp);
        etcPanel.add(submit);

        menupanel.add(coffeePanel);
        menupanel.add(frappuccinoPanel);
        menupanel.add(teaPanel);
        menupanel.add(etcPanel);

        showPanel(type[0]);

        // 버튼 삽입 패널-----------------------------------------------------------
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonpanel.setBackground(Color.DARK_GRAY);
        buttonpanel.setPreferredSize(new Dimension(520, 80));

        for (String aType : type) {
            JButton button = new JButton(aType);
            button.setFont(new Font("나눔고딕",Font.BOLD,13));
            button.setPreferredSize(new Dimension(100, 50));
            button.addActionListener(e -> showPanel(aType));
            buttonpanel.add(button);
        }

        c.add(buttonpanel);

        //메뉴 버튼 생성--------------------------------------------------------------
        for (int i = 0; i < coffee.length; i++) {
            JButton coffeebutton = createMenuButton("C:\\images\\" + coffee[i] + ".png", c_name[i], c_price[i]);
            coffeePanel.add(coffeebutton);
        }
        for (int i = 0; i < frappuccino.length; i++) {
            JButton frapbutton = createMenuButton("C:\\images\\" + frappuccino[i] + ".png", f_name[i], f_price[i]);
            frappuccinoPanel.add(frapbutton);
        }
        for (int i = 0; i < tea.length; i++) {
            JButton teabutton = createMenuButton("C:\\images\\" + tea[i] + ".png", t_name[i], t_price[i]);
            teaPanel.add(teabutton);
        }

        //주문목록 삽입 패널--------------------------------------------------------------
        JPanel orderpanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,18));
        orderpanel.setBackground(Color.DARK_GRAY);
        orderpanel.setPreferredSize(new Dimension(350, 80));
        JLabel orderList = new JLabel("주문 목록");
        orderList.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        orderList.setForeground(Color.white);
        orderpanel.add(orderList);
        c.add(orderpanel);
        c.add(menupanel);

        // 가격 패널--------------------------------------------------------------------
        JPanel pricepanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,18));
        pricepanel.setBackground(Color.gray);
        pricepanel.setPreferredSize(new Dimension(350, 470));
        tableModel = new DefaultTableModel();
        tableModel.addColumn("상품명");
        tableModel.addColumn("단가");
        tableModel.addColumn("수량");
        tableModel.addColumn("합계");
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(340, 300));

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(160);
        columnModel.getColumn(1).setPreferredWidth(70);
        columnModel.getColumn(2).setPreferredWidth(60);
        columnModel.getColumn(3).setPreferredWidth(70);

        totalAmountLabel = new JLabel("총 금액: 0원");
        totalAmountLabel.setForeground(Color.white);
        totalAmountLabel.setFont(btnfont);

        pricepanel.add(scrollPane);

        //플러스, 마이너스 버튼 생성
        JButton minusButton = new JButton("-");
        minusButton.setPreferredSize(new Dimension(50, 30));
        minusButton.setFont(btnfont);
        JButton plusButton = new JButton("+");
        plusButton.setPreferredSize(new Dimension(50, 30));
        plusButton.setFont(btnfont);

        minusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int quantity = (int) tableModel.getValueAt(selectedRow, 2);
                    if (quantity > 1) {
                        tableModel.setValueAt(quantity - 1, selectedRow, 2);
                        updateTable(selectedRow);
                        updateTotalAmount();
                    }
                    else {
                        // 수량이 1일 때 - 버튼을 누르면 해당 행 삭제
                        tableModel.removeRow(selectedRow);
                        updateTotalAmount();
                    }
                }
            }
        });

        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int quantity = Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString());
                    tableModel.setValueAt(quantity + 1, selectedRow, 2);
                    updateTable(selectedRow);
                    updateTotalAmount();
                }
            }
        });

        pricepanel.add(minusButton);
        pricepanel.add(plusButton);
        pricepanel.add(totalAmountLabel);

        //결제,초기화버튼 패널 ---------------------------------------------------------------
        JPanel paypanel = new JPanel(new FlowLayout(FlowLayout.CENTER,30,20));
        paypanel.setBackground(Color.gray);
        JButton payButton = new JButton("결제");
        payButton.setBackground(Color.red);
        payButton.setFont(btnfont);
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 영수증 창 열기
                new Receipt(tableModel);
                // 주문 목록 초기화
                tableModel.setRowCount(0);
                updateTotalAmount();
            }
        });

        JButton resetButton = new JButton("초기화");
        resetButton.setFont(btnfont);
        //초기화버튼 누를 시 동작 추가. 테이블 비우기
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 주문 목록 초기화
                tableModel.setRowCount(0);
                updateTotalAmount();
            }
        });

        paypanel.add(resetButton);
        paypanel.add(payButton);
        pricepanel.add(paypanel);

        c.add(pricepanel);

        // dgu 로고 패널--------------------------------------------------------------
        JPanel dgupanel = new JPanel();
        dgupanel.setBackground(Color.white);
        dgupanel.setPreferredSize(new Dimension(900, 70));
        ImageIcon dgu = new ImageIcon(new ImageIcon("C:\\images\\dgu_logo.png").getImage().getScaledInstance(130, 55, Image.SCALE_SMOOTH));
        JLabel dgulabel = new JLabel(dgu);
        dgupanel.add(dgulabel);
        c.add(dgupanel);

        // setSize
        setSize(900, 800);
        setVisible(true);
    }

    public JButton createMenuButton(String imagePath, String text, int price) { //이미지 경로, 메뉴이름, 메뉴가격을 바탕으로 메뉴버튼을 생성
        JButton button = new JButton();
        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH));
        button.setIcon(icon);
        button.setFont(new Font("나눔고딕",Font.BOLD,13));
        button.setText("<html><center>" + text + "<br>" + price + "원</center></html>");
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToOrderList(text, price);
            }
        });
        return button;
    }

    public void addToOrderList(String menuName, int menuPrice) { //메뉴버튼 클릭 시 주문목록 테이블에 항목을 추가하는 메소드
        // 현재 선택된 행 찾기
        int selectedRow = -1;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(menuName)) {
                selectedRow = i;
                break;
            }
        }
        if (selectedRow == -1) {
            // 테이블에 새로운 행 추가
            Object[] newRow = { menuName, menuPrice, 1, menuPrice };
            tableModel.addRow(newRow);
        } else {
            // 이미 있는 경우 수량 증가 및 합계 갱신
            int quantityStr = (int) tableModel.getValueAt(selectedRow, 2);
            int newQuantity = quantityStr + 1;
            int total = newQuantity * menuPrice;

            tableModel.setValueAt(newQuantity, selectedRow, 2);
            tableModel.setValueAt(total, selectedRow, 3);
        }
        updateTotalAmount();
    }

    public void updateTotalAmount() { //총 금액을 업데이트
        int totalAmount = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            totalAmount += Integer.parseInt(tableModel.getValueAt(i, 3).toString());
        }
        totalAmountLabel.setText("총 금액: " + totalAmount + "원");
    }

    public void updateTable(int row) { //주문목록 테이블을 업데이트
        try {
            int price = (int) tableModel.getValueAt(row, 1);
            int quantity = (int) tableModel.getValueAt(row, 2);
            int total = price * quantity;
            tableModel.setValueAt(Integer.toString(total), row, 3);
            updateTotalAmount();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static void saveSurveyToFile() {
        try {
            // 파일 경로 및 이름 설정
            String fileName = "C:\\융프2\\설문" + surveyCount + ".txt";
            File file = new File(fileName);

            // 파일 존재 여부 확인
            if (!file.exists()) {
                file.createNewFile();
            }
            // 파일에 내용 작성
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("성함: " + cnText.getText() + "\n");
            bw.write("전화번호: " + cpText.getText() + "\n");
            bw.write("서비스: " + (satis.isSelected() ? "만족" : "불만족") + "\n");
            bw.write("개선점: " + improveText.getText() + "\n");
            bw.close();
            // 설문 번호 증가
            surveyCount++;

            // 저장 완료 메시지 팝업
            JOptionPane.showMessageDialog(null, "저장되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
            // 저장 후에 입력된 내용 초기화
            cnText.setText("  ");
            cpText.setText("  ");
            satis.setSelected(false);
            improveText.setText("");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showPanel(String panelType) { //버튼 누를때 패널 바뀌게 해주는 메소드
        coffeePanel.setVisible(false);
        frappuccinoPanel.setVisible(false);
        teaPanel.setVisible(false);
        etcPanel.setVisible(false);

        switch (panelType) {
            case "커피":
                coffeePanel.setVisible(true);
                break;
            case "프라푸치노":
                frappuccinoPanel.setVisible(true);
                break;
            case "티":
                teaPanel.setVisible(true);
                break;
            case "etc":
                etcPanel.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Coffee();
    }
}
