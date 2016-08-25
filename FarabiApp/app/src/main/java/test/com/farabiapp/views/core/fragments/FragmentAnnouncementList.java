package test.com.farabiapp.views.core.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import test.com.farabiapp.R;
import test.com.farabiapp.UIHelper;
import test.com.farabiapp.api.model.AnnouncementData;
import test.com.farabiapp.views.bitmap.GlideHelper;
import test.com.farabiapp.views.core.CoreActivity;
import test.com.farabiapp.widgets.DividerItemDecoration;

/**
 * Created by sathya on 25/08/16.
 */
public class FragmentAnnouncementList extends AbstractCoreFragment {

    private ArrayList<AnnouncementData> dataList;
    private RecyclerView announcementListView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_announcement_list, container, false);

        initialize(view);

        return view;
    }

    private void initialize(View view) {

        if (mContext != null) {
            if (mContext instanceof CoreActivity) {
                ((CoreActivity) mContext).setupToolbar(false, true, true, false);
                ((CoreActivity) mContext).setToolBarTitle("Announcement List");
            }
        }

        announcementListView = (RecyclerView) view.findViewById(R.id.partner_banks);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        announcementListView.setLayoutManager(mLayoutManager);
        announcementListView.setItemAnimator(new DefaultItemAnimator());
        announcementListView.addItemDecoration(new DividerItemDecoration(mContext));

        if (dataList != null) {
            announcementListView.setAdapter(new AnnouncementListAdapter(dataList));
        }

    }

    public void setDataList(ArrayList<AnnouncementData> dataList) {
        if (dataList != null) {
            this.dataList = dataList;

            if (announcementListView != null) {
                announcementListView.setAdapter(new AnnouncementListAdapter(dataList));
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onDetach() {

        mContext = null;
        super.onDetach();
    }

    public class AnnouncementListAdapter extends RecyclerView.Adapter<AnnouncementListAdapter.MyViewHolder> {

        private List<AnnouncementData> announcementData;

        public AnnouncementListAdapter(List<AnnouncementData> announcementData) {
            this.announcementData = announcementData;
        }

        @Override
        public AnnouncementListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_list_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(AnnouncementListAdapter.MyViewHolder holder, int position) {
            final AnnouncementData announcementData = this.announcementData.get(position);
            String title = announcementData.getAnnouncementTitleModel().getTitle().toString();
            String imageUrl = announcementData.getAnnouncementImageModel().getImageUrl().toString();

            if (!TextUtils.isEmpty(title)) {
                holder.announcementTitle.setText(title);
            } else {
                holder.announcementTitle.setText("");
            }

            if (!TextUtils.isEmpty(imageUrl)) {
                GlideHelper.urlToImageView(getActivity(), holder.announcementImage, imageUrl, false,
                        mContext.getResources().getDrawable(R.mipmap.ic_launcher));
            } else {
                holder.announcementImage.setImageResource(R.mipmap.ic_launcher);
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String htmlContent = announcementData.getAnnouncementHtmlModel().getHtml().toString();
                    if (TextUtils.isEmpty(htmlContent.toString().trim())) {
                        UIHelper.showInfoDialog(mContext, "Sorry, no Details found!", "Oops!");
                    } else {
                        flowImpl.onAnnouncementSelected(htmlContent);
                    }
                }
            });

        }



        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView announcementTitle;
            public ImageView announcementImage;

            public View mView;

            public MyViewHolder(View view) {
                super(view);
                mView = view;
                announcementTitle = (TextView) view.findViewById(R.id.announcement_text);
                announcementImage = (ImageView) view.findViewById(R.id.announcement_image);
            }
        }
    }

    private String replaceUrlPrefix(String imageUrl) {
        imageUrl = imageUrl.replaceFirst("HTTP://", "http://");
        return imageUrl;
    }

}
