package app;

import java.math.BigDecimal;
import java.math.RoundingMode;

import linearAlgebra.CMatrix;
import linearAlgebra.CVector_Col;
import linearAlgebra.CVector_Row;

public class May03 {

	public static void main(String[] args) {
		//double[][] data = { { 1, 0.7841, 0.3392, 0.4935 }, { 0.7841, 1.0, 0.4976, 0.5469 },
		//		{ 0.3392, 0.4976, 1.0, 0.8838 }, { 0.4935, 0.5469, 0.8838, 1.0 } };
		//double[][] data = {{6.5,3},{3,2}};
		double[][] data = {{3,1},{1,3}};
		printLine("original Matrix");
		CMatrix origin = new CMatrix(data);
		printMat(origin);
		//
		CVector_Col eigenValue = origin.eigenValueVec_Vec();
		//
		printLine("固有値");
		printVec(eigenValue);
		printLine("固有ベクトル");
		printMat(origin);
		

	} // end of main()

	// support methods
	static void printLine(String string) {
		System.out.println("----------" + string + "----------");
	}

	static void printMat(double[][] in) {
		for (double[] r : in) {
			for (double d : r) {
				BigDecimal a = BigDecimal.valueOf(d);
				System.out.print(a.setScale(4, RoundingMode.HALF_UP) + "\t");
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
			System.out.print("\t" + String.format("%.4f", in.getValue(i)));
		}
		System.out.println();
	}
}
