package app;

import linearAlgebra.CMatrix;
import linearAlgebra.CVector_Col;
import linearAlgebra.CVector_Row;

public class CMain {

	public static void main(String[] args) {
		double[] v = { 1, 2, -1 };
		CVector_Col a = new CVector_Col(v);
		printVec(a);
		CVector_Row b = new CVector_Row(new double[] { 1, 3, -2 });
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
		System.out.println("--------4x4 matrix ---------");
		double[][] data = {{2,-2,4,2},{2,-1,6,3},{3,-2,12,12},{-1,3,-4,4}};
		//double[][] data = { { 0, 1, 4, -6 }, { 4, -2, -2, 4 }, { -2, 0, 0, 3 }, { 8, 1, 6, -2 } };
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
			System.out.println("baseRow =" + baseRow);
			printVec(baseRowVec);
			int targetCol = baseRow;
			double baseValue = baseRowVec.getValue(targetCol);
			System.out.println("baseValue = " + baseValue);
			for (int targetRow = baseRow + 1; targetRow < original.rowN; targetRow++) {
				// 処理する行を抜き出す
				CVector_Row targetRowVec = original.getRow(targetRow);
				System.out.println("target vector");
				printVec(targetRowVec);
				double targetValue = targetRowVec.getValue(targetCol);
				System.out.println("targetValue = " + targetValue);
				double ratio = targetValue / baseValue;
				System.out.println("ratio =" + ratio);
				// base行にすべて ratio をかける
				System.out.println("第" + baseRow + "行 x ratio");
				CVector_Row tmpVec = baseRowVec.byScalar(ratio);
				printVec(tmpVec);
				System.out.println("第" + targetRow + "行 - 第" + baseRow + "行 x ratio");
				CVector_Row resultVec = targetRowVec.subtractVec(tmpVec);
				printVec(resultVec);
				// 行列の注目行をこの結果に置き換える。
				original.setRow(targetRow, resultVec);
				System.out.println("結果行列");
				printMat(original);
			} // end of for(int targetRow=baseRow+1
			
		} // end of for(int baseRow = 0
		System.out.println("結果行列");
		printMat(original);
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
			pivotVec.byScalarThis(invPivot);
			original.setRow(pivot, pivotVec);
			System.out.println("pivot ="+pivot+"行の処理終了後");
			printMat(original);
			for(int i=0;i<pivot;i++) {
				if(i != pivot) {
					CVector_Row targetVec = original.getRow(i);
					double value = targetVec.getValue(pivot);
					CVector_Row tmpVec = pivotVec.byScalar(value);
					CVector_Row subVec = targetVec.subtractVec(tmpVec);
					original.setRow(i, subVec);
				}
				System.out.println("target i ="+i+"行の処理終了後");
				printMat(original);
			}
		}
		//
		
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
		for (int i = 0; i < in.getDim(); i++) {
			System.out.println(in.getValue(i));
		}
	}

	//
	static void printVec(CVector_Row in) {
		for (int i = 0; i < in.getDim(); i++) {
			System.out.print("\t" + in.getValue(i));
		}
		System.out.println();
	}

}
