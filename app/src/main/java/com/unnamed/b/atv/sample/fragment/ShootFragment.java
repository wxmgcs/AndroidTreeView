package com.unnamed.b.atv.sample.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.sample.R;
import com.unnamed.b.atv.sample.holder.TreeItemHolder;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wxmgcs@gmail.com
 * @create 2020-04-28 下午9:33
 */
public class ShootFragment extends Fragment {
    private AndroidTreeView tView;
    static SimpleDateFormat nowFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static String now() {
        Date now = new Date();
        return nowFormat.format(now);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_shoot, null, false);
        ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.container);

        TreeNode root = TreeNode.root();
        TreeNode scene1Node = new TreeNode(new TreeItemHolder.TreeItem("公共区域-会议室",now()));

        TreeNode image1 = new TreeNode(new TreeItemHolder.TreeItem(1,"DSC_0000.NEF",now()));
        TreeNode image2 = new TreeNode(new TreeItemHolder.TreeItem(2,"DSC_0001.NEF",now()));
        TreeNode image3 = new TreeNode(new TreeItemHolder.TreeItem(3,"DSC_0002.NEF",now()));
        scene1Node.addChildren(image1);
        scene1Node.addChildren(image2);
        scene1Node.addChildren(image3);

        TreeNode scene2Node = new TreeNode(new TreeItemHolder.TreeItem("公共区域-会议室2",now()));

        TreeNode photo1 = new TreeNode(new TreeItemHolder.TreeItem(1,"DSC_0004.NEF",now()));
        TreeNode photo2 = new TreeNode(new TreeItemHolder.TreeItem(2,"DSC_0005.NEF",now()));
        TreeNode photo3 = new TreeNode(new TreeItemHolder.TreeItem(3,"DSC_0006.NEF",now()));

        scene2Node.addChildren(photo1, photo2, photo3);

        root.addChildren(scene1Node);
        root.addChildren(scene2Node);

        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(false);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(TreeItemHolder.class);
        tView.setDefaultNodeClickListener(nodeClickListener);
        tView.setDefaultNodeLongClickListener(nodeLongClickListener);

        containerView.addView(tView.getView());

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }

        return rootView;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.expandAll:
                tView.expandAll();
                break;

            case R.id.collapseAll:
                tView.collapseAll();
                break;
        }
        return true;
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            TreeItemHolder.TreeItem item = (TreeItemHolder.TreeItem) value;
            if(item.id != 0){
                Toast.makeText(getActivity(),"点击了:"+item.text,Toast.LENGTH_SHORT).show();
            }

        }
    };

    private TreeNode.TreeNodeLongClickListener nodeLongClickListener =
            new TreeNode.TreeNodeLongClickListener() {
                @Override
                public boolean onLongClick(TreeNode node, Object value) {
                    TreeItemHolder.TreeItem item = (TreeItemHolder.TreeItem) value;
                    Toast.makeText(getActivity(), "Long click: " + item.text, Toast.LENGTH_SHORT).show();
                    return true;
                }
            };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }
}
