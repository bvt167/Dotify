package edu.uw.dotify.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import coil.load
import edu.uw.dotify.databinding.ItemSongBinding
import edu.uw.dotify.model.Song

class SongListAdapter(private var songList: List<Song>): RecyclerView.Adapter<SongListAdapter.SongListViewHolder>() {

    var onSongClickListener: (song: Song) -> Unit = { }
    var onSongLongClickListener: (song: Song) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context))
        return SongListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
        val song: Song = songList[position]
        with(holder.binding) {
            ivSongPic.load(song.smallImageURL)
            tvSongTitle.text = song.title
            tvSongArtistName.text = song.artist

            root.setOnClickListener {
                onSongClickListener(song)
            }

            root.setOnLongClickListener {
                onSongLongClickListener(song)
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

//    non animated way to update song list
//    fun updateSongList(newSongList: List<Song>) {
//        this.songList = newSongList
//        notifyDataSetChanged()
//    }

    class SongListViewHolder(val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root)

}