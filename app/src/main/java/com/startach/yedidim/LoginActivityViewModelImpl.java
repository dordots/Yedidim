package com.startach.yedidim;

import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.regex.Pattern;

import io.reactivex.Observable;

/**
 * Created by yb34982 on 06/09/2017.
 */

class LoginActivityViewModelImpl implements LoginActivityViewModel {

    @Override
    public boolean validNumber(CharSequence phoneNumber) {
        Pattern phonePattern = Pattern.compile("(05)(\\d{8})");
        return phonePattern.matcher(phoneNumber).matches();
    }

    @Override
    public Observable<Boolean> getEditActionDoneObservable(EditText m_phoneField) {
        return RxTextView.editorActionEvents(m_phoneField)
                .filter(textViewEditorActionEvent -> textViewEditorActionEvent.actionId() == EditorInfo.IME_ACTION_DONE)
                .map(textViewEditorActionEvent -> textViewEditorActionEvent.view().getText().toString())
                .map(this::validNumber);
    }
}
