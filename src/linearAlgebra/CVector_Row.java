package linearAlgebra;

public class CVector_Row {
	private int dim ; //列ベクトルの次数
	private double norm; //ノルム
	private double[][] mat; //1行n列の行列として実装
	public CVector_Row(int n) {
		this.dim = n;
		this.mat = new double[1][n];
		this.norm = norm();
	}
	//
	public CVector_Row(double[] in) {
		this.dim = in.length;
		this.mat = new double[1][this.dim];
		this.norm = norm();
		for(int i=0;i<this.dim;i++) {
			this.mat[0][i] = in[i];
		}
	}
	//
	public double norm() {
		double r = 0.0;
		for(int i=0;i<this.dim;i++) {
			r += this.mat[0][i]*this.mat[0][i];
		}
		r = Math.sqrt(r);
		return r;
	}
	//getter
	public int getDim() {
		return this.dim;
	}
	//
	public double[] getArray() {
		double[] r = new double[this.dim];
		for(int i=0;i<r.length;i++) {
			r[i] = this.mat[0][i];
		}
		return r;
	}
	//第ｋ番目の要素を返す
	public double getValue(int k) {
		return this.mat[0][k];
	}
}
