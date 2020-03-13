package com.google.ar.sceneform.samples.augmentedimage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.google.ar.sceneform.samples.augmentedimage.R;
import com.google.ar.sceneform.samples.common.helpers.EditTextAlwaysLast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class PrecificacaoActivity extends AppCompatActivity {
    private static final char separator = ',';
    private static final Pattern regex = Pattern.compile("^(\\d{1,21}[" + separator + "]\\d{2}){1}$");
    //pattern for inserted text, like 005 in buffer inserted to 0,05 at position of first zero => 5,05 as a result
    private static final Pattern regexPaste = Pattern.compile("^([0]+\\d{1,20}[" + separator + "]\\d{2})$");
    private MyCustomEditTextListener etListenerCoca, etListenerPringles, etListenerStella;

    private EditTextAlwaysLast etPrecoCoca, etPrecoPringles, etPrecoStella;
    private Map<Integer, Double> mapPrecos = new HashMap<>();

    private Button btnFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precificacao);
        etPrecoCoca = findViewById(R.id.et_coca);

        etListenerCoca = new MyCustomEditTextListener();
        etListenerCoca.setEdit(etPrecoCoca);
        etPrecoCoca.addTextChangedListener(etListenerCoca);
        btnFinalizar = findViewById(R.id.btn_finalizar);


        etPrecoCoca.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                salvarPreco(1, etPrecoCoca.getText().toString());

            }
            return false;
        });

        etPrecoCoca.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    salvarPreco(1, etPrecoCoca.getText().toString());
            }
        });


        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrecificacaoActivity.this,AugmentedImageActivity.class);
                startActivity(i);
            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void salvarPreco(int produto, String text) {
        Double precoDouble = sGetDecimalStringAnyLocaleAsDouble(text);
        mapPrecos.put(produto, precoDouble);

    }


    public static class MyCustomEditTextListener implements TextWatcher {
        private EditTextAlwaysLast mEdit;

        public void setEdit(EditTextAlwaysLast editTextAlwaysLast) {
            this.mEdit = editTextAlwaysLast;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            //listaRecycler.get(position).setPreco(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

            if (!editable.toString().equals("0.0") && editable.length() > 0 && (!editable.toString().matches(regex.toString()) || editable.toString().matches(regexPaste.toString()))) {

                //Unformatted string without any not-decimal symbols
                String coins = editable.toString().replaceAll("[^\\d]", "");
                StringBuilder builder = new StringBuilder(coins);

                //Coleta: 0006
                while (builder.length() > 3 && builder.charAt(0) == '0')
                    //Result: 006
                    builder.deleteCharAt(0);
                //Coleta: 06
                while (builder.length() < 3)
                    //Result: 006
                    builder.insert(0, '0');
                //Final result: 0,06 or 0.06
                builder.insert(builder.length() - 2, separator);
                mEdit.setText(builder.toString());
            }
            mEdit.setSelection(mEdit.getText().length());
        }
    }

    public static double sGetDecimalStringAnyLocaleAsDouble(String value) {

        if (value == null) {
            Log.e("CORE", "Null value!");
            return 0.0;
        }

        //Locale theLocale = Locale.getDefault();
        final Locale theLocale = new Locale("pt", "BR");
        NumberFormat numberFormat = DecimalFormat.getInstance(theLocale);
        Number theNumber;
        try {
            theNumber = numberFormat.parse(value);
            return theNumber.doubleValue();
        } catch (ParseException e) {
            // The string value might be either 99.99 or 99,99, depending on Locale.
            // We can deal with this safely, by forcing to be a point for the decimal separator, and then using Double.valueOf ...
            //http://stackoverflow.com/questions/4323599/best-way-to-parsedouble-with-comma-as-decimal-separator
            String valueWithDot = value.replaceAll(",", ".");

            try {
                return Double.valueOf(valueWithDot);
            } catch (NumberFormatException e2) {
                // This happens if we're trying (say) to parse a string that isn't a number, as though it were a number!
                // If this happens, it should only be due to application logic problems.
                // In this case, the safest thing to do is return 0, having first fired-off a log warning.
                Log.w("CORE", "Warning: Value is not a number" + value);
                return 0.0;
            }
        }
    }


}
