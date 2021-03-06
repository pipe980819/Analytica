package com.example.andresavendano.analytica;

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


public class FragVandermonde extends Fragment {
    int num = 3;
    double puntos [][];
    double A [][];
    double b [];
    private TextView polinomio;
    private TableLayout tablePoints;

    private boolean isError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.frag_vandermonde, container, false);
        tablePoints = inflaterView.findViewById(R.id.tablePoints);
        polinomio = inflaterView.findViewById(R.id.polinomio);
        createTable(inflaterView);
        Button button = inflaterView.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isError = false;
                try {
                    puntos = getMatrixA();
                    vandermonde(puntos);
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
                for(int i = 0;i<num-1;i++){
                    TableRow row = (TableRow) tablePoints.getChildAt(i);
                    edit[i]=row;
                }
                tablePoints.removeAllViews();
                for(int j=0; j<num; j++){
                    if(j<num-1) {
                        TableRow row = edit[j];
                        tablePoints.addView(row);
                    } else {
                        TableRow row = new TableRow(getContext());
                        for(int i=0;i<2;i++){
                            EditText editText = new EditText(getContext());
                            editText.setHint("  0  ");
                            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
                            editText.setGravity(Gravity.CENTER_HORIZONTAL);
                            row.addView(editText);
                        }
                        tablePoints.addView(row);
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
                for(int i = 0;i<num+1;i++){
                    TableRow row = (TableRow) tablePoints.getChildAt(i);
                    edit[i]=row;
                }
                tablePoints.removeAllViews();
                for(int j =0;j<num;j++){
                    TableRow row = edit[j];
                    tablePoints.addView(row);
                }
            }
        });
        return inflaterView;
    }

    public void createTable(View inflaterView){
        TableLayout table = inflaterView.findViewById(R.id.tablePoints);
        for(int j =0; j<num; j++){
            TableRow row = new TableRow(getContext());
            row.setId(j);
            for(int i = 0;i<2;i++) {
                EditText editText = new EditText(getContext());
                editText.setId(i+j);
                editText.setHint("  0  ");
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                editText.setGravity(Gravity.CENTER_HORIZONTAL);
                row.addView(editText);
            }
            table.addView(row);
        }
    }

    public void vandermonde(double puntos[][]) {
        int n = puntos.length;
        A = new double[n][n];
        b = new double[n];
        for (int i = 0; i < n; i++) {
            b[i] = puntos[i][1];
            for (int j = 0; j < n; j++) {
                A[i][j] = Math.pow(puntos[i][0],(n-(j+1)));
            }
        }
        double[][] Ab = scallingMatrix(A, b, A.length);
        for (int i = 0; i < Ab.length; i++) {
            System.out.println();
            for (int j = 0; j < Ab[0].length; j++) {
                System.out.print(Ab[i][j] + " ");
            }
        }
        double[] x = regressiveSubstitution(Ab, Ab.length);
        polinomio.setText("P(x)=");
        for (int i = 0; i < n; i++) {
            if(x[i] == Double.NaN || x[i] == Double.NEGATIVE_INFINITY || x[i] == Double.POSITIVE_INFINITY) {
                polinomio.setText("");
                Toast toast = Toast.makeText(getContext(),"Mathematical error", Toast.LENGTH_LONG);
                View view = toast.getView();
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(Color.BLACK);
                text.setGravity(1);
                view.setBackgroundColor(Color.parseColor("#B3E5FE"));
                toast.show();
                break;
            } else {
                if (x[i] > 0) {
                    polinomio.append("+");
                }
                if (x.length - 1 - i == 0) {
                    polinomio.append(String.format("%.1f", x[i]));
                } else {
                    polinomio.append(String.format("%.1f", x[i]) + "x^" + (x.length - 1 - i));
                }
            }
        }
    }

    public double[][] getMatrixA(){
        int n = tablePoints.getChildCount();
        puntos = new double [n][n];
        for(int i=0; i<n; i++){
            TableRow row = (TableRow) tablePoints.getChildAt(i);
            for(int x = 0;x<row.getChildCount();++x){
                EditText f = (EditText) row.getChildAt(x);
                puntos[i][x] = Double.valueOf(f.getText().toString());
            }
        }
        return puntos;
    }

    public double[] regressiveSubstitution(double [][] Ab, int n){
        double x[] = new double[n];
        x[n-1] = Ab[n-1][n]/Ab[n-1][n-1];
        for(int i = n-1; i > 0; i--){
            double sum = 0;
            for(int p = i + 1; p < n + 1; p++){
                sum += Ab[i-1][p-1]*x[p-1];
            }
            x[i-1] = (Ab[i-1][n]-sum)/(Ab[i-1][i-1]);
        }
        return x;
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

    public double[][] scallingMatrix(double A[][], double b[], int n){
        double Ab[][] = augmentMatrix(A, b);
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

        for(int k = 0; k < n; k++){
            for(int i = k+1; i < n; i++){
                double m = (float)(Ab[i][k])/Ab[k][k];
                for(int j = 0; j < n+1; j++){
                    Ab[i][j] = Ab[i][j]-(m*Ab[k][j]);
                }
            }
        }
        return Ab;
    }
}
