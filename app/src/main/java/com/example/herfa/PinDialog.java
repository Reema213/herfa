package com.example.herfa;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class PinDialog {

    public interface OnAdminLoginListener {
        void onAdminLogin();
    }

    public static void show(final Context context, final OnAdminLoginListener listener) {
        final EditText pinInput = new EditText(context);
        pinInput.setHint(R.string.pin_hint);
        pinInput.setInputType(InputType.TYPE_CLASS_NUMBER
                | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.pin_title);
        builder.setView(pinInput);

        builder.setPositiveButton(R.string.pin_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pin = pinInput.getText().toString();
                if (AdminSession.login(context, pin)) {
                    Toast.makeText(context, R.string.pin_correct, Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        listener.onAdminLogin();
                    }
                } else {
                    Toast.makeText(context, R.string.pin_wrong, Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton(R.string.pin_cancel, null);
        builder.show();
    }
}

