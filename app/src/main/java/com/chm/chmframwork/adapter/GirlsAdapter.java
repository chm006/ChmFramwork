package com.chm.chmframwork.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chm.chmframwork.R;
import com.chm.chmframwork.listener.OnItemClickListener;
import com.chm.chmframwork.bean.GirlsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * GirlsFragment
 * Created by chenmin on 2017/6/15.
 */
public class GirlsAdapter extends RecyclerView.Adapter<GirlsAdapter.VH> {
    private List<GirlsBean.ResultsEntity> mItems = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;

    public GirlsAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        View view = mInflater.inflate(R.layout.item_girls, parent, false);
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
    public void onBindViewHolder(final VH holder, int position) {
        GirlsBean.ResultsEntity item = mItems.get(position);
        // 把每个图片视图设置不同的Transition名称, 防止在一个视图内有多个相同的名称, 在变换的时候造成混乱
        // Fragment支持多个View进行变换, 使用适配器时, 需要加以区分
        ViewCompat.setTransitionName(holder.img, String.valueOf(position) + "_image");

        Glide.with(mContext)
                .load(item.getUrl())
                //.asBitmap()
                //.placeholder(R.mipmap.default_bg)
                //.error(R.mipmap.default_bg)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        TransitionDrawable td = new TransitionDrawable(new Drawable[]{new ColorDrawable(0xFFC7EDCC), resource});
                        td.setCrossFadeEnabled(true);
                        holder.img.setImageDrawable(td);
                        td.startTransition(500);
                    }
                });

        //holder.itemView.setTag(position);
    }

    public void setDatas(List<GirlsBean.ResultsEntity> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public GirlsBean.ResultsEntity getItem(int position) {
        return mItems.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView img;

        VH(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.item_iv_girls);
        }
    }
}
