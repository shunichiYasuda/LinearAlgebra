package linearAlgebra;

public class CMatrix {
	private double[][] mat;
	private int colN, rowN; //行数、列数
	private boolean isSquare; //正方行列か？
	public CMatrix(int m,int n){
		this.mat = new double[m][n]; //m行ｎ列行列
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
