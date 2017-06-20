package com.chm.chmframwork.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chm.chmframwork.R;
import com.chm.chmframwork.home.listener.OnItemClickListener;
import com.chm.framwork.utilcode.util.StringUtils;
import com.chm.framwork.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * GirlDetailFragment
 * {
 * "_id": "5714e6f667765974f885bf1f",
 * "createdAt": "2016-04-18T21:53:58.560Z",
 * "desc": "Android\u81ea\u5b9a\u4e49Notification\u5e76\u6ca1\u6709\u90a3\u4e48\u7b80\u5355",
 * "publishedAt": "2016-04-19T12:13:58.869Z",
 * "source": "web",
 * "type": "Android",
 * "url": "http://sixwolf.net/blog/2016/04/18/Android%E8%87%AA%E5%AE%9A%E4%B9%89Notification%E5%B9%B6%E6%B2%A1%E6%9C%89%E9%82%A3%E4%B9%88%E7%AE%80%E5%8D%95/",
 * "used": true,
 * "who": "Tang Likang"
 * },
 * {
 * "_id": "56d7897867765958a4af0f96",
 * "createdAt": "2016-03-03T08:46:48.452Z",
 * "desc": "\u5de5\u5177\u7c7b\u96c6\u5408",
 * "publishedAt": "2016-03-03T12:12:56.684Z",
 * "source": "chrome",
 * "type": "Android",
 * "url": "https://github.com/TheFinestArtist/AndroidBaseUtils",
 * "used": true,
 * "who": "MVP"
 * },
 * Created by chenmin on 2017/6/15.
 */
public class GirlDetailAdapter extends RecyclerView.Adapter<GirlDetailAdapter.VH> {
    private List<Map<String, Object>> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;

    public GirlDetailAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        View view = mInflater.inflate(R.layout.item_girl_detail, parent, false);
        final VH holder = new VH(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, holder);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Map<String, Object> item = mItems.get(position);
        // 2017-06-15T13:55:57.947Z
        String createdAt = (String) item.get("createdAt");
        String desc = (String) item.get("desc");
        String url = (String) item.get("url");
        if (!StringUtils.isEmpty(createdAt)) {
            // yyyy-MM-dd HH:mm:ss
            Date date = TimeUtils.string2Date(createdAt.split("T")[0] + " " + createdAt.split("T")[1]);
            holder.tv_time.setText(TimeUtils.date2String(date));
        }
        if (!StringUtils.isEmpty(desc)) {
            holder.tv_desc.setText(desc);
        }
        if (!StringUtils.isEmpty(url)) {
            holder.tv_url.setText(url);
        }
    }

    public void setDatas(List<Map<String, Object>> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Map<String, Object> getItem(int position) {
        return mItems.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    class VH extends RecyclerView.ViewHolder {
        TextView tv_time;
        TextView tv_desc;
        TextView tv_url;

        VH(View itemView) {
            super(itemView);
            tv_time = (TextView) itemView.findViewById(R.id.item_tv_time);
            tv_desc = (TextView) itemView.findViewById(R.id.item_tv_desc);
            tv_url = (TextView) itemView.findViewById(R.id.item_tv_url);
        }
    }
}
