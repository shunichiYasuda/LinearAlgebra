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
	public CMatrix(double[][] in) {
		this.rowN = in.length;
		this.colN = in[0].length;
		this.mat = new double[this.rowN][this.colN];
		for(int i=0;i<this.rowN;i++) {
			for(int j=0;j<this.colN;j++) {
				this.mat[i][j] = in[i][j];
			}
		}
	} // end of CMatrix(double[][] )
	//
	public CMatrix transPort() {
		CMatrix r = new CMatrix(this.colN,this.rowN);
		for(int i=0;i<r.rowN;i++) {
			for(int j=0;j<r.colN;j++) {
				r.mat[i][j] = this.mat[j][i];
			}
		}
		return r;
	}
	//getter
	public double[][] getMat(){
		return this.mat;
	}
	//第j列を列ベクトルとして返す
	public CVector_Col getCol(int j) {
		CVector_Col r = new CVector_Col(getColArray(j));
		return r;
	}
	//第j列を double[] で返す
	public double[] getColArray(int j) {
		double[] r = new double[this.rowN];
		for(int i=0;i<r.length;i++) {
			r[i] = this.mat[i][j];
		}
		return r;
	}
	//第i行を行ベクトルとして返す
	public CVector_Row getRow(int i) {
		CVector_Row r = new CVector_Row(getRowArray(i));
		return r;
	}
	//第i行を double[] で返す
	public double[] getRowArray(int i) {
		double[] r = new double[this.colN];
		for(int j=0;j<r.length;j++) {
			r[j] = this.mat[i][j];
		}
		return r;
	}
	//setter
	//(i,j)要素に値をセットする
	public void setValue(int i, int j, double v) {
		this.mat[i][j] = v;
	}
	
}
