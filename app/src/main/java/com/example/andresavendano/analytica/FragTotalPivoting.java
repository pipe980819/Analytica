package com.example.andresavendano.analytica;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.net.SocketPermission;
import java.util.ArrayList;

public class FragTotalPivoting extends Fragment {
    int num = 3;
    double A [][];
    double b [];
    int[] marks;
    private TableLayout table;
    private TableLayout vectorBB;
    private TableLayout vectorX;
    private TableLayout matrixAb;
    private TextView ab;
    private ArrayList<Double[][]> stepsMatrix = new ArrayList<Double[][]>();;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.frag_total_pivoting, container, false);
        table = inflaterView.findViewById(R.id.tableGauss);
        vectorBB = inflaterView.findViewById(R.id.vectorB);
        vectorX = inflaterView.findViewById(R.id.vectorX);
        matrixAb = inflaterView.findViewById(R.id.matrixAb);
        ab = inflaterView.findViewById(R.id.Ab);
        createTable(inflaterView);
        Button button = inflaterView.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    A = getMatrixA();
                    b = getVectorB();
                    gaussianElimination(A, b);
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
        final Button steps = inflaterView.findViewById(R.id.butEtapas);
        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(v.getContext(), StepsActivity.class);
                    intent.putExtra("stepsMatrix", stepsMatrix);
                    startActivityForResult(intent, 0);
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
                        editTextB.setGravity(Gravity.CENTER_HORIZONTAL);
                        editTextX.setHint("  X"+num+"  ");
                        editTextX.setGravity(Gravity.CENTER_HORIZONTAL);
                        //editTextB.setWidth(170);
                        rowB.addView(editTextB);
                        rowX.addView(editTextX);
                        for(int i=0;i<num;i++){
                            EditText editText = new EditText(getContext());
                            //editText.setText("0");
                            editText.setHint("  0  ");
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

    public void gaussianElimination(double [][] A, double [] b) {
        int n = A.length;
        marks = new int[n];
        for(int i = 0; i < this.marks.length; i++) {
            marks[i] = i+1;
        }
        double mult;
        Double Ab[][] = augmentMatrix(A, b);
        for(int k = 0; k < n-1; k++) {
            Ab = totalPivoting(Ab, n, k);
            for(int i = k+1; i < n; i++) {
                mult = Ab[i][k]/Ab[k][k];
                for(int j = k; j < n+1; j++) {
                    Ab[i][j] -=  mult*Ab[k][j];
                }
                stepsMatrix.add(Ab);
                System.out.print(stepsMatrix.size());
            }
            System.out.println("\nStep " + Integer.toString(k+1));
            print(Ab);
        }

        matrixAb.removeAllViews();
        // Escribe Ab es matrixAb
        for (int v = 0; v < Ab.length; v++) {
            TableRow row = new TableRow(getContext());
            row.setId(v);
            for (int m = 0; m < Ab[v].length; m++) {
                System.out.print (Ab[v][m]);
                EditText ed = new EditText(getContext());
                ed.setEnabled(false);
                ed.setTextColor(getResources().getColor(R.color.colorAccent));
                ed.setText(String.format("%.3f", Ab[v][m])+"");
                row.addView(ed);
                ab.setText("Ab");
                ab.setTextSize(30);
                //ab.setTextColor(getResources().getColor(R.color.colorAccent));
            }
            matrixAb.addView(row);
        }

        System.out.println("-----------------------");
        for (int i = 0; i < this.marks.length; i++) {
            System.out.print(marks[i] + " ");
        }
        System.out.println();
        double x[] = regressiveSubstitution(Ab, Ab.length);

        // Escribe en el vector X Regressive Substitution
        for(int i = 0; i < x.length; i++) {
            TableRow row = (TableRow) vectorX.getChildAt(i);
            EditText ed = (EditText) row.getChildAt(0);
            ed.setEnabled(false);
            ed.setTextColor(getResources().getColor(R.color.colorAccent));
            ed.setText(String.format("%.3f", x[i])+"");
        }
    }

    public Double[][] totalPivoting(Double Ab [][], int n, int k) {
        Double maxi = 0.0;
        int maxiRow = k;
        int maxiCol = k;
        for(int r = k; r < n; r++) {
            for(int s = k; s < n; s++) {
                if (Math.abs(Ab[r][s]) > maxi) {
                    maxi = Math.abs(Ab[r][s]);
                    maxiRow = r;
                    maxiCol = s;
                }
            }
        }
        if (maxi == 0) {
            System.out.println("The system has no solution");
        } else {
            if(maxiRow != k) {
                Ab = exchangeRows(Ab, maxiRow, k);
                return Ab;
            }
            if(maxiCol != k) {
                Ab = exchangeCols(Ab, maxiCol, k);
                exchangeMarks(maxiCol, k);
            }
        }
        return Ab;
    }

    public Double[][] exchangeRows(Double [][] Ab, int maxiRow, int k) {
        Double aux;
        for(int i = 0; i < Ab[0].length; i++) {
            aux = Ab[k][i];
            Ab[k][i] = Ab[maxiRow][i];
            Ab[maxiRow][i] = aux;
        }
        return Ab;
    }

    public Double[][] exchangeCols(Double [][] Ab, int maxiCol, int k) {
        Double aux;
        for(int i = 0; i < Ab.length; i++) {
            aux = Ab[i][k];
            Ab[i][k] = Ab[i][maxiCol];
            Ab[i][maxiCol] = aux;
        }
        return Ab;
    }

    public void exchangeMarks(int maxiCol, int k) {
        int aux = this.marks[k];
        this.marks[k] = this.marks[maxiCol];
        this.marks[maxiCol] = aux;
    }

    public void print(Double[][] Ab) {
        System.out.println("\n Ab");
        for (int v = 0; v < Ab.length; v++) {
            for (int m = 0; m < Ab[v].length; m++) {
                System.out.print (Ab[v][m]);
                if (m != Ab[v].length-1) System.out.print("   ");
            }
            System.out.println();
        }
    }

    public Double[][] augmentMatrix(double A[][], double b[]){
        Double result[][] = new Double[A.length][A.length+1];
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

    public double[] regressiveSubstitution(Double Ab[][], int n){
        double x[] = new double[n];
        x[n-1] = Ab[n-1][n]/(double)Ab[n-1][n-1];
        for(int i = n-1; i > 0; i--){
            Double sum = 0.0;
            for(int p = i + 1; p < n + 1; p++){
                sum += Ab[i-1][p-1]*x[p-1];
            }
            x[i-1] = (Ab[i-1][n]-sum)/(double)(Ab[i-1][i-1]);
        }
        return x;
    }
}
