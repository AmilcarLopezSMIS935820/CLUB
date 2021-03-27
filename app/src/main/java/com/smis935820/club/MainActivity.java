package com.smis935820.club;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText codMiembro, numAfiliacion, nombre, telefono;
    Button insert, list, update;
    DatabaseHandler DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codMiembro = (EditText)findViewById(R.id.codMiembro);
        numAfiliacion = (EditText)findViewById(R.id.numAfiliacion);
        nombre = (EditText)findViewById(R.id.nombre);
        telefono = (EditText)findViewById(R.id.telefono);
        insert = findViewById(R.id.btnInsert);
        list = findViewById(R.id.btnViewData);
        update = findViewById(R.id.btnActualizar);
        DB = new DatabaseHandler(this);

        ///Evento de boton

        //Boton insertar
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idTXT=codMiembro.getText().toString().trim();
                String numATXT=numAfiliacion.getText().toString().trim();
                String nombreTXT=nombre.getText().toString().trim();
                String telefonoTXT=telefono.getText().toString().trim();

                if (validar()){

                    Boolean checkInsert=DB.insertData(idTXT, numATXT, nombreTXT, telefonoTXT);

                    //Evaluación de la data insertada
                    if (checkInsert==true){
                        Toast.makeText(MainActivity.this, "Se ha insertado un nuevo registro", Toast.LENGTH_SHORT).show();
                        codMiembro.setText("");
                        numAfiliacion.setText("");
                        nombre.setText("");
                        telefono.setText("");
                        codMiembro.requestFocus();
                    } else{
                        Toast.makeText(MainActivity.this, "No se ha podido insertado el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /// Mostrar los registros
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result= DB.getData();

                ///Evaluar registros
                if (result.getCount()==0){
                    Toast.makeText(MainActivity.this, "Aun no existen registros", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer= new StringBuffer();
                //Leer cursor y almacenar en StringBuffer
                while (result.moveToNext()){
                    buffer.append("Codigo: " + result.getString(0) + "\n");
                    buffer.append("Afiliación #: " + result.getString(1) + "\n");
                    buffer.append("Nombre: " + result.getString(2) + "\n");
                    buffer.append("Telefono: " + result.getString(3) + "\n");
                    buffer.append("--------------------------------------------------------------" + "\n\n");
                }

                //Mostrar los registros
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Listado de Miembros");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });

        //Boton actualizar

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idTXT=codMiembro.getText().toString().trim();
                String numATXT=numAfiliacion.getText().toString().trim();
                String nombreTXT=nombre.getText().toString().trim();
                String telefonoTXT=telefono.getText().toString().trim();

                if (validar()){
                    Boolean check=DB.updateData(idTXT, numATXT, nombreTXT, telefonoTXT);

                    if (check==true){
                        Toast.makeText(MainActivity.this, "Se ha actualizado el registro", Toast.LENGTH_SHORT).show();
                        codMiembro.setText("");
                        numAfiliacion.setText("");
                        nombre.setText("");
                        telefono.setText("");
                        codMiembro.requestFocus();
                    } else{
                        Toast.makeText(MainActivity.this, "No se ha podido actualizar el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public boolean validar(){
        boolean retorno= true;
            String c1 = codMiembro.getText().toString();
            String c2 = numAfiliacion.getText().toString();
            String c3 = nombre.getText().toString();
            String c4 = telefono.getText().toString();

            if(c1.isEmpty()){
                retorno = false;
                codMiembro.setError("Campo vacio");
            }

            if(c2.isEmpty()){
                retorno = false;
                numAfiliacion.setError("Campo vacio");
            }

            if(c3.isEmpty()){
                retorno = false;
                nombre.setError("Campo vacio");
            }

            if(c4.isEmpty()){
                retorno = false;
                telefono.setError("Campo vacio");
            }

        return retorno;
    }
}