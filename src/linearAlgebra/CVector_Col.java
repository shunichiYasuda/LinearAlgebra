package linearAlgebra;

public class CVector_Col {
	private int dim ; //列ベクトルの次数
	private double norm; //ノルム
	private double[][] mat; //n行1列の行列として実装
	//
	public CVector_Col(int n) {
		this.dim = n;
		this.mat = new double[n][1];
		this.norm = norm();
	}
	//
	public CVector_Col(double[] in) {
		this.dim = in.length;
		this.mat = new double[this.dim][1];
		this.norm = norm();
		for(int i=0;i<this.dim;i++) {
			this.mat[i][0] = in[i];
		}
	}
	//
	public double norm() {
		double r = 0.0;
		for(int i=0;i<this.dim;i++) {
			r += this.mat[i][0]*this.mat[i][0];
		}
		r = Math.sqrt(r);
		return r;
	}
	//転置
	public CVector_Row transpose() {
		CVector_Row r = new CVector_Row(this.getArray());
		return r;
	}
	//かけ算・ベクトル同士
	public CMatrix byVec(CVector_Row in) {
		int num_row = this.dim; //このベクトルの次数
		int num_col = in.getDim(); //後ろの行ベクトルの次数
		CMatrix r = new CMatrix(num_row,num_col);
		for(int i=0;i<num_row;i++) {
			for(int j=0;j<num_col;j++) {
				r.setValue(i, j, this.getValue(i)*in.getValue(j));
			}
		}
		return r;
	}
	//getter
	public int getDim() {
		return this.dim;
	}
	//第k番目の要素を返す
	public double getValue(int k) {
		return this.mat[k][0];
	}
	//double[] として返す
	public double[] getArray() {
		double[] r = new double[this.dim];
		for(int i=0;i<r.length;i++) {
			r[i] = this.mat[i][0];
		}
		return r;
	}
	//ノルムを返す
	public double getNorm() {
		return this.norm;
	}
	//setter
	//第 i 要素に値を入れる
	public void setValue(int i,double d) {
		this.mat[i][0] = d;
		this.norm = norm();
	}
	//double[] で中身を入れ替える
	public void setValue(double[] in) {
		for(int i=0;i<this.dim;i++) {
			this.mat[i][0] = in[i];
		}
		this.norm = norm();
	}
}
