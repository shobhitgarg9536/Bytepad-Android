package in.silive.bytepad.Fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import in.silive.bytepad.Adapters.DirListAdapter;
import in.silive.bytepad.Config;
import in.silive.bytepad.PrefManager;
import in.silive.bytepad.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFileDir extends DialogFragment {
    public static boolean flag_ext_storage_avail;
    public static boolean flag_ext_storage_readabl;
    //UI Elements
    Button btnSelect, btnCancel;
    EditText etAddress;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String currentDir;
    ListView lvDir;
    ArrayList<String> dirList = new ArrayList<>();
    DirListAdapter adapter;
    ImageView ivBack, ivNewDir;
    DialogFileDir.Listener listener;
    View view;
    PrefManager prefManager;

    public DialogFileDir() {
        // Required empty public constructor
    }

    public void setListener(DialogFileDir.Listener listener) {
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
        this.getDialog().getWindow().setBackgroundDrawableResource(R.color.colorPrimary);
        setCancelable(false);
        view = inflater.inflate(R.layout.fragment_dialog_file_dir, container, false);
        sharedPreferences = getActivity().getSharedPreferences(Config.KEY_BYTEPAD, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        btnSelect = (Button) view.findViewById(R.id.btnSelect);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        etAddress = (EditText) view.findViewById(R.id.etAddress);
        ivNewDir = (ImageView) view.findViewById(R.id.ivNewDir);
        etAddress.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            setUpList(etAddress.getText().toString());
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
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
                        String newDir = currentDir + File.separator + editText.getText().toString();
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
        ivBack = (ImageView) view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(currentDir)) {
                    String[] array = currentDir.split(File.separator);
                    currentDir = "";
                    for (int i = 0; i < array.length - 1; ++i) {
                        currentDir = currentDir + array[i];
                        if (i != array.length - 2)
                            currentDir += File.separator;
                    }
                    setUpList(currentDir);
                }
            }
        });
        lvDir = (ListView) view.findViewById(R.id.lvDirectories);
        lvDir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view.findViewById(R.id.tvDirName);
                currentDir = currentDir + File.separator + tv.getText().toString();
                setUpList(currentDir);
            }
        });
        setUpList(Environment.getExternalStorageDirectory().getAbsolutePath());
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(Config.KEY_DOWNLOAD_DIR, currentDir);
                editor.commit();
                if (listener != null)
                    listener.onDirSelected(currentDir);
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prevAddress = sharedPreferences.getString(Config.KEY_DOWNLOAD_DIR, "");
                if (TextUtils.isEmpty(currentDir)) {
                    File file = new File(Environment.getExternalStorageDirectory()
                            + File.separator + "in.silive.bo");
                    file.mkdirs();
                    prefManager.setDownloadPath(file.getAbsolutePath());
                        if (listener!=null)
                            listener.onDirSelected(file.getAbsolutePath());
                }

                dismiss();
            }
        });
              /* flag_ext_storage_avail = CheckExtStorageAvailable();
               flag_ext_storage_readabl = CheckExternalStorageReadable();*/

        return view;
    }

    private void setUpList(String addr) {
        File f = new File(addr);
        if (!f.exists()) {
            Snackbar.make(view, "Couldn't access the directory", Snackbar.LENGTH_SHORT).show();
            return;
        }
        File[] files = f.listFiles();
        if (files == null) {
            Snackbar.make(view, "Couldn't access the directory", Snackbar.LENGTH_SHORT).show();
            return;
        }
        dirList.clear();
        currentDir = addr;
        etAddress.setText(currentDir);
        for (File inFile : files) {
            if (inFile.isDirectory()) {
                dirList.add(inFile.getName());
            }
        }
        adapter = new DirListAdapter(getActivity(), dirList);
        lvDir.setAdapter(adapter);
    }


    public static interface Listener {
        public void onDirSelected(String addr);
    }


}
