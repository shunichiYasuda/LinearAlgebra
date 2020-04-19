package app;

import linearAlgebra.CMatrix;
import linearAlgebra.CVector_Col;
import linearAlgebra.CVector_Row;

public class CMain {

	public static void main(String[] args) {
		double[] v = {1,2,-1,1};
		CVector_Col a = new CVector_Col(v);
		printVec(a);
		System.out.println("norm="+a.norm());
		System.out.println("k=2:"+ a.getValue(2));
		CVector_Row b = a.tranPort();
		printVec(b);
		System.out.println("-----------------");
		printMat(a.byVec(b));
		printVec(a.byVec(b).getRow(2));
		printVec(a.byVec(b).getCol(1));
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
