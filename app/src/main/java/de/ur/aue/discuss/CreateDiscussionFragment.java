package de.ur.aue.discuss;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CreateDiscussionFragment extends DialogFragment {

    private EditText mTitle;
    private EditText mContent;
    private EditText regEdit;
    private EditText catEdit;

    // Regions
    private ToggleButton mLocal;
    private ToggleButton mGermany;
    private ToggleButton mEU;
    private ToggleButton mWorld;

    private ToggleButton[] regionButtons = new ToggleButton[4];//{mLocal, mGermany, mEU, mWorld};

    // Categories
    private ToggleButton mHealth;
    private ToggleButton mEconomy;
    private ToggleButton mCrime;
    private ToggleButton mEnvironment;
    private ToggleButton mWork;
    private ToggleButton mSocial;
    private ToggleButton mInfrastructure;

    private ToggleButton[] categoryButtons =  new ToggleButton[7];//= {mHealth, mEconomy, mCrime, mEnvironment, mWork, mSocial, mInfrastructure};

    private View createDialogView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        createDialogView = inflater.inflate(R.layout.create_discussion_dialog, (ViewGroup) getView(), false);

        mTitle = createDialogView.findViewById(R.id.createTitle);
        mContent = createDialogView.findViewById(R.id.createContent);
        regEdit = createDialogView.findViewById(R.id.createTextRegion);
        catEdit = createDialogView.findViewById(R.id.createTextCat);

        OnCheckedChangeListener CheckChangedListener = new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    for(ToggleButton rBtn : regionButtons)
                    {
                        if(rBtn.isChecked() && rBtn != buttonView)
                            rBtn.setChecked(false);
                    }
                }
            }
        };

        mLocal = createDialogView.findViewById(R.id.createLokal);
        mLocal.setOnCheckedChangeListener(CheckChangedListener);
        mGermany = createDialogView.findViewById(R.id.createDeutschland);
        mGermany.setOnCheckedChangeListener(CheckChangedListener);
        mEU = createDialogView.findViewById(R.id.createEU);
        mEU.setOnCheckedChangeListener(CheckChangedListener);
        mWorld = createDialogView.findViewById(R.id.createWelt);
        mWorld.setOnCheckedChangeListener(CheckChangedListener);
        regionButtons[0] = mLocal;
        regionButtons[1] = mGermany;
        regionButtons[2] = mEU;
        regionButtons[3] = mWorld;

        mHealth = createDialogView.findViewById(R.id.createCatGesundheit);
        mEconomy = createDialogView.findViewById(R.id.createCatWirtschaft);
        mCrime = createDialogView.findViewById(R.id.createCatVerbrechen);
        mEnvironment = createDialogView.findViewById(R.id.createCatUmwelt);
        mWork = createDialogView.findViewById(R.id.createCatArbeit);
        mSocial = createDialogView.findViewById(R.id.createCatGesellschaft);
        mInfrastructure = createDialogView.findViewById(R.id.createCatInfrastruktur);
        categoryButtons[0] = mHealth;
        categoryButtons[1] = mEconomy;
        categoryButtons[2] = mCrime;
        categoryButtons[3] = mEnvironment;
        categoryButtons[4] = mWork;
        categoryButtons[5] = mSocial;
        categoryButtons[6] = mInfrastructure;


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(createDialogView)
                .setTitle("Erstelle eine neue Diskussion")
                .setPositiveButton("Erstellen", null)
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if(attemptCreate())
                            dismiss();
                    }
                });
            }
        });
        // Create the AlertDialog object and return it
        return alertDialog;
    }

    private boolean attemptCreate() {

        // Reset errors.
        mTitle.setError(null);
        mContent.setError(null);
        regEdit.setError(null);
        catEdit.setError(null);

        // Store values at the time of the login attempt.
        String sTitle = mTitle.getText().toString();
        String sContent = mContent.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check if region is set
        boolean isRegionSet = false;
        for(ToggleButton rBtn : regionButtons)
        {
            if(rBtn.isChecked())
                isRegionSet = true;
        }
        if(!isRegionSet) {
            focusView = regEdit;
            regEdit.setError("Du musst eine Region angeben");
            cancel = true;
        }

        // Check if categories are set
        boolean isCategorySet = false;
        for(ToggleButton cBtn : categoryButtons)
        {
            if(cBtn.isChecked())
                isCategorySet = true;
        }
        if(!isCategorySet) {
            focusView = catEdit;
            catEdit.setError("Du musst mindestens eine Kategorie angeben");
            cancel = true;
        }

        // Check for a valid title.
        if (TextUtils.isEmpty(sTitle)) {
            mTitle.setError("Du musst einen Titel eingeben");
            focusView = mTitle;
            cancel = true;
        } else if (sTitle.length() < 3) {
            mTitle.setError("Der Titel muss länger al 3 Zeichen sein");
            focusView = mTitle;
            cancel = true;
        }

        // Check for a valid content.
        if (TextUtils.isEmpty(sContent)) {
            mContent.setError("Du musst einen Text eingeben");
            focusView = mContent;
            cancel = true;
        } else if (sContent.length() < 3) {
            mContent.setError("Der Text muss länger als 3 Zeichen sein");
            focusView = mContent;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            // TODO Success creating discussion
        }

        return !cancel;
    }
}
