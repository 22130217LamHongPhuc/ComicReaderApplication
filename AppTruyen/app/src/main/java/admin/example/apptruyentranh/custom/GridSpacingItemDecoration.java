package admin.example.apptruyentranh.custom;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // vị trí của item
        int column = position % spanCount; // cột của item

        if (includeEdge) {
            outRect.right = (column + 1) * spacing / spanCount;

          // khoảng cách dưới của item
        } else {

            outRect.right = spacing - (column + 1) * spacing / spanCount;

        }
    }
}