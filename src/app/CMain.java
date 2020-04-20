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
	}
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
