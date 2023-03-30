package com.android.cloud.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.cloud.R;

public class MusicFragment extends Fragment {
    public String[] mName = {"苏溪——红尘悠悠", "蔡健雅——红色高跟鞋", "Taylor Swift——Love Story"};
    public static int[] sIcons = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_list, null);
        ListView listView = view.findViewById(R.id.lv);
        MusicListAdapter adapter = new MusicListAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //创建Intent对象，参数就是从frag1跳转到MusicActivity
                Intent intent = new Intent(MusicFragment.this.getContext(), MusicActivity.class);
                //将歌曲名和歌曲的下标存入Intent对象
                intent.putExtra("name", mName[position]);
                intent.putExtra("position", String.valueOf(position));
                startActivity(intent);
            }
        });
        return view;
    }

    class MusicListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mName.length;
        }

        @Override
        public Object getItem(int position) {
            return mName[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MusicFragment.this.getContext(), R.layout.item_layout, null);
            TextView tvName = view.findViewById(R.id.item_name);
            ImageView imageView = view.findViewById(R.id.iv);
            tvName.setText(mName[position]);
            imageView.setImageResource(sIcons[position]);
            return view;
        }
    }
}
