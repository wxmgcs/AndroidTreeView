package com.unnamed.b.atv.sample.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.sample.R;

/**
 * @author wxmgcs@gmail.com
 * @create 2020-04-28 下午9:33
 */
public class TreeItemHolder extends TreeNode.BaseNodeViewHolder<TreeItemHolder.TreeItem> {
    private TextView tvValue;
    private PrintView arrowView;

    public TreeItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, TreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_node, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);


        if (node.getLevel() == 1) {
            tvValue.setText(value.text+"("+value.timestamp+")");
        }else{
            tvValue.setText(String.format("%s. \t%s\t\t%s",value.id,value.text,value.timestamp));
        }

        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);

        view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTreeView().removeNode(node);
            }
        });


        if (node.getLevel() != 1) {
            view.findViewById(R.id.arrow_icon).setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public static class TreeItem {
        public int id = 0;
        public String text;
        public String timestamp;

        public TreeItem(int id,String text,String timestamp) {
            this.id = id;
            this.text = text;
            this.timestamp = timestamp;
        }

        public TreeItem(String text,String timestamp) {
            this.text = text;
            this.timestamp = timestamp;
        }
    }
}
