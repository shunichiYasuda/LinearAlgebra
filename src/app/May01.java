package app;

import java.math.BigDecimal;
import java.math.RoundingMode;

import linearAlgebra.CMatrix;
import linearAlgebra.CVector_Col;
import linearAlgebra.CVector_Row;

public class May01 {

	public static void main(String[] args) {
		double[][] data = { { 1, 0.7841, 0.3392, 0.4935 }, { 0.7841, 1.0, 0.4976, 0.5469 },
				{ 0.3392, 0.4976, 1.0, 0.8838 }, { 0.4935, 0.5469, 0.8838, 1.0 } };
		printLine("original Matrix");
		CMatrix origin = new CMatrix(data);
		printMat(origin);
		//
		// �ŗL�x�N�g�����͂���A����� CVector_Col �z��
		int numOfVariables = origin.colN;
		CVector_Col[] eigenVecArray = new CVector_Col[numOfVariables];
		for (int i = 0; i < eigenVecArray.length; i++) {
			eigenVecArray[i] = new CVector_Col(numOfVariables);
			eigenVecArray[i].setValue(0, 1.0);
		}
		// �ŗL�l������\��� double �z��
		double[] eigenValue = new double[numOfVariables];
		// �ׂ���@
		for (int loop = 0; loop < numOfVariables; loop++) {
			CVector_Col before = eigenVecArray[loop];
			boolean checkFlag = false; // ��������t���O
			while (!checkFlag) {
				CVector_Col after = origin.byVec(before);
				eigenValue[loop] = after.getNorm();
				after.normalizeThis();
				// �����`�F�b�N
				checkFlag = checkVec(after, before);
				if (checkFlag) {
					eigenVecArray[loop] = after;
				}
				// ����ւ�
				before = after;
			} // end of while(...
			printLine("�ŗL�x�N�g��:"+loop+"���");
			printVec(eigenVecArray[loop]);
			printLine("�ŗL�l:"+loop+"���");
			System.out.println(eigenValue[loop]);
			// 2��
			// ���������s�������
			CMatrix subMatrix = eigenVecArray[loop].byVec(eigenVecArray[loop].transpose());
			subMatrix.byScalarThis(eigenValue[loop]);
			printLine("subMatrix");
			printMat(subMatrix);
			//
			origin = origin.subtractMat(subMatrix);
			printLine("orgin - subMatrix");
			printMat(origin);
		}
		//�ŗL�l
		printLine("�ŗL�l");
		for(double d : eigenValue) {
			System.out.print(d+"\t");
		}
		System.out.println("");
		//�ŗL�x�N�g������Ȃ�s��
		CMatrix eigenMatrix = new CMatrix(eigenVecArray);
		printLine("�ŗL�s��");
		printMat(eigenMatrix);

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

	//
	// �����`�F�b�N�̂��߂�2�̃x�N�g���̍����Ƃ�A�������l�����Ȃ� true ��Ԃ�
	static boolean checkVec(CVector_Col x1, CVector_Col x2) {
		boolean r = true;
		CVector_Col check = x1.subtractVec(x2);
		for (int i = 0; i < check.getDim(); i++) {
			if (Math.abs(check.getValue(i)) > 1.0E-5)
				r = false;
		}
		return r;
	}
}// end of class May01