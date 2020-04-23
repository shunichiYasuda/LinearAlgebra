package linearAlgebra;

public class CMatrix {
	private double[][] mat;
	public int colN, rowN; //行数、列数
	public boolean isSquare; //正方行列か？
	public CMatrix(int m,int n){
		this.mat = new double[m][n]; //m行ｎ列行列
		this.rowN = m;
		this.colN = n;
		if(m==n) {
			this.isSquare = true;
		}else {
			this.isSquare = false;
		}
		//基本は単位行列
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
		if(this.rowN==this.colN) {
			this.isSquare = true;
		}else {
			this.isSquare = false;
		}
	} // end of CMatrix(double[][] )
	public CMatrix(CMatrix in) {
		this.rowN = in.rowN;
		this.colN = in.colN;
		this.mat = new double[this.rowN][this.colN];
		for(int i=0;i<this.rowN;i++) {
			for(int j=0;j<this.colN;j++) {
				this.mat[i][j] = in.mat[i][j];
			}
		}
		if(this.rowN==this.colN) {
			this.isSquare = true;
		}else {
			this.isSquare = false;
		}
	}
	//転置
	public CMatrix transpose() {
		CMatrix r = new CMatrix(this.colN,this.rowN);
		for(int i=0;i<r.rowN;i++) {
			for(int j=0;j<r.colN;j++) {
				r.mat[i][j] = this.mat[j][i];
			}
		}
		return r;
	}
	//行列の足し算
	public CMatrix addMat(CMatrix in) {
		CMatrix r = new CMatrix(this.rowN,this.colN);
		for(int i= 0;i<this.rowN;i++) {
			for(int j=0;j<this.colN;j++) {
				double d = this.mat[i][j]+in.mat[i][j];
				r.mat[i][j] = d;
			}
		}
		return r;
	}
	//行列の引き算
	public CMatrix subtractMat(CMatrix in) {
		CMatrix r = new CMatrix(this.rowN,this.colN);
		for(int i= 0;i<this.rowN;i++) {
			for(int j=0;j<this.colN;j++) {
				double d = this.mat[i][j]-in.mat[i][j];
				r.mat[i][j] = d;
			}
		}
		return r;
	}
	//行列の定数倍
	public CMatrix byScalar(double in) {
		CMatrix r = new CMatrix(this.rowN,this.colN);
		for(int i= 0;i<this.rowN;i++) {
			for(int j=0;j<this.colN;j++) {
				double d = this.mat[i][j]*in;
				r.mat[i][j] = d;
			}
		}
		return r;
	}
	//自分自身を定数倍
	public void byScalarThis(double in) {
		for(int i= 0;i<this.rowN;i++) {
			for(int j=0;j<this.colN;j++) {
				double d = this.mat[i][j]*in;
				this.mat[i][j] = d;
			}
		}
	}
	//行列かけベクトル。後ろから列ベクトルをかける。
	//結果はこの行列の行数を次数とする列ベクトル
	public CVector_Col byVec(CVector_Col in) {
		CVector_Col r = new CVector_Col(this.rowN);
		for(int i=0;i<this.rowN;i++) {
			CVector_Row row = this.getRow(i);
			r.setValue(i,row.byVec(in));
		}
		return r;
	}
	//この行列の第p列と第q列を入れ替える
	public void exchangeCol(int p,int q) {
		CVector_Col tmp = this.getCol(p);
		this.setCol(p, this.getCol(q));
		this.setCol(q, tmp);
	}
	//この行列の第 p 行と第 q 行を入れ替える
	public void exchangeRow(int p,int q) {
		CVector_Row tmp = this.getRow(p);
		this.setRow(p, this.getRow(q));
		this.setRow(q, tmp);
	}
	//getter
	public double[][] getMat(){
		return this.mat;
	}
	//第(i,j)要素を返す
	public double getValue(int i,int j) {
		return this.mat[i][j];
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
	//正方行列かどうか？
	public boolean isSquare() {
		return this.isSquare;
	}
	//setter
	//(i,j)要素に値をセットする
	public void setValue(int i, int j, double v) {
		this.mat[i][j] = v;
	}
	//第i 行を CVector_Row で置き換える
	public void setRow(int i, CVector_Row in) {
		for(int j=0;j<this.colN;j++) {
			this.mat[i][j] = in.getArray()[j];
		}
	}
	//第i行を double[] で置き換える。
	public void setRow(int i, double[] in) {
		for(int j=0;j<this.colN;j++) {
			this.mat[i][j] = in[j];
		}
	}
	//第j列を CVector_Colで置き換える
	public void setCol(int j, CVector_Col in) {
		for(int i=0;i<this.rowN;i++) {
			this.mat[i][j] = in.getArray()[i];
		}
	}
	//第j列を double[] で置き換える
	public void setCol(int j, double[] in) {
		for(int i=0;i<this.rowN;i++) {
			this.mat[i][j] = in[i];
		}
	}
	
}
