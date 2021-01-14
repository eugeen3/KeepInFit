package eugeen3.keepinfit.itemtouch

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    fun onItemDismiss(position: Int)
}