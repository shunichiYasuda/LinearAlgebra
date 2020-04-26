package app;

import java.math.BigDecimal;
import java.math.RoundingMode;

import linearAlgebra.CMatrix;
import linearAlgebra.CVector_Col;
import linearAlgebra.CVector_Row;

public class Householder {

	public static void main(String[] args) {
		double[][] d = {{1,-5,3},
				{2,1,1},
				{2,0,2}
		};
		double[][] data = { { 0, 1, 4, -6 }, { 4, -2, -2, 4 }, { -2, 0, 0, 3 }, { 8,1, 6, -2 } };
		CMatrix A = new CMatrix(data);
		printMat(A);
		CVector_Col x = A.getCol(0);
		printVec(x);
		CVector_Col y = new CVector_Col(x.getDim());
		printVec(y);
		double norm_x = x.getNorm();
		y.setValue(0, (-1)*norm_x);
		System.out.println("---------------------");
		printVec(y);
		//
		CVector_Col v = x.subtractVec(y);
		//
		System.out.println("---------------------");
		printVec(v);
		System.out.println("---------------------");
		CMatrix sourceHH = v.byVec(v.transpose());
		System.out.println("---------------------");
		printMat(sourceHH);
		double coef = 2.0 / (v.getNorm()*v.getNorm());
		sourceHH.byScalarThis(coef);
		System.out.println("---------------------");
		printMat(sourceHH);
		//
		CMatrix unit = new CMatrix(sourceHH.colN,sourceHH.rowN);
		System.out.println("---------------------");
		printMat(unit);
		//
		CMatrix HH = unit.subtractMat(sourceHH);
		System.out.println("---------------------");
		printMat(HH);
		//
		System.out.println("---------A1----------");
		CMatrix A1 = HH.byMat(A);
		printMat(A1);
		System.out.println("--------ëÊ2íiäK----------");
		//A1ÇÃè¨çsóÒÇçÏÇÈ
		CMatrix A2dash = A1.contractMat(0, 0);
		System.out.println("----A1ÇÃè¨çsóÒ-------");
		printMat(A2dash);
		x = A2dash.getCol(0);
		System.out.println("----------------------");
		printVec(x);
		y = new CVector_Col(x.getDim());
		norm_x = x.getNorm();
		y.setValue(0, (-1)*norm_x);
		System.out.println("---------------------");
		printVec(y);
		v = x.subtractVec(y);
		System.out.println("---------------------");
		printVec(v);
		sourceHH = v.byVec(v.transpose());
		coef = 2.0 / (v.getNorm()*v.getNorm());
		sourceHH.byScalarThis(coef);
		System.out.println("---------------------");
		printMat(sourceHH);
		unit = new CMatrix(sourceHH.colN,sourceHH.rowN);
		HH = unit.subtractMat(sourceHH);
		System.out.println("---------------------");
		printMat(HH);
		//HH ÇägëÂ
		CMatrix H1 = HH.expanedMat();
		System.out.println("---------------------");
		printMat(H1);
		System.out.println("---------------------");
		printMat(H1.byMat(A1));
		
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
