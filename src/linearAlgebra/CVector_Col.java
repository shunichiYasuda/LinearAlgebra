package linearAlgebra;

public class CVector_Col {
	private int dim ; //��x�N�g���̎���
	private double norm; //�m����
	private double[][] mat; //n�s1��̍s��Ƃ��Ď���
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
	//�]�u
	public CVector_Row tranPort() {
		CVector_Row r = new CVector_Row(this.getArray());
		return r;
	}
	//�����Z�E�x�N�g�����m
	public CMatrix byVec(CVector_Row in) {
		int num_row = this.dim; //���̃x�N�g���̎���
		int num_col = in.getDim(); //���̍s�x�N�g���̎���
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
	//��k�Ԗڂ̗v�f��Ԃ�
	public double getValue(int k) {
		return this.mat[k][0];
	}
	//double[] �Ƃ��ĕԂ�
	public double[] getArray() {
		double[] r = new double[this.dim];
		for(int i=0;i<r.length;i++) {
			r[i] = this.mat[i][0];
		}
		return r;
	}
	//
}