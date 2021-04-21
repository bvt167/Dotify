package edu.uw.dotify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.ericchee.songdataprovider.Song
import edu.uw.dotify.databinding.ItemSongBinding

class SongListAdapter(private var songList: List<Song>): RecyclerView.Adapter<SongListAdapter.SongListViewHolder>() {

    var onSongClickListener: (song: Song) -> Unit = { }
    var onSongLongClickListener: (position: Int, song: Song) -> Unit = {position, song ->}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context))
        return SongListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongListAdapter.SongListViewHolder, position: Int) {
        val song: Song = songList[position]
        with(holder.binding) {
            ivSongPic.setImageResource(song.smallImageID)
            tvSongTitle.text = song.title
            tvSongArtistName.text = song.artist

            root.setOnClickListener {
                onSongClickListener(song)
            }

            root.setOnLongClickListener {
                onSongLongClickListener(position, song)
                true
            }
        }
    }

    override fun getItemCount(): Int = songList.size

    fun updateSongList(newSongList: List<Song>) {
        val callback = SongDiffCallback(newSongList, songList)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)
        this.songList = newSongList
    }

    class SongListViewHolder(val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root)

}