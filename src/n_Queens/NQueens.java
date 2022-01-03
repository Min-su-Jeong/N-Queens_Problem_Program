package n_Queens;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JMenu;

public class NQueens extends JFrame implements ActionListener {
	private int size, page, iconselect;
	private long time;
	private ArrayList<Queen> ans;
	private JToggleButton[][] btns;
	private ImageIcon queen = new ImageIcon(new ImageIcon("image/queen.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)),
			bishop = new ImageIcon(new ImageIcon("image/bishop.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)),
			rook = new ImageIcon(new ImageIcon("image/rook.png").getImage().getScaledInstance(50, 48, Image.SCALE_SMOOTH)),
			horse = new ImageIcon(new ImageIcon("image/horse.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
	private Dimension BUTTON_DIM = new Dimension(50, 50);
	private JLabel cur_page = new JLabel("", JLabel.CENTER);
	private JButton below = new JButton("이전"), next = new JButton("다음");
	private String
	SOUND_MODE = null,
	SET_SIZE = "변경할 체스보드의 크기(n)를 입력해주세요.(최대: 15)",
	DESC = String.format("\n%90s", " ") + "문제 설명\n\n" + "체스에서 여왕은 장애물이 없을 경우 전후좌우 그리고 대각선까지 원하는 칸 수만큼 이동할 수 있습니다.\n" + "본 문제는 체스보드에서 n개의 여왕을 어떻게 배치하면 서로 공격할 수 없는 위치에 놓을 수 있는가에 대한 문제입니다.",
	RULES = String.format("\n%57s", " ") + "문제 규칙\n\n" + "N X N 체스판에 N개의 여왕을 배치해 두 여왕이 서로 공격하지 않도록 하면 됩니다.\n" + "N개의 여왕을 모두 배치하고 서로 공격을 하지 않았다면 문제는 해결됩니다.";
	
	// 생성자
	public NQueens(int size) {
		this.size = size;
		
		// 프레임 설정
		setTitle("N-Queens Problem");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new BorderLayout(0, 20));
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		
		// 메뉴바 달기
		createMenu();
		c.add(createTitle(), BorderLayout.NORTH);
		c.add(createBoard(), BorderLayout.CENTER);
		c.add(createFunc(), BorderLayout.SOUTH);
		
		// 객체 생성 시 모든 해 구하기(모든 해를 보여주기 위한 최적화 작업)
		long start = System.currentTimeMillis();
		Solve solve = new Solve(size);
		solve.backTracking(0);
		ans = solve.getCols();
		long end = System.currentTimeMillis();
		time = end - start;
		
		// 프레임 크기 자동맞춤, 위치, 보이기 값 설정
		pack();
		setLocation(res.width/3, res.height/4);
		setVisible(true);
	}
	// 메뉴를 생성하는 함수
	private void createMenu() {
		JMenuBar mb = new JMenuBar(); // 메뉴바 생성
		
		// 메뉴 생성
		JMenu setMenu = new JMenu("설정"); 
		JMenu gameMenu = new JMenu("게임"); 
		JMenu helpMenu = new JMenu("도움말"); 
		
		// 메뉴 바에 메뉴 추가
		mb.add(setMenu);
		mb.add(gameMenu);
		mb.add(helpMenu);
		
		// 서브 메뉴 생성
		JMenuItem menuItem = null;
		String[] itemTitle = { "보드크기 설정(Custom)", "탐색과정 보기", "수행시간", "종료", "문제 설명", "문제 규칙" };
		for(int i=0; i < itemTitle.length; i++) {
			menuItem = new JMenuItem(itemTitle[i]);
			menuItem.addActionListener(this);
			if(i<1)
				setMenu.add(menuItem);
			else if(i>=1 && i<=3) {
				gameMenu.add(menuItem);
				if(i==2)
					gameMenu.addSeparator();
			}
			else if(i>3)
				helpMenu.add(menuItem);
		}
		
		// 서브메뉴를 가진 메뉴 아이템 생성
		JMenu dif = new JMenu("보드크기 설정");
		JMenu bs = new JMenu("클릭음 설정");
		JMenu bi = new JMenu("체스말 설정");
		String[] subTitle = { "4x4", "6x6", "8x8", "모드1", "모드2", "모드3", "모드4", "끄기", "Queen", "Bishop", "Rook", "Horse", "없음"};
		for(int i=0; i < subTitle.length; i++) {
			menuItem = new JMenuItem(subTitle[i]);
			menuItem.addActionListener(this);
			if(i<3)
				dif.add(menuItem);
			else if(i>=3 && i<8)
				bs.add(menuItem);
			else
				bi.add(menuItem);
		}
		setMenu.add(dif);
		setMenu.add(bs);
		setMenu.add(bi);
		
		// 메뉴바를 프레임에 부착
		setJMenuBar(mb); 
	}
	// Label 생성 함수
	private JPanel createTitle() {
		JPanel t_pn = new JPanel(new BorderLayout());
		JLabel title = new JLabel(size + " - Queens Problem", JLabel.CENTER);
		title.setFont(new Font("sansserif", Font.BOLD, 35)); // Set the font to large
        t_pn.add(title, BorderLayout.NORTH);
        return t_pn;
	}
	// 보드판을 만들기 위한 토글버튼 생성 함수
	private JPanel createBoard() {
		JPanel b_pn = new JPanel(new GridLayout(size,size));
		JToggleButton button;
		
		// 토글 버튼 생성 및 이벤트 리스너, 패널에 추가
		btns = new JToggleButton[size][size];
		for(int i=0; i < size; i++) {
			for(int j=0; j < size; j++) {
				button = new JToggleButton();
				button.setActionCommand(i + "," + j);
				button.addActionListener(this); // 버튼 클릭음을 위한 이벤트 리스너에 추가
				button.setPreferredSize(BUTTON_DIM);
				btns[i][j] = button;
				b_pn.add(button);
			}
		}
		return b_pn;
	}
	// 버튼(reset, solve, solveAll) 생성 함수
	private JPanel createFunc() {
		JPanel f_pn = new JPanel(new BorderLayout(0, 10));
		JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel b_pn = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
		String[] btnTitle = { "Reset", "Solve", "Solve all" };
		
		// 버튼 패널 및 이벤트 리스너에 추가, 보이기 옵션: false
		below.addActionListener(this);
		next.addActionListener(this);
		below.setEnabled(false);
		next.setEnabled(false);
		flow.add(below);
		flow.add(next);
		f_pn.add(flow, BorderLayout.NORTH);
		f_pn.add(cur_page, BorderLayout.CENTER);
		
		for(int i=0; i < btnTitle.length; i++) {
			JButton btn = new JButton(btnTitle[i]);
			btn.addActionListener(this);
			b_pn.add(btn);
		}
		f_pn.add(b_pn, BorderLayout.SOUTH);
		
		return f_pn;
	}
	// 기능 수행 함수
	// 보드크기 설정(Custom) 함수(최대크기:15x15)
	private void setBoard() { 
		String ans = JOptionPane.showInputDialog(this, SET_SIZE);
		while(this.isNumeric(ans) != true || Integer.parseInt(ans) > 15) {
			JOptionPane.showMessageDialog(this, "잘못 입력하셨습니다. 다시 입력해주세요!", "Error", JOptionPane.ERROR_MESSAGE);
			ans = JOptionPane.showInputDialog(this, SET_SIZE);
			if(this.isNumeric(ans) == true && Integer.parseInt(ans) <= 15) {
				break;
			}
		}
		while(this.isNumeric(ans) != true || Integer.parseInt(ans) < 4) {
			JOptionPane.showMessageDialog(this, "잘못 입력하셨습니다. 다시 입력해주세요!", "Error", JOptionPane.ERROR_MESSAGE);
			ans = JOptionPane.showInputDialog(this, SET_SIZE);
			if(this.isNumeric(ans) == true && Integer.parseInt(ans) >= 4) {
				break;
			}
		}
		this.dispose();
		new NQueens(Integer.parseInt(ans));
	}
	// 입력받은 문자열이 숫자인지 판별하는 함수
	private boolean isNumeric(String input) { 
		try {
			Double.parseDouble(input);
			return true; } 
		catch (NumberFormatException e) {
			return false; }
	}
	// 클릭음 설정 함수
	private void Play(String fileName) { 
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
            Clip clip = AudioSystem.getClip();
            clip.stop();
            clip.open(ais);
            clip.start();
        }
        catch (Exception ex) {}
    }
	// 수행 시간보기 함수
	private void showTimes() {
		JOptionPane.showMessageDialog(this, "수행시간 : " + time + "ms", "Performance Times", JOptionPane.PLAIN_MESSAGE);
	}
	// 게임 설명 함수
	private void showInfo() { 
		JOptionPane.showMessageDialog(this, DESC, "Description", JOptionPane.QUESTION_MESSAGE);
	}
	// 게임 규칙 함수
	private void showRules() { 
		JOptionPane.showMessageDialog(this, RULES, "Rules", JOptionPane.QUESTION_MESSAGE);
	}
	// 초기화 함수
	private void Reset() {
		for(int i=0; i < size; i++) {
			for(int j=0; j < size; j++) {
				btns[i][j].setSelected(false);
				btns[i][j].setIcon(null);
			}
		}
		below.setEnabled(false);
		next.setEnabled(false);
		cur_page.setVisible(false);
	}
	// 하나의 해를 출력하는 함수
	private void Solve(int select) {
		this.Reset();
		for(int i=0; i < size; i++) {
			for(int j=0; j < size; j++)  {
				if(!(ans.get(i+(page*size)).getCol() == j))
					btns[j][i].setSelected(true);
				else {
					if(select == 1)
						btns[j][i].setIcon(queen);
					else if(select == 2)
						btns[j][i].setIcon(bishop);
					else if(select == 3)
						btns[j][i].setIcon(rook);
					else if(select == 4)
						btns[j][i].setIcon(horse);
					else
						btns[j][i].setIcon(null);
				}
			}
		}
	}
	// 모든 해를 출력하는 함수
	private void SolveAll() {
		// 페이지에 대한 예외 상황 처리
		if(page < 0) 
			page = 0;
		else if(page > (ans.size() / size) - 1)
			page = (ans.size() / size) - 1;
		
		// SolveAll을 누를 때만 이전,다음 버튼 활성화 및 페이지 수 출력
		this.Solve(iconselect);
		below.setEnabled(true);
		next.setEnabled(true);
		cur_page.setVisible(true);
		cur_page.setText((page+1) + " / " + (ans.size()/size));
	}
	// 이벤트 처리 함수
	public void actionPerformed(ActionEvent e) {
		Play(SOUND_MODE);
		String s = e.getActionCommand();
		switch(s) {
		case "보드크기 설정(Custom)":
			setBoard();
			break;
		case "4x4":
			this.dispose();
			new NQueens(4);
			break;
		case "6x6":
			this.dispose();
			new NQueens(6);
			break;
		case "8x8":
			this.dispose();
			new NQueens(8);
			break;
		case "모드1":
			SOUND_MODE = "sound/btn1.wav";
			break;
		case "모드2":
			SOUND_MODE = "sound/btn2.wav";
			break;
		case "모드3":
			SOUND_MODE = "sound/btn3.wav";
			break;
		case "모드4":
			SOUND_MODE = "sound/btn4.wav";
			break;
		case "끄기":
			SOUND_MODE = "null";
			break;
		case "Queen":
			iconselect = 1;
			break;
		case "Bishop":
			iconselect = 2;
			break;
		case "Rook":
			iconselect = 3;
			break;
		case "Horse":
			iconselect = 4;
			break;
		case "없음":
			iconselect = 0;
			break;
		case "탐색과정 보기":
			new Visualization(size);
			break;
		case "수행시간":
			showTimes();
			break;
		case "종료":
			System.exit(0);
			break;
		case "문제 설명":
			showInfo();
			break;
		case "문제 규칙":
			showRules();
			break;
		case "Reset":
			Reset();
			break;
		case "Solve":
			page = 0;
			Solve(iconselect);
			break;
		case "Solve all":
			SolveAll();
			break;
		case "이전":
			page -= 1;
			SolveAll();
			break;
		case "다음":
			page += 1;
			SolveAll();
			break;
		}
	}
}