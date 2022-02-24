package club.aborigen.general

import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContextDialog(val resId: Int, val anchor: View, val listener: (item: Int)->Unit) : DialogFragment()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.ContextDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.dialog_context_menu, container)
        val ar = Rect()
        val gr = Rect()
        val ls = IntArray(2)
        anchor.getLocationOnScreen(ls)
        anchor.getLocalVisibleRect(ar)
        requireActivity().window.decorView.getWindowVisibleDisplayFrame(gr)
        val wlp = dialog?.window?.attributes
        wlp?.gravity = Gravity.TOP or Gravity.END
        wlp?.x = resources.displayMetrics.widthPixels - ls[0] - ar.width()/2
        wlp?.y = ls[1] + ar.height() - gr.top
        dialog?.window?.attributes = wlp

        val list = root.findViewById<RecyclerView>(R.id.context_menu_list)
        val mi = MenuInflater(context)
        val pm = PopupMenu(context, null)
        mi.inflate(resId, pm.menu)
        list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        list.adapter = ContextMenuAdapter(pm.menu)
        return root
    }

    private inner class ViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.view_context_menu_item, parent, false)) {

        val icon = itemView.findViewById<ImageView>(R.id.context_menu_item_icon)
        val title = itemView.findViewById<TextView>(R.id.context_menu_item_text)
    }

    private inner class ContextMenuAdapter(val menu: Menu) : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.icon.setImageDrawable(menu.getItem(position).icon)
            holder.title.text = menu.getItem(position).title
            holder.itemView.setOnClickListener{
                listener(menu.getItem(position).itemId)
                dismiss()
            }
        }

        override fun getItemCount(): Int {
            return menu.size()
        }
    }
}
