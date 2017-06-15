package com.chm.chmframwork.fragment_full.provider;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chm.chmframwork.R;
import com.chm.chmframwork.base.BaseBackFragment;
import com.chm.framwork.utilcode.util.IntentUtils;
import com.chm.framwork.utilcode.util.StringUtils;
import com.chm.framwork.utilcode.util.ToastUtils;

/**
 * AddContactsFragment
 * Created by chm00 on 2017/7/5.
 */

public class AddContactsFragment extends BaseBackFragment implements View.OnClickListener {

    private EditText et_name;
    private EditText et_num;

    public static AddContactsFragment newInstance() {
        Bundle args = new Bundle();
        AddContactsFragment fragment = new AddContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_contacts, container, false);
        initToolbar(view);
        initView(view);
        return view;
    }

    private void initToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("添加联系人");
        initToolbarNav(toolbar);
    }

    private void initView(View view) {
        Button btn = (Button) view.findViewById(R.id.btn);
        btn.setText("添加");
        btn.setOnClickListener(this);

        et_name = (EditText) view.findViewById(R.id.et_name);
        et_name.setHint("姓名");
        et_num = (EditText) view.findViewById(R.id.et_num);
        et_num.setHint("号码");
    }

    @Override
    public void onClick(View v) {
        add();
    }

    public void add() {
        String name = et_name.getText().toString();
        String num = et_num.getText().toString();
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(num)) {
            Toast.makeText(_mActivity, "姓名和号码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", name);
                contentValues.put("number", num);
                _mActivity.getContentResolver().insert(Uri.parse("content://com.example.providerdemo/contact"), contentValues);
                _mActivity.setResult(Activity.RESULT_OK);
                _mActivity.onBackPressed();
            } catch (Exception e) {
                e.getMessage();
                try {
                    startActivity(IntentUtils.getLaunchAppIntent("com.example.providerdemo"));
                } catch (Exception x) {
                    ToastUtils.showLong("请先下载 Provider Demo");
                }
            }
        }
    }
}
