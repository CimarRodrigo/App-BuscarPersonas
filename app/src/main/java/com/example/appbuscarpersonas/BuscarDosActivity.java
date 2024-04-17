package com.example.appbuscarpersonas;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class BuscarDosActivity extends AppCompatActivity {

    TextView txtNumeroClientes, txtProfesion, txtDepartamento, txtGeneral;
    EditText edtDepartamento, edtProfesion;

    ArrayList<ArrayList<String>> clientes = new ArrayList<>();
    ArrayList<ArrayList<String>> departamentos = new ArrayList<>();
    ArrayList<ArrayList<String>> profesiones = new ArrayList<>();

    ArrayList<ArrayList<String>> cuentas = new ArrayList<>();
    private double cantidad = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscardos);
        initConponents();
        verificarPermisos();
        cantidad = clientes.size() - 1;
    }


    public void initConponents(){
        txtNumeroClientes = findViewById(R.id.txtNumeroClientes);
        txtProfesion = findViewById(R.id.txtProfesion);
        txtDepartamento = findViewById(R.id.txtDepartamento);
        txtGeneral = findViewById(R.id.txtGeneral);
        edtDepartamento = findViewById(R.id.edtDepartamento);
        edtProfesion = findViewById(R.id.edtProfesion);
    }

    public void verificarPermisos(){
        accessFile();
    }
    // Método para acceder al archivo después de que se conceda el permiso
    private void accessFile() {
        String estado = Environment.getExternalStorageState();
        if (!estado.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "No se encuentra la tarjeta SD", Toast.LENGTH_SHORT).show();
            return;
        }



        File dir = Environment.getExternalStorageDirectory();
        //File dir = Environment.getRootDirectory();
        File p = new File(dir.getAbsolutePath() + File.separator + "clientes.csv");
        File p1 = new File(dir.getAbsolutePath() + File.separator + "departamentos.csv");
        File p2 = new File(dir.getAbsolutePath() + File.separator + "profesiones.csv");
        File p3 = new File(dir.getAbsolutePath() + File.separator + "cuentas.csv");

        try {
            BufferedReader lector = new BufferedReader(new FileReader(p));
            BufferedReader lector1 = new BufferedReader(new FileReader(p1));
            BufferedReader lector2 = new BufferedReader(new FileReader(p2));
            BufferedReader lector3 = new BufferedReader(new FileReader(p3));
            StringBuilder res = new StringBuilder();
            String linea;

            while ((linea = lector.readLine()) != null) {
                clientes.add(new ArrayList<>(List.of(linea.split(";"))));

            }

            while((linea = lector1.readLine()) != null){
                departamentos.add(new ArrayList<>(List.of(linea.split(";"))));
            }

            while((linea = lector2.readLine()) != null){
                profesiones.add(new ArrayList<>(List.of(linea.split(";"))));
            }

            while((linea = lector3.readLine()) != null){
                cuentas.add(new ArrayList<>(List.of(linea.split(";"))));
            }


            lector.close();
        } catch (IOException e) {
            Toast.makeText(this,  e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }




    public void buscar(View v){
        String profesion = "";
        String departamento = "";
        int nClientes = 0;
        profesion = edtProfesion.getText().toString();
        departamento = edtDepartamento.getText().toString();
        int cProfesion = 0;
        int cDepartamento = 0;
        double Pprofesiones = 0, Pdepartamentos = 0, Pgeneral = 0, contadorProf = 0, contadorDep = 0;

        System.out.println(profesion);
        System.out.println(departamento);

        for(int i = 1; i < profesiones.size(); i++){
            if(profesion.equals(profesiones.get(i).get(1))){
                cProfesion = Integer.parseInt(profesiones.get(i).get(0));
            }
        }

        for(int i = 1; i < departamentos.size(); i++){

            if(departamento.equals(departamentos.get(i).get(1))){
                cDepartamento = Integer.parseInt(departamentos.get(i).get(0));
            }
        }

        for(int i = 1; i < clientes.size(); i++){
            if(Integer.parseInt(clientes.get(i).get(2)) == cProfesion){
                contadorProf++;
            }
            if(Integer.parseInt(clientes.get(i).get(3)) == cDepartamento){
                contadorDep++;
            }
            System.out.println("TERCERO");
        }


        String cli = "", prof = "", dep = "", gen = "";
        if(!profesion.equals("") && !departamento.equals("")){
            for (int i = 1; i < clientes.size(); i++ ){
                if(Integer.parseInt(clientes.get(i).get(2)) == cProfesion && Integer.parseInt(clientes.get(i).get(3)) == cDepartamento){
                    nClientes++;
                }
            }

            Pgeneral = (double) (nClientes * 100) / cantidad;
            Pprofesiones = (nClientes * 100) / contadorProf;
            Pdepartamentos = (nClientes * 100) / contadorDep;
            cli = String.valueOf(nClientes);
            prof = String.format("%.2f", Pprofesiones) + "%";
            dep = String.format("%.2f", Pdepartamentos)  + "%";
            gen = String.format("%.2f", Pgeneral)  + "%";

        }
        else if(!profesion.equals("")){
            for (int i = 1; i < clientes.size(); i++ ){
                if(Integer.parseInt(clientes.get(i).get(2)) == cProfesion){
                    nClientes++;
                }
            }

            Pgeneral = (double) (nClientes * 100) / cantidad;
            Pprofesiones = Pgeneral;
            cli = String.valueOf(nClientes);
            prof = String.format("%.2f", Pprofesiones) + "%";
            dep = "-";
            gen = String.format("%.2f", Pgeneral)  + "%";
        }
        else{
            for (int i = 1; i < clientes.size(); i++ ){
                if(Integer.parseInt(clientes.get(i).get(3)) == cDepartamento){
                    nClientes++;
                }
            }

            Pgeneral = (double) (nClientes * 100) / cantidad;
            Pdepartamentos = Pgeneral;
            cli = String.valueOf(nClientes);
            prof = "-";
            dep = String.format("%.2f", Pdepartamentos)  + "%";
            gen = String.format("%.2f", Pgeneral)  + "%";

        }

        txtNumeroClientes.setText(cli);
        txtProfesion.setText(prof);
        txtDepartamento.setText(dep);
        txtGeneral.setText(gen);


    }

}
