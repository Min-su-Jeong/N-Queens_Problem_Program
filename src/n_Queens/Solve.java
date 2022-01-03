package n_Queens;

import java.util.ArrayList;

public class Solve{
	private int N;
	private int cols[]; // �� �࿡ �� ���� �� ��ǥ
	private ArrayList<Queen> ans = new ArrayList<Queen>(); // ��� ���� ������ ��� ���� ���� ����
	
	// ������
	public Solve(int size){ 
	    this.N=size;
	    cols = new int[size];
	}
	// ��� ���� ������ ��� ���� Getter
	public ArrayList<Queen> getCols() { 
		return ans;
	}
	// ���� ���� �� �ִ� ��ġ���� Ȯ���ϴ� �Լ�
	public boolean Possibility(int col) {
	    for(int i=0; i < col; i++) {
	        if(cols[i] == cols[col] || Math.abs(col - i) == Math.abs(cols[col] - cols[i])) // ���� �࿡ ������ ��� or �밢�� �� �����ִ� ���
	            return false; // �Ұ��� �ϹǷ� false ����
	    }
	    return true; // �̿��� ��� ���� �ϹǷ� true ����
	}
	// ��Ʈ��ŷ�� �̿��� �� ���ϴ�(���� �ذ�) �Լ�
	public void backTracking(int level) { // level: ���� ��Ŀ���� ���� ��ġ
        if(level == N) { // N-1���� ���� �����ϸ� N���� �� ���� �࿡ ���ؼ� ��� ������ �����Ѵٴ� ��(�ϳ��� ��)
            for(int j=0; j < N; j++)
            	ans.add(new Queen(j, cols[j])); // �ظ� ArrayList<Queen>(��, ��) ���·� �� �߰�
            return;
        }
        for(int i=0; i < N; i++) {
            cols[level] = i;
            if(Possibility(level)) // ���� ���� �� �ִ� ��ġ�� ��� ���ȣ���� ���� ���� �ܰ� ����
                backTracking(level + 1);
        }
	}
}

