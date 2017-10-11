package com.example.dkim5.gpa;

        import android.app.DialogFragment;
        import android.os.Bundle;
        import android.text.Html;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.widget.TextView;

public class DialogHelp extends DialogFragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstantState) {
        View view = inflater.inflate(R.layout.dialog_help, null);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        TextView explanationTV = (TextView) view.findViewById(R.id.explanation_text);
        String str =
                "<b>REMOVE</b>: remove checked grade items." +
                        "<br /><br /><b>CALCULATE</b>: calculate current GPA" +
                        "<br /><br /><b>+</b> :  add a new grade item." +
                        "<br /><br /><b>Credit</b>: class credit" +
                        "<br /><br /><b>Number</b>:Number of same grade and credit classes"+
                        "<br />Checked grade items are not included in the calculation.<br />";

        explanationTV.setText(Html.fromHtml(str));
        return view;
    }
}