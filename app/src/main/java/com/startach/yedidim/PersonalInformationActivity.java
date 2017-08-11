package com.startach.yedidim;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

/**
 * Created by yuval on 26/07/2017.
 */

public class PersonalInformationActivity extends AppCompatActivity {
    // Finals
    final int DIALOG_ID = 3;

    // Data members
    EditText _fullName;
    EditText _activeNumber;
    EditText _idNumber;
    EditText _birthDate;
    EditText _profession;
    EditText _qualification;
    EditText _city;
    EditText _neighborhood;
    EditText _street;
    EditText _building;
    EditText _apartmentNumber;
    EditText _phoneNumber;
    EditText _mailAddress;
    EditText _carType;
    EditText _carCompany;
    EditText _carModel;
    EditText _carLicensePlate;
    EditText _availableEquipment;
    Button _saveInformation;
    EditText[] _fields;
    int _year, _month, _day;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        findFormFields();

        initializeFieldsList();

        readValueFromSharedPreferences();

        initializeFormFields();

        saveFieldsTextOnBlur();
    }

    private void findFormFields() {
        _fullName = (EditText) findViewById(R.id.fullNameInput);
        _activeNumber = (EditText) findViewById(R.id.activeNumberInput);
        _idNumber = (EditText) findViewById(R.id.IDInput);
        _birthDate = (EditText) findViewById(R.id.birthdateInput);
        _profession = (EditText) findViewById(R.id.professionInput);
        _qualification = (EditText) findViewById(R.id.qualificationInput);
        _city = (EditText) findViewById(R.id.cityInput);
        _neighborhood = (EditText) findViewById(R.id.neighborhoodInput);
        _street = (EditText) findViewById(R.id.streetInput);
        _building = (EditText) findViewById(R.id.buildingInput);
        _apartmentNumber = (EditText) findViewById(R.id.apartmentInput);
        _phoneNumber = (EditText) findViewById(R.id.phoneInput);
        _mailAddress = (EditText) findViewById(R.id.mailInput);
        _carType = (EditText) findViewById(R.id.carTypeInput);
        _carCompany = (EditText) findViewById(R.id.carCompanyInput);
        _carModel = (EditText) findViewById(R.id.carModelInput);
        _carLicensePlate = (EditText) findViewById(R.id.carLicensePlateInput);
        _availableEquipment = (EditText) findViewById(R.id.availableEquipmentInput);
        _saveInformation = (Button) findViewById(R.id.sendPersonalInformationData);
    }

    private void initializeFormFields() {
        initializeBirthdateField();
        initializeActiveNumberField();
        initializeQualification();
        initializeCarType();
        initializeAvailableEquipment();
        initializeSaveInformationButton();
    }

    private void initializeFieldsList() {
        _fields = new EditText[] {
                _fullName,
                _activeNumber,
                _idNumber,
                _birthDate,
                _profession,
                _qualification,
                _city,
                _neighborhood,
                _street,
                _building,
                _apartmentNumber,
                _phoneNumber,
                _mailAddress,
                _carType,
                _carCompany,
                _carModel,
                _carLicensePlate,
                _availableEquipment
        };
    }

    private void saveFieldsTextOnBlur() {
        for (final EditText field: _fields) {
            field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        // TODO: save the input text in the shared preferences file
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_personal_information_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(String.valueOf(field.getId()), field.getText().toString());
                        editor.commit();
                    }
                }
            });
        }
    }

    private void readValueFromSharedPreferences() {
        for (EditText field :_fields) {
            SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_file_personal_information_key), Context.MODE_PRIVATE);
            field.setText(prefs.getString(String.valueOf(field.getId()), ""));
        }
    }

    private void initializeSaveInformationButton() {
        final Intent mainPage = new Intent(getApplicationContext(), MainPageActivity.class);
        _saveInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFormValidation())
                {
                    saveCommittedFirstLogin();
                    Toast.makeText(getApplicationContext(), "הפרטים נשמרו בהצלחה", Toast.LENGTH_SHORT).show();
                    startActivity(mainPage);
                } else {
                    Toast.makeText(getApplicationContext(), "אחד או יותר מהפרטים שהזנת אינו תקין", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void saveCommittedFirstLogin() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(R.string.first_page_variable), String.valueOf(R.string.main_page_id));
        editor.commit();
    }

    private void initializeCarType() {
        final String[] carTypes = {"רכב", "הנעה כפולה", "קטנוע", "אופניים"};
        String title = "בחר סוג רכב";
        final AlertDialog carTypeDialog = createRadioAlertDialog(_carType, carTypes, title);

        _carType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {carTypeDialog.show();
            }
        });

    }

    private void initializeAvailableEquipment() {
        final String[] availableEquipments = {"כבלים", "בוסטר", "מפתח גלגלים - צלב", "מפתח גלגלים - ידית כוח",
                                              "מפתח גלגלים - חשמלי", "ג'ק עגלה", "קומפרסור", "ערכת פתיחת רכב",
                                              "ערכת תיקון פנצ'ר (תולעים)", "רצועות למשיכה ולחילוץ"};
        final ArrayList<String> selectedItems = new ArrayList<>();
        String title = "הכנס ציוד שברשותך";
        final AlertDialog availableEquipmentDialog = createSelectAlertDialog(_availableEquipment, availableEquipments, selectedItems, title);

        _availableEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {availableEquipmentDialog.show();
            }
        });
    }

    private void initializeQualification() {
        final String[] qualifications = {"חובש", "מחלץ"};
        final ArrayList<String> selectedItems = new ArrayList<>();
        String title = "הכנס הכשרה";

        final AlertDialog selectQualificationDialog = createSelectAlertDialog(_qualification, qualifications, selectedItems, title);

        _qualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {selectQualificationDialog.show();
            }
        });
    }

    private void initializeActiveNumberField() {
        // TODO: get active number from server
        _activeNumber.setText("999");
    }

    private void initializeBirthdateField() {
        setCurrentDate();
        _birthDate.setText(_day + "/" + (_month + 1) + "/" + _year);
        _birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    private void setCurrentDate() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        _year = calendar.get(java.util.Calendar.YEAR);
        _month = calendar.get(java.util.Calendar.MONTH);
        _day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                                                                     R.style.date_picker_theme,
                                                                     birthdatePickerDialog,
                                                                     _year,
                                                                     _month,
                                                                     _day);
            datePickerDialog.updateDate(_year, _month, _day);
            datePickerDialog.setTitle("הכנס תאריך לידה");
                datePickerDialog.setMessage("מתנדבים בעמותת ידידים צריכים להיות מעל גיל 14");
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            return datePickerDialog;
        } else {
            return null;
        }
    }

    DatePickerDialog.OnDateSetListener birthdatePickerDialog = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            _year = year;
            _month = month;
            _day = day;
            _birthDate.setText(day + "/" + (month + 1) + "/" + year);
        }
    };

    private AlertDialog createSelectAlertDialog(final EditText currentView,
                                                final String[] options,
                                                final ArrayList<String> selectedItems,
                                                String DialogTitle) {
        return new AlertDialog.Builder(this)
                .setTitle(DialogTitle)
                .setMultiChoiceItems(options, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            selectedItems.add(options[indexSelected]);
                        } else if (selectedItems.contains(options[indexSelected])) {
                            selectedItems.remove(options[indexSelected]);
                        }
                    }
                }).setPositiveButton("סיום", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String qualificationText = "";

                        for (String qualification: selectedItems)
                            qualificationText += qualification + ", ";

                        if (!qualificationText.equals(""))
                            currentView.setText(qualificationText.substring(0, qualificationText.length() - 2));
                    }
                }).create();
    }

    private AlertDialog createRadioAlertDialog(final EditText currentView,
                                                final String[] options,
                                                String DialogTitle) {
        return new AlertDialog.Builder(this)
                .setTitle(DialogTitle)
                .setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int clickedIndex) {
                        currentView.setText(options[clickedIndex]);
                    }
                }).setPositiveButton("סיום", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
    }

    private boolean checkFormValidation() {
        ArrayList<Boolean> validationResults = new ArrayList<Boolean>();
        validationResults.add(checkIdValidation());
        validationResults.add(checkLicensePlateValidation());
        validationResults.add(checkPhoneValidation());
        validationResults.add(checkMailValidation());
        validationResults.add(checkFullNameValidation());
//        checkBirthdateValidation();
//        checkProfessionValidation();
//        checkCityValidation();
//        checkActiveNumberValidation();
//        checkQualificationValidation();
//        checkNeighborhoodValidation();
//        checkStreetValidation();
//        checkBuildingValidation();
//        checkApartmentNumberValidation();
//        checkCarTypeValidation();
//        checkCarCompanyValidation();
//        checkCarModelValidation();
        for (Boolean result : validationResults) {
            if (result == false) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIdValidation() {
        Pattern idPattern = Pattern.compile("(\\d{9})");
        if (!idPattern.matcher(_idNumber.getText()).matches()) {
            _idNumber.setError("תעודת הזהות צריכה להכיל 9 ספרות");
            return false;
        }
        return true;
    }

    private boolean checkMailValidation() {
        Pattern mailPattern =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        if (!mailPattern.matcher(_mailAddress.getText()).matches()) {
            _mailAddress.setError("האימייל שהכנסת אינו תקין");
            return false;
        }
        return true;
    }

    private boolean checkPhoneValidation() {
        Pattern phonePattern = Pattern.compile("(05)(\\d{8})");
        if (!phonePattern.matcher(_phoneNumber.getText()).matches()) {
            _phoneNumber.setError("מספר הפלאפון שהכנסת אינו תקין");
            return false;
        }
        return true;
    }

    private boolean checkLicensePlateValidation() {
        Pattern idPattern = Pattern.compile("(\\d{7})");
        if (!idPattern.matcher(_carLicensePlate.getText()).matches()) {
            _carLicensePlate.setError("מספר הרישוי שהכנסת אינו תקין");
            return false;
        }
        return true;
    }

    private boolean checkFullNameValidation() {
        if(_fullName.getText().length() < 5) {
            _fullName.setError("אנא הכנס שם מלא");
            return false;
        }
        return true;
    }

    private boolean checkBirthdateValidation() {return true;}

    private boolean checkCarModelValidation() {
        return true;
        //_carModel.getText().length() > 0;
    }

    private boolean checkCarCompanyValidation() {
        return true;
        //_carCompany.getText().length() > 0;
    }

    private boolean checkCarTypeValidation() {
        return true;
        //_carType.getText().length() > 0;
    }

    private boolean checkBuildingValidation() {
        return true;
    }

    private boolean checkApartmentNumberValidation() {
        return true;
        //_apartmentNumber.getText().length() > 0;
    }

    private boolean checkStreetValidation() {
        return true;
        //_street.getText().length() > 0;
    }

    private boolean checkNeighborhoodValidation() {
        return true;
        //_neighborhood.getText().length() > 0;
    }

    private boolean checkCityValidation() {
        return true;
        //_city.getText().length() > 0;
    }

    private boolean checkQualificationValidation() {
        return true;
    }

    private boolean checkProfessionValidation() {
        return true;
        //_profession.getText().length() > 0;
    }

    private boolean checkActiveNumberValidation() {
        return true;
    }
}
