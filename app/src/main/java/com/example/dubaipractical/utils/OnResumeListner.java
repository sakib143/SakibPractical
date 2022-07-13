package com.example.dubaipractical.utils;

public class OnResumeListner {
    public interface onScreenCloseListner {
        void stateChanged();
    }

    private static OnResumeListner mInstance;
    private onScreenCloseListner mListener;
    private boolean mState;

    private OnResumeListner() {}

    public static OnResumeListner getInstance() {
        if(mInstance == null) {
            mInstance = new OnResumeListner();
        }
        return mInstance;
    }

    public void setListener(onScreenCloseListner listener) {
        mListener = listener;
    }

    public void changeState(boolean state) {
        if(mListener != null) {
            mState = state;
            notifyStateChange();
        }
    }

    public boolean getState() {
        return mState;
    }

    private void notifyStateChange() {
        mListener.stateChanged();
    }

}
