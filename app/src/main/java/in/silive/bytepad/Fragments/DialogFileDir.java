package in.silive.bytepad.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import in.silive.bytepad.Adapters.DirListAdapter;
import in.silive.bytepad.Config;

import in.silive.bytepad.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFileDir extends DialogFragment {
    //UI Elements
    Button btnSelect, btnCancel;
    EditText etAddress;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String currentDir;
    public static boolean flag_ext_storage_avail;
    public static boolean flag_ext_storage_readabl;
    ListView lvDir;
ArrayList<String> dirList = new ArrayList<>();
    DirListAdapter adapter;
    ImageView ivBack,ivNewDir;
    DialogFileDir.Listener listener;
    public DialogFileDir() {
        // Required empty public constructor
    }

    public void setListener(DialogFileDir.Listener listener){
        this.listener = listener;
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
        sharedPreferences = getActivity().getSharedPreferences(Config.KEY_BYTEPAD, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        btnSelect = (Button)view.findViewById(R.id.btnSelect);
        btnCancel = (Button)view.findViewById(R.id.btnCancel);
        etAddress = (EditText)view.findViewById(R.id.etAddress);
        ivNewDir = (ImageView)view.findViewById(R.id.ivNewDir);
        ivNewDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("New Folder");
                final EditText editText = new EditText(getActivity());
                builder.setView(editText);
                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newDir = currentDir+File.separator+editText.getText().toString();
                        try {
                            File file = new File(newDir);
                            file.mkdirs();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        setUpList(newDir);
                       dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        ivBack = (ImageView)view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(currentDir)){
                    String[] array = currentDir.split(File.separator);
                    currentDir = "";
                    for (int i=0;i<array.length-1;++i){
                        currentDir = currentDir + array[i];
                        if (i!=array.length-2)
                            currentDir += File.separator;
                    }
                    setUpList(currentDir);
                }
            }
        });
        lvDir = (ListView)view.findViewById(R.id.lvDirectories);
        lvDir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView)view.findViewById(R.id.tvDirName);
                currentDir = currentDir + File.separator + tv.getText().toString();
                setUpList(currentDir);
            }
        });
        setUpList(Environment.getExternalStorageDirectory().getAbsolutePath());
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(Config.KEY_DOWNLOAD_DIR,currentDir);
                editor.commit();
                if (listener!=null)
                    listener.onDirSelected(currentDir);
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prevAddress = sharedPreferences.getString(Config.KEY_DOWNLOAD_DIR,"");
                if (TextUtils.isEmpty(currentDir)){
                    File file = new File(Environment.getExternalStorageDirectory()
                            +File.separator+"bytepad");
                    file.mkdirs();
                    editor.putString(Config.KEY_DOWNLOAD_DIR,file.getAbsolutePath());
                 editor.commit();
                }
                dismiss();
            }
        });
               flag_ext_storage_avail = CheckExtStorageAvailable();
               flag_ext_storage_readabl = CheckExternalStorageReadable();

        return view;
    }
    private void setUpList(String addr) {
        File f = new File(addr);
        if (!f.exists())
            return;
        File[] files = f.listFiles();
        if (files == null)
            return;
        dirList.clear();
        currentDir = addr;
        etAddress.setText(currentDir);
        for (File inFile : files) {
            if (inFile.isDirectory()) {
                dirList.add(inFile.getName());
            }
        }
        adapter = new DirListAdapter(getActivity(),dirList);
        lvDir.setAdapter(adapter);
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

    public static interface Listener{
        public void onDirSelected(String addr);
    }


}
