package cn.bluedrum.b4pos;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MenuFragment extends Fragment {
	
	//表示结点数据是HashMap的ArrayList链表.
			// HashMap的下标是字符串，存储类型是Object,表示是任意对象
			private ArrayList<HashMap<String,Object>> gridData  = null;
			
			
			private GridView gridView = null;
			
			public static void showMessage(Context context, String text)
			{
				Toast.makeText(context, text, Toast.LENGTH_LONG).show();
			
			}
			
			public void createGridData()
			{
				gridData = new ArrayList<HashMap<String,Object>>();
				
				for(int i=0 ; i < 9 ;i++)
				{
					HashMap<String,Object> node = new HashMap<String,Object>();				
					node.put("image", R.drawable.food1+i);
					gridData.add(node);
				}
				
				
					
				
			}
	//通过本方法使用布局文件创建界面
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container , Bundle save)
    {
    	//根据布局文件创建Fragment布局
    	View view  = inflater.inflate(R.layout.fragment_menu,container,false);
    	
    	createGridData();
		
		/* 建立数据与布局关系 
		 *  第二个参数是数据，第三个是对应的布局文件
		 * */
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), gridData, R.layout.menu_item, 
				/* 简单指明哪一个内容与哪一个界面id对应*/
				new String[]{"image"},new int[]{R.id.foodImage});
		
		
		gridView = (GridView)view.findViewById(R.id.gridView1);
		
		//把创建的适配器设置到ListView控件上.
		gridView.setAdapter(adapter);
		
		//增加对点击控制的监听
		//gridView.setOnItemClickListener(this);
		
    	return view;
    }
}