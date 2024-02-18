package Kiosk;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt extends JFrame {
    private DefaultTableModel tableModel;
    private JLabel receiptLabel;
    private JLabel totalAmountLabel;

    public Receipt(DefaultTableModel tableModel) { //영수증 창 생성
        this.tableModel = tableModel;

        setTitle("영수증");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel receiptPanel = new JPanel(new BorderLayout());
        receiptPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        receiptLabel = new JLabel();
        receiptLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        receiptPanel.add(receiptLabel, BorderLayout.CENTER);

        add(receiptPanel, BorderLayout.CENTER);

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalAmountLabel = new JLabel();
        totalAmountLabel.setFont(new Font("맑은고딕", Font.BOLD, 14));
        totalPanel.add(totalAmountLabel);

        add(totalPanel, BorderLayout.SOUTH);

        createReceipt();

        exportToCsv(tableModel, "C:\\융프2\\주문목록.csv");

        setSize(400, 580);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createReceipt() {  //영수증 내용 생성
        StringBuilder receiptText = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(new Date());

        receiptText.append("<html><body>");
        receiptText.append("<h2 style='text-align:center;'>영수증</h2>");
        receiptText.append("<table style='width: 280px;'>");
        receiptText.append("<tr>");
        receiptText.append("<th style='width: 60%; text-align: left;'>상품명</th>");
        receiptText.append("<th style='width: 20%; text-align: center;'>수량</th>");
        receiptText.append("<th style='width: 20%; text-align: right;'>가격</th>");
        receiptText.append("</tr>");


        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String itemName = (String) tableModel.getValueAt(i, 0);
            String quantity = tableModel.getValueAt(i, 2).toString();
            String price = tableModel.getValueAt(i, 1).toString();
            String total = String.valueOf(Integer.parseInt(quantity) * Integer.parseInt(price));

            receiptText.append("<tr>");
            receiptText.append("<td style='text-align: left;'>").append(itemName).append("</td>");
            receiptText.append("<td style='text-align: center;'>").append(quantity).append("</td>");
            receiptText.append("<td style='text-align: right;'>").append(total).append("원</td>");
            receiptText.append("</tr>");
        }

        receiptText.append("</table>");
        receiptText.append("<br><br>");
        receiptText.append("<p style='text-align:right;'>총 결제금액: ").append(calculateTotalAmount()).append("원</p>");
        receiptText.append("<p style='text-align:right;'>결제일시: ").append(currentDate).append("</p>");
        receiptText.append("</body></html>");

        receiptLabel.setText(receiptText.toString());
        totalAmountLabel.setText("총 금액: " + calculateTotalAmount() + "원");
    }

    private int calculateTotalAmount() {
        int totalAmount = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            totalAmount += Integer.parseInt(tableModel.getValueAt(i, 3).toString());
        }
        return totalAmount;
    }

    private void exportToCsv(DefaultTableModel tableModel, String csvFilePath) {  //csv파일로 주문내역 저장
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFilePath, true), StandardCharsets.UTF_8))) {
            // 헤더를 추가할 때는 파일이 존재하지 않을 때만 추가
            if (tableModel.getRowCount() > 0) {
                // BOM 쓰기
                writer.write('\ufeff');

                // 헤더 쓰기 (새로 추가하는 경우에만)
                writer.write("상품명,단가,수량,합계\n");
            }

            // 데이터 쓰기
            int rowCount = tableModel.getRowCount();

            for (int i = 0; i < rowCount; i++) {
                String productName = (String) tableModel.getValueAt(i, 0);
                int unitPrice = (int) tableModel.getValueAt(i, 1);
                int quantity = (int) tableModel.getValueAt(i, 2);
                int total = (int) tableModel.getValueAt(i, 3);

                writer.write(String.format("%s,%d,%d,%d원%n", productName, unitPrice, quantity, total));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("파일 생성 실패");
        }
    }

    public static void main(String[] args) {
        new Receipt(new DefaultTableModel());
    }
}