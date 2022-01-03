package n_Queens;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Visualization extends JFrame implements ActionListener {  
    private int SIZE; // ���� ũ��
    private JLabel [][] btns; // ȭ�鿡 ��ư ��ǥ�� �����ֱ� ���� ����
    private Thread thread; // ������ ����
	
    // ������
    public Visualization(int size) { 
    	SIZE = size;
    	btns = new JLabel[SIZE][SIZE];
    	
    	// ������ ����
    	setTitle(SIZE + "-Queens Problem Visualization"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane(); 
		c.setLayout(new BorderLayout());
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize(); // ���� ȭ���� �ػ� ��������
		
		// �г�, ��ư ���� �� �̺�Ʈ ������ �߰�
    	JPanel grid = new JPanel(new GridLayout(SIZE, SIZE));
    	JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    	JButton pause = new JButton("�Ͻ�����");
    	JButton resume = new JButton("�簳");
    	JButton exit = new JButton("����");
		pause.addActionListener(this);
		resume.addActionListener(this);
		exit.addActionListener(this);
		
		// ��ư�� ��ǥ �� ���� �� �гο� ����
		for (int i=0; i < SIZE; i++) {
			for (int j=0; j < SIZE; j++) {
				btns[i][j] = new JLabel(i + "," + j);
				btns[i][j].setSize(50, 50);
				btns[i][j].setOpaque(true); // Opaque���� true�� �̸� ������ �־�� ������ �����
				btns[i][j].setHorizontalAlignment(SwingConstants.CENTER); // Label �߾� ��ġ
				grid.add(btns[i][j]);
			}
		}
		flow.add(pause);
		flow.add(resume);
		flow.add(exit);
		c.add(grid, BorderLayout.CENTER);
		c.add(flow, BorderLayout.SOUTH);
		
		// ������ ��ü ����
		this.thread = new Thread(new Visual_Runnable(SIZE, btns));
		
		// ������ ��ġ, ũ��, ���̱� �� ����
		setSize(500, 500);
		setLocation((int)(res.width/1.8), res.height/4);
        setVisible(true);
        
        // ������ ����
        thread.start();
    }
    // ������ �Ͻ����� �Լ�
    private void stopThread() {
    	thread.suspend();
    }
    // ������ �簳 �Լ�
    private void resumeThread() {
    	thread.resume();
    }
    // �̺�Ʈ ó�� �Լ� 
    public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		switch(s) {
		case "�Ͻ�����":
			stopThread();
			break;
		case "�簳":
			resumeThread();
			break;
		case "����":
			this.dispose();
			break;
		}
    }
}

class Visual_Runnable implements Runnable {
	private int SIZE;
    private JLabel [][] btns;
    private int[] cols;
    
    // ������
	public Visual_Runnable(int size, JLabel[][] btns) {
		this.SIZE = size;
		this.btns = btns;
		this.cols = new int[size];
	}
	// ���� ���� �� �ִ� ��ġ���� Ȯ���ϴ� �Լ�
    public boolean Possibility(int col) {
        try {
            Thread.sleep(50); }
        catch (InterruptedException e) {
            e.printStackTrace(); }
        
        for(int i=0; i < col; i++) {        
	        if(cols[i] == cols[col] || Math.abs(col - i) == Math.abs(cols[col] - cols[i])) // ���� �࿡ ������ ��� or �밢�� �� �����ִ� ���
	            return false; // �Ұ��� �ϹǷ� false ����
	    }
	    return true; // �̿��� ��� ���� �ϹǷ� true ����
	}
    // ��Ʈ��ŷ�� �̿��� �� ���ϴ�(���� �ذ�) �Լ�
    public boolean backTracking(int level) { // level: ���� ��Ŀ���� ���� ��ġ
    	if(level == SIZE) // N-1���� ���� �����ϸ� SIZE���� �� ���� �࿡ ���ؼ� ��� ������ �����Ѵٴ� ��(�ϳ��� ��)
            return true;

        for(int i=0;i<SIZE;i++) {
        	try {
                Thread.sleep(0); }
            catch (InterruptedException e) {
                e.printStackTrace(); }
        	
            cols[level] = i;
            if(Possibility(level)) { // ���� ���� �� �ִ� ��ġ�� ��� ���ȣ��
            	btns[i][level].setBackground(Color.BLUE);
            	if(backTracking(level+1) == true)
            		return true;
            	btns[i][level].setBackground(Color.WHITE);
            }
        }
        return false;
	}
    // ������ �ڵ� �Լ�
    public void run(){
        try {
            Thread.sleep(50); }
        catch (InterruptedException e) {
            e.printStackTrace(); }
        
        // ���� �ʱ�ȭ
        for(int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                try {
                    Thread.sleep(5); } 
                catch (InterruptedException e) {
                    e.printStackTrace(); }
                btns[i][j].setBackground(Color.PINK);
            }
        }
        // �ذ� ���� ��� ���� �޽��� ���
        if( backTracking(0) == false )
        	JOptionPane.showMessageDialog(null, "�ظ� ã�� �� �����ϴ�.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}