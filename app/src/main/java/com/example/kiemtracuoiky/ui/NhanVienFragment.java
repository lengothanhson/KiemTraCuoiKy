package com.example.kiemtracuoiky.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kiemtracuoiky.R;
import com.example.kiemtracuoiky.models.CNDataQuery;
import com.example.kiemtracuoiky.models.NV;
import com.example.kiemtracuoiky.models.NVAdapter;
import com.example.kiemtracuoiky.models.NVDataQuery;
import com.example.kiemtracuoiky.models.PB;
import com.example.kiemtracuoiky.models.PBAdapter;
import com.example.kiemtracuoiky.models.PBDataQuery;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NhanVienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NhanVienFragment extends Fragment implements NVAdapter.UserCallback{
    RecyclerView rvListNV;
    ArrayList<NV> lstUser;
    NVAdapter nvAdapter;
    FloatingActionButton fbAdd;
    Context thisContext;
    int id;
    Button btnOK,btnCancel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NhanVienFragment(int s) {
        // Required empty public constructor
        id = s;
    }

    public NhanVienFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NhanVienFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NhanVienFragment newInstance(String param1, String param2) {
        NhanVienFragment fragment = new NhanVienFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thisContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nhan_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvListNV = view.findViewById(R.id.rvListNV);
        fbAdd = view.findViewById(R.id.fbAddNV);
        fbAdd.setOnClickListener(view1 -> addUserDialog());

        lstUser = NVDataQuery.getAll(getContext(),id);
        nvAdapter = new NVAdapter(lstUser);
        nvAdapter.setCallback(this);

        rvListNV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        rvListNV.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rvListNV.setHasFixedSize(true);
        rvListNV.setAdapter(nvAdapter);
        nvAdapter.notifyDataSetChanged();
    }

    void addUserDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Thêm mới");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_nv, null);
        alertDialog.setView(dialogView);

        EditText edName = (EditText) dialogView.findViewById(R.id.edNameNV);
        EditText edSDT = (EditText) dialogView.findViewById(R.id.edSDTNV);
        EditText edMail = (EditText) dialogView.findViewById(R.id.edMailNV);

        alertDialog.setPositiveButton("Đồng ý", (dialog, which) -> {
            String name = edName.getText().toString();
            String sdt =  edSDT.getText().toString();
            String mail = edMail.getText().toString();
            if (!isValid(edMail.getText().toString())) {
                edMail.setError("Mail không hợp lệ");
                return;
            }



            if (name.isEmpty()  || sdt.length() < 10 || mail.isEmpty()) {
                Toast.makeText(getContext(), "Nhập dữ liệu không hợp lệ", Toast.LENGTH_LONG).show();
            } else {
                NV nv = new NV(0, id, name, sdt, mail);
                Log.d("Tesst", "addUserDialog: "+sdt);
                long id = NVDataQuery.insert(getContext(), nv);
                if (id > 0) {
                    Toast.makeText(getContext(), "Thêm nhân viên thành công", Toast.LENGTH_LONG).show();
                    resetData();
                    dialog.dismiss();
                }
            }
        });
        alertDialog.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.create();
        alertDialog.show();
    }

    void updateUserDialog(NV nv) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Cập nhật");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_nv, null);
        alertDialog.setView(dialogView);
        EditText edName = (EditText) dialogView.findViewById(R.id.edNameNVu);
        EditText edIDPBNV = (EditText) dialogView.findViewById(R.id.edIDPBNVu);
        EditText edSDT = (EditText) dialogView.findViewById(R.id.edSDTNVu);
        EditText edMail = (EditText) dialogView.findViewById(R.id.edMailNVu);

        edName.setText(nv.getName());
        edIDPBNV.setText(String.valueOf(nv.getPbid()));
        edSDT.setText(nv.getSdt());
        edMail.setText(nv.getMail());

        alertDialog.setPositiveButton("Đồng ý", (dialog, which) -> {
            nv.setName(edName.getText().toString());
            nv.setPbid(id);
            nv.setSdt(edSDT.getText().toString());
            if (!isValid(edMail.getText().toString())) {
                edMail.setError("Mail không hợp lệ");
                return;
            }
            if (nv.getName().isEmpty() || nv.getSdt().length() < 10 || String.valueOf(nv.getPbid()).isEmpty()) {
                Toast.makeText(getContext(), "Nhập dữ liệu không hợp lệ", Toast.LENGTH_LONG).show();
            }
            else {
                NVDataQuery.update(thisContext,nv);
                Toast.makeText(getContext(), "Cập nhật nhân viên thành công", Toast.LENGTH_LONG).show();
                resetData();
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.create();
        alertDialog.show();
    }
    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    void resetData() {
        lstUser.clear();
        lstUser.addAll(NVDataQuery.getAll(getContext(),id));
        nvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(NV nv, int position) {

    }

    @Override
    public void onItemDeleteClicked(NV nv, int position) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_confirm_delete);
        btnOK = dialog.findViewById(R.id.btnYes);
        btnCancel = dialog.findViewById(R.id.btnNo);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean rs = NVDataQuery.delete(getContext(), nv.getId());
                if (rs) {
                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
                    resetData();
                } else {
                    Toast.makeText(getContext(), "Xóa thất bại", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    @Override
    public void onItemEditClicked(NV nv, int position) {
        updateUserDialog(nv);
    }
}