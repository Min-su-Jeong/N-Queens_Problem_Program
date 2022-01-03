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
	private JLabel la = new JLabel("보드 크기 선택");
	private JButton easy = new JButton("4x4");
	private JButton medium = new JButton("6x6");
	private JButton hard = new JButton("8x8");
	
	public SelectBoard() {
		// 프레임 설정
		setTitle("N-Queens Puzzle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(null);
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		
		// 객체 위치 및 크기 설정
		la.setFont(new Font("맑은 고딕", Font.BOLD, 35));
		easy.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		medium.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		hard.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		la.setBounds(120, 0, 400, 100);
		easy.setBounds(190, 110, 100, 40);
		medium.setBounds(190, 170, 100, 40);
		hard.setBounds(190, 230, 100, 40);
		
		// 버튼에 대한 이벤트 리스너 달기
		easy.addActionListener(this);
		medium.addActionListener(this);
		hard.addActionListener(this);
		
		// 컨테이너에 추가
		c.add(la);
		c.add(easy);
		c.add(medium);
		c.add(hard);
		
		// 프레임 위치, 크기, 보이기 값 설정
		setLocation(res.width/3, res.height/3);
		setSize(500, 350);
		setVisible(true);
	}
	// 버튼에 대한 이벤트 처리 함수
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
