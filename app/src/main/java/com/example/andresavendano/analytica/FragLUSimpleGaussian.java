package com.example.andresavendano.analytica;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class FragLUSimpleGaussian extends Fragment {

    int num = 3;
    double A [][];
    double b [];
    private TableLayout table;
    private TableLayout vectorBB;
    private TableLayout vectorX;
    private TableLayout matrixL;
    private TableLayout matrixU;
    private TextView txtL;
    private TextView txtU;
    private TextView t;

    private boolean isError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.frag_lusimple_gaussian, container, false);
        final View helpView = inflater.inflate(R.layout.help_bisection,  (ViewGroup) getView(), false);
        table = inflaterView.findViewById(R.id.tableGauss);
        vectorBB = inflaterView.findViewById(R.id.vectorB);
        vectorX = inflaterView.findViewById(R.id.vectorX);
        matrixL = inflaterView.findViewById(R.id.matrixL);
        matrixU = inflaterView.findViewById(R.id.matrixU);
        txtL = inflaterView.findViewById(R.id.L);
        txtU = inflaterView.findViewById(R.id.U);
        createTable(inflaterView);
        Button button = inflaterView.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isError = false;
                try {
                    A = getMatrixA();
                    b = getVectorB();
                    luWithSimpleGaussianElimination(A, b,helpView);
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getContext(),"Complete the fields and verify that the fields are well written, see helps", Toast.LENGTH_LONG);
                    View view = toast.getView();
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    text.setTextColor(Color.BLACK);
                    text.setGravity(1);
                    view.setBackgroundColor(Color.parseColor("#B3E5FE"));
                    toast.show();
                }
            }
        });
        Button button3 = inflaterView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                TableRow edit[]=new TableRow[num];
                TableRow edit2[]=new TableRow[num];
                TableRow edit3[]=new TableRow[num];
                for(int i = 0;i<num-1;i++){
                    TableRow row = (TableRow) table.getChildAt(i);
                    TableRow rowB = (TableRow) vectorBB.getChildAt(i);
                    TableRow rowX = (TableRow) vectorX.getChildAt(i);
                    edit[i]=row;
                    edit2[i]=rowB;
                    edit3[i]=rowX;
                }
                table.removeAllViews();
                vectorBB.removeAllViews();
                vectorX.removeAllViews();
                for(int j=0; j<num; j++){
                    if(j<num-1) {
                        TableRow row = edit[j];
                        TableRow rowB = edit2[j];
                        TableRow rowX = edit3[j];
                        EditText editText = new EditText(getContext());
                        //editText.setText("0");
                        editText.setHint("  0  ");
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                        editText.setGravity(Gravity.CENTER_HORIZONTAL);
                        //editText.setWidth(170);
                        row.addView(editText);
                        table.addView(row);
                        vectorBB.addView(rowB);
                        vectorX.addView(rowX);
                    } else {
                        TableRow row = new TableRow(getContext());
                        TableRow rowB = new TableRow(getContext());
                        TableRow rowX = new TableRow(getContext());
                        EditText editTextB = new EditText(getContext());
                        EditText editTextX = new EditText(getContext());
                        //editTextB.setText("0");
                        editTextB.setHint("  0  ");
                        editTextB.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
                        editTextB.setGravity(Gravity.CENTER_HORIZONTAL);
                        editTextX.setHint("  X"+num+"  ");
                        editTextX.setEnabled(false);
                        editTextX.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
                        editTextX.setGravity(Gravity.CENTER_HORIZONTAL);
                        //editTextB.setWidth(170);
                        rowB.addView(editTextB);
                        rowX.addView(editTextX);
                        for(int i=0;i<num;i++){
                            EditText editText = new EditText(getContext());
                            //editText.setText("0");
                            editText.setHint("  0  ");
                            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
                            editText.setGravity(Gravity.CENTER_HORIZONTAL);
                            //editText.setWidth(170);
                            row.addView(editText);
                        }
                        vectorBB.addView(rowB);
                        vectorX.addView(rowX);
                        table.addView(row);
                    }
                }
            }
        });
        Button button4 = inflaterView.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num--;
                TableRow edit[] = new TableRow[num+1];
                TableRow editB[] = new TableRow[num+1];
                TableRow editX[] = new TableRow[num+1];
                for(int i = 0;i<num+1;i++){
                    TableRow row = (TableRow) table.getChildAt(i);
                    TableRow rowB = (TableRow) vectorBB.getChildAt(i);
                    TableRow rowX = (TableRow) vectorX.getChildAt(i);
                    edit[i]=row;
                    editB[i]=rowB;
                    editX[i]=rowX;
                }
                table.removeAllViews();
                vectorBB.removeAllViews();
                vectorX.removeAllViews();
                for(int j =0;j<num;j++){
                    TableRow row = edit[j];
                    TableRow rowB = editB[j];
                    TableRow rowX = editX[j];
                    row.removeViewAt(num);
                    vectorBB.addView(rowB);
                    vectorX.addView(rowX);
                    table.addView(row);
                }
            }
        });
        final Button help = inflaterView.findViewById(R.id.butHelp);
        t = (TextView) helpView.findViewById(R.id.helpText);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                if (helpView.getParent() != null)
                    ((ViewGroup) helpView.getParent()).removeView(helpView);
                builder.setView(helpView);
                t.setText("The method of gaussian elimination is used to solve systems of linear " +
                        "equations. This method has to steps. First it converts the original matrix " +
                        "to another equivalent trought a serie of transformations, this matrix is" +
                        " called the scalonated matrix,  this matrix is also an lower triangular " +
                        "matrix. And the final step is to replace variables from the last row to " +
                        "the first one.\n" +
                        "If the determinant of the matrix is 0, then it is not invertible and does not have solution.");
                t.setTextSize(25);
                builder.show();
            }
        });
        return inflaterView;
    }
    public void createTable(View inflaterView){
        TableLayout table = inflaterView.findViewById(R.id.tableGauss);
        for(int j =0; j<num; j++){
            TableRow row = new TableRow(getContext());
            row.setId(j);
            for(int i = 0;i<num;i++) {
                EditText editText = new EditText(getContext());
                editText.setId(i+j);
                //editText.setText("0");
                editText.setHint("  0  ");
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
                editText.setGravity(Gravity.CENTER_HORIZONTAL);
                //editText.setWidth(170);
                row.addView(editText);
            }
            table.addView(row);
        }

        TableLayout vectorB = inflaterView.findViewById(R.id.vectorB);
        for(int j=0; j<num; j++){
            TableRow row = new TableRow(getContext());
            row.setId(j);
            EditText editText = new EditText(getContext());
            editText.setId(j);
            //editText.setText("0");
            editText.setHint("  0  ");
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setGravity(Gravity.CENTER_HORIZONTAL);
            //editText.setWidth(170);
            row.addView(editText);
            vectorB.addView(row);
        }

        TableLayout vectorX = inflaterView.findViewById(R.id.vectorX);
        int count = 1;
        for(int j=0; j<num; j++){
            TableRow row = new TableRow(getContext());
            row.setId(j);
            EditText editText = new EditText(getContext());
            editText.setId(j);
            //editText.setText("0");
            editText.setHint("  X"+count+"  ");
            editText.setEnabled(false);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setGravity(Gravity.CENTER_HORIZONTAL);
            //editText.setWidth(170);
            row.addView(editText);
            vectorX.addView(row);
            count++;
        }
    }

    public double[][] getMatrixA(){
        int n = table.getChildCount();
        A = new double [n][n];
        for(int i=0; i<n; i++){
            TableRow row = (TableRow) table.getChildAt(i);
            for(int x = 0;x<row.getChildCount();++x){
                EditText f = (EditText) row.getChildAt(x);
                A[i][x] = Double.valueOf(f.getText().toString());
            }
        }
        return A;
    }

    public double[] getVectorB(){
        int n = vectorBB.getChildCount();
        b = new double [n];
        for(int i=0; i<n; i++) {
            TableRow row = (TableRow) vectorBB.getChildAt(i);
            EditText f = (EditText) row.getChildAt(0);
            b[i] = Double.valueOf(f.getText().toString());
        }
        return b;
    }

    public void luWithSimpleGaussianElimination(double [][] A, double [] b,View helpView) {
        int n = A.length;
        double det = det(A);
        if(det == 0) {
            isError = true;
            Toast toast = Toast.makeText(getContext(),"The matrix you entered can not be invertible", Toast.LENGTH_LONG);
            View view = toast.getView();
            TextView text = (TextView) view.findViewById(android.R.id.message);
            text.setTextColor(Color.BLACK);
            text.setGravity(1);
            view.setBackgroundColor(Color.parseColor("#B3E5FE"));
            toast.show();
        }
        for (int i = 0; i < A.length; i++) {
            if(A[i][i] == 0){
                double a[] = new double[A[0].length];
                System.arraycopy(A[i], 0, a, 0, A[0].length); // a = Ab[i]
                for (int j = 1; j < A.length; j++) {
                    if(A[j][i] != 0){
                        System.arraycopy(A[j], 0, A[i], 0, A[0].length); //Ab[i] = Ab[j]
                        System.arraycopy(a, 0, A[j], 0, A[0].length); //Ab[j] = a
                        break;
                    }

                }
            }
        }
        double [][] L = new double[n][n];
        for (int x=0; x < L.length; x++) {
            for (int y=0; y < L[x].length; y++) {
                if (x == y) {
                    L[x][y] = 1;
                }
            }
        }
        for(int k = 0; k < n-1; k++){
            for(int i = k+1; i < n; i++){
                double m = (float)(A[i][k])/A[k][k];
                if(!isError) {
                    if (A[k][k] == 0) {
                        isError = true;
                        Toast toast = Toast.makeText(getContext(), "Division by 0, check the matrix", Toast.LENGTH_LONG);
                        View view = toast.getView();
                        TextView text = (TextView) view.findViewById(android.R.id.message);
                        text.setTextColor(Color.BLACK);
                        text.setGravity(1);
                        view.setBackgroundColor(Color.parseColor("#B3E5FE"));
                        toast.show();
                    }
                }
                L[i][k] = m;
                for(int j = 0; j < n; j++){
                    A[i][j] = A[i][j]-(m*A[k][j]);
                }
            }
        }
        double [][] U = A;

        matrixL.removeAllViews();
        // Escribe L en matrixL
        for (int v = 0; v < L.length; v++) {
            TableRow row = new TableRow(getContext());
            row.setId(v);
            for (int m = 0; m < L[v].length; m++) {
                System.out.print (L[v][m]);
                EditText ed = new EditText(getContext());
                ed.setEnabled(false);
                ed.setTextColor(getResources().getColor(R.color.colorAccent));
                ed.setText(String.format("%.3f", L[v][m])+"");
                row.addView(ed);
                txtL.setText("L");
                txtL.setTextSize(30);
                //ab.setTextColor(getResources().getColor(R.color.colorAccent));
            }
            if(!isError) {
                matrixL.addView(row);
            } else {
                matrixL.removeView(row);
            }
        }

        matrixU.removeAllViews();
        // Escribe U en matrixU
        for (int v = 0; v < U.length; v++) {
            TableRow row = new TableRow(getContext());
            row.setId(v);
            for (int m = 0; m < U[v].length; m++) {
                System.out.print (U[v][m]);
                EditText ed = new EditText(getContext());
                ed.setEnabled(false);
                ed.setTextColor(getResources().getColor(R.color.colorAccent));
                ed.setText(String.format("%.3f", U[v][m])+"");
                row.addView(ed);
                txtU.setText("U");
                txtU.setTextSize(30);
                //ab.setTextColor(getResources().getColor(R.color.colorAccent));
            }
            if(!isError) {
                matrixU.addView(row);
            } else {
                matrixU.removeView(row);
            }
        }
        double [][] Lb = augmentMatrix(L,b);
        double [] z = progressiveSubstitution(L, b, Lb.length);
        double [][] Uz = augmentMatrix(U, z);
        double [] x = regressiveSubstitution(Uz, Uz.length);
        // Escribe en el vector X Regressive Substitution
        for(int i = 0; i < x.length; i++) {
            TableRow row = (TableRow) vectorX.getChildAt(i);
            EditText ed = (EditText) row.getChildAt(0);
            ed.setEnabled(false);
            ed.setTextColor(getResources().getColor(R.color.colorAccent));
            if (isError) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                if (helpView.getParent() != null)
                    ((ViewGroup) helpView.getParent()).removeView(helpView);
                builder.setView(helpView);
                double[][] matrix = diagonalDomiante(A);
                String text="The matrix is not diagonally dominant, try with this one.\n";
                for(int j = 0;j<matrix.length;j++){
                    for(int k =0;k<matrix.length;k++){
                        text=text+matrix[j][k] + "  ";
                    }
                    text=text+"\n";
                }
                t.setText(text);

                t.setTextSize(25);
                builder.show();
                break;
            } else {
                ed.setText(String.format("%.3f", x[i]) + "");
            }
        }
    }
    public double[][] diagonalDomiante(double m[][]){
        for(int i = 0; i < m.length;i++){
            double sum = getSum(i, m);
            int index = extractGreater(i, m);
            if(m[i][index] > (sum-m[i][index])){
                double aux = m[i][i];
                m[i][i] = m[i][index];
                m[i][index] = aux;
            }else{
                Toast toast = Toast.makeText(getContext(),"This matrix can't be convert to diagonally dominant", Toast.LENGTH_LONG);
                View view = toast.getView();
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(Color.BLACK);
                text.setGravity(1);
                view.setBackgroundColor(Color.parseColor("#B3E5FE"));
                toast.show();
            }
        }
        return m;
    }

    public int extractGreater(int i, double m[][]){
        double greater = 0;
        int index = 0;
        for(int j = 0; j < m[0].length; j++){
            if(m[i][j] > greater){
                greater = m[i][j];
                index = j;
            }
        }
        return index;
    }

    public double getSum(int i, double m[][]){
        double sum = 0;
        for(int j = 0; j < m[0].length; j++){
            sum+=Math.abs(m[i][j]);
        }
        return sum;
    }
    public static double det(double[][] A) {
        double det;
        if(A.length == 2) {
            det = (A[0][0]*A[1][1])-(A[1][0]*A[0][1]);
            return det;
        }
        double suma = 0;
        for(int i = 0; i < A.length; i++){
            double[][] nm = new double[A.length-1][A.length-1];
            for(int j = 0; j< A.length; j++){
                if(j != i){
                    for(int k = 1; k < A.length; k++){
                        int indice = -1;
                        if (j < i) {
                            indice = j;
                        } else if(j > i) {
                            indice = j - 1;
                        }
                        nm[indice][k-1]=A[j][k];
                    }
                }
            }
            if(i%2==0)
                suma+=A[i][0] * det(nm);
            else
                suma-=A[i][0] * det(nm);
        }
        System.out.println(suma);
        return suma;
    }

    public double[][] augmentMatrix(double A[][], double b[]){
        double result[][] = new double[A.length][A.length+1];
        for(int i = 0; i < result.length; i++){
            for(int j = 0; j < result[0].length; j++){
                if(j < A.length){
                    result[i][j] = A[i][j];
                }else{
                    result[i][j] = b[i];
                }
            }
        }
        return result;
    }

    public double[] regressiveSubstitution(double Ab[][], int n){
        double x[] = new double[n];
        x[n-1] = Ab[n-1][n]/(double)Ab[n-1][n-1];
        for(int i = n-1; i > 0; i--){
            double sum = 0;
            for(int p = i + 1; p < n + 1; p++){
                sum += Ab[i-1][p-1]*x[p-1];
            }
            x[i-1] = (Ab[i-1][n]-sum)/(double)(Ab[i-1][i-1]);
        }
        return x;
    }

    public double[] progressiveSubstitution(double L[][], double b[], int n) {
        double[] x = new double[n];
        for(int i = 0; i < n; i++) {
            double sum = 0;
            for(int p = 0; p < n; p++) {
                sum += (L[i][p] * x[p]);
            }
            x[i] = (b[i] - sum) / (double)(L[i][i]);
        }
        return x;
    }
}
