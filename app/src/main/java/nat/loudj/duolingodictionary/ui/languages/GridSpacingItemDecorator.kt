package nat.loudj.duolingodictionary.ui.languages

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView


class GridSpacingItemDecorator(spacingInDp: Int, density: Float, private val spanCount: Int) :
    RecyclerView.ItemDecoration() {

    private val spacing = Math.round(spacingInDp * density)

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        val column: Int = itemPosition % spanCount
        outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
        outRect.right =
            spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
        if (itemPosition >= spanCount) {
            outRect.top = spacing // item top
        }
    }
}