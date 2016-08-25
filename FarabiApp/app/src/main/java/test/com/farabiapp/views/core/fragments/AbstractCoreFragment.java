package test.com.farabiapp.views.core.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import test.com.farabiapp.views.core.ICoreFlow;

/**
 * Created by sathya on 25/08/16.
 */
public abstract class AbstractCoreFragment extends Fragment {

    protected ICoreFlow flowImpl;

    public void setFlowImpl(ICoreFlow flowImpl) {
        this.flowImpl = flowImpl;
    }

    public ICoreFlow getFlowImpl() {
        return flowImpl;
    }

    @Override
    public void onAttach(Context context) {

        if (context instanceof ICoreFlow && flowImpl == null) {
            flowImpl = (ICoreFlow) context;
        }
        super.onAttach(context);
    }
}
