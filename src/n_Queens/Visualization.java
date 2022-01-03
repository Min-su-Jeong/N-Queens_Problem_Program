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
    private int SIZE; // 보드 크기
    private JLabel [][] btns; // 화면에 버튼 좌표를 보여주기 위한 변수
    private Thread thread; // 스레드 변수
	
    // 생성자
    public Visualization(int size) { 
    	SIZE = size;
    	btns = new JLabel[SIZE][SIZE];
    	
    	// 프레임 설정
    	setTitle(SIZE + "-Queens Problem Visualization"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane(); 
		c.setLayout(new BorderLayout());
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize(); // 현재 화면의 해상도 가져오기
		
		// 패널, 버튼 생성 및 이벤트 리스너 추가
    	JPanel grid = new JPanel(new GridLayout(SIZE, SIZE));
    	JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    	JButton pause = new JButton("일시정지");
    	JButton resume = new JButton("재개");
    	JButton exit = new JButton("종료");
		pause.addActionListener(this);
		resume.addActionListener(this);
		exit.addActionListener(this);
		
		// 버튼의 좌표 값 생성 및 패널에 부착
		for (int i=0; i < SIZE; i++) {
			for (int j=0; j < SIZE; j++) {
				btns[i][j] = new JLabel(i + "," + j);
				btns[i][j].setSize(50, 50);
				btns[i][j].setOpaque(true); // Opaque값을 true로 미리 설정해 주어야 배경색이 적용됨
				btns[i][j].setHorizontalAlignment(SwingConstants.CENTER); // Label 중앙 배치
				grid.add(btns[i][j]);
			}
		}
		flow.add(pause);
		flow.add(resume);
		flow.add(exit);
		c.add(grid, BorderLayout.CENTER);
		c.add(flow, BorderLayout.SOUTH);
		
		// 스레드 객체 생성
		this.thread = new Thread(new Visual_Runnable(SIZE, btns));
		
		// 프레임 위치, 크기, 보이기 값 설정
		setSize(500, 500);
		setLocation((int)(res.width/1.8), res.height/4);
        setVisible(true);
        
        // 스레드 시작
        thread.start();
    }
    // 스레드 일시정지 함수
    private void stopThread() {
    	thread.suspend();
    }
    // 스레드 재개 함수
    private void resumeThread() {
    	thread.resume();
    }
    // 이벤트 처리 함수 
    public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		switch(s) {
		case "일시정지":
			stopThread();
			break;
		case "재개":
			resumeThread();
			break;
		case "종료":
			this.dispose();
			break;
		}
    }
}

class Visual_Runnable implements Runnable {
	private int SIZE;
    private JLabel [][] btns;
    private int[] cols;
    
    // 생성자
	public Visual_Runnable(int size, JLabel[][] btns) {
		this.SIZE = size;
		this.btns = btns;
		this.cols = new int[size];
	}
	// 퀸을 놓을 수 있는 위치인지 확인하는 함수
    public boolean Possibility(int col) {
        try {
            Thread.sleep(50); }
        catch (InterruptedException e) {
            e.printStackTrace(); }
        
        for(int i=0; i < col; i++) {        
	        if(cols[i] == cols[col] || Math.abs(col - i) == Math.abs(cols[col] - cols[i])) // 같은 행에 존재할 경우 or 대각선 상에 놓여있는 경우
	            return false; // 불가능 하므로 false 리턴
	    }
	    return true; // 이외의 경우 가능 하므로 true 리턴
	}
    // 백트래킹을 이용한 해 구하는(문제 해결) 함수
    public boolean backTracking(int level) { // level: 현재 포커싱할 행의 위치
    	if(level == SIZE) // N-1까지 행이 존재하며 SIZE까지 온 것은 행에 대해서 모든 조건을 만족한다는 것(하나의 해)
            return true;

        for(int i=0;i<SIZE;i++) {
        	try {
                Thread.sleep(0); }
            catch (InterruptedException e) {
                e.printStackTrace(); }
        	
            cols[level] = i;
            if(Possibility(level)) { // 퀸을 놓을 수 있는 위치일 경우 재귀호출
            	btns[i][level].setBackground(Color.BLUE);
            	if(backTracking(level+1) == true)
            		return true;
            	btns[i][level].setBackground(Color.WHITE);
            }
        }
        return false;
	}
    // 스레드 코드 함수
    public void run(){
        try {
            Thread.sleep(50); }
        catch (InterruptedException e) {
            e.printStackTrace(); }
        
        // 보드 초기화
        for(int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                try {
                    Thread.sleep(5); } 
                catch (InterruptedException e) {
                    e.printStackTrace(); }
                btns[i][j].setBackground(Color.PINK);
            }
        }
        // 해가 없는 경우 에러 메시지 출력
        if( backTracking(0) == false )
        	JOptionPane.showMessageDialog(null, "해를 찾을 수 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}