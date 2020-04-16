package linearAlgebra;

public class CMatrix {
	private double[][] mat;
	private int colN, rowN; //�s���A��
	private boolean isSquare; //�����s�񂩁H
	public CMatrix(int m,int n){
		this.mat = new double[m][n]; //m�s����s��
		this.rowN = m;
		this.colN = n;
		if(m==n) {
			isSquare = true;
		}else {
			isSquare = false;
		}
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				if(i==j) {
					this.mat[i][j] = 1.0;
				}else {
					this.mat[i][j] = 0.0;
				}
			}
		}
	} //end of CMatrix(int , int)
	
}
