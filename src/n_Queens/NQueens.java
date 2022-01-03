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
	private JButton below = new JButton("����"), next = new JButton("����");
	private String
	SOUND_MODE = null,
	SET_SIZE = "������ ü�������� ũ��(n)�� �Է����ּ���.(�ִ�: 15)",
	DESC = String.format("\n%90s", " ") + "���� ����\n\n" + "ü������ ������ ��ֹ��� ���� ��� �����¿� �׸��� �밢������ ���ϴ� ĭ ����ŭ �̵��� �� �ֽ��ϴ�.\n" + "�� ������ ü�����忡�� n���� ������ ��� ��ġ�ϸ� ���� ������ �� ���� ��ġ�� ���� �� �ִ°��� ���� �����Դϴ�.",
	RULES = String.format("\n%57s", " ") + "���� ��Ģ\n\n" + "N X N ü���ǿ� N���� ������ ��ġ�� �� ������ ���� �������� �ʵ��� �ϸ� �˴ϴ�.\n" + "N���� ������ ��� ��ġ�ϰ� ���� ������ ���� �ʾҴٸ� ������ �ذ�˴ϴ�.";
	
	// ������
	public NQueens(int size) {
		this.size = size;
		
		// ������ ����
		setTitle("N-Queens Problem");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new BorderLayout(0, 20));
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		
		// �޴��� �ޱ�
		createMenu();
		c.add(createTitle(), BorderLayout.NORTH);
		c.add(createBoard(), BorderLayout.CENTER);
		c.add(createFunc(), BorderLayout.SOUTH);
		
		// ��ü ���� �� ��� �� ���ϱ�(��� �ظ� �����ֱ� ���� ����ȭ �۾�)
		long start = System.currentTimeMillis();
		Solve solve = new Solve(size);
		solve.backTracking(0);
		ans = solve.getCols();
		long end = System.currentTimeMillis();
		time = end - start;
		
		// ������ ũ�� �ڵ�����, ��ġ, ���̱� �� ����
		pack();
		setLocation(res.width/3, res.height/4);
		setVisible(true);
	}
	// �޴��� �����ϴ� �Լ�
	private void createMenu() {
		JMenuBar mb = new JMenuBar(); // �޴��� ����
		
		// �޴� ����
		JMenu setMenu = new JMenu("����"); 
		JMenu gameMenu = new JMenu("����"); 
		JMenu helpMenu = new JMenu("����"); 
		
		// �޴� �ٿ� �޴� �߰�
		mb.add(setMenu);
		mb.add(gameMenu);
		mb.add(helpMenu);
		
		// ���� �޴� ����
		JMenuItem menuItem = null;
		String[] itemTitle = { "����ũ�� ����(Custom)", "Ž������ ����", "����ð�", "����", "���� ����", "���� ��Ģ" };
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
		
		// ����޴��� ���� �޴� ������ ����
		JMenu dif = new JMenu("����ũ�� ����");
		JMenu bs = new JMenu("Ŭ���� ����");
		JMenu bi = new JMenu("ü���� ����");
		String[] subTitle = { "4x4", "6x6", "8x8", "���1", "���2", "���3", "���4", "����", "Queen", "Bishop", "Rook", "Horse", "����"};
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
		
		// �޴��ٸ� �����ӿ� ����
		setJMenuBar(mb); 
	}
	// Label ���� �Լ�
	private JPanel createTitle() {
		JPanel t_pn = new JPanel(new BorderLayout());
		JLabel title = new JLabel(size + " - Queens Problem", JLabel.CENTER);
		title.setFont(new Font("sansserif", Font.BOLD, 35)); // Set the font to large
        t_pn.add(title, BorderLayout.NORTH);
        return t_pn;
	}
	// �������� ����� ���� ��۹�ư ���� �Լ�
	private JPanel createBoard() {
		JPanel b_pn = new JPanel(new GridLayout(size,size));
		JToggleButton button;
		
		// ��� ��ư ���� �� �̺�Ʈ ������, �гο� �߰�
		btns = new JToggleButton[size][size];
		for(int i=0; i < size; i++) {
			for(int j=0; j < size; j++) {
				button = new JToggleButton();
				button.setActionCommand(i + "," + j);
				button.addActionListener(this); // ��ư Ŭ������ ���� �̺�Ʈ �����ʿ� �߰�
				button.setPreferredSize(BUTTON_DIM);
				btns[i][j] = button;
				b_pn.add(button);
			}
		}
		return b_pn;
	}
	// ��ư(reset, solve, solveAll) ���� �Լ�
	private JPanel createFunc() {
		JPanel f_pn = new JPanel(new BorderLayout(0, 10));
		JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel b_pn = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
		String[] btnTitle = { "Reset", "Solve", "Solve all" };
		
		// ��ư �г� �� �̺�Ʈ �����ʿ� �߰�, ���̱� �ɼ�: false
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
	// ��� ���� �Լ�
	// ����ũ�� ����(Custom) �Լ�(�ִ�ũ��:15x15)
	private void setBoard() { 
		String ans = JOptionPane.showInputDialog(this, SET_SIZE);
		while(this.isNumeric(ans) != true || Integer.parseInt(ans) > 15) {
			JOptionPane.showMessageDialog(this, "�߸� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���!", "Error", JOptionPane.ERROR_MESSAGE);
			ans = JOptionPane.showInputDialog(this, SET_SIZE);
			if(this.isNumeric(ans) == true && Integer.parseInt(ans) <= 15) {
				break;
			}
		}
		while(this.isNumeric(ans) != true || Integer.parseInt(ans) < 4) {
			JOptionPane.showMessageDialog(this, "�߸� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���!", "Error", JOptionPane.ERROR_MESSAGE);
			ans = JOptionPane.showInputDialog(this, SET_SIZE);
			if(this.isNumeric(ans) == true && Integer.parseInt(ans) >= 4) {
				break;
			}
		}
		this.dispose();
		new NQueens(Integer.parseInt(ans));
	}
	// �Է¹��� ���ڿ��� �������� �Ǻ��ϴ� �Լ�
	private boolean isNumeric(String input) { 
		try {
			Double.parseDouble(input);
			return true; } 
		catch (NumberFormatException e) {
			return false; }
	}
	// Ŭ���� ���� �Լ�
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
	// ���� �ð����� �Լ�
	private void showTimes() {
		JOptionPane.showMessageDialog(this, "����ð� : " + time + "ms", "Performance Times", JOptionPane.PLAIN_MESSAGE);
	}
	// ���� ���� �Լ�
	private void showInfo() { 
		JOptionPane.showMessageDialog(this, DESC, "Description", JOptionPane.QUESTION_MESSAGE);
	}
	// ���� ��Ģ �Լ�
	private void showRules() { 
		JOptionPane.showMessageDialog(this, RULES, "Rules", JOptionPane.QUESTION_MESSAGE);
	}
	// �ʱ�ȭ �Լ�
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
	// �ϳ��� �ظ� ����ϴ� �Լ�
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
	// ��� �ظ� ����ϴ� �Լ�
	private void SolveAll() {
		// �������� ���� ���� ��Ȳ ó��
		if(page < 0) 
			page = 0;
		else if(page > (ans.size() / size) - 1)
			page = (ans.size() / size) - 1;
		
		// SolveAll�� ���� ���� ����,���� ��ư Ȱ��ȭ �� ������ �� ���
		this.Solve(iconselect);
		below.setEnabled(true);
		next.setEnabled(true);
		cur_page.setVisible(true);
		cur_page.setText((page+1) + " / " + (ans.size()/size));
	}
	// �̺�Ʈ ó�� �Լ�
	public void actionPerformed(ActionEvent e) {
		Play(SOUND_MODE);
		String s = e.getActionCommand();
		switch(s) {
		case "����ũ�� ����(Custom)":
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
		case "���1":
			SOUND_MODE = "sound/btn1.wav";
			break;
		case "���2":
			SOUND_MODE = "sound/btn2.wav";
			break;
		case "���3":
			SOUND_MODE = "sound/btn3.wav";
			break;
		case "���4":
			SOUND_MODE = "sound/btn4.wav";
			break;
		case "����":
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
		case "����":
			iconselect = 0;
			break;
		case "Ž������ ����":
			new Visualization(size);
			break;
		case "����ð�":
			showTimes();
			break;
		case "����":
			System.exit(0);
			break;
		case "���� ����":
			showInfo();
			break;
		case "���� ��Ģ":
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
		case "����":
			page -= 1;
			SolveAll();
			break;
		case "����":
			page += 1;
			SolveAll();
			break;
		}
	}
}