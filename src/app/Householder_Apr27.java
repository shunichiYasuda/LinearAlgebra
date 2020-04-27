package app;

import java.math.BigDecimal;
import java.math.RoundingMode;

import linearAlgebra.CMatrix;
import linearAlgebra.CVector_Col;
import linearAlgebra.CVector_Row;

public class Householder_Apr27 {

	public static void main(String[] args) {
		double[][] data = { { 0, 1, 4, -6 }, { 4, -2, -2, 4 }, { -2, 0, 0, 3 }, { 8,1, 6, -2 } };
		CMatrix A = new CMatrix(data);
		printMat(A);
		System.out.println("det A ="+A.det());
		CMatrix Q = new CMatrix(A.rowN,A.colN);
		int loopLimit = A.rowN-1;
		for(int pivot = 0;pivot < loopLimit;pivot++) {
			//Householder 小行列をつくる。
			//行列の pivot 行までは単位行列であるとして、その部分を剥ぎ取る
			CMatrix Hr = A.contractMat(pivot);
			System.out.println("-------第"+ pivot +"回 Hr -------");
			printMat(Hr);
			//Hrの最初の列ベクトルを x とする。
			CVector_Col x = Hr.getCol(0);
			System.out.println("-------第"+ pivot +"回の x-------");
			printVec(x);
			CVector_Col y = new CVector_Col(x.getDim());
			double norm_x = x.getNorm();
			y.setValue(0, (-1)*norm_x);
			System.out.println("-------第"+ pivot +"回の y-------");
			printVec(y);
			//v = x -y を作る
			CVector_Col v = x.subtractVec(y);
			//この行列の Householder 行列
			CMatrix sourceHH = v.byVec(v.transpose());
			double coef = 2.0 / (v.getNorm()*v.getNorm());
			sourceHH.byScalarThis(coef);
			CMatrix unit = new CMatrix(sourceHH.colN,sourceHH.rowN);
			CMatrix HH = unit.subtractMat(sourceHH);
			System.out.println("-------第"+ pivot +"回の HH-------");
			printMat(HH);
			//HHを元の次数に拡大
			CMatrix exHH = HH.expanedMat(pivot);
			System.out.println("-------第"+ pivot +"回の exHH-------");
			printMat(exHH);
			A = exHH.byMat(A);
			System.out.println("-------第"+ pivot +"回の 行列A-------");
			printMat(A);
			//行列Q を作る。
			Q = Q.byMat(exHH.transpose());
			System.out.println("-------第"+ pivot +"回の 行列Q-------");
			printMat(Q);
		} //end of for(int pivot =0 ...
		//QR分解のチェック
		System.out.println("------QR ----------");
		printMat(Q.byMat(A));
		System.out.println("-------R 対角要素の積---------");
		System.out.println("det R="+ A.det());
		
	}
	static void printMat(double[][] in) {
		for (double[] r : in) {
			for (double d : r) {
				BigDecimal a = BigDecimal.valueOf(d);
				System.out.print(a.setScale(4,RoundingMode.HALF_UP) + "\t");
			}
			System.out.print("\n");
		}
	}// end of printMatrix(double[][] )

	static void printMat(CMatrix in) {
		printMat(in.getMat());
	}

	//
	static void printVec(CVector_Col in) {
		for (int i = 0; i < in.getDim(); i++) {
			System.out.println(String.format("%4f", in.getValue(i)));
		}
	}

	//
	static void printVec(CVector_Row in) {
		for (int i = 0; i < in.getDim(); i++) {
			System.out.print("\t" + String.format("%.4f",in.getValue(i)));
		}
		System.out.println();
	}

}
