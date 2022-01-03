package n_Queens;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame implements ActionListener{
	private JLabel la = new JLabel("N-Queens Problem");
	private JLabel maker = new JLabel("- Made by. MSJ -");
	private JButton start = new JButton("����");
	private JButton exit = new JButton("����");
	
	public Main() {
		setTitle("N-Queens Puzzle"); // ������ Ÿ��Ʋ ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ������ �����찡 ���� �� ���α׷� ����
		Container c = getContentPane(); // �������� ����Ʈ�� �˾Ƴ���
		c.setLayout(null); // ��ġ ��ġ ���� ����
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize(); // ���� ȭ���� �ػ� ��������
		
		// ��ü ��ġ �� ũ�� ����
		la.setFont(new Font("Serif", Font.BOLD, 45));
		la.setBounds(60, 0, 400, 100);
		maker.setFont(new Font("Serif", Font.BOLD, 15));
		maker.setBounds(350, 220, 150, 50);
		start.setBounds(190, 120, 100, 40);
		exit.setBounds(190, 180, 100, 40);
		
		// ��ư�� ���� �̺�Ʈ ������ �ޱ�
		start.addActionListener(this);
		exit.addActionListener(this);
		
		// �����̳ʿ� �߰�
		c.add(la);
		c.add(maker);
		c.add(start);
		c.add(exit);
		
		// ������ ��ġ, ũ��, ���̱� �� ����
		setLocation(res.width/3, res.height/3); 
		setSize(500, 300); 
		setVisible(true); 
	}
	// ��ư�� ���� �̺�Ʈ ó�� �Լ�
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		if(b.getText().equals("����")) { // '����'��ư�� ���� �̺�Ʈ ó��
			new SelectBoard();
			this.dispose(); // ���� �����Ӹ� ����
		}
		else if(b.getText().equals("����")) // '����'��ư�� ���� �̺�Ʈ ó��
			System.exit(0);
	}
	// ���� �Լ�
	public static void main(String args[]) {
		new Main();
	}
}