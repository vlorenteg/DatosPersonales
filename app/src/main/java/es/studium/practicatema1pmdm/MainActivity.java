package es.studium.practicatema1pmdm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNombre, etApellidos, etEdad;
    private RadioGroup rgGenero;
    private Spinner spEstadoCivil;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchHijos;
    private TextView tvMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //vistas con sus IDs
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etEdad = findViewById(R.id.etEdad);
        rgGenero = findViewById(R.id.rgGenero);
        spEstadoCivil = findViewById(R.id.spEstadoCivil);
        switchHijos = findViewById(R.id.switchHijos);
        tvMensaje = findViewById(R.id.tvMensaje);
        Button btnGenerarNotificacion = findViewById(R.id.btnGenerarNotificacion);
        Button btnReset = findViewById(R.id.btnreset);

        //array de strings
        String[] estadosCiviles = getResources().getStringArray(R.array.estados_civiles);

        //adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, estadosCiviles) {
            @Override
            public boolean isEnabled(int position) {
                //deshabilitar el primer item (Estado civil)
                return position != 0;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstadoCivil.setAdapter(adapter);

        //aceptar
        btnGenerarNotificacion.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                String apellidos = etApellidos.getText().toString().trim();
                String edadTexto = etEdad.getText().toString().trim();
                String estadoCivil = spEstadoCivil.getSelectedItem().toString();

                //validar que no faltan campos y que se selecciona un estado civil
                if (nombre.isEmpty() || apellidos.isEmpty() || edadTexto.isEmpty() || estadoCivil.equals(getString(R.string.estado_civil_titulo))) {
                    tvMensaje.setText(getString(R.string.mensaje_faltan_campos));
                    tvMensaje.setVisibility(View.VISIBLE);
                } else {
                    int edad = Integer.parseInt(edadTexto); //edad a número
                    String genero = rgGenero.getCheckedRadioButtonId() == R.id.rbHombre ? getString(R.string.hombre) : getString(R.string.mujer);
                    String tieneHijos = switchHijos.isChecked() ? getString(R.string.con_hijos) : getString(R.string.sin_hijos);

                    String edadCategoria = edad >= 18 ? getString(R.string.mayor_de_edad) : getString(R.string.menor_de_edad);

                    //mensaje
                    String mensaje = apellidos + ", " + nombre + ", " + edadCategoria + ", " + genero + ", " + estadoCivil + " y " + tieneHijos + ".";

                    //mensaje en un Toast
                    Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_LONG).show();
                }
            }
        });

        //reset
        btnReset.setOnClickListener(v -> {
            etNombre.setText("");
            etApellidos.setText("");
            etEdad.setText("");
            rgGenero.clearCheck();
            spEstadoCivil.setSelection(0);
            switchHijos.setChecked(false);
            tvMensaje.setVisibility(View.GONE);
        });
    }
}
