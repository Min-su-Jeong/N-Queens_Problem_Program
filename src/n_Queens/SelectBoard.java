package n_Queens;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SelectBoard extends JFrame implements ActionListener {
	private JLabel la = new JLabel("���� ũ�� ����");
	private JButton easy = new JButton("4x4");
	private JButton medium = new JButton("6x6");
	private JButton hard = new JButton("8x8");
	
	public SelectBoard() {
		// ������ ����
		setTitle("N-Queens Puzzle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(null);
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		
		// ��ü ��ġ �� ũ�� ����
		la.setFont(new Font("���� ���", Font.BOLD, 35));
		easy.setFont(new Font("���� ���", Font.BOLD, 15));
		medium.setFont(new Font("���� ���", Font.BOLD, 15));
		hard.setFont(new Font("���� ���", Font.BOLD, 15));
		la.setBounds(120, 0, 400, 100);
		easy.setBounds(190, 110, 100, 40);
		medium.setBounds(190, 170, 100, 40);
		hard.setBounds(190, 230, 100, 40);
		
		// ��ư�� ���� �̺�Ʈ ������ �ޱ�
		easy.addActionListener(this);
		medium.addActionListener(this);
		hard.addActionListener(this);
		
		// �����̳ʿ� �߰�
		c.add(la);
		c.add(easy);
		c.add(medium);
		c.add(hard);
		
		// ������ ��ġ, ũ��, ���̱� �� ����
		setLocation(res.width/3, res.height/3);
		setSize(500, 350);
		setVisible(true);
	}
	// ��ư�� ���� �̺�Ʈ ó�� �Լ�
	public void actionPerformed(ActionEvent e) { 
		JButton b = (JButton)e.getSource();
		if(b.getText().equals("4x4")) {
			new NQueens(4);
			this.dispose();
		}
		else if(b.getText().equals("6x6")) {
			new NQueens(6);
			this.dispose();
		}
		else if(b.getText().equals("8x8")) {
			new NQueens(8);
			this.dispose();
		}
	}
}
