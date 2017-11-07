package com.chm.chmframwork.fragment.two.childpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chm.chmframwork.R;
import com.chm.chmframwork.base.BaseFragment;
import com.chm.chmframwork.fragment.two.childpager.provider.ContactsFragment;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by chm00 on 2017/7/5.
 */
public class ProviderPagerFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private List<String> list;

    public static ProviderPagerFragment newInstance() {
        Bundle args = new Bundle();
        ProviderPagerFragment fragment = new ProviderPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_pager, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        list = new ArrayList<>();
        list.add("添加联系人");
    }

    private void initView(View view) {
        ListView listview = (ListView) view.findViewById(R.id.listview_provider_pager);
        listview.setAdapter(new ArrayAdapter<>(_mActivity, R.layout.item_provider_pager, list));
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                ((SupportFragment) getParentFragment()).start(ContactsFragment.newInstance());
                break;
        }
    }
}
