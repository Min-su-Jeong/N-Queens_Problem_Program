package n_Queens;

import java.util.ArrayList;

public class Solve{
	private int N;
	private int cols[]; // 각 행에 들어갈 퀸의 열 좌표
	private ArrayList<Queen> ans = new ArrayList<Queen>(); // 모든 해의 정보를 담기 위한 변수 선언
	
	// 생성자
	public Solve(int size){ 
	    this.N=size;
	    cols = new int[size];
	}
	// 모든 해의 정보가 담긴 변수 Getter
	public ArrayList<Queen> getCols() { 
		return ans;
	}
	// 퀸을 놓을 수 있는 위치인지 확인하는 함수
	public boolean Possibility(int col) {
	    for(int i=0; i < col; i++) {
	        if(cols[i] == cols[col] || Math.abs(col - i) == Math.abs(cols[col] - cols[i])) // 같은 행에 존재할 경우 or 대각선 상에 놓여있는 경우
	            return false; // 불가능 하므로 false 리턴
	    }
	    return true; // 이외의 경우 가능 하므로 true 리턴
	}
	// 백트래킹을 이용한 해 구하는(문제 해결) 함수
	public void backTracking(int level) { // level: 현재 포커싱할 행의 위치
        if(level == N) { // N-1까지 행이 존재하며 N까지 온 것은 행에 대해서 모든 조건을 만족한다는 것(하나의 해)
            for(int j=0; j < N; j++)
            	ans.add(new Queen(j, cols[j])); // 해를 ArrayList<Queen>(행, 열) 형태로 값 추가
            return;
        }
        for(int i=0; i < N; i++) {
            cols[level] = i;
            if(Possibility(level)) // 퀸을 놓을 수 있는 위치일 경우 재귀호출을 통해 다음 단계 진행
                backTracking(level + 1);
        }
	}
}

