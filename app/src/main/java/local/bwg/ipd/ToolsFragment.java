package local.bwg.ipd;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Fragment: tools
 * @author ssvs
 */
public class ToolsFragment extends Fragment {

    EditText textedit_address_input;
    TextView textview_result;
    Button button_testinput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools, container, false);

        textedit_address_input = (EditText) view.findViewById(R.id.textedit_address_input);
        textview_result = (TextView) view.findViewById(R.id.textview_result);
        button_testinput = (Button) view.findViewById(R.id.button_testinput);

        button_testinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReachableTestTask rt = new ReachableTestTask();
                rt.execute();
            }
        });
        return view;
    }

    /**
     * Get reachable ip - task
     */
    @SuppressLint("StaticFieldLeak")
    private class ReachableTestTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            button_testinput.setEnabled(false);
            textedit_address_input.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(String... params) {

            String input = String.valueOf(textedit_address_input.getText());

            if (input.equals(""))
                return false;

            boolean isreachable = false;
            try {
                isreachable = InetAddress.getByName(input).isReachable(5000);
            } catch (UnknownHostException e) {
                try {
                    isreachable = InetAddress.getByAddress(input.getBytes()).isReachable(5000);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return isreachable;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            textview_result.setText(result.toString());
            textedit_address_input.setEnabled(true);
            button_testinput.setEnabled(true);
        }
    }
}