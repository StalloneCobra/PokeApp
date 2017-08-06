package me.gunna.exemploteste.androidapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.chart.model.BarSet;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.ChartView;

import me.gunna.exemploteste.androidapp.R;
import me.gunna.exemploteste.androidapp.databinding.FragmentChartBinding;
import me.gunna.exemploteste.androidapp.viewmodel.ChartScreenViewModel;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Daniel on 18/07/17.
 */

public class ChartFragment extends BaseFragment<FragmentChartBinding> {

    private ChartScreenViewModel mViewModel;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();


    public static ChartFragment newInstance() {
        Bundle args = new Bundle();
        ChartFragment fragment = new ChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.title_chart));

        if (mViewModel == null)
            mViewModel = new ChartScreenViewModel();

        mViewBinding = FragmentChartBinding.inflate(inflater, container, false);

        mViewBinding.setViewModel(mViewModel);

        return mViewBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getChartData();
        if (!mSubscriptions.hasSubscriptions()) {
            addOnGetData();
            addOnError();
        }
    }

    private void addOnError() {
        mSubscriptions.add(mViewModel
                .getOnErrorObservable()
                .subscribe(this::onError)
        );
    }

    private void onError(Throwable error) {
        Snackbar.make(
                mViewBinding.getRoot(),
                error.getMessage(),
                Snackbar.LENGTH_LONG
        ).show();
        error.printStackTrace();
    }

    private void addOnGetData() {
        mSubscriptions.add(mViewModel
                .getOnGetDataSetObservable()
                .subscribe(this::onGetDataSet)
        );
    }

    private void onGetDataSet(BarSet set) {

        if (mViewBinding.myChart.getData() == null) {
            mViewBinding.myChart.addData(set);
            configChart(mViewBinding.myChart);
        }else{
            mViewBinding.myChart.reset();
            mViewBinding.myChart.addData(set);
        }
        mViewBinding.myChart.show();
    }

    private void configChart(ChartView chart) {
        chart.setYLabels(AxisRenderer.LabelPosition.INSIDE);
        chart.setAxisLabelsSpacing(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.destroy();
    }
}
