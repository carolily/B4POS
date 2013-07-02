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
	
	//��ʾ���������HashMap��ArrayList����.
			// HashMap���±����ַ������洢������Object,��ʾ���������
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
	//ͨ��������ʹ�ò����ļ���������
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container , Bundle save)
    {
    	//���ݲ����ļ�����Fragment����
    	View view  = inflater.inflate(R.layout.fragment_menu,container,false);
    	
    	createGridData();
		
		/* ���������벼�ֹ�ϵ 
		 *  �ڶ������������ݣ��������Ƕ�Ӧ�Ĳ����ļ�
		 * */
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), gridData, R.layout.menu_item, 
				/* ��ָ����һ����������һ������id��Ӧ*/
				new String[]{"image"},new int[]{R.id.foodImage});
		
		
		gridView = (GridView)view.findViewById(R.id.gridView1);
		
		//�Ѵ��������������õ�ListView�ؼ���.
		gridView.setAdapter(adapter);
		
		//���ӶԵ�����Ƶļ���
		//gridView.setOnItemClickListener(this);
		
    	return view;
    }
}