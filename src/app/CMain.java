package app;

import linearAlgebra.CMatrix;
import linearAlgebra.CVector_Col;
import linearAlgebra.CVector_Row;

public class CMain {

	public static void main(String[] args) {
		double[] v = {1,2,-1};
		CVector_Col a = new CVector_Col(v);
		printVec(a);
		CVector_Row b = new CVector_Row(new double[] {1,3,-2});
		printVec(b);
		System.out.println("-----------------");
		CMatrix c = a.byVec(b);
		printMat(c);
		System.out.println("-----------------");
		c.setCol(2, a);
		printMat(c);
		System.out.println("-----------------");
		c.setRow(1, b);
		printMat(c);
		System.out.println("--------c a matrix by vector---------");
		printVec(c.byVec(a));
		System.out.println("--------b c vector by matrix ---------");
		printVec(b.byMat(c));
		System.out.println("--------3x3 matrix ---------");
		double[][] data ={{1,2,0,-1},{-1,1,2,0},{2,0,1,1},{1,-2,-1,1}};
		CMatrix original = new CMatrix(data);
		printMat(original);
		System.out.println("--------この行列の各行について最大列番号と絶対最大値 ---------");
		for(int i=0;i<original.rowN;i++) {
			int maxpos = original.getRow(i).hwMaxPos();
			System.out.println("max pos ="+ maxpos);
			double d = original.getRow(i).getMax();
			System.out.println("max value="+d);
		}
		System.out.println("--------この行列の各列について最大列番号と絶対最大値 ---------");
		for(int j=0;j<original.colN;j++) {
			int maxpos = original.getCol(j).hwMaxPos();
			System.out.println("max pos ="+ maxpos);
			double d = original.getCol(j).getMax();
			System.out.println("max value="+d);
		}
		System.out.println("--------行ベクトル b の3倍 ---------");
		printVec(b.byScalar(3.0));
	} // end of main()
	//
	static void printMat(double[][] in) {
		for (double[] r : in) {
			for (double d : r) {
				System.out.print(d + "\t");
			}
			System.out.print("\n");
		}
	}// end of printMatrix(double[][] )
	static void printMat(CMatrix in) {
		printMat(in.getMat());
	}
	//
	static void printVec(CVector_Col in) {
		for(int i=0;i< in.getDim();i++) {
			System.out.println(in.getValue(i));
		}
	}
	//
	static void printVec(CVector_Row in) {
		for(int i=0;i< in.getDim();i++) {
			System.out.print("\t"+in.getValue(i));
		}
		System.out.println();
	}

}
