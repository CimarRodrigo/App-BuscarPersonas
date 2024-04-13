package com.example.appbuscarpersonas;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView txtNombre, txtProfesion, txtDepartamento, txtCuentas;
    EditText edtCarnet;

    ArrayList<ArrayList<String>> clientes = new ArrayList<>();
    ArrayList<ArrayList<String>> departamentos = new ArrayList<>();
    ArrayList<ArrayList<String>> profesiones = new ArrayList<>();

    ArrayList<ArrayList<String>> cuentas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initConponents();
        verificarPermisos();
    }

    public void initConponents(){
        txtNombre = findViewById(R.id.txtNombre);
        txtProfesion = findViewById(R.id.txtProfesion);
        txtDepartamento = findViewById(R.id.txtDepartamento);
        txtCuentas = findViewById(R.id.txtCuentas);
        edtCarnet = findViewById(R.id.edtCarnet);
    }




    public void buscarCarnet(View v){
        int carnet = Integer.parseInt(edtCarnet.getText().toString());
        String nombre = "", profesion = "", departamento = "", cuenta = "";


        for(int i = 1; i < clientes.size(); i++){
            if(Integer.parseInt(clientes.get(i).get(0)) == carnet){
                System.out.println("cliente");
                nombre = clientes.get(i).get(1);
                for(int j = 1; i < profesiones.size(); i++){
                    if(Integer.parseInt(clientes.get(i).get(2)) == Integer.parseInt(profesiones.get(j).get(0))){
                        profesion = profesiones.get(j).get(1);
                    }
                }

                for (int j = 1; j < departamentos.size(); j++){
                    if(Integer.parseInt(clientes.get(i).get(3)) == Integer.parseInt(departamentos.get(j).get(0))){
                        departamento = departamentos.get(j).get(1);
                    }
                }

            }
        }

        for (int i = 1; i < cuentas.size(); i++){
            if(Integer.parseInt(cuentas.get(i).get(1)) == carnet){
                cuenta += cuentas.get(i).get(0) + "\n";
            }
        }



        txtNombre.setText(nombre);
        txtProfesion.setText(profesion);
        txtDepartamento.setText(departamento);
        txtCuentas.setText(cuenta);

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

    /*
    public void boton(View v){

        int cont=0;
        int cont2=0;
        String h="";
        for(int i=0;i<cod.size();i++){
            if(cod.get(i).get(0).equals(x.getText().toString())) {
                h = h + cod.get(i).get(1) + " " + cod.get(i).get(2) + " " + cod.get(i).get(3) + " " + cod.get(i).get(4) + " " +  cod.get(i).get(5) + "\n";
                cont+=Integer.parseInt(cod.get(i).get(5));
                cont2+=1;
            }
        }
        if(cont2==0){
            Toast.makeText(this, "No se encontro el producto", Toast.LENGTH_SHORT).show();
        }else {
            Double tt = Double.valueOf((cont * 100.0) / Tot());
            DecimalFormat d = new DecimalFormat("#.#####");
            ex.setText("Existencia : " + cont);
            tx.setText(h);
            to.setText("Porcentaje : " + String.format("%.5f", tt) + "%");
            to2.setText("Total de productos : " + cont2);
        }
    }


    */

    // Manejar el resultado de la solicitud de permisos

}