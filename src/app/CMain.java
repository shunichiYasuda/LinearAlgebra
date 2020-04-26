package app;

import java.math.BigDecimal;
import java.math.RoundingMode;

import linearAlgebra.CMatrix;
import linearAlgebra.CVector_Col;
import linearAlgebra.CVector_Row;
import linearAlgebra.UnitVector_Col;

public class CMain {

	public static void main(String[] args) {
		System.out.println("--------4x4 matrix ---------");
		//double[][] data = { { 2, -2, 4, 2 }, { 2, -1, 6, 3 }, { 3, -2, 12, 12 }, { -1, 3, -4, 4 } };
		double[][] data = { { 0, 1, 4, -6 }, { 4, -2, -2, 4 }, { -2, 0, 0, 3 }, { 8,1, 6, -2 } };
		CMatrix original = new CMatrix(data);
		printMat(original);
		System.out.println("-------4x4単位行列 ---------");
		CMatrix unit = new CMatrix(4, 4);
		printMat(unit);
		System.out.println("-------入れ替え処理 ---------");
		int count = 0;
		for (int i = 0; i < original.rowN; i++) {
			int pivotRow = i;
			int maxpos = original.getCol(pivotRow).hwMaxPos(i);
			System.out.println("第" + pivotRow + "列目　max pos =" + maxpos);
			if (maxpos != pivotRow) {
				System.out.println("第" + maxpos + " 行と" + "第" + pivotRow + "行を入れ替える.単位行列も同じ処理");
				original.exchangeRow(pivotRow, maxpos);
				unit.exchangeRow(pivotRow, maxpos);
				count++;
				printMat(original);
				System.out.println("------------------------");
				printMat(unit);
			} // end of if(...)
		} // end of for(...)
		System.out.println("入れ替え回数" + count);
		// ここから上三角行列をつくる
		for (int baseRow = 0; baseRow < original.rowN - 1; baseRow++) {
			CVector_Row baseRowVec = original.getRow(baseRow);
			CVector_Row baseRowVecUnit = unit.getRow(baseRow);
			System.out.println("baseRow =" + baseRow);
			printVec(baseRowVec);
			int targetCol = baseRow;
			double baseValue = baseRowVec.getValue(targetCol);
			double baseValueUnit = baseRowVecUnit.getValue(targetCol);
			System.out.println("baseValue = " + baseValue);
			for (int targetRow = baseRow + 1; targetRow < original.rowN; targetRow++) {
				// 処理する行を抜き出す
				CVector_Row targetRowVec = original.getRow(targetRow);
				CVector_Row targetRowVecUnit = unit.getRow(targetRow);
				System.out.println("target vector");
				printVec(targetRowVec);
				double targetValue = targetRowVec.getValue(targetCol);
				System.out.println("targetValue = " + targetValue);
				double ratio = targetValue / baseValue;
				System.out.println("ratio =" + ratio);
				// base行にすべて ratio をかける
				System.out.println("第" + baseRow + "行 x ratio");
				CVector_Row tmpVec = baseRowVec.byScalar(ratio);
				CVector_Row tmpVecUnit = baseRowVecUnit.byScalar(ratio);
				printVec(tmpVec);
				System.out.println("第" + targetRow + "行 - 第" + baseRow + "行 x ratio");
				CVector_Row resultVec = targetRowVec.subtractVec(tmpVec);
				CVector_Row resultVecUnit = targetRowVecUnit.subtractVec(tmpVecUnit);
				printVec(resultVec);
				// 行列の注目行をこの結果に置き換える。
				original.setRow(targetRow, resultVec);
				unit.setRow(targetRow, resultVecUnit);
				System.out.println("結果行列");
				printMat(unit);
			} // end of for(int targetRow=baseRow+1

		} // end of for(int baseRow = 0
		System.out.println("結果行列");
		printMat(unit);
		// 以上で original 行列は上三角行列になった。
		// 行列式
		double det = 1.0;
		for (int i = 0; i < original.rowN; i++) {
			det *= original.getValue(i, i);
		}
		det = Math.pow(-1.0, count) * det;
		System.out.println("det = " + det);
		System.out.println("----ガウス・ジョルダン法を上三角行列に適用-----");
		//
		for (int pivot = 0; pivot < original.rowN; pivot++) {
			// pivot 行、pivot列成分を1 にする。
			double invPivot = 1.0 / original.getValue(pivot, pivot);
			CVector_Row pivotVec = original.getRow(pivot);
			CVector_Row pivotVecUnit = unit.getRow(pivot);
			pivotVec.byScalarThis(invPivot);
			pivotVecUnit.byScalarThis(invPivot);
			original.setRow(pivot, pivotVec);
			unit.setRow(pivot, pivotVecUnit);
			System.out.println("pivot =" + pivot + "行の処理終了後");
			// printMat(original);
			for (int i = 0; i < pivot; i++) {
				if (i != pivot) {
					CVector_Row targetVec = original.getRow(i);
					CVector_Row targetVecUnit = unit.getRow(i);
					double value = targetVec.getValue(pivot);
					CVector_Row tmpVec = pivotVec.byScalar(value);
					CVector_Row subVec = targetVec.subtractVec(tmpVec);
					CVector_Row tmpVecUnit = pivotVecUnit.byScalar(value);
					CVector_Row subVecUnit = targetVecUnit.subtractVec(tmpVecUnit);
					original.setRow(i, subVec);
					unit.setRow(i, subVecUnit);
				}
				System.out.println("target i =" + i + "行の処理終了後");
				printMat(unit);
			}
			//
			

		}

		double[][] tableA = { { -2, 3 }, { 1, -1 } };
		double[][] tableB = { { -1, 4 }, { 2, -1 } };
		CMatrix matA = new CMatrix(tableA);
		CMatrix matB = new CMatrix(tableB);
		System.out.println("matA");
		printMat(matA);
		System.out.println("matB");
		printMat(matB);
		CMatrix matC = matA.byMat(matB);
		System.out.println("--- matA x matB ---");
		printMat(matC);
		//
		CMatrix matD = new CMatrix(data);
		printMat(matD.byMat(unit));
		//
		System.out.println("method テスト");
		CMatrix permutUnitMatrix = matD.permut();
		printMat(permutUnitMatrix);
		System.out.println("det ="+matD.det());
		//
		System.out.println("matD");
		printMat(matD);
		System.out.println("--------inverse--------");
		CMatrix invMatD = matD.inverse();
		printMat(invMatD);
		System.out.println("check");
		printMat(matD.byMat(invMatD));
		//
		System.out.println("matA");
		printMat(matA);
		System.out.println("--------inverse--------");
		CMatrix invMatA = matA.inverse();
		printMat(invMatA);
		System.out.println("check");
		printMat(matA.byMat(invMatA));
		System.out.println("det ="+matA.det());
		
		//
		CMatrix matrixA = new CMatrix(data);
		System.out.println("----- matrix A ------");
		printMat(matrixA);
		CMatrix matrixB = matrixA.contractMat(0, 0);
		System.out.println("----- matrix B ------");
		printMat(matrixB);
		//
		CMatrix matrixC =matrixB.expanedMat();
		System.out.println("--------matrix C --------");
		printMat(matrixC);
	} // end of main()
		//

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
	//
	static void printVec(UnitVector_Col in) {
		for (int i = 0; i < in.getDim(); i++) {
			System.out.println(String.format("%4f", in.getValue(i)));
		}
	}

}
