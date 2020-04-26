package linearAlgebra;

public class CMatrix {
	private double[][] mat;
	public int colN, rowN; // 行数、列数
	public boolean isSquare; // 正方行列か？

	public CMatrix(int m, int n) {
		this.mat = new double[m][n]; // m行ｎ列行列
		this.rowN = m;
		this.colN = n;
		if (m == n) {
			this.isSquare = true;
		} else {
			this.isSquare = false;
		}
		// 基本は単位行列
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					this.mat[i][j] = 1.0;
				} else {
					this.mat[i][j] = 0.0;
				}
			}
		}
	} // end of CMatrix(int , int)

	public CMatrix(double[][] in) {
		this.rowN = in.length;
		this.colN = in[0].length;
		this.mat = new double[this.rowN][this.colN];
		for (int i = 0; i < this.rowN; i++) {
			for (int j = 0; j < this.colN; j++) {
				this.mat[i][j] = in[i][j];
			}
		}
		if (this.rowN == this.colN) {
			this.isSquare = true;
		} else {
			this.isSquare = false;
		}
	} // end of CMatrix(double[][] )

	public CMatrix(CMatrix in) {
		this.rowN = in.rowN;
		this.colN = in.colN;
		this.mat = new double[this.rowN][this.colN];
		for (int i = 0; i < this.rowN; i++) {
			for (int j = 0; j < this.colN; j++) {
				this.mat[i][j] = in.mat[i][j];
			}
		}
		if (this.rowN == this.colN) {
			this.isSquare = true;
		} else {
			this.isSquare = false;
		}
	}

	// 転置
	public CMatrix transpose() {
		CMatrix r = new CMatrix(this.colN, this.rowN);
		for (int i = 0; i < r.rowN; i++) {
			for (int j = 0; j < r.colN; j++) {
				r.mat[i][j] = this.mat[j][i];
			}
		}
		return r;
	}

	// 縮小行列
	public CMatrix contractMat(int i, int j) {
		// 第i行、第j列を取り去った縮小行列を返す。
		CMatrix r = new CMatrix(this.rowN-1, this.colN-1);
		for (int p = 0; p < i; p++) {
			for (int q = 0; q < j; q++) {
				r.mat[p][q] = this.mat[p][q];
			}
		}
		for(int p=i+1;p<this.rowN;p++) {
			for(int q=j+1;q<this.colN;q++) {
				r.mat[p-1][q-1] = this.mat[p][q];
			}
		}
		return r;
	}
	//自分自身を縮小する
	public void contractMatThis(int i, int j) {
		double[][] con = new double[this.rowN-1][this.colN-1];
		for (int p = 0; p < i; p++) {
			for (int q = 0; q < j; q++) {
				con[p][q] = this.mat[p][q];
			}
		}
		for(int p=i+1;p<this.rowN;p++) {
			for(int q=j+1;q<this.colN;q++) {
				con[p-1][q-1] = this.mat[p][q];
			}
		}
		//
		this.rowN -= 1;
		this.colN -=1;
		this.isSquare = this.isSquare();
		this.mat = con;
	}
	//拡大行列
	public CMatrix expanedMat() {
		int theRow = this.rowN;
		int theCol = this.colN;
		int exRow = theRow+1;
		int exCol = theCol+1;
		//CMatrx() は対角要素が1で他が0であることを利用する。
		CMatrix r = new CMatrix(exRow,exCol);
		for(int i=0;i<theRow;i++) {
			for(int j=0;j<theCol;j++) {
				r.mat[i+1][j+1] = this.mat[i][j];
			}
		}
		//
		return r;
	}

	// 行列の足し算
	public CMatrix addMat(CMatrix in) {
		CMatrix r = new CMatrix(this.rowN, this.colN);
		for (int i = 0; i < this.rowN; i++) {
			for (int j = 0; j < this.colN; j++) {
				double d = this.mat[i][j] + in.mat[i][j];
				r.mat[i][j] = d;
			}
		}
		return r;
	}

	// 行列の引き算
	public CMatrix subtractMat(CMatrix in) {
		CMatrix r = new CMatrix(this.rowN, this.colN);
		for (int i = 0; i < this.rowN; i++) {
			for (int j = 0; j < this.colN; j++) {
				double d = this.mat[i][j] - in.mat[i][j];
				r.mat[i][j] = d;
			}
		}
		return r;
	}

	// 行列の定数倍
	public CMatrix byScalar(double in) {
		CMatrix r = new CMatrix(this.rowN, this.colN);
		for (int i = 0; i < this.rowN; i++) {
			for (int j = 0; j < this.colN; j++) {
				double d = this.mat[i][j] * in;
				r.mat[i][j] = d;
			}
		}
		return r;
	}

	// 自分自身を定数倍
	public void byScalarThis(double in) {
		for (int i = 0; i < this.rowN; i++) {
			for (int j = 0; j < this.colN; j++) {
				double d = this.mat[i][j] * in;
				this.mat[i][j] = d;
			}
		}
	}

	// 行列かけベクトル。後ろから列ベクトルをかける。
	// 結果はこの行列の行数を次数とする列ベクトル
	public CVector_Col byVec(CVector_Col in) {
		CVector_Col r = new CVector_Col(this.rowN);
		for (int i = 0; i < this.rowN; i++) {
			CVector_Row row = this.getRow(i);
			r.setValue(i, row.byVec(in));
		}
		return r;
	}

	// 行列のかけ算。後ろからかける
	public CMatrix byMat(CMatrix in) {
		CMatrix r = new CMatrix(this.rowN, in.colN);
		for (int i = 0; i < r.rowN; i++) {
			for (int j = 0; j < r.colN; j++) {
				r.mat[i][j] = this.getRow(i).byVec(in.getCol(j));
			}
		}
		return r;
	}

	// 逆行列計算のため単位置換行列を出力する。
	public CMatrix permut() {
		CMatrix original = new CMatrix(this);
		CMatrix r = new CMatrix(original.rowN, original.colN);
		// int count = 0;
		for (int i = 0; i < original.rowN; i++) {
			int pivotRow = i;
			int maxpos = original.getCol(pivotRow).hwMaxPos(i);
			if (maxpos != pivotRow) {
				original.exchangeRow(pivotRow, maxpos);
				r.exchangeRow(pivotRow, maxpos);
				// count++;
			} // end of if(...)
		} // end of for(...)
		return r;
	}

	// 行列式。
	public double det() {
		CMatrix original = new CMatrix(this);
		CMatrix r = new CMatrix(this.rowN, this.colN);
		int count = 0;
		for (int i = 0; i < original.rowN; i++) {
			int pivotRow = i;
			int maxpos = original.getCol(pivotRow).hwMaxPos(i);
			if (maxpos != pivotRow) {
				original.exchangeRow(pivotRow, maxpos);
				r.exchangeRow(pivotRow, maxpos);
				count++;
			} // end of if(...)
		} // end of for(...)
			// 以上で置換終わり
			// ここから上三角行列をつくる
		for (int baseRow = 0; baseRow < original.rowN - 1; baseRow++) {
			CVector_Row baseRowVec = original.getRow(baseRow);
			CVector_Row baseRowVecUnit = r.getRow(baseRow);
			int targetCol = baseRow;
			double baseValue = baseRowVec.getValue(targetCol);
			for (int targetRow = baseRow + 1; targetRow < original.rowN; targetRow++) {
				// 処理する行を抜き出す
				CVector_Row targetRowVec = original.getRow(targetRow);
				CVector_Row targetRowVecUnit = r.getRow(targetRow);
				double targetValue = targetRowVec.getValue(targetCol);
				double ratio = targetValue / baseValue;
				// base行にすべて ratio をかける
				CVector_Row tmpVec = baseRowVec.byScalar(ratio);
				CVector_Row tmpVecUnit = baseRowVecUnit.byScalar(ratio);
				CVector_Row resultVec = targetRowVec.subtractVec(tmpVec);
				CVector_Row resultVecUnit = targetRowVecUnit.subtractVec(tmpVecUnit);
				// 行列の注目行をこの結果に置き換える。
				original.setRow(targetRow, resultVec);
				r.setRow(targetRow, resultVecUnit);
			} // end of for(int targetRow=baseRow+1
		} // end of for(int baseRow = 0
		double det = 1.0;
		for (int i = 0; i < original.rowN; i++) {
			det *= original.getValue(i, i);
		}
		det = Math.pow(-1.0, count) * det;
		return det;
	}// end of det()
		// 逆行列

	public CMatrix inverse() {
		CMatrix original = new CMatrix(this);
		CMatrix unit = new CMatrix(this.rowN, this.colN);
		for (int i = 0; i < original.rowN; i++) {
			int pivotRow = i;
			int maxpos = original.getCol(pivotRow).hwMaxPos(i);
			if (maxpos != pivotRow) {
				original.exchangeRow(pivotRow, maxpos);
				unit.exchangeRow(pivotRow, maxpos);
			} // end of if(...)
		} // end of for(...)
			// 以上で置換終わり。ここから上三角行列をつくる
		for (int baseRow = 0; baseRow < original.rowN - 1; baseRow++) {
			CVector_Row baseRowVec = original.getRow(baseRow);
			CVector_Row baseRowVecUnit = unit.getRow(baseRow);
			int targetCol = baseRow;
			double baseValue = baseRowVec.getValue(targetCol);
			for (int targetRow = baseRow + 1; targetRow < original.rowN; targetRow++) {
				// 処理する行を抜き出す
				CVector_Row targetRowVec = original.getRow(targetRow);
				CVector_Row targetRowVecUnit = unit.getRow(targetRow);
				double targetValue = targetRowVec.getValue(targetCol);
				double ratio = targetValue / baseValue;
				// base行にすべて ratio をかける
				CVector_Row tmpVec = baseRowVec.byScalar(ratio);
				CVector_Row tmpVecUnit = baseRowVecUnit.byScalar(ratio);
				CVector_Row resultVec = targetRowVec.subtractVec(tmpVec);
				CVector_Row resultVecUnit = targetRowVecUnit.subtractVec(tmpVecUnit);
				// 行列の注目行をこの結果に置き換える。
				original.setRow(targetRow, resultVec);
				unit.setRow(targetRow, resultVecUnit);
			} // end of for(int targetRow=baseRow+1
		} // end of for(int baseRow = 0
			// 以上で original 行列は上三角行列になった。
			// ガウス・ジョルダン法を上三角行列に適用
		for (int pivot = 0; pivot < original.rowN; pivot++) {
			// pivot 行、pivot列成分を1 にする。
			double invPivot = 1.0 / original.getValue(pivot, pivot);
			CVector_Row pivotVec = original.getRow(pivot);
			CVector_Row pivotVecUnit = unit.getRow(pivot);
			pivotVec.byScalarThis(invPivot);
			pivotVecUnit.byScalarThis(invPivot);
			original.setRow(pivot, pivotVec);
			unit.setRow(pivot, pivotVecUnit);
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
				} // end of if()
			} // end of for(int i=0...
		} // end of for( int pivot = 0...
		return unit;
	} // end of inverse()

	// この行列の第p列と第q列を入れ替える
	public void exchangeCol(int p, int q) {
		CVector_Col tmp = this.getCol(p);
		this.setCol(p, this.getCol(q));
		this.setCol(q, tmp);
	}

	// この行列の第 p 行と第 q 行を入れ替える
	public void exchangeRow(int p, int q) {
		CVector_Row tmp = this.getRow(p);
		this.setRow(p, this.getRow(q));
		this.setRow(q, tmp);
	}

	// getter
	public double[][] getMat() {
		return this.mat;
	}

	// 第(i,j)要素を返す
	public double getValue(int i, int j) {
		return this.mat[i][j];
	}

	// 第j列を列ベクトルとして返す
	public CVector_Col getCol(int j) {
		CVector_Col r = new CVector_Col(getColArray(j));
		return r;
	}

	// 第j列を double[] で返す
	public double[] getColArray(int j) {
		double[] r = new double[this.rowN];
		for (int i = 0; i < r.length; i++) {
			r[i] = this.mat[i][j];
		}
		return r;
	}

	// 第i行を行ベクトルとして返す
	public CVector_Row getRow(int i) {
		CVector_Row r = new CVector_Row(getRowArray(i));
		return r;
	}

	// 第i行を double[] で返す
	public double[] getRowArray(int i) {
		double[] r = new double[this.colN];
		for (int j = 0; j < r.length; j++) {
			r[j] = this.mat[i][j];
		}
		return r;
	}

	// 正方行列かどうか？
	public boolean isSquare() {
		return this.isSquare;
	}

	// setter
	// (i,j)要素に値をセットする
	public void setValue(int i, int j, double v) {
		this.mat[i][j] = v;
	}

	// 第i 行を CVector_Row で置き換える
	public void setRow(int i, CVector_Row in) {
		for (int j = 0; j < this.colN; j++) {
			this.mat[i][j] = in.getArray()[j];
		}
	}

	// 第i行を double[] で置き換える。
	public void setRow(int i, double[] in) {
		for (int j = 0; j < this.colN; j++) {
			this.mat[i][j] = in[j];
		}
	}

	// 第j列を CVector_Colで置き換える
	public void setCol(int j, CVector_Col in) {
		for (int i = 0; i < this.rowN; i++) {
			this.mat[i][j] = in.getArray()[i];
		}
	}

	// 第j列を double[] で置き換える
	public void setCol(int j, double[] in) {
		for (int i = 0; i < this.rowN; i++) {
			this.mat[i][j] = in[i];
		}
	}

}
