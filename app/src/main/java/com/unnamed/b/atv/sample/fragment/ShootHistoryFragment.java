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
import android.widget.TextView;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.sample.R;
import com.unnamed.b.atv.sample.holder.SelectableItemHolder;
import com.unnamed.b.atv.sample.holder.TreeItemHolder;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 历史任务
 * @author wxmgcs@gmail.com
 * @create 2020-04-28 下午9:34
 */
public class ShootHistoryFragment extends Fragment {
    private TextView statusBar;
    private AndroidTreeView tView;
    static SimpleDateFormat nowFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static String now() {
        Date now = new Date();
        return nowFormat.format(now);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_default, null, false);
        ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.container);

        statusBar = (TextView) rootView.findViewById(R.id.status_bar);

        TreeNode root = TreeNode.root();
        TreeNode computerRoot = new TreeNode(new TreeItemHolder.TreeItem("任务名","时间"));

        TreeNode myDocuments = new TreeNode(new TreeItemHolder.TreeItem("地点-场景-时间","时间"));
        TreeNode file1 = new TreeNode(new TreeItemHolder.TreeItem(1,"Folder 1","1"));
        TreeNode file2 = new TreeNode(new TreeItemHolder.TreeItem(2, "Folder 2",now()));
        TreeNode file3 = new TreeNode(new TreeItemHolder.TreeItem(3, "Folder 3",now()));
        TreeNode file4 = new TreeNode(new TreeItemHolder.TreeItem(4, "Folder 4",now()));
        myDocuments.addChildren(file1, file2, file3, file4);

        TreeNode myMedia = new TreeNode(new TreeItemHolder.TreeItem("地点-场景-时间",now()));
        TreeNode file11 = new TreeNode("File1").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file21 = new TreeNode("File2").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file31 = new TreeNode("File3").setViewHolder(new SelectableItemHolder(getActivity()));
        myMedia.addChildren(file11, file21, file31);


        computerRoot.addChildren(myDocuments, myMedia);

        root.addChildren(computerRoot);

        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
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

    private int counter = 0;

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            TreeItemHolder.TreeItem item = (TreeItemHolder.TreeItem) value;
            statusBar.setText("Last clicked: " + item.text);
        }
    };

    private TreeNode.TreeNodeLongClickListener nodeLongClickListener = new TreeNode.TreeNodeLongClickListener() {
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