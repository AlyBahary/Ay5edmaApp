package com.example.smatech.ay5edma.Adapters.SpecialAapterwihSwipe;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class RecyclerViewSwipeItem extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTouchListner listner;

    public RecyclerViewSwipeItem(int dragDirs, int swipeDirs,RecyclerItemTouchListner listner) {

        super(dragDirs, swipeDirs);
        this.listner=listner;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if(listner !=null){
            listner.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());
        }

    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View forground=((NotificationsAdapter.ViewHolder)viewHolder).forground_view;
        getDefaultUIUtil().clearView(forground);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
       /* View forground=((NotificationsAdapter.ViewHolder)viewHolder).forground_view;
        getDefaultUIUtil().onSelected(forground);
*/
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View forground=((NotificationsAdapter.ViewHolder)viewHolder).forground_view;
        getDefaultUIUtil().onDraw(c,recyclerView,forground,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View forground=((NotificationsAdapter.ViewHolder)viewHolder).forground_view;
        getDefaultUIUtil().onDrawOver(c,recyclerView,forground,dX,dY,actionState,isCurrentlyActive);    }
}
