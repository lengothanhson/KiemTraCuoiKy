package com.example.kiemtracuoiky.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kiemtracuoiky.R;
import com.example.kiemtracuoiky.models.CN;
import com.example.kiemtracuoiky.models.CNAdapter;
import com.example.kiemtracuoiky.models.CNDataQuery;
import com.example.kiemtracuoiky.models.TP;
import com.example.kiemtracuoiky.models.TPAdapter;
import com.example.kiemtracuoiky.models.TPDataQuery;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChiNhanhFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChiNhanhFragment extends Fragment implements CNAdapter.UserCallback {
    RecyclerView rvListCN;
    ArrayList<CN> lstUser;
    CNAdapter cnAdapter;
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

    public ChiNhanhFragment(int s) {
        // Required empty public constructor
        id = s;
    }
    public ChiNhanhFragment() {
        // Required empty public constructo
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChiNhanhFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChiNhanhFragment newInstance(String param1, String param2) {
        ChiNhanhFragment fragment = new ChiNhanhFragment();
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
        thisContext= context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chi_nhanh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvListCN = view.findViewById(R.id.rvListCN);
        fbAdd = view.findViewById(R.id.fbAddCN);
        fbAdd.setOnClickListener(view1 -> addUserDialog());

        lstUser = CNDataQuery.getAll(getContext(),id);
        cnAdapter = new CNAdapter(lstUser);
        cnAdapter.setCallback(this);

        rvListCN.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        rvListCN.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rvListCN.setHasFixedSize(true);
        rvListCN.setAdapter(cnAdapter);
        cnAdapter.notifyDataSetChanged();
    }

    void addUserDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Thêm mới");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_tp, null);
        alertDialog.setView(dialogView);

        EditText edName = (EditText) dialogView.findViewById(R.id.edName);

        alertDialog.setPositiveButton("Đồng ý", (dialog, which) -> {
            String name = edName.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Nhập dữ liệu không hợp lệ", Toast.LENGTH_LONG).show();
            } else {
                CN cn = new CN(0, name,id);

                long id = CNDataQuery.insert(getContext(), cn);
                if (id > 0) {
                    Toast.makeText(getContext(), "Thêm thành phố thành công", Toast.LENGTH_LONG).show();
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

    void updateUserDialog(CN cn) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Cập nhật");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_cn, null);
        alertDialog.setView(dialogView);
        EditText edName = (EditText) dialogView.findViewById(R.id.edNameCN);
        EditText edIDTPCN = (EditText) dialogView.findViewById(R.id.edIDTPCN);
        edName.setText(cn.getName());
        edIDTPCN.setText(String.valueOf(cn.getTpid()));

        alertDialog.setPositiveButton("Đồng ý", (dialog, which) -> {
            cn.setName(edName.getText().toString());
            cn.setTpid(id);
            if (cn.getName().isEmpty() || String.valueOf(cn.getTpid()).isEmpty()) {
                Toast.makeText(getContext(), "Nhập dữ liệu không hợp lệ", Toast.LENGTH_LONG).show();
            }

            if (cn.getName().isEmpty() == false && String.valueOf(cn.getTpid()).isEmpty() == false) {
                int id = CNDataQuery.update(thisContext,cn);
                if (id > 0) {
                    Toast.makeText(getContext(), "Cập nhật thành phố thành công", Toast.LENGTH_LONG).show();
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

    void resetData() {
        lstUser.clear();
        lstUser.addAll(CNDataQuery.getAll(getContext(),id));
        cnAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(CN cn, int position) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, new PhongBanFragment(cn.getId()));
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    @Override
    public void onItemDeleteClicked(CN cn, int position) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_confirm_delete);
        btnOK = dialog.findViewById(R.id.btnYes);
        btnCancel = dialog.findViewById(R.id.btnNo);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean rs = CNDataQuery.delete(getContext(), cn.getId());
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
    public void onItemEditClicked(CN cn, int position) {
        updateUserDialog(cn);
    }
}