package app;

import java.math.BigDecimal;
import java.math.RoundingMode;

import linearAlgebra.CMatrix;
import linearAlgebra.CVector_Col;
import linearAlgebra.CVector_Row;

public class Householder_Apr27_2 {

	public static void main(String[] args) {
		//double[][] data = {{ 8, 1, 6, -2 }, { 4, -2, -2, 4 }, { 0, 1, 4, -6 },{ -2, 0, 0, 3 }  };
		//double[][] data = {{4,2,1},{3,2,1},{1,1,1}};
		double[][] data = {{1,0.7841,0.3392,0.4935},
				{0.7841,1.0,0.4976,0.5469},
				{0.3392,0.4976,1.0,0.8838},
				{0.4935,0.5469,0.8838,1.0}
		};
		CMatrix A = new CMatrix(data);
		System.out.println("------- Matrix A ---------");
		printMat(A);
		//収束チェック配列：初期値をAの対角成分
		double[] checkArray = new double[A.rowN];
		for(int j=0;j<checkArray.length;j++) {
			checkArray[j] = A.getValue(j, j);
		}
		CVector_Row before = new CVector_Row(checkArray);
		printVec(before);
		boolean checkFlag = false;
		int maxCount = 200;
		int n=0;
		while(!checkFlag && n<maxCount) {
			CMatrix[] qrMatrix = A.QRDecomp();
			System.out.println("-------Q("+n+") ---------");
			CMatrix Q = qrMatrix[0];
			printMat(Q);
			System.out.println("-------R("+n+") ---------");
			CMatrix R = qrMatrix[1];
			printMat(R);
			System.out.println("------- A("+n+") = RQ ---------");
			A = R.byMat(Q);
			printMat(A);
			//収束チェック
			for(int j=0;j<checkArray.length;j++) {
				checkArray[j] = A.getValue(j, j);
			}
			CVector_Row after = new CVector_Row(checkArray);
			System.out.print("before:");
			printVec(before);
			System.out.print("after:");
			printVec(after);
			CVector_Row check = after.subtractVec(before);
			System.out.print("check:");
			printVec(check);
			checkFlag = true;
			for(int i=0;i<check.getDim();i++) {
				if(Math.abs(check.getValue(i)) > 1.0E-5) checkFlag =false;
			}
			before = after;
			n++;
		}

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
