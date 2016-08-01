package in.silive.bytepad.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import in.silive.bytepad.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFileDir extends DialogFragment {
    //UI Elements
    Button btn_ok;
    public static boolean flag_ext_storage_avail;
    public static boolean flag_ext_storage_readabl;


    public DialogFileDir() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().setTitle("Select folder");
        setCancelable(false);
        View view = inflater.inflate(R.layout.fragment_dialog_file_dir, container, false);
        btn_ok = (Button)view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               flag_ext_storage_avail = CheckExtStorageAvailable();
               flag_ext_storage_readabl = CheckExternalStorageReadable();
                File file = GetPaperStorageDir(getContext(),"Bytepad");
                dismiss();
            }
        });
        return view;
    }

    public boolean CheckExtStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Toast.makeText(getContext(), "External Storage Available", Toast.LENGTH_SHORT).show();
            Log.d("Bytepad", "External Storage Available");
            return true;
        }
        Toast.makeText(getContext(), "External Storage UnAvailable", Toast.LENGTH_SHORT).show();
        Log.d("Bytepad", "External Storage UnAvailable");
        return false;

    }

    public boolean CheckExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Toast.makeText(getContext(), "External Storage Readable", Toast.LENGTH_SHORT).show();
            Log.d("Bytepad", "External Storage Readable");
            return true;
        }
        Toast.makeText(getContext(), "External Storage not Readable", Toast.LENGTH_SHORT).show();
        Log.d("Bytepad", "External Storage not Readable");
        return false;
    }

    public File GetPaperStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS), albumName);
        if (!file.mkdirs()) {
            Log.d("Bytepad", "Directory not created");
        } else {
            Log.d("Bytepad", "Directory created");
        }
        return file;
    }


}
