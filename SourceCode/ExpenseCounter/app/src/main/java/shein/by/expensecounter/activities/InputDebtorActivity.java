package shein.by.expensecounter.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import shein.by.expensecounter.R;

public class InputDebtorActivity extends Activity implements View.OnClickListener {

    private EditText etName;
    private EditText etMoney;
    private Button btnOK;
    private Button btnAddContact;
    private String check;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_deptor);
        etName = (EditText) findViewById(R.id.et_arrears_name);
        etMoney = (EditText) findViewById(R.id.et_arrears_money);
        btnOK = (Button) findViewById(R.id.input_debtor_add);
        btnOK.setOnClickListener(this);

        btnAddContact = (Button) findViewById(R.id.input_debtor_add_contacts);
        btnAddContact.setOnClickListener(this);

        radioGroup = (RadioGroup) findViewById(R.id.input_debtor_rgroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.yours_debts:
                        check = "Вы должны";
                        break;
                    case R.id.debts:
                        check = "Вам должны";
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.input_debtor_add:
                try {
                    double money = Math.rint(100.0 * Double.parseDouble(etMoney.getText().toString())) / 100.0;
                    if(money < 0 || money > Math.pow(10,4)){
                        Toast.makeText(this, "Долг не может быть меньше нуля или настолько большим",
                                Toast.LENGTH_LONG).show();
                        etMoney.setText(null);
                    }
                    else if(etName.getText().toString().equals("")){
                        Toast.makeText(this, "Введите имя", Toast.LENGTH_LONG).show();
                    }
                    else{
                        intent.putExtra("name", etName.getText().toString());
                        intent.putExtra("money", money);
                        intent.putExtra("choise", check);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                } catch (Exception e) {
                    Toast.makeText(this, "Неправильно введено число",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.input_debtor_add_contacts:
                Intent contactIntent = new Intent(this, ContactActivity.class);
                startActivityForResult(contactIntent, 1);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null) return;
        String name = data.getStringExtra("contact");
        etName.setText(name);
    }
}
